package almacenes.classes;

public class Cliente implements Runnable {
  private Almacen almacen;
  private int intentos;
  private int clientId;

  public Cliente(int clientId, Almacen almacen){
    this.almacen = almacen;
    this.intentos = 0;
    this.clientId = clientId;
  }

  @Override
  public void run(){
    try{
      while(intentos < 10) {
        if(almacen.getEstadoApertura()){
          almacen.Entrada();
          almacen.AdquirirProducto(clientId);
          break;
        }

        intentos ++;
        if(intentos == 10){
          System.out.println("  - El cliente (" + clientId + ") ha superado los intentos máximos");
        }

        Thread.sleep(1000);
      }
    }catch (Exception ex){
      System.out.println("ERROR: " + ex.getMessage());
    }
  }
}
