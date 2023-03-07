package Ex1.classes;

import java.io.Serializable;

public class Curso implements Serializable {
  private String id;
  private String descripcion;

  public Curso(String id, String descripcion){
    this.id = id;
    this.descripcion = descripcion;
  }

  public Curso(){

  }

  public String getId(){
    return id;
  }

  public String getDescripcion(){
    return descripcion;
  }

  public void setId(String id){
    this.id = id;
  }

  public void setDescripcion(String descripcion){
    this.descripcion = descripcion;
  }
}
