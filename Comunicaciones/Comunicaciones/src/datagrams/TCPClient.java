package datagrams;

import datagrams.classes.Numbers;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {
  public static void main(String[] args){
    try{
      var scanner = new Scanner(System.in);

      while(true){
        var clientSocket = new Socket("localhost", 12345);

        System.out.println("Introduce un numero mayor que 0");
        int n = scanner.nextInt();

        if(n <= 0){
          System.out.println("Cerrando conexión...");
          break;
        }

        var nums = new Numbers(n, 0, 0);

        System.out.println("[CLIENTE]: Enviando datos al servidor");
        // envío de datos al servidor
        var output = new ObjectOutputStream(clientSocket.getOutputStream());
        output.writeObject(nums);

        // respuesta del servidor
        var input = new ObjectInputStream(clientSocket.getInputStream());
        nums = (Numbers)input.readObject();

        System.out.println("[CLIENTE]: Recibiendo respuesta del servidor");
        System.out.println("N: " + nums.getNumber());
        System.out.println("N^2: " + nums.getSquare());
        System.out.println("N^3: " + nums.getCube());
        clientSocket.close();
      }
    }catch (Exception ex){
      ex.printStackTrace();
    }
  }

  public static class DatagramClient {
  }
}
