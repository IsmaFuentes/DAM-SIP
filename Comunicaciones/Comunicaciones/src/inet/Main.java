package inet;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
  public static void main(String[] args){
    var scanner = new Scanner(System.in);

    System.out.println("Introduce una ip o hostname ");
    String name = scanner.nextLine();

    try{
      var info = InetAddress.getByName(name);
      System.out.println("[ADDRESS]: " + info.toString());
      System.out.println("[HOSTNAME]: " + info.getHostName());
      System.out.println("[HOST ADDRESS]: " + info.getHostAddress());
      System.out.println("[CANNONICAL HOSTNAME]: " + info.getCanonicalHostName());
    }catch (UnknownHostException ex){
      System.out.println("[UNKNOWN HOST]: " + ex.getMessage());
    }
  }
}
