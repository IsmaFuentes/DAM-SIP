package semaforos;

import semaforos.classes.Almacen;
import semaforos.classes.Consumidor;
import semaforos.classes.Productor;

public class Main {
  public static void main(String[] args){
    final int PRODUCTOR = 1;
    final int CONSUMIDOR = 2;

    Almacen almacen = new Almacen();

    for (int i = 0; i < PRODUCTOR; i++) {
      new Productor("Productor " + i, almacen).start();
    }

    for (int i = 0; i < CONSUMIDOR; i++) {
      new Consumidor("Consumidor " + i, almacen).start();
    }
  }
}
