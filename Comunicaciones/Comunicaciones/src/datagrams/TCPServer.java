package datagrams;

import datagrams.classes.Numbers;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;

public class TCPServer {
  public static void main(String[] args){
    try{
      var serverSocket = new ServerSocket(12345);

      while (true){
        var client = serverSocket.accept();

        System.out.println("[SERVER]: Recibiendo entrada.");
        var input = new ObjectInputStream(client.getInputStream());
        var numbs = (Numbers)input.readObject();

        int n = numbs.getNumber();
        if(n <= 0){
          System.out.println("Cerrando conexión...");
          break;
        }

        numbs.setCube((long)Math.pow(n, 3));
        numbs.setSquare((long)Math.pow(n, 2));

        System.out.println("[SERVER]: Enviando resultado al cliente.");
        var output = new ObjectOutputStream(client.getOutputStream());
        output.writeObject(numbs);

        Thread.sleep(100);
      }

      serverSocket.close();
    }catch (Exception ex){
      ex.printStackTrace();
    }
  }
}
