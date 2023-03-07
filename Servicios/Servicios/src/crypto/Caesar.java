package crypto;

public class Caesar {
  private static int ASCII_START = 97; // Valor de a en ASCII
  private static int ASCII_END = 122;  // Valor de z en ASCII

  public static String Encrypt(String input, String key) throws Exception {
    if(key.length() > 1) {
      throw new Exception("El valor de la clave debe ser una única letra");
    }

    int cod = key.toLowerCase().charAt(0);
    int inc = cod - ASCII_START;

    return Encrypt(input, inc);
  }

  public static String Decrypt(String input, String key) throws Exception {
    if(key.length() > 1) {
      throw new Exception("El valor de la clave debe ser una única letra");
    }

    int cod = key.toLowerCase().charAt(0);
    int inc = cod - ASCII_START;

    return Decrypt(input, inc);
  }

  /**
   * Encriptado César
   * @param input valor a encriptar
   * @param increments clave de encriptado
   * @return valor encriptado
   */
  public static String Encrypt(String input, int increments){
    String lowercase = input.toLowerCase();
    String output = "";
    for(char c:lowercase.toCharArray()){
      if(Character.isLetter(c)){
        int value = ((int)c) + increments;
        if(value > ASCII_END) {
          value = ((value - ASCII_END) - 1) + ASCII_START;
        }
        output += (char)value;
      }else{
        output += c;
      }
    }

    return output;
  }

  /**
   * Desencriptado César
   * @param input valor a desencriptar
   * @param increments clave de encriptado
   * @return Valor desencriptado
   */
  public static String Decrypt(String input, int increments){
    String lowercase = input.toLowerCase();
    String output = "";
    for(char c:lowercase.toCharArray()){
      if(Character.isLetter(c)){
        int value = ((int)c) - increments;
        if(value < ASCII_START){
          value = ((value + ASCII_END) + 1) - ASCII_START;
        }
        output += (char)value;
      } else{
        output += c;
      }
    }

    return output;
  }
}
