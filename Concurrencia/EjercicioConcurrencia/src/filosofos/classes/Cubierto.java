package filosofos.classes;

public class Cubierto {
  private Boolean ocupado;
  public Cubierto(){
    this.ocupado = false;
  }

  public synchronized void Ocupar(Boolean val){
    this.ocupado = val;
  }

  public Boolean isOcupado() {
    return this.ocupado;
  }
}
