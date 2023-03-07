package threading.classes;

import java.util.ArrayList;

public class Arbiter {
  private int randomValue;
  private int players;
  private int currentTurn;
  private Boolean finished;
  private ArrayList<Integer> guesses;

  public Arbiter(int players){
    this.randomValue = 1 + (int)Math.floor(Math.random() * 10);
    this.players = players;
    this.finished = false;
    this.currentTurn = 1;
    this.guesses = new ArrayList<Integer>();
    System.out.println("[Número a adivinar]: " + randomValue);
  }

  public Boolean GameHasFinished(){
    return this.finished;
  }

  public Boolean PlayerCanMove(int uniqueId){
    return this.currentTurn == uniqueId;
  }

  public Boolean ContainsGuess(int n){
    return guesses.contains(n);
  }

  public synchronized void MakeGuess(int n, int playerId) {
    System.out.println("Jugador " + playerId + " prueba con: " + n);
    if (randomValue == n) {
      this.finished = true;
      System.out.println("Jugador " + playerId + " gana!");
    } else {
      guesses.add(n);
      currentTurn = currentTurn < players ? currentTurn + 1 : 1;
      System.out.println("   Le toca a Jugador" + currentTurn);
    }
  }
}
