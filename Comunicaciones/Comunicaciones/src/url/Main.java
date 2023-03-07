package url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Main {
  public static void main(String[] args){
    sendQueryParams();
  }

  public static void sendQueryParams(){
    try{
      String urlString = "http://localhost/vernombre.php?";
      urlString += "nombre=";
      urlString += URLEncoder.encode("Ismael", StandardCharsets.UTF_8);
      urlString += "&apellidos=";
      urlString += URLEncoder.encode("Fuentes Sintes", StandardCharsets.UTF_8);
      System.out.println(urlString);

      var url = new URL(urlString);
      var con = url.openConnection();
      con.setDoOutput(true);

      // ESCRIBIR EN LA URL
//      var output = new PrintWriter(con.getOutputStream());
//      output.write("nombre=Ismael&apellidos=Fuentes Sintes");
//      output.close();

      var output = new PrintWriter(con.getOutputStream());
      output.write(url.getQuery());
      output.close();



      // LECTURA DEL RESULTADO
      var inputStream = con.getInputStream();
      var reader = new BufferedReader(new InputStreamReader(inputStream));

      String line;
      while((line = reader.readLine()) != null){
        System.out.println(line);
      }

      reader.close();
      inputStream.close();
    }catch(MalformedURLException ex){
      System.out.println(ex.getMessage());
    }catch (IOException ex) {
      System.out.println(ex.getMessage());
    }
//    }catch (URISyntaxException ex){
//      System.out.println(ex.getMessage());
//    }
  }

  public static void printUrlContent(){
    try{
      var url = new URL("https://www.iesjoanramis.org/");

      var con = url.openConnection();
      var inputStream = con.getInputStream();
      var reader = new BufferedReader(new InputStreamReader(inputStream));

      String line;
      while((line = reader.readLine()) != null){
        System.out.println(line);
      }

      reader.close();
      inputStream.close();
    }catch(MalformedURLException ex){
      System.out.println(ex.getMessage());
    }catch (IOException ex){
      System.out.println(ex.getMessage());
    }
  }

  public static void getUrlInfo(){
    try{
      var url = new URL("https://www.iesjoanramis.org/");

      System.out.println("[HOST]: " + url.getHost());
      System.out.println("[PORT]: " + url.getPort());
      System.out.println("[FILE]: " + url.getFile());
      System.out.println("[AUTH]: " + url.getAuthority());
      System.out.println("[PATH]: " + url.getPath());
      System.out.println("[PROTOCOL]: " + url.getProtocol());
    }catch (MalformedURLException ex){
      System.out.println(ex.getMessage());
    }
  }
}
