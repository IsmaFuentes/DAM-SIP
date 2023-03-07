package Ex2.classes;

import java.io.Serializable;

public class Especialidad implements Serializable {
  private int id;
  private String nombreespe;

  public Especialidad(int id, String nombreespe){
    this.id = id;
    this.nombreespe = nombreespe;
  }

  public Especialidad(){

  }

  public int getId(){
    return id;
  }

  public String getNombreespe(){
    return nombreespe;
  }

  public void setId(int id){
    this.id = id;
  }

  public void setNombreespe(String nombreespe){
    this.nombreespe = nombreespe;
  }

  public String toString(){
    return String.format("Especialidad: %s - %s", id, nombreespe);
  }
}
