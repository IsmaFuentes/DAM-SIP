package processbuilder.programs;

public class LeerNom {
  public static void main(String[] args){
    try{
      System.out.println("Leer nom arguments: " + args[0]);
      System.exit(1);
    }catch (Exception ex){
      System.out.println("Ha ocurrido un error: " + ex.getMessage());
      System.exit(-1);
    }
  }
}
