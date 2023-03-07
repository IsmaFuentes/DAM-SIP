package datagrams.classes;

import java.io.Serializable;

public class Persona implements Serializable {

  public Persona(String nombre, String apellidos, int edad){
    this.nombre = nombre;
    this.apellidos = apellidos;
    this.edad = edad;
  }

  public Persona(){

  }

  private String nombre;
  private String apellidos;
  private int edad;

  public String getNombre(){
    return this.getNombre();
  }

  public String getApellidos(){
    return this.apellidos;
  }

  public int getEdad(){
    return this.edad;
  }

  public void setNombre(String nombre){
    this.nombre = nombre;
  }

  public void setApellidos(String apellidos){
    this.apellidos = apellidos;
  }

  public void setEdad(int edad){
    this.edad = edad;
  }

  @Override
  public String toString(){
    return this.nombre + " " + this.apellidos + " " + this.edad;
  }
}
