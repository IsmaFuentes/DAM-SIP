package sockets.threads;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
  public static void main(String[] args) {
    try{
      int port = 44444;
      ServerSocket server = new ServerSocket(port);
      System.out.println("[SERVER]: Servidor iniciado en puerto " + port);
      while(true){
        var client = server.accept();
        System.out.println("=> Conecta IP " + client.getInetAddress() + ", Puerto remoto: " + client.getPort());
        var thread = new ServerThread(client);
        thread.start();
      }
    }catch (IOException ex){
      ex.printStackTrace();
    }
  }
}