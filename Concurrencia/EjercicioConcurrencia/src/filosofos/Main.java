package filosofos;

import filosofos.classes.Cubierto;
import filosofos.classes.Filosofo;

public class Main {
  public static void main(String[] args){
    /**
     *        F1
     *    c1      c2
     * F5            F2
     *   c5        c3
     *    F4     F3
     *        c4
     */

    var cubiertos = new Cubierto[5];
    for(int i = 0; i < cubiertos.length; i++) {
      cubiertos[i] = new Cubierto();
    }

    var filosofos = new Filosofo[5];
    filosofos[0] = new Filosofo("Sócrates",cubiertos[0], cubiertos[1]);
    filosofos[1] = new Filosofo("Pitágoras", cubiertos[1], cubiertos[2]);
    filosofos[2] = new Filosofo("Platón",cubiertos[2], cubiertos[3]);
    filosofos[3] = new Filosofo("Heráclito", cubiertos[3], cubiertos[4]);
    filosofos[4] = new Filosofo("Demócrito", cubiertos[4], cubiertos[0]);

    for(var filosofo:filosofos){
      var th = new Thread(filosofo);
      th.start();
    }
  }
}
