package server;

import server.classes.ServerThread;
import server.classes.SessionThread;
import java.io.IOException;
import java.net.ServerSocket;

public class Server {
  public static void main(String args[]){
    try{
      ServerSocket server = new ServerSocket(6000);
      System.out.println("[SERVER] Iniciando servidor...");
      int rand = (int) (1 + 25* Math.random());
      System.out.println("[SERVER] Numero a adivinar: " + rand);

      var session = new SessionThread(rand);
      int id = 1;
      while(true){
        var clientSocket = server.accept();
        var thread = new ServerThread(clientSocket, session, id);
        thread.start();
        id++;
      }
    }catch (IOException ex){
      ex.printStackTrace();
    }
  }
}
