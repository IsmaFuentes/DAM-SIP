package Ex1;

import Ex1.classes.Alumno;
import Ex1.classes.Curso;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

public class UDPServer {
  private static ArrayList<Alumno> alumnos = new ArrayList();

  public static void main(String[] args){
    var curso = new Curso("SIP", "Servicios y procesos");

    alumnos.add(new Alumno("A0001", "Paco", curso, 10));
    alumnos.add(new Alumno("A0002", "Pepe", curso, 6));
    alumnos.add(new Alumno("A0003", "Luis", curso, 7));
    alumnos.add(new Alumno("A0004", "Pedro", curso, 5));
    alumnos.add(new Alumno("A0005", "Eustaquio", curso, 8));


    try{
      var serverSocket = new DatagramSocket(12345);
      System.out.println("[SERVER]: Servidor en marcha...");
      while(true){
        var recBuffer = new byte[1024];
        var recPacket = new DatagramPacket(recBuffer, recBuffer.length);

        // Mensaje Cliente => Servidor
        serverSocket.receive(recPacket);
        var requestID = new String(recPacket.getData()).trim();
        System.out.println("[SERVER]: Se ha solicitado la información del alumno " + requestID);

        // Mensaje Servidor => Cliente
        var data = new Alumno();
        for(var alumno:alumnos){
          if(alumno.getIdalumno().equals(requestID)){
            data = alumno;
          }
        }

        var clientAddress = recPacket.getAddress();
        int port = recPacket.getPort();

        var byteOutputStream = new ByteArrayOutputStream();
        var outputStream = new ObjectOutputStream(byteOutputStream);
        outputStream.writeObject(data);
        outputStream.close();

        var sendBuffer = byteOutputStream.toByteArray();
        var sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress, port);
        serverSocket.send(sendPacket);
      }
    }catch (Exception ex){
      ex.printStackTrace();
    }
  }
}
