package Ex2;

import Ex2.classes.Profesor;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ServerThread extends Thread {
  private int id;
  private Socket socket;
  private List<Profesor> profesores;
  private DataInputStream inputStream;
  private ObjectOutputStream outputStream;
  public ServerThread(Socket socket, int id, List<Profesor> profesores){
    this.id = id;
    this.socket = socket;
    this.profesores = profesores;

    try{
      outputStream = new ObjectOutputStream(socket.getOutputStream());
      inputStream = new DataInputStream(socket.getInputStream());
    }catch (IOException ex){
      System.out.println("[SERVER THREAD] Error: " + ex.getMessage());
    }
  }

  @Override
  public void run(){
    while(true){
      try{
        String clientMessage = inputStream.readUTF();
        System.out.println(clientMessage);

        if (clientMessage.trim().equals("*")) {
          System.out.println("[SERVER] El cliente " + id + " ha finalizado.");
          System.out.println("[SERVER] Fin con " + socket.getInetAddress());
          break;
        }

        int requestID = Integer.parseInt(clientMessage);
        System.out.println("[SERVER]: Consultando id: " + requestID + " por cliente " + id);

        var data = new Profesor();
        for(var p:profesores){
          if(p.getIdprofesor() == requestID){
            data = p;
          }
        }

        outputStream.writeObject(data);
      }catch (Exception ex){
        System.out.println("    => La conexión se ha cerrado de forma inesperada " + socket.getInetAddress() + ", Puerto remoto: " + socket.getPort());
        break;
      }
    }
  }
}
