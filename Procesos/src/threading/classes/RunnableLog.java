package threading.classes;

public class RunnableLog implements  Runnable {
  private String logMessage;
  public RunnableLog(String logMessage){
    this.logMessage = logMessage;
  }

  @Override
  public void run() {
    try{
      while(Thread.currentThread().isAlive()){
        System.out.println(logMessage);
        Thread.currentThread().sleep(1000);
      }
    }catch (InterruptedException ex){
      System.out.println(String.format("[Thread Interrupted Exception]: %s", ex.getMessage()));
    }
  }
}
