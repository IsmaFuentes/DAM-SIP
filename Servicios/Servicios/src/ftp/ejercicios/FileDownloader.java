package ftp.ejercicios;

import org.apache.commons.net.ftp.FTPClient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

public class FileDownloader extends JFrame implements ActionListener {
  private FTPClient client;
  private JTextField hostTextField;
  private JTextField userTextField;
  private JPasswordField passTextField;
  private JTextArea resultArea;
  private JTable filesTable;
  public FileDownloader(){
    setupLayout();
    client = new FTPClient();
  }

  private void setupLayout(){
    setBounds(0,0, 600,400);
    setResizable(false);

    // adress textfield
    hostTextField = new JTextField();
    hostTextField.setEditable(false);
    hostTextField.setBounds(10,10,150,25);
    hostTextField.setText("localhost");
    add(hostTextField);

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

    // exit button
    var downloadBtn = new JButton("Descargar");
    downloadBtn.setBounds(475, 45, 100, 25);
    downloadBtn.addActionListener(this);
    add(downloadBtn);

    // download file button
    var exitBtn = new JButton("Salir");
    exitBtn.setBounds(475, 325, 100, 25);
    exitBtn.addActionListener(this);
    add(exitBtn);

    // files table
    filesTable = new JTable(new DefaultTableModel(null, new String[] { "Nombre", "Ruta" }));
    var filesAreaScrollPane = new JScrollPane(filesTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    filesAreaScrollPane.setBounds(10, 50, 460, 150); // h: 300
    add(filesAreaScrollPane);

    // textview logs
    resultArea = new JTextArea();
    resultArea.setEditable(false);
    resultArea.setLineWrap(true);
    resultArea.setWrapStyleWord(true);
    var resultAreaScrollPane = new JScrollPane(resultArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    resultAreaScrollPane.setBounds(10, 200, 460, 150); // h: 300
    add(resultAreaScrollPane);

    setLayout(null);
    setVisible(true);
  }

  public void actionPerformed(ActionEvent e){
    var caption = ((JButton)e.getSource()).getText().toLowerCase();

    if(caption.equals("conectar")){
      if(client.isConnected()) return;
      String user = userTextField.getText();
      String pass = passTextField.getText();
      String host = hostTextField.getText();

      try{
        client.connect(host);
        client.enterLocalPassiveMode();
        if (client.login(user, pass)) {
          log("Autenticación exitosa");
          // añadimos los ficheros FTP a la lista
          populateTable();
        }else{
          log("Autenticación fallida");
        }
      }catch (Exception ex){
        log(ex.getMessage());
      }
    }

    if(caption.equals("descargar")){
      try{
        int row = filesTable.getSelectedRow();
        // ruta al fichero remtoto
        String filename = filesTable.getValueAt(row, 0).toString();
        String filepath = filesTable.getValueAt(row, 1).toString();
        // ruta a la carpeta de descargas
        String download = String.format("%s/Downloads/%s", System.getProperty("user.home"), filename);
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(download));
        client.retrieveFile(filepath, out);
        out.close();
        log("descarga completada");
      }catch (Exception ex){
        log(ex.getMessage());
      }
    }

    if(caption.equals("salir")){
      if(client.isConnected()){
        try{
          client.logout();
          client.disconnect();
        }catch (Exception ex){
          log(ex.getMessage());
        }
        System.exit(0);
      }
    }
  }

  private void populateTable(){
    try{
      log("recuperando ficheros del directorio raíz");
      var filesOnly = Arrays.stream(client.listFiles()).filter(x -> x.isFile()).collect(Collectors.toList());
      var model = (DefaultTableModel)filesTable.getModel();
      for(var file:filesOnly){
        model.addRow(new Object[]{ file.getName(), String.format("%s%s", client.printWorkingDirectory(), file.getName())});
      }
    }catch (Exception ex){
      log(ex.getMessage());
    }
  }

  private void log(String message){
    resultArea.append(String.format(" [%s]: %s.\r\n", new Date(), message));
  }

  public static void main(String[] args){
    new FileDownloader();
  }
}
