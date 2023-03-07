package datagrams.classes;

import java.io.Serializable;

public class Numbers implements Serializable {
  private int number;
  private long square;
  private long cube;

  public Numbers(int number, long square, long cube){
    this.number = number;
    this.cube = cube;
    this.square = square;
  }

  public Numbers(){

  }

  public int getNumber(){
    return number;
  }

  public long getSquare(){
    return square;
  }

  public long getCube(){
    return cube;
  }

  public void setNumber(int n){
    this.number = n;
  }

  public void setSquare(long s){
    this.square = s;
  }

  public void setCube(long c){
    this.cube = c;
  }
}
