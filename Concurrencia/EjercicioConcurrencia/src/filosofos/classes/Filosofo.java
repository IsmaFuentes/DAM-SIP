package filosofos.classes;

public class Filosofo implements Runnable {
  private String nombre;
  private Cubierto izq, der;

  public Filosofo(String nombre, Cubierto izq, Cubierto der){
    this.nombre = nombre;
    this.izq = izq;
    this.der = der;
    this.IsFinished = false;
  }

  private boolean IsFinished;

  @Override
  public void run() {
    try{
      while(!IsFinished){
        comer();
      }
    }catch (Exception ex){
      System.out.println(ex.getMessage());
    }
  }

  public synchronized void comer() throws Exception {
    if(!izq.isOcupado() && !der.isOcupado()){
      izq.Ocupar(true);
      der.Ocupar(true);

      System.out.println(nombre + " está comiendo.");
      Thread.sleep(5000);

      izq.Ocupar(false);
      der.Ocupar(false);
      IsFinished = true;
    }

    if(IsFinished == false){
      System.out.println(nombre + " está pensando.");
      Thread.sleep((int)Math.floor(Math.random() * 5000));
    }else{
      System.out.println(nombre + " ha terminado de comer");
    }
  }
}
