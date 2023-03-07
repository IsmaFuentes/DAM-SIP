package datagrams;

import datagrams.classes.Persona;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class DatagramServer {
  public static void main(String[] args){
    try{
      var serverSocket = new DatagramSocket(12345);
      System.out.println("[SERVIDOR] - Esperando paquetes...");

      var buffer = new byte[1024];
      var recData = new DatagramPacket(buffer, buffer.length);

      serverSocket.receive(recData);

      var clientAddress = recData.getAddress();
      int port = recData.getPort();

      // Decodificación del paquete
      var receivedBytes = recData.getData();
      var receiver = new DatagramPacket(receivedBytes, receivedBytes.length);
      byte[] rec = receiver.getData();
      var byteInputStream = new ByteArrayInputStream(rec);
      var inputStream = new ObjectInputStream(byteInputStream);
      var persona = (Persona)inputStream.readObject();

      System.out.println("[SERVIDOR] - Objeto recibido: " + persona.toString());

      persona.setNombre("Alejandro");
      persona.setApellidos("Fuentes Sintes");
      persona.setEdad(19);

      System.out.println("[SERVIDOR] - Enviando...");

      // Conversión del objeto a bytes
      var byteOutputStream = new ByteArrayOutputStream();
      var outputStream = new ObjectOutputStream(byteOutputStream);
      outputStream.writeObject(persona);
      outputStream.close();

      // Envío de paquetes
      var objectBytes = byteOutputStream.toByteArray();
      var packet = new DatagramPacket(objectBytes, objectBytes.length, clientAddress, port);
      serverSocket.send(packet);

      System.out.println("[SERVIDOR] - Objeto enviado: " + persona.toString());

      serverSocket.close();
    }catch (Exception ex){
      ex.printStackTrace();
    }
  }
}
