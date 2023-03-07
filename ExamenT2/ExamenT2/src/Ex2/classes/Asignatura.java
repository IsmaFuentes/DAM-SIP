package Ex2.classes;

import java.io.Serializable;

public class Asignatura implements Serializable {
  private int id;
  private String nombreasig;

  public Asignatura(int id, String nombreasig){
    this.id = id;
    this.nombreasig = nombreasig;
  }

  public Asignatura(){

  }

  public String getNombreasig(){
    return nombreasig;
  }

  public int getId(){
    return id;
  }

  public void setNombreasig(String nombreasig){
    this.nombreasig = nombreasig;
  }

  public void setId(int id){
    this.id = id;
  }

  public String toString(){
    return String.format("Asignatura: %s - %s", id, nombreasig);
  }
}
