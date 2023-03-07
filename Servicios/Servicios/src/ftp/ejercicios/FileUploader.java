package ftp.ejercicios;

import org.apache.commons.net.ftp.FTPClient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Date;

public class FileUploader extends JFrame implements ActionListener {
  private FTPClient client;
  private JTextField userTextField;
  private JPasswordField passTextField;
  private JTextField connTextField;
  private JTextArea resultArea;

  public FileUploader(){
    setupLayout();
    client = new FTPClient();
  }

  private void setupLayout(){
    setBounds(0,0, 600,400);
    setResizable(false);

    // adress textfield
    connTextField = new JTextField();
    connTextField.setEditable(false);
    connTextField.setBounds(10,10,150,25);
    connTextField.setText("localhost");
    add(connTextField);

    // username textfield
    userTextField = new JTextField();
    userTextField.setEditable(true);
    userTextField.setBounds(165,10,150,25);
    userTextField.setText("dam");
    add(userTextField);

    // password textfield
    passTextField = new JPasswordField();
    passTextField.setEditable(true);
    passTextField.setBounds(320,10,150,25);
    passTextField.setText("1234");
    add(passTextField);

    // connect button
    var connectBtn = new JButton("Conectar");
    connectBtn.setBounds(475, 10, 100, 25);
    connectBtn.addActionListener(this);
    add(connectBtn);

    var uploadBtn = new JButton("Subir");
    uploadBtn.setBounds(475, 325, 100, 25);
    uploadBtn.addActionListener(this);
    add(uploadBtn);

    // textview with folder structure
    resultArea = new JTextArea();
    resultArea.setEditable(false);
    resultArea.setLineWrap(true);
    resultArea.setWrapStyleWord(true);
    var scrollPane = new JScrollPane(resultArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setBounds(10, 50, 460, 300);
    add(scrollPane);

    setLayout(null);
    setVisible(true);
  }

  public void actionPerformed(ActionEvent e){
    var caption = ((JButton)e.getSource()).getText().toLowerCase();

    if(caption.equals("conectar")){
      String user = userTextField.getText();
      String pass = passTextField.getText();
      String host = connTextField.getText();

      try{
        client.connect(host);
        client.enterLocalPassiveMode();
        if (client.login(user, pass)) {
          log("Autenticación exitosa");
        }else{
          log("Autenticación fallida");
        }
      }catch (Exception ex){
        log(ex.getMessage());
      }
    }

    if(caption.equals("subir")){
      if(!client.isConnected()) return;
      var filePicker = new JFileChooser();
      filePicker.showOpenDialog(this);
      var file = filePicker.getSelectedFile();
      if(file != null){
        try{
          var inputStream = new BufferedInputStream(new FileInputStream(file.getAbsolutePath()));
          if(client.storeFile(client.printWorkingDirectory() + file.getName(), inputStream)){
            log("El fichero se ha subido correctamente");
          }else{
            log("No ha sido posible subir el archivo");
          }
        }catch (Exception ex){
          log(ex.getMessage());
        }
      }
    }
  }

  private void log(String message){
    resultArea.append(String.format(" [%s]: %s.\r\n", new Date(), message));
  }

  public static void main(String[] args){
    new FileUploader();
  }
}
