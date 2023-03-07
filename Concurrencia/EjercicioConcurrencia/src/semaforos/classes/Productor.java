package semaforos.classes;

public class Productor extends Thread {
  private Almacen almacen;

  public Productor(String name, Almacen almacen) {
    super(name);
    this.almacen = almacen;
  }

  @Override
  public void run() {
    while(true){
      almacen.producir(this.getName());
    }
  }
}
