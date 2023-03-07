package almacenes;

import almacenes.classes.Almacen;
import almacenes.classes.Cliente;

public class Main {
  public static void main(String[] args){
    int clientes = 300;
    int productos = 100;
    var almacen = new Almacen(productos);

    try{
      for(int i = 0; i < clientes; i++){
        Thread th =  new Thread(new Cliente(i, almacen));
        th.start();
      }
    }catch (Exception ex){
      System.out.println("ERROR: " + ex.getMessage());
    }
  }
}
