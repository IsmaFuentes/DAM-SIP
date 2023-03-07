package threading;

import threading.classes.*;

public class Threads {
  public static void main(String[] args){
    Ejercicio4();
  }


  public static void Ejercicio4(){
    var th1 = new ThreadedCounter("TH1");
    th1.setPriority(Thread.MAX_PRIORITY);

    var th2 = new ThreadedCounter("TH2");
    th2.setPriority(Thread.MIN_PRIORITY);

    var th3 = new ThreadedCounter("TH3");
    th3.setPriority(Thread.NORM_PRIORITY);

    th1.start();
    th2.start();
    th3.start();
  }

  public static void Ejercicio3(int threadCount) {
    for(int i = 0; i < threadCount; i++){
      var th = new ThreadedCounter(String.format("Thread %s", i));
      th.start();
    }

    System.out.println("Active threads: " + Thread.activeCount());
  }

  public static void Ejercicio2_2(){
    var tic = new Thread(new RunnableLog("TIC"), "Thread-TIC");
    var tac = new Thread(new RunnableLog("TAC"), "Thread-TAC");

    tic.start();
    tac.start();

    try{
      Thread.sleep(10000);
      tic.interrupt();
      tac.interrupt();
    }catch (InterruptedException ex){
      System.out.println("[Error]: " + ex.getMessage());
    }
  }

  public static void Ejercicio2_1(){
    var tic = new ThreadLog("TIC");
    var tac = new ThreadLog("TAC");

    tic.start();
    tac.start();

    try{
      Thread.sleep(10000);
      tic.interrupt();
      tac.interrupt();
    }catch (InterruptedException ex){
      System.out.println("[Error]: " + ex.getMessage());
    }
  }

  public static void Ejemplo2(){
    ThreadedCounter th = null;
    for(int i = 0; i < 3; i++){
      th = new ThreadedCounter(String.format("Thread%s", i + 1));
      th.start();
    }
  }

  public static void Ejemplo1(){
    var t1 = new ThreadedCounter("Contador1");
    var t2 = new ThreadedCounter("Contador2");
    var t3 = new ThreadedCounter("Contador3");

    t1.start();
    t2.start();
    t3.start();
  }
}
