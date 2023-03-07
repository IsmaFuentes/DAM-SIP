package classes;

import java.util.Random;

public class PincharGlobo implements Runnable {
  private String nombre;
  private AlmacenGlobos almacen;

  public PincharGlobo(int id, AlmacenGlobos almacen){
    this.nombre = "PG" + id;
    this.almacen = almacen;
  }

  @Override
  public void run(){
    while(true){
      try{
        var random = new Random();
        int ms = random.ints(1000, 10000).findFirst().getAsInt(); // máx 10 seg
        Thread.sleep(ms);
        almacen.pincharGlobo(this.nombre);
      }catch (InterruptedException ex){
        System.out.println(ex.getMessage());
      }
    }
  }
}
