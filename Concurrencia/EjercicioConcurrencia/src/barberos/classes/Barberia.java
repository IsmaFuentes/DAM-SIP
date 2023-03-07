package barberos.classes;

import barberos.Randomizer;
import java.util.ArrayList;

public class Barberia {
  private final int maxMiliseconds = 10000;
  private Barbero[] barberos;
  private ArrayList<Cliente> clientes;
  private int sillas;

  public Barberia(int barberos, int sillas){
    this.sillas = sillas;
    this.clientes = new ArrayList<>();
    this.barberos = new Barbero[barberos];
    for(int i = 0; i < barberos; i++){
      this.barberos[i] = new Barbero(this, "Barbero-" + i);
    }
  }

  public void EmpezarJornada(){
    try{
      for(var barbero:barberos) {
        new Thread(barbero).start();
      }

      Thread.sleep(100);

      while(true){
        new Thread(new Cliente(this)).start();
        // Tiempo de espera hasta que aparezca un nuevo cliente
        Thread.sleep(Randomizer.GetRandomInteger(maxMiliseconds));
        System.out.println("                  [HILOS EN EJECUCIÓN]: " + Thread.activeCount());
      }
    }catch (Exception ex){
      System.out.println("ERROR: " + ex.getMessage());
    }
  }

  public ArrayList<Cliente> GetClientes(){
    return this.clientes;
  }

  public int GetClientesEnEspera(){
    return this.clientes.size();
  }

  public synchronized void DespacharCliente(){
    if(clientes.size() > 0){
      clientes.get(0).AbandonarBarberia();
      clientes.remove(0);
      System.out.println("  - En espera: " + this.GetClientesEnEspera());
    }
  }

  public synchronized boolean OcuparSilla(Cliente cliente){
    if (this.clientes.size() < this.sillas){
      clientes.add(cliente);
      System.out.println("  - En espera: " + this.GetClientesEnEspera());
      return true;
    } else {
      return false;
    }
  }
}
