package crypto;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  private static ServerSocket socket;

  public static void main(String[] args){
    try{
      socket = new ServerSocket(3005);
      System.out.println("[SERVER]: Servidor en marcha");
      while(true){
        var client = socket.accept();
        System.out.println("[SERVER]: Cliente conectado");
        var thread = new ServerThread(client);
        thread.run();
      }
    } catch (Exception ex){
      ex.printStackTrace();
    }
  }

  public static class ServerThread extends Thread {
    private Socket socket;
    private DataInputStream inputStream;
    public ServerThread(Socket clientSocket) throws IOException {
      this.socket = clientSocket;
      this.inputStream = new DataInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
      try{
        String hostname = socket.getInetAddress().getHostName();

        while(true){
          String input = inputStream.readUTF();

          if(input.equals("*")){
            this.socket.close();
            System.out.println(String.format("[SERVER]: %s cierra la conexión", hostname));
            break;
          }

          String encryptedString = input.split(";")[0]; // mensaje cifrado
          String encriptionParam = input.split(";")[1]; // clave de cifrado

          // Desencriptamos el mensaje y lo mostramos por pantalla
          String decryptedString = Caesar.Decrypt(encryptedString, encriptionParam);
          String serverMessage = String.format("[SERVER]: Mensaje encriptado de %s => %s (%s)", hostname, encryptedString, decryptedString);
          System.out.println(serverMessage);
        }
      } catch (Exception ex){
        ex.printStackTrace();
      }
    }
  }
}
