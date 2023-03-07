package barberos.classes;

public class Cliente implements  Runnable {
  private Boolean abandonar;
  private Boolean ocupado;
  private Barberia barberia;

  public Cliente(Barberia barberia){
    this.abandonar = false;
    this.ocupado = false;
    this.barberia = barberia;
  }

  @Override
  public void run() {
    try{
      while(!abandonar) {
        VisitarBarberia();
      }
    }catch (Exception ex){
      System.out.println("ERROR: " + ex.getMessage());
    }
  }

  public void AbandonarBarberia(){
    this.abandonar = true;
  }

  public synchronized void VisitarBarberia() throws InterruptedException{
    if(!ocupado){
      if(barberia.OcuparSilla(this)){
        // 1.) El cliente llega a la barberia y ocupa una silla
        ocupado = true;
      } else{
        // 2.) El cliente no encuentra silla y se va
        System.out.println("No hay sillas libres, el cliente se va.");
        abandonar = true;
      }
    }

    Thread.sleep(100);
  }
}
