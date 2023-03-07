package threads.classes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {
  private Socket socket;
  private ObjectInputStream inputStream;
  private ObjectOutputStream outputStream;
  private SharedSession session;
  private int clientId;
  private int attempts;
  public ServerThread(Socket socket, SharedSession session, int clientId){
    this.socket = socket;
    this.session = session;
    this.clientId = clientId;

    try{
      outputStream = new ObjectOutputStream(socket.getOutputStream());
      inputStream = new ObjectInputStream(socket.getInputStream());
    }catch (IOException ex){
      System.out.println("[SERVER THREAD] Error: " + ex.getMessage());
    }
  }

  public void run(){
    System.out.println("[SERVER THREAD] Cliente conectado: " + clientId);

    var data = new SessionData("Adivina un número entre 1 y 25", attempts, clientId);

    if(session.isFinished()){
      data.setMessage("EL JUEGO HA TERMINADO, HAN ADIVINADO EL Nº");
      data.setIsPlaying(false);
    }

    try{
      outputStream.reset();
      outputStream.writeObject(data);
    }catch (IOException ex){
      System.out.println("[SERVER THREAD] Error " + ex.getMessage());
    }

    while(!session.isFinished() || data.getAttempts() < 5){
      int aux = 0;
      try{
        var outputData = (SessionData)inputStream.readObject();
        aux = Integer.parseInt(outputData.getMessage());
      } catch (Exception ex){
        System.out.println("[SERVER THREAD] Error " + ex.getMessage());
        break;
      }

      String message = session.makeGuess(clientId, aux);
      attempts++;
      data = new SessionData(message, attempts, clientId);

      if(session.isFinished()){
        data.setIsPlaying(false);
        if(clientId == session.getWinner()){
          data.setIsWinner(true);
        }
      }

      try{
        outputStream.reset();
        outputStream.writeObject(data);
      } catch (Exception ex){
        System.out.println("[SERVER THREAD] Error " + ex.getMessage());
        break;
      }
    }

    if(session.isFinished()){
      System.out.println("[SERVER THREAD] El juego ha terminado...");
      System.out.println("[SERVER THREAD] Finalizando conexión...");
    }

    try{
      outputStream.close();
      inputStream.close();
    }catch (IOException ex){
      ex.printStackTrace();
    }
  }
}
