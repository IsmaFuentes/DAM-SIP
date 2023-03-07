package ftp;

import java.io.*;
import org.apache.commons.net.ftp.*;


public class ClienteFTP2 {
  public static void main(String[] args) {
		FTPClient cliente = new FTPClient();
		String servFTP = "ftp.rediris.es";
		System.out.println("Nos conectamos a: " + servFTP);
		String usuario = "anonymous";
		String clave = "anonymous";

		try {
			cliente.connect(servFTP);
			cliente.enterLocalPassiveMode(); //modo pasivo

			boolean login = cliente.login(usuario, clave);
			if (login){
				System.out.println("Login correcto...");
			} else {
				System.out.println("Login Incorrecto...");
				cliente.disconnect();
				System.exit(1);
			}

			System.out.println("Directorio actual: " + cliente.printWorkingDirectory());

			FTPFile[] files = cliente.listFiles();
			System.out.println("Ficheros en el directorio actual:" + files.length);
			//array para visualizar el tipo de fichero
			String tipos[] = {"Fichero", "Directorio","Enlace simb."};

			for (int i = 0; i < files.length; i++) {
				System.out.println("\t" + files[i].getName() + " => " + tipos[files[i].getType()]);
			}

			boolean logout = cliente.logout();
			if (logout){
				System.out.println("Logout del servidor FTP...");
			} else{
				System.out.println("Error al hacer Logout...");
			}

			cliente.disconnect();
			System.out.println("Desconectado...");
		} catch (IOException ioe) {
				ioe.printStackTrace();
		}
	}

  public static class SubirFichero {
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
        String direc = "/NUEVODIREC";
        cliente.enterLocalPassiveMode();

        if (login) {
          System.out.println("Login correcto");

          if (!cliente.changeWorkingDirectory(direc)) {
            String directorio = "/dam/NUEVODIREC";

            if (cliente.makeDirectory(directorio)) {
              System.out.println("Directorio :  " +  directorio + " creado ...");
              cliente.changeWorkingDirectory(directorio);
            } else {
              System.out.println("No se ha podido crear el Directorio");
              System.exit(0);
            }
          }

          System.out.println("Directorio actual: " + cliente.printWorkingDirectory());

          String archivo = "C:\\Users\\Ismael\\DAM\\Serveis i processos\\1.Preguntes_inicials.pdf";
          BufferedInputStream in = new BufferedInputStream(new FileInputStream(archivo));

          if (cliente.storeFile("EJEMPLO.pdf", in)){
            System.out.println("Subido correctamente... ");
          }
          else{
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
  }
}
