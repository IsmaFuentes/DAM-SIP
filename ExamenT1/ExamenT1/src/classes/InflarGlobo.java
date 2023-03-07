package classes;

public class InflarGlobo implements Runnable {
  private String nombre;
  private AlmacenGlobos almacen;

  public InflarGlobo(int id, AlmacenGlobos almacen){
    this.nombre = "IG" + id;
    this.almacen = almacen;
  }

  @Override
  public void run() {
    try{
      while(true){
        var globo = almacen.adquirirGlobo();
        if(globo != null){
          System.out.println("Globo " + globo.getIdGlobo() + " Entregado a " + this.nombre);
          while(globo.incrementarVolumen()){
            Thread.sleep(1000);
          }

          almacen.decrementarAux();
          if(globo.getEstado() == Globo.STATUS_EXPLOTADO){
            System.out.println("Globo " + globo.getIdGlobo() + " EXPLOTA!");
          }
        }

        Thread.sleep(1000);
      }
    }catch (InterruptedException ex){
      System.out.println(ex.getMessage());
    }
  }
}
