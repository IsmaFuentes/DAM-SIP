package ftp;

import java.io.*;
import org.apache.commons.net.ftp.*;

public class SubirFichero {
  public static void main(String[] args) {
    FTPClient cliente = new FTPClient();

    String servidor = "localhost";
    String user = "dam";
    String pasw = "1234";

    try {
      System.out.println("Conectandose a " + servidor);
      cliente.connect(servidor);
      boolean login = cliente.login(user, pasw);

      cliente.setFileType(FTP.BINARY_FILE_TYPE);
      String directorio = "dam/NUEVODIREC";
      cliente.enterLocalPassiveMode();

      if (login) {
        System.out.println("Login correcto");
        if (!cliente.changeWorkingDirectory(directorio)) {
          if (cliente.makeDirectory(directorio)) {
            System.out.println("Directorio :  " + directorio + " creado ...");
            cliente.changeWorkingDirectory(directorio);
          } else {
            System.out.println("No se ha podido crear el Directorio");
            System.exit(0);
          }
        }

        System.out.println("Directorio actual: " + cliente.printWorkingDirectory());

        String archivo ="C:\\Users\\Ismael\\DAM\\Serveis i processos\\ejemplo.pdf";
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(archivo));

        if (cliente.storeFile("EJEMPLO.pdf", in)){
          System.out.println("Subido correctamente... ");
        } else{
          System.out.println("No se ha podido subir el fichero... ");
        }

        in.close();
        cliente.logout();
        cliente.disconnect();
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }



}// ..
