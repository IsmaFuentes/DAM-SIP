package Ex2.classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Profesor implements Serializable {
  private int idprofesor;
  private String nombre;
  private ArrayList<Asignatura> asignaturas = new ArrayList();
  private Especialidad espe;

  public Profesor(int idprofesor, String nombre, Especialidad espe){
    this.idprofesor = idprofesor;
    this.nombre = nombre;
    this.espe = espe;
  }

  public Profesor(){
    idprofesor = 0;
    nombre = "";
    espe = null;
  }

  public int getIdprofesor(){
    return idprofesor;
  }

  public String getNombre(){
    return nombre;
  }

  public Especialidad getEspe(){
    return espe;
  }

  public ArrayList<Asignatura> getAsignaturas() {
    return asignaturas;
  }

  public void setIdprofesor(int idprofesor) {
    this.idprofesor = idprofesor;
  }

  public void setEspe(Especialidad espe) {
    this.espe = espe;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public void addAsignatura(Asignatura a){
    asignaturas.add(a);
  }

  public String toString(){
    if(idprofesor > 0){
      return String.format("Nombre: %s, %s", nombre, espe.toString());
    }else{
      return "No se ha encontrado un profesor para la id solicitada";
    }
  }
}
