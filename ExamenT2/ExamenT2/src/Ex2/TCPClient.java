package Ex2;
import Ex2.classes.Profesor;

import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {
  public static void main(String[] args){
    try{
      var clientSocket = new Socket("localhost", 6000);
      var scanner = new Scanner(System.in);

      var outputStream = new DataOutputStream(clientSocket.getOutputStream());
      var inputStream = new ObjectInputStream(clientSocket.getInputStream());

      while(true){
        System.out.println("Introduce identificador a consultar: ");
        String requestID = scanner.next();

        // Mensaje Cliente => Servidor
        outputStream.writeUTF(requestID);

        if(requestID.equals("*")){
          System.out.println("[CLIENTE]: Cerrando conexión...");
          clientSocket.close();
          System.exit(0);
        }

        var result = (Profesor)inputStream.readObject();

        if(result.getIdprofesor() > 0){
          System.out.println(result);
          for(var a:result.getAsignaturas()){
            System.out.println("   " + a.toString());
          }
        }else{
          System.out.println(result);
        }
      }
    }catch (Exception ex){
      ex.printStackTrace();
    }
  }
}
