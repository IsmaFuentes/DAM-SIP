package processbuilder.examples;

public class Algos {

  public static void main(String[] args) {
    String[] data = { "0", "1", "2", "3", "4", "5" };
    System.out.println("FIFO\n");
    FIFO(data);
    System.out.println("LIFO\n");
    LIFO(data);
  }

  // First in first out
  private static void FIFO(String[] args){
    for(int i = 0; i < args.length; i++){
      System.out.println(args[i]);
    }
  }

  // Last in last out
  private static void LIFO(String[] args){
    for(int i = args.length -1; i >= 0; i--){
      System.out.println(args[i]);
    }
  }

}