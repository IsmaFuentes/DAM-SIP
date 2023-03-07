package almacenes.classes;

public class Almacen {
  private int productos;
  private boolean abierto;

  public Almacen(int productos){
    this.productos = productos;
    this.abierto = true;
  }

  public void setEstaddoApertura(boolean estado){
    this.abierto = estado;
  }

  public boolean getEstadoApertura(){
    return this.abierto;
  }

  public synchronized void Entrada() throws InterruptedException {
      setEstaddoApertura(false);
      Thread.sleep(1000);
      setEstaddoApertura(true);
  }

  public synchronized boolean AdquirirProducto(int clientId){
    if(productos > 0){
      productos--;
      System.out.println("[ALMACEN]: cliente (" + clientId + ") ha recogido un producto.");
      System.out.println("[ALMACEN]: productos restantes - " + productos);
      return  true;
    }else{
      System.out.println("[ALMACEN]: no quedan productos, el cliente (" + clientId + ") se va.");
      return false;
    }
  }
}
