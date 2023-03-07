package semaforos.classes;

import java.util.concurrent.Semaphore;

public class Almacen {
  private final int MAX_LIMITE = 20;
  private int producto = 0;
  private Semaphore productor = new Semaphore(MAX_LIMITE);
  private Semaphore consumidor = new Semaphore(0);
  private Semaphore mutex = new Semaphore(1);

  public void producir(String nombre) {
    System.out.println(nombre + " intentando almacenar un producto");
    try {
      productor.acquire();
      mutex.acquire();
      producto++;
      System.out.println(nombre + " almacena un producto. " + "Almacén con " + producto + (producto > 1 ? " productos." : " producto."));
      mutex.release();

      Thread.sleep(1000);

    } catch (InterruptedException ex) {
      System.out.println(ex.getMessage());
    } finally {
      consumidor.release();
    }

  }

  public void consumir(String nombre) {
    System.out.println(nombre + " intentando retirar un producto");
    try {
      consumidor.acquire();
      mutex.acquire();

      producto--;
      System.out.println(nombre + " retira un producto. " + "Almacén con " + producto + (producto > 1 ? " productos." : " producto."));
      mutex.release();

      Thread.sleep(1000);
    } catch (InterruptedException ex) {
      System.out.println(ex.getMessage());
    } finally {
      productor.release();
    }
  }
}
