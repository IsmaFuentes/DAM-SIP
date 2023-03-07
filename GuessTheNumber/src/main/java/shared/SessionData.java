package shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SessionData implements Serializable {
  static final long serialVersionUID = 42L;
  private String message;
  private int attempts;
  private int clientId;
  private boolean isWinner;
  private boolean isPlaying;

  public SessionData(String message, int attempts, int clientId) {
    this.message = message;
    this.attempts = attempts;
    this.clientId = clientId;
    this.isWinner = false;
    this.isPlaying = true;
  }

  public SessionData() {
    super();
  }

  public boolean isRunning() {
    return isPlaying;
  }

  public void setIsPlaying(boolean playing) {
    this.isPlaying = playing;
  }

  public boolean isWinner() {
    return isWinner;
  }

  public void setIsWinner(boolean winner) {
    this.isWinner = winner;
  }

  public int getClientId() {
    return clientId;
  }

  public void setClientId(int id) {
    this.clientId = id;
  }

  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }
  public int getAttempts() {
    return attempts;
  }
  public void setAttempts(int attempts) {
    this.attempts = attempts;
  }
}
