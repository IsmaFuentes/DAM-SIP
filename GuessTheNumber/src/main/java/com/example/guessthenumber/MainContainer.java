package com.example.guessthenumber;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import shared.SessionData;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class MainContainer implements Initializable {
  @FXML
  private Spinner<Integer> spinner;
  @FXML
  private Label attempts;
  @FXML
  private Label playerid;
  @FXML
  private Label logMessage;
  @FXML
  private CheckBox connectedCheckbox;

  // CONNECTION PARAMS
  private Socket clientSocket;
  private ObjectInputStream inputStream;
  private ObjectOutputStream outputStream;
  private SessionData sessionData;
  private String message;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    try{
      // CONEXIÓN CON EL SERVIDOR
      clientSocket = new Socket("localhost", 6000);
      inputStream = new ObjectInputStream(clientSocket.getInputStream());
      outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

      // SESSION DATA
      sessionData = (SessionData) inputStream.readObject();

      playerid.setText(sessionData.getClientId() + "");
      attempts.setText("0");
      connectedCheckbox.setSelected(true);

      if(!sessionData.isRunning()){
        message = "*";
      }

    } catch (Exception ex){
      logMessage.setText(ex.getMessage());
      playerid.setText("0");
      attempts.setText("0");
    }
  }

  @FXML
  public void exitApplication(ActionEvent event) throws Exception {
    if(clientSocket != null && !clientSocket.isClosed()){
      clientSocket.close();
    }
  }

  @FXML
  protected void onGuessPressed() {
    message = spinner.getValue().toString();

    if(sessionData.isRunning() && !message.trim().equals("*")){
      var data = new SessionData();
      if(validateMessage(message)){
        try{
          data.setMessage(message);
          outputStream.reset();
          outputStream.writeObject(data);

          data = (SessionData)inputStream.readObject();
          logMessage.setText(data.getMessage());
          attempts.setText(data.getAttempts() + "");

          if(data.getAttempts() >= 5){
            message = "*";
            logMessage.setText("JUEGO FINALIZADO");
          }

          if(data.isWinner()){
            message = "*";
            logMessage.setText("JUEGO FINALIZADO, HAS GANADO!");
          } else if(!data.isRunning()){
            logMessage.setText("JUEGO FINALIZADO, HAN ADIVINADO EL NUMERO");
            message = "*";
          }
        }catch (Exception ex){
          logMessage.setText(ex.getMessage());
        }
      }

      if(message.equals("*")){
        try{
          clientSocket.close();
          outputStream.close();
          inputStream.close();
        }catch (Exception ex){
          logMessage.setText(ex.getMessage());
        }
      }
    }else{
      logMessage.setText("EL JUEGO HA TERMINADO");
    }
  }

  private boolean validateMessage(String message){
    boolean value = false;
    try{
      Integer.parseInt(message);
      value = true;
    }catch (Exception ex){
      value = false;
    }
    return value;
  }
}