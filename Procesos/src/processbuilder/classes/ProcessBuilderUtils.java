package processbuilder.classes;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessBuilderUtils {
  public static int CompileClass(String classpath, String entrypoint){
    try{
      var processBuilder = new ProcessBuilder();
      System.out.println("Compiling " + entrypoint + "...");
      processBuilder.command("jar", "cfe", classpath + ".jar", entrypoint, classpath + ".java");
      var buildProcess = processBuilder.start();
      return buildProcess.waitFor();
    }catch (Exception ex){
      return -1;
    }
  }

  public static String GetInputString(InputStream stream){
    try{
      var content = new StringBuilder();
      var reader = new BufferedReader(new InputStreamReader(stream));

      String line;
      while((line = reader.readLine()) != null) content.append(line);
      reader.close();
      return content.toString();
    }catch (Exception ex){
      return null;
    }
  }
}
