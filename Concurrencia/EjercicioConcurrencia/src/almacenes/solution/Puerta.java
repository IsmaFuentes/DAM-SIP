package almacenes.solution;

public class Puerta {
  boolean ocupada;

  public Puerta() {
    this.ocupada = false;
  }

  public boolean estaOcupada(){
    return this.ocupada;
  }

  public synchronized void liberarPuerta(){
    this.ocupada = false;
  }

  public synchronized boolean intentarEntrar(){
    if (this.ocupada) {
      return false;
    }else{
      this.ocupada = true;
      return true;
    }
  }
}
