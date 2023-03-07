package com.example.guessthenumber;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
public class Client extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("main-view.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 320, 240);
    scene.getStylesheets().add(Client.class.getResource("/styles.css").toExternalForm());
    stage.setTitle("Adivina el numero!");
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}