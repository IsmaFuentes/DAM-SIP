package ftp.ejercicios;

import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;

public class Main {
  public static void main(String[] args){
    try{
      ListarDirectoriosFTP("localhost", "dam", "1234");
    }catch (IOException ex){
      ex.printStackTrace();
    }
  }

  public static void ListarDirectoriosFTP(String hostname, String username, String password) throws IOException {
    var client = new FTPClient();
    System.out.println("Estableciento conexión con: " + hostname);

    client.connect(hostname);
    client.enterLocalPassiveMode();
    if(client.login(username, password)){
      System.out.println("conexión establecida");
    } else {
      System.out.println("credenciales invalidas");
      return;
    }

    System.out.println("\nDirectorios en: " + client.printWorkingDirectory());
    var directories = client.listDirectories();
    for(var dir:directories){
      if(dir.isDirectory()) System.out.println(dir.getName());
    }

    for(var directory:client.listDirectories()){
      if(client.changeWorkingDirectory(directory.getName())){
        System.out.println("\nFicheros en " + directory.getName());
        for(var file:client.listFiles()){
          if(file.isFile()) System.out.println(file.getName());
        }
      }
    }

    System.out.println("\nCerrando conexión...");
    client.logout();
    client.disconnect();
  }
}
