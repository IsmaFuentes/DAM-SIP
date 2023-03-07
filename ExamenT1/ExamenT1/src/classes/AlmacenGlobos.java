package classes;

import java.util.ArrayList;
import java.util.Random;

public class AlmacenGlobos {
  private ArrayList<Globo> globos;
  private int lastGlobo;
  private int aux;
  public AlmacenGlobos(int max_globos){
    this.globos = new ArrayList();
    this.aux = 0;
    for(int i = 0; i < max_globos; i++){
      this.globos.add(new Globo(i));
    }
  }

  public void decrementarAux(){
    this.aux--;
  }

  public synchronized Globo adquirirGlobo(){
    if(aux < 3 && lastGlobo < globos.size()){
      lastGlobo++;

      while(true){
        var g = globos.get(lastGlobo - 1);
        if(g.getEstado() != Globo.STATUS_EXPLOTADO && g.getEstado() != Globo.STATUS_PINCHADO && g.getEstado() != Globo.STATUS_HINCHANDO){
          return g;
        }else{
          System.out.println("No quedan globos.");
          break;
        }
      }
    }

    System.out.println("No pueden hincharse más globos...");
    return null;
  }

  public synchronized void pincharGlobo(String nombre){
    var random = new Random();
    int rand = random.ints(0, globos.size()).findFirst().getAsInt();
    var globo = globos.get(rand);
    if(globo.getEstado() == Globo.STATUS_HINCHANDO){
      globo.pincharGlobo();
      System.out.println("GLOBO " + globo.getIdGlobo() + " PINCHADO POR " + nombre);
      aux--;
    }
  }
}
