package ftp;

import java.io.*;
import org.apache.commons.net.ftp.*;
public class DescargarFichero {
  public static void main(String[] args) {
    FTPClient cliente = new FTPClient();

    String servidor = "localhost";
    String user = "dam";
    String pasw = "1234";

    try {
      System.out.println("Conectandose a " + servidor);
      cliente.connect(servidor);
      cliente.enterLocalPassiveMode();
      boolean login = cliente.login(user, pasw);

      if (login) {
        System.out.println("Login correcto");

        //descargar fichero
        String direc = "dam/NUEVODIREC";
        cliente.changeWorkingDirectory(direc);

        //stream de salida para recibir el fichero descargado
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("C:\\Users\\Ismael\\DAM\\Serveis i processos\\filezilla\\NUEVODIREC\\EJEMPLO2.pdf"));

        if(cliente.retrieveFile("EJEMPLO.pdf", out)){
          System.out.println("Recuperado correctamente... ");
        } else{
          System.out.println("No se ha podido descargar... ");
        }

        out.close();
        cliente.logout();
        cliente.disconnect();
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
}