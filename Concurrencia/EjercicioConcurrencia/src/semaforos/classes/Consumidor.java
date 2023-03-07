package semaforos.classes;

public class Consumidor extends Thread {
  private Almacen almacen;

  public Consumidor(String name, Almacen almacen) {
    super(name);
    this.almacen = almacen;
  }

  @Override
  public void run() {
    while(true){
      almacen.consumir(this.getName());
    }
  }
}
