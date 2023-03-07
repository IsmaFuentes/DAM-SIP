package threading.classes;

public class Consumer extends Thread {
  private Producer producer;
  public Consumer(Producer producer, String threadName) {
    super(threadName);
    this.producer = producer;
  }

  @Override
  public void run(){
    try{
      System.out.println("[" + getName() + "]: Result -> " + producer.getContent());
    }catch (Exception ex){
      System.out.println(ex.getMessage());
    }
  }
}
