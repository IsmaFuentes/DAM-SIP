package ftp.ejercicios;

import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class FTPLog {
  private FTPClient client;
  public FTPLog(String username, String password) throws Exception {
    client = new FTPClient();
    client.connect("localhost");
    client.enterLocalPassiveMode();

    if(!client.login(username, password)){
      throw new Exception("Credenciales incorrectas");
    }
  }

  public void createUserLog() throws IOException {
    if(client.isConnected()){
      var files = client.listFiles();

      if(!Arrays.stream(files).anyMatch(x -> x.getName().equals("log.txt"))){
        // Si no existe el log lo creamos
        var content = "Conexiones del usuario";
        var inputStream = new ByteArrayInputStream(content.getBytes());
        client.storeFile("log.txt", inputStream);
        inputStream.close();
        files = client.listFiles();
      }


      var file = Arrays.stream(files).filter(x -> x.getName().equals("log.txt")).findFirst().orElse(null);

      BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file.getName()));
      client.retrieveFile(file.getName(), out);
      out.close();

      var fileWriter = new FileWriter(file.getName(), true);
      fileWriter.write("\nHora de conexión: " + new Date());
      fileWriter.close();

      BufferedInputStream in = new BufferedInputStream(new FileInputStream(file.getName()));
      client.storeFile(file.getName(), in);
      in.close();
    }
  }

  public void disconnect() throws IOException {
    if(client.isConnected()){
      client.disconnect();
    }
  }

  public static void main(String[] args){
    var scanner = new Scanner(System.in);
    while(true){
      try{
        System.out.println("------------------");
        System.out.print("Username: ");
        String username = scanner.nextLine();

        if(username.equals("*")) {
          System.out.println("Cerrando...");
          break;
        }

        System.out.print("Password: ");
        String password = scanner.nextLine();

        var log = new FTPLog(username, password);
        log.createUserLog();
        log.disconnect();
      } catch (Exception ex){
        System.out.println("ERROR: " + ex);
      }
    }
  }
}
