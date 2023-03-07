package sockets.classes;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;

public class MultiSocketServer implements Closeable {
  private ServerSocket server;
  private int maxClients;

  public MultiSocketServer(int port, int maxClients) throws IOException {
    this.server = new ServerSocket(port);
    this.maxClients = maxClients;
  }

  public void waitForConnections() throws IOException {
    for(int i = 0; i < maxClients; i++){
      var client = server.accept();
      int clientNumber = i + 1;
      System.out.println("[SERVER] - Sending message to client " + clientNumber);
      var messageStream = client.getOutputStream();
      var outputWriter = new DataOutputStream(messageStream);
      outputWriter.writeUTF("Your number is: " + clientNumber);
      outputWriter.close();
    }
  }

  @Override
  public void close() throws IOException {
    server.close();
  }
}
