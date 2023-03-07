package Ex1.classes;

import java.io.Serializable;

public class Alumno implements Serializable {
  private String idalumno;
  private String nombre;
  private Curso curso;
  private int nota;

  public Alumno(String idalumno, String nombre, Curso curso, int nota){
    this.idalumno = idalumno;
    this.nombre = nombre;
    this.curso = curso;
    this.nota = nota;
  }

  public Alumno(){
    idalumno = "";
    nombre = "";
    curso = null;
    nota = 0;
  }

  public String getIdalumno(){
    return idalumno;
  }

  public String getNombre(){
    return nombre;
  }

  public Curso getCurso(){
    return curso;
  }

  public int getNota(){
    return nota;
  }

  public void setIdalumno(String idalumno){
    this.idalumno = idalumno;
  }

  public void setNombre(String nombre){
    this.nombre = nombre;
  }

  public void setCurso(Curso curso){
    this.curso = curso;
  }

  public void setNota(int nota){
    this.nota = nota;
  }

  public String toString(){
    if(idalumno.length() > 0){
      return String.format("[%s] - %s matriculaldo en %s con nota %s", this.idalumno, this.nombre, this.curso.getId(), this.nota);
    } else {
      return "No se han encontrado datos para el alumno solicitado";
    }
  }
}
