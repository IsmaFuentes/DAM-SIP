package threading.classes;

public class Player extends Thread{
  private int uniqueId;
  private Arbiter arbiter;

  public Player(int uniqueId, Arbiter arbiter){
    super();
    this.uniqueId = uniqueId;
    this.arbiter = arbiter;
  }

  @Override
  public void run(){
    try{
      while(!arbiter.GameHasFinished()){
        if (arbiter.PlayerCanMove(this.uniqueId)) {
          int guess = 1 + (int)Math.floor(Math.random() * 10);
          while(arbiter.ContainsGuess(guess)){
            guess = 1 + (int)Math.floor(Math.random() * 10);
          }
          arbiter.MakeGuess(guess, this.uniqueId);
        }
        Thread.sleep(1000);
      }
    } catch (Exception ex){
      System.out.println(ex.getMessage());
    }
  }
}
