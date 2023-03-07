package datagrams;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {
  public static void main(String[] args){
    try {
      var serverSocket = new DatagramSocket(12345);

      System.out.println("[SERVER] - Esperando paquetes...");

      while(true){
        var buffer = new byte[1024];
        var recData = new DatagramPacket(buffer, buffer.length);

        serverSocket.receive(recData);

        var message = new String(recData.getData()).trim();
        System.out.println("[SERVER] - Mensaje recibido: " + message);

        if (message.contains("*")) {
          System.out.println("Cerrando conexión...");
          break;
        }

        // envío de vuelta al cliente
        var clientAddress = recData.getAddress();
        int port = recData.getPort();

        var bytes = message.toUpperCase().getBytes();

        var sendData = new DatagramPacket(bytes, bytes.length, clientAddress, port);
        serverSocket.send(sendData);

        Thread.sleep(100);
      }

      serverSocket.close();
    } catch (IOException ex) {
      ex.printStackTrace();
    } catch (InterruptedException ex){
      ex.printStackTrace();
    }
  }
}
