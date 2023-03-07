package processbuilder.examples;

import java.io.File;

public class Redirection {

  public static void main(String[] args) {
    LaunchCommandLine();
  }

  public static void LaunchCommandLine(){
    try{
      var processBuilder = new ProcessBuilder("CMD");

      // ProcessBuilder.Redirect
      // - From(File file)     => Redirección al fichero especificado
      // - To(File file)       => Redirección para escribir en el fichero especificado
      // - AppendTo(File file) => Redirección para añadir contenido en el fichero especificado
      // - Inherit:            => Misma entrada y salida que la del proceso actual

      processBuilder.redirectInput(ProcessBuilder.Redirect.from(new File("C:\\Users\\Ismael\\DAM\\Serveis i processos\\saludo.bat")));
      processBuilder.redirectOutput(ProcessBuilder.Redirect.to(new File("C:\\Users\\Ismael\\DAM\\Serveis i processos\\output.txt")));
      processBuilder.redirectError(ProcessBuilder.Redirect.to(new File("C:\\Users\\Ismael\\DAM\\Serveis i processos\\error.txt")));

      // redirección de la entrada
      // processBuilder.redirectInput(new File("C:\\Users\\Ismael\\Desktop\\DAM\\Serveis i processos\\saludo.bat"));
      // redirección de la salida
      // processBuilder.redirectOutput(new File("C:\\Users\\Ismael\\Desktop\\DAM\\Serveis i processos\\output.txt"));
      // redirección del error
      // processBuilder.redirectError(new File("C:\\Users\\Ismael\\Desktop\\DAM\\Serveis i processos\\error.txt"));

      var process = processBuilder.start();
      var statusCode = process.waitFor();

      System.out.println("Status: " + statusCode);
    }catch (java.io.IOException ex){
      System.out.println(ex.getMessage());
    }catch (InterruptedException ex){
      System.out.println("Proceso interrumpido: " + ex.getMessage());
    }
  }

}
