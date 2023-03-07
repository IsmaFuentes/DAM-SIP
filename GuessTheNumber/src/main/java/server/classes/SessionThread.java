package server.classes;

public class SessionThread {
  private int number;
  private boolean isFinished;
  private int winner;

  public SessionThread(int number){
    this.number = number;
    this.isFinished = false;
  }

  public boolean isFinished(){
    return this.isFinished;
  }

  public int getWinner(){
    return this.winner;
  }

  public synchronized String makeGuess(int player, int guess){
    String message = "";
    if(!isFinished()){
      if(guess > number){
        message = "Numero demasiado grande";
      }
      if(guess < number){
        message = "Numero demasiado bajo";
      }
      if(guess == number){
        message = "Jugador " + player + " gana, adivinó el número: " + number;
        isFinished = true;
        winner = player;
      }
    } else {
      message = "Jugador " + winner + " adivinó el número: " + number;
    }

    return message;
  }
}
