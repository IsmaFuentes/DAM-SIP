package sockets.threads;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerThread extends Thread {
  private Socket socket;
  private DataInputStream inpStream;

  public ServerThread(Socket socket){
    this.socket = socket;
    try{
      inpStream = new DataInputStream(socket.getInputStream());
    }catch (IOException ex){
      ex.printStackTrace();
    }
  }

  public void run(){
    while(true){
      try{
        String clientMessage = inpStream.readUTF();
        var outStream = new DataOutputStream(socket.getOutputStream());
        outStream.writeUTF(clientMessage.toUpperCase());
        if (clientMessage.trim().equals("*")) {
          System.out.println("  => Desconecta IP " + socket.getInetAddress() + ", Puerto remoto: " + socket.getPort());
          break;
        }
      }catch (Exception ex){
        System.out.println("    => La conexión se ha cerrado de forma inesperada " + socket.getInetAddress() + ", Puerto remoto: " + socket.getPort());
        break;
      }
    }
  }
}
