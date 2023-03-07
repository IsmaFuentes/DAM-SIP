package datagrams;

import datagrams.classes.Persona;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DatagramClient {
  public static void main(String[] args){
    try{
      var clientSocket = new DatagramSocket();

      var serverAddress = InetAddress.getLocalHost();
      int port = 12345;

      var persona = new Persona("Ismael", "Fuentes Sintes", 26);
      System.out.println("[CLIENTE] - Enviando objeto: " + persona.toString());

      // Conversión del objeto a bytes
      var byteOutputStream = new ByteArrayOutputStream();
      var outputStream = new ObjectOutputStream(byteOutputStream);
      outputStream.writeObject(persona);
      outputStream.close();

      // Envío de paquetes
      var objectBytes = byteOutputStream.toByteArray();
      var packet = new DatagramPacket(objectBytes, objectBytes.length, serverAddress, port);
      clientSocket.send(packet);

      // Recibo de paquetes
      var receivedBytes = new byte[1024];
      var receiver = new DatagramPacket(receivedBytes, receivedBytes.length);

      System.out.println("[CLIENTE] - Recibiendo...");
      clientSocket.setSoTimeout(5000);
      clientSocket.receive(receiver);

      byte[] rec = receiver.getData();
      var byteInputStream = new ByteArrayInputStream(rec);
      var inputStream = new ObjectInputStream(byteInputStream);
      persona = (Persona)inputStream.readObject();

      System.out.println("[CLIENTE] - Objeto recibido: " + persona.toString());
      System.out.println("[CLIENTE] - Cerrando conexión...");

      clientSocket.close();
    }catch (Exception ex){
      ex.printStackTrace();
    }
  }
}
