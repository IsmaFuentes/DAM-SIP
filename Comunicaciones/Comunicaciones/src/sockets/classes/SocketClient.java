package sockets.classes;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketClient implements Closeable {
  private Socket socket;

  public SocketClient(String address, int port) throws IOException {
    socket = new Socket(address, port);
  }

  public void sendMessage(double number) throws IOException {
    System.out.println("[CLIENT] - Sending message to server...");
    var outputStream = new DataOutputStream(socket.getOutputStream());
    outputStream.writeDouble(number);
  }

  public void sendMessage(String message) throws IOException{
    System.out.println("[CLIENT] - Sending message to server...");
    var outputStream = new DataOutputStream(socket.getOutputStream());
    outputStream.writeUTF(message);
  }

  public void receiveMessage() throws IOException {
    var inputStream = new DataInputStream(socket.getInputStream());
    System.out.println("[CLIENT] - Message from server: \n      " + inputStream.readUTF());
  }

  public void receiveResult() throws IOException{
    var inputStream = new DataInputStream(socket.getInputStream());
    System.out.println("[CLIENT] - Message from server: " + inputStream.readDouble());
  }

  public void getServerInfo() throws IOException{
    System.out.println("[SERVER PORT]: " + socket.getPort());
    System.out.println("[SERVER REMOTE ADDRESS]: " + socket.getRemoteSocketAddress());
    System.out.println("[SERVER LOCAL IP]: " + socket.getInetAddress());
    System.out.println("[SERVER REMOTE IP]: " + socket.getLocalAddress().getHostName());
  }

  @Override
  public void close() throws IOException{
    socket.close();
  }
}
