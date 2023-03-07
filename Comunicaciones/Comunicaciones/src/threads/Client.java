package threads;

import threads.classes.SessionData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
  public static void main(String[] args) throws IOException, ClassNotFoundException {
    var clientSocket = new Socket("localhost", 6000);

    var outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
    var inputStream = new ObjectInputStream(clientSocket.getInputStream());

    var scanner = new Scanner(System.in);
    String message = "";

    var sessionData = (SessionData)inputStream.readObject();
    int clientId = sessionData.getClientId();
    System.out.println("[CLIENTE] Se ha asignado la id: "+ clientId);
    System.out.println(sessionData.getMessage());

    if(!sessionData.isRunning()){
      message = "*";
    }

    while(sessionData.isRunning() && !message.trim().equals("*")){
      System.out.println("[CLIENTE] Intento: " + sessionData.getAttempts() + 1 + " Introduce un numero: ");
      message = scanner.nextLine();

      var data = new SessionData();

      if(!validateMessage(message)){
        continue;
      }

      data.setMessage(message);
      outputStream.reset();
      outputStream.writeObject(data);

      data = (SessionData)inputStream.readObject();
      System.out.println("[CLIENTE] " + data.getMessage());

      if(data.getAttempts() >= 5){
        System.out.println("\t<<JUEGO FINALIZADO>>");
        message = "*";
      }

      if(data.isWinner()){
        System.out.println("\t<<HAS GANADO, JUEGO FINALIZADO>>");
        message = "*";
      } else if(!data.isRunning()){
        System.out.println("\t<<JUEGO FINALIZADO, HAN ADIVINADO EL NUMERO>>");
        message = "*";
      }
    }

    outputStream.close();
    inputStream.close();
    System.out.println("Fin del proceso...");

    scanner.close();
    clientSocket.close();
  }

  private static boolean validateMessage(String message){
    boolean value = false;
    try{
      Integer.parseInt(message);
      value = true;
    }catch (Exception ex){
      value = false;
    }

    return value;
  }
}
