package processbuilder.programs;

import java.util.Scanner;

public class IntroducirParametros {
  public static void main(String[] args){
    var scanner = new Scanner(System.in);
    try{
      System.out.println("Introduce una cadena: ");
      String params = scanner.nextLine();
      System.out.println(params);
      System.exit(1);
    }catch (Exception ex){
      ex.printStackTrace();
      System.exit(-1);
    }
  }
}
