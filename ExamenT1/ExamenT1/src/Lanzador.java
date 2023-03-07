import classes.AlmacenGlobos;
import classes.InflarGlobo;
import classes.PincharGlobo;

public class Lanzador {
  public static void main(String[] args) {
    int max_globos = 10;

    AlmacenGlobos almacenGlobos = new AlmacenGlobos(max_globos);
    int max_entidades = 2;
    for(int i = 0; i < max_entidades; i++){
      var inflador = new Thread(new InflarGlobo(i, almacenGlobos));
      inflador.start();

      var pinchador = new Thread(new PincharGlobo(i, almacenGlobos));
      pinchador.start();
    }
  }
}