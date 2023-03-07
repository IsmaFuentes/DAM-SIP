package sockets;

import sockets.classes.MultiSocketServer;
import sockets.classes.SocketClient;
import sockets.classes.SocketServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
  public static void main(String[] args){
    Actividad3_5();
  }

  private static void Actividad3_5(){
    try{
      ArrayList<SocketClient> clients = new ArrayList<>();

      int MAX_CLIENTS = 3;

      var multiClientServer = new MultiSocketServer(3000, MAX_CLIENTS);
      for(int i = 0; i < MAX_CLIENTS; i++){
        var client = new SocketClient("localhost", 3000);
        clients.add(client);
      }

      multiClientServer.waitForConnections();

      for(var client:clients){
        client.receiveMessage();
        client.close();
      }

      multiClientServer.close();

    }catch (Exception ex){
      System.out.println(ex.getMessage());
    }
  }

  private static void Actividad3_4(){
    try{
      var server = new SocketServer(3000);
      var client = new SocketClient("localhost", 3000);

      var scanner = new Scanner(System.in);
      System.out.println("Escribe un número:");
      double n = scanner.nextDouble();

      client.sendMessage(n);
      server.receiveAndResolveOperation();
      client.receiveResult();

      client.close();
      server.close();
    }catch (Exception ex){
      System.out.println(ex.getMessage());
    }
  }

  private static void Actividad3_3(){
    try{

      var server = new SocketServer(3000);
      var client = new SocketClient("localhost", 3000);

      client.sendMessage("MENSAJE DEL CLIENTE");
      server.receiveAndSendBack();
      client.receiveMessage();

      client.close();
      server.close();
    }catch (Exception ex){
      System.out.println(ex.getMessage());
    }
  }

  private static void Actividad3_2(){
    try{
      var server = new SocketServer(3000);

      var client1 = new SocketClient("localhost", 3000);
      var client2 = new SocketClient("localhost", 3000);

      server.waitForClient();
      server.waitForClient();

      System.out.println("\n[CLIENTE 1]");
      client1.getServerInfo();

      System.out.println("\n[CLIENTE 1]");
      client2.getServerInfo();

      server.close();
      client1.close();
      client2.close();
    }catch (IOException ex){
      System.out.println(ex.getMessage());
    }
  }
}
