import java.io.File;

public class Main {

  public static void main(String[] args) {
    var file = new File("test.txt");
  }

  public static void ProcessBuilderExample(){
    try{
      // lanza el proceso cmd
      var processBuilder = new ProcessBuilder();
      processBuilder.command("cmd.exe", "/c", "start");

      var process = processBuilder.start();
      var statusCode = process.waitFor();
      System.out.println("Status: " + statusCode);
    }catch (java.io.IOException ex){
      System.out.println(ex.getMessage());
    }catch (InterruptedException ex){
      System.out.println("Proceso interrumpido: " + ex.getMessage());
    }
  }

  public static void ProcessExample(){
    try{
      var runtime = Runtime.getRuntime();

      // lanza el proceso notepad.exe y solicita que abra el archivo "test.txt"
      String[] params = { "notepad.exe", "test.txt" };
      Process process = runtime.exec(params);

      int result = process.waitFor();
      System.out.println("status code: " + result);

    }catch (java.io.IOException ex){
      System.out.println(ex.getMessage());
    }catch (InterruptedException ex){
      System.out.println("Proceso interrumpido: " + ex.getMessage());
    }
  }

}