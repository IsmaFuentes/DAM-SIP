package barberos.classes;

import barberos.Randomizer;

public class Barbero implements Runnable {
  private final int maxMiliseconds = 10000;
  private final int minMiliseconds = 5000;
  private String nombre;
  private Boolean ocupado;
  private Barberia barberia;

  public Barbero(Barberia barberia, String nombre){
    this.nombre = nombre;
    this.ocupado = false;
    this.barberia = barberia;
  }

  @Override
  public void run() {
    try{
      while(true) {
        AtenderClientes();
      }
    }catch (Exception ex){
      System.out.println("[ERROR]: " + ex.getMessage());
    }
  }

  public synchronized void AtenderClientes() throws Exception {
    var clientes = barberia.GetClientes();
    if(clientes.size() > 0){
      // 1.) El barbero atiende al cliente
      System.out.println(nombre + " está atendiendo a un cliente.");
      Thread.sleep(Randomizer.GetRandomInteger(minMiliseconds, maxMiliseconds));
      // 2.) El barbero ha terminado, vaciamos una silla y el barbero se toma un descanso
      barberia.DespacharCliente();
      System.out.println(nombre + " está tomandose un descanso.");
      Thread.sleep(Randomizer.GetRandomInteger(minMiliseconds, maxMiliseconds));
    } else {
      // 3.) No hay clientes esperando, el barbero se pone a dormir
      System.out.println(nombre + " está durmiendo");
      Thread.sleep(Randomizer.GetRandomInteger(minMiliseconds, maxMiliseconds));
    }
  }
}
