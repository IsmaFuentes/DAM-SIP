package Ex1;

import Ex1.classes.Alumno;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {
  public static void main(String[] args){
    try{
      var clientSocket = new DatagramSocket();
      while(true){
        var scanner = new Scanner(System.in);
        System.out.println("[CLIENTE] Introduce la id del alumno: ");
        String requestID = scanner.nextLine();

        if(requestID.equals("*")){
          System.out.println("[CLIENTE] Cerrando el proceso...");
          break;
        }

        // Mensaje Cliente => Servidor
        var requestBytes = requestID.getBytes();
        var clientMessage = new DatagramPacket(requestBytes, requestBytes.length, InetAddress.getLocalHost(), 12345);
        clientSocket.send(clientMessage);

        // Mensaje Servidor => Cliente
        var receiveBytes = new byte[1024];
        var serverMessage = new DatagramPacket(receiveBytes, receiveBytes.length);
        clientSocket.receive(serverMessage);

        var responseBytes = serverMessage.getData();
        var byteInputStream = new ByteArrayInputStream(responseBytes);
        var inputStream = new ObjectInputStream(byteInputStream);
        var alumno = (Alumno)inputStream.readObject();

        System.out.println("[CLIENTE]: " + alumno.toString());
      }

      clientSocket.close();
    }catch (Exception ex){
      ex.printStackTrace();
    }
  }
}
