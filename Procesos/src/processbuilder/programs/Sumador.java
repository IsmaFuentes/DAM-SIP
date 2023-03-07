package processbuilder.programs;

import java.util.Scanner;

public class Sumador {
  public static void main(String[] args){
    try{
      var scanner = new Scanner(System.in);
      int num1 = scanner.nextInt();
      int num2 = scanner.nextInt();
      System.out.println(num1 + num2);
      System.exit(1);
    }catch (Exception ex){
      ex.printStackTrace();
      System.exit(-1);
    }
  }
}
