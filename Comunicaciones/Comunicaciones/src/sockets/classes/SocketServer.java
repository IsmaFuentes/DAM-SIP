package sockets.classes;

import java.io.*;
import java.net.ServerSocket;

public class SocketServer implements Closeable {
  private ServerSocket server;

  public SocketServer(int port) throws IOException {
    server = new ServerSocket(port);
  }

  public void waitForClient() throws IOException {
    var client = server.accept();
    System.out.println("\nReceiving connection...");
    System.out.println("[CLIENT LOCAL PORT]: " + client.getLocalPort());
    System.out.println("[CLIENT REMOTE PORT]: " + client.getPort());
  }

  public void receiveAndSendBack() throws IOException {
    var client = server.accept();
    var entranceStream = client.getInputStream();
    String message = new DataInputStream(entranceStream).readUTF();
    System.out.println("\n[SERVER] - Message received: " + message);

    System.out.println("\n[SERVER] - Sending message to client...");
    var messageStream = client.getOutputStream();
    var outputWriter = new DataOutputStream(messageStream);
    outputWriter.writeUTF(message.toLowerCase());

    outputWriter.close();
    messageStream.close();
    client.close();
  }

  public void receiveAndResolveOperation() throws IOException {
    var client = server.accept();
    var entranceStream = client.getInputStream();
    double message = new DataInputStream(entranceStream).readDouble();
    System.out.println("\n[SERVER] - Message received: " + message);

    System.out.println("\n[SERVER] - Sending message to client...");
    var messageStream = client.getOutputStream();
    var outputWriter = new DataOutputStream(messageStream);
    outputWriter.writeDouble(Math.pow(message, 2));

    outputWriter.close();
    messageStream.close();
    client.close();
  }


  public void sendDataToClient(String message) throws IOException {
    System.out.println("\n[SERVER] - Sending message to client...");
    var client = server.accept();

    var messageStream = client.getOutputStream();
    var outputWriter = new DataOutputStream(messageStream);
    outputWriter.writeUTF(message);

    outputWriter.close();
    messageStream.close();
    client.close();
  }

  @Override
  public void close() throws IOException {
    server.close();
  }
}
