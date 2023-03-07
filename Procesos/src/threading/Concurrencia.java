package threading;

import threading.classes.Arbiter;
import threading.classes.Consumer;
import threading.classes.Player;
import threading.classes.Producer;

public class Concurrencia {
  public static void main(String[] args){
    Ejercicio11();
  }
  public static void Ejercicio11(){
    int players = 3;
    var arbiter = new Arbiter(3);
    for(int i = 0; i < players; i++){
      int playerId = i + 1;
      var player = new Player(playerId, arbiter);
      player.start();
    }
  }

  public static void Ejercicio10(){
    var filepath = System.getProperty("user.dir") + "\\src\\threading\\files\\data.txt";

    try{
      for(int i = 0; i < 3; i++){
        var producer = new Producer(filepath);
        producer.start();

        var consumer = new Consumer(producer, "Thread" + i);
        producer.join();
        consumer.start();
        consumer.join();

        System.out.println("[" + consumer.getName() + "]: " + consumer.getState());
      }
    }catch (Exception ex){
      System.out.println(ex.getMessage());
    }
  }
}
