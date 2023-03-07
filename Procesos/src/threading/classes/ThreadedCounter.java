package threading.classes;

public class ThreadedCounter extends Thread {
  public ThreadedCounter(String name){
    super(name);
    // System.out.println(String.format("THREAD: %s | PRIORITY: %s | ID: %s", getName(), getPriority(), getId()));
  }

  @Override
  public void run(){
    try{
      for(int i = 0; i < 100; i++){
        System.out.println(String.format("Thread: %s Priority: %s C = %s", getName(), getPriority(),i));
        int rand = (int)Math.floor(Math.random() * 100);
        sleep(rand);
      }
    }catch (Exception ex){
      System.out.println(ex.getMessage());
    }
  }
}
