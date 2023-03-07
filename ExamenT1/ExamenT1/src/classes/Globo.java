package classes;

public class Globo {
  public static final String STATUS_EXPLOTADO = "explotado";
  public static final String STATUS_HINCHANDO = "hinchando";
  public static final String STATUS_DESINCHADO = "desinchado";
  public static final String STATUS_PINCHADO = "pinchado";
  private int volumen;
  private int idGlobo;
  private String estado;

  public Globo(int idGlobo){
    this.idGlobo = idGlobo;
    this.volumen = 1;
    this.estado = STATUS_DESINCHADO;
  }

  public int getIdGlobo(){
    return this.idGlobo;
  }

  public String getEstado(){
    return this.estado;
  }

  public Boolean incrementarVolumen(){
    if(volumen < 5 && this.getEstado() != STATUS_PINCHADO){
      this.estado = STATUS_HINCHANDO;
      volumen++;
      return true;
    }else if (this.getEstado() != STATUS_PINCHADO){
      this.estado = STATUS_EXPLOTADO;
      return false;
    }else{
      return false;
    }
  }

  public void pincharGlobo(){
    this.estado = STATUS_PINCHADO;
  }
}
