package datagrams;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UDPClient {
  public static void main(String[] args){
    try {
      var clientSocket = new DatagramSocket();

      var serverAddress = InetAddress.getLocalHost();
      int port = 12345;

      var scanner = new Scanner(System.in);

      while(true){
        System.out.println("Introduce el mensaje:");
        String message = scanner.nextLine();

        // envío de paquetes
        System.out.println("[CLIENTE] - Enviando...");
        var bytes = message.getBytes();
        var packet = new DatagramPacket(bytes, bytes.length, serverAddress, port);
        clientSocket.send(packet);

        if (message.contains("*")) {
          System.out.println("Cerrando conexión...");
          break;
        }

        // recibo de paquetes
        var receivedBytes = new byte[message.length()];
        var receiver = new DatagramPacket(receivedBytes, receivedBytes.length);

        System.out.println("[CLIENTE] - Recibiendo...");
        clientSocket.setSoTimeout(5000);
        clientSocket.receive(receiver);

        byte[] rec = receiver.getData();
        System.out.println("[CLIENTE] - Respuesta: " + new String(rec, StandardCharsets.UTF_8));
      }

      clientSocket.close();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
