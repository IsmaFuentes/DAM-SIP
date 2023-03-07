package threading.classes;

public class ThreadLog extends Thread {
  private String logMessage;
  private boolean showMessage;
  public ThreadLog(String logMessage){
    this.logMessage = logMessage;
  }

  public ThreadLog(String logMessage, Boolean showMessage){
    this.logMessage = logMessage;
  }


  @Override
  public void run(){
    try{
      while(isAlive()){
        if(showMessage) System.out.println(logMessage);
        sleep(1000);
      }
    }catch (InterruptedException ex){
      System.out.println(String.format("[%s] Error: %s", ex.getMessage()));
    }
  }
}
