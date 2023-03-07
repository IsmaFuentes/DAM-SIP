package barberos;

import java.util.Random;

public class Randomizer {
  private static final Random rand = new Random();

  public static int GetRandomInteger(int maxInteger){
    return rand.nextInt(maxInteger);
  }

  public static int GetRandomInteger(int min, int max){
    return rand.ints(min, max).findFirst().getAsInt();
  }
}
