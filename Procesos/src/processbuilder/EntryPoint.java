package processbuilder;

import processbuilder.classes.ProcessBuilderUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class EntryPoint {
  public static void main(String[] args){
    LaunchProgramWithParams("LeerNom");
  }

  public static void EjercicioSumador(){
    String currentDirectory = System.getProperty("user.dir");
    String programPath = Path.of(currentDirectory, "src","processbuilder","programs", "Sumador").toString();

    if(ProcessBuilderUtils.CompileClass(programPath, "Sumador") == 0) {

      try{
        var scanner = new Scanner(System.in);
        System.out.println("Introduce Num 1: ");
        String num1 = scanner.next();
        System.out.println("Introduce Num 2: ");
        String num2 = scanner.next();

        LanzarSumador(programPath, num1, num2);
      }catch (Exception ex){
        ex.printStackTrace();
      }
    }
  }

  public static void LanzarSumador(String entrypoint, String num1, String num2) {
    String classFileName = entrypoint + ".java";
    String jarFileName = entrypoint + ".jar";

    try {
      var builder = new ProcessBuilder();
      builder.command("java", "-cp", jarFileName, classFileName, "Ismael Fuentes Sintes");
      var launchProcess = builder.start();

      var outputStream = launchProcess.getOutputStream();
      outputStream.write(String.format("%s %s", num1, num2).getBytes());
      outputStream.flush();
      outputStream.close();

      String output;

      output = ProcessBuilderUtils.GetInputString(launchProcess.getInputStream());
      if (!output.isEmpty() && output != null) {
        System.out.println("El resultado de la suma es: " + output);
      }

      output = ProcessBuilderUtils.GetInputString(launchProcess.getErrorStream());
      if (!output.isEmpty()) {
        System.out.println(output);
      }

      System.out.println("Sumador finished with exit code " + launchProcess.waitFor());
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
    } catch (InterruptedException ex) {
      System.out.println(ex.getMessage());
    }
  }

  public static void LaunchProgramWithParams(String className){
    String currentDirectory = System.getProperty("user.dir");
    String programPath = Path.of(currentDirectory, "src", "processbuilder", "programs", className).toString();

    if(ProcessBuilderUtils.CompileClass(programPath, className) == 0){
      try{
        System.out.println("launching " + className + "...");
        var processBuilder = new ProcessBuilder();
        processBuilder.command("java", "-cp",  programPath + ".jar", programPath + ".java", "Ismael Fuentes Sintes");

        var launchProcess = processBuilder.start();
        var errorStream = launchProcess.getErrorStream();
        var outputStream = launchProcess.getOutputStream();

        outputStream.write("Parámetros de entrada!".getBytes());
        outputStream.flush();
        outputStream.close();

        String output = ProcessBuilderUtils.GetInputString(launchProcess.getInputStream());

        if(output != "-1"){
          System.out.println(output);
        }

        int byteCode;
        String errorMessage = "";
        while((byteCode = errorStream.read()) != -1){
          errorMessage += (char)byteCode;
        }

        if(errorMessage != "0"){
          System.out.println(errorMessage);
          System.exit(1);
        }

      }catch (Exception ex){
        System.out.println(ex.getMessage());
      }
    }
  }
}
