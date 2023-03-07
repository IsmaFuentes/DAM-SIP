module com.example.guessthenumber {
  requires javafx.controls;
  requires javafx.fxml;

  requires org.controlsfx.controls;
  requires com.dlsc.formsfx;

  opens com.example.guessthenumber to javafx.fxml;
  exports com.example.guessthenumber;
}