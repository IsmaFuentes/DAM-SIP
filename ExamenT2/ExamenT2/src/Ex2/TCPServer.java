package Ex2;

import Ex2.classes.Asignatura;
import Ex2.classes.Especialidad;
import Ex2.classes.Profesor;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class TCPServer {
  private static ArrayList<Profesor> profesores = new ArrayList();
  public static void main(String[] args){

    var profesor1 = new Profesor(1, "María Jesús", new Especialidad(1, "Informática"));
    profesor1.addAsignatura(new Asignatura(2, "AD"));
    profesor1.addAsignatura(new Asignatura(3, "PSP"));
    profesor1.addAsignatura(new Asignatura(4, "PDM"));
    profesores.add(profesor1);

    var profesor2 = new Profesor(2, "Pedro Gutiérrez", new Especialidad(1, "Programación"));
    profesor2.addAsignatura(new Asignatura(2, "AD"));
    profesor2.addAsignatura(new Asignatura(3, "PP"));
    profesor2.addAsignatura(new Asignatura(4, "MM"));
    profesores.add(profesor2);

    try{
      ServerSocket server = new ServerSocket(6000);
      System.out.println("[SERVER] Iniciando servidor...");
      int id = 1;
      while(true){
        var clientSocket = server.accept();
        System.out.println("[SERVIDOR]: Cliente " + id + " conectado");
        var thread = new ServerThread(clientSocket, id, profesores);
        thread.start();
        id++;
      }
    }catch (IOException ex){
      ex.printStackTrace();
    }
  }
}
