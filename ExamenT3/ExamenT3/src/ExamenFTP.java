import org.apache.commons.net.ftp.FTPClient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

public class ExamenFTP extends JFrame implements ActionListener {
  private FTPClient client;
  private String WORKING_DIR = "isma_servidor";
  private String USER_DIRECTORY = System.getProperty("user.home") + File.separator + "isma_client";
  private JTextField hostTextField;
  private JTextField userTextField;
  private JPasswordField passTextField;
  private JTextArea resultArea;
  private JTable filesTable;

  public ExamenFTP(){
    initLayout();
    client = new FTPClient();

    // crea la carpeta del servidor si no existe
    var file = new File(USER_DIRECTORY);
    if(!file.exists()){
      try{
        log("creando carpeta cliente");
        file.mkdir();
      }catch (Exception ex){
        log(ex.getMessage());
      }
    }else{
      log("la carpeta del cliente ya estaba creada");
    }
  }

  private void initLayout(){
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
    userTextField.setText("examenftp");
    add(userTextField);

    // password textfield
    passTextField = new JPasswordField();
    passTextField.setEditable(true);
    passTextField.setBounds(320,10,150,25);
    passTextField.setText("123456");
    add(passTextField);

    // connect button
    var connectBtn = new JButton("Conectar");
    connectBtn.setBounds(475, 10, 100, 25);
    connectBtn.setBounds(475, 10, 100, 25);
    connectBtn.addActionListener(this);
    add(connectBtn);

    // download file button
    var downloadBtn = new JButton("Descarga");
    downloadBtn.setBounds(475, 45, 100, 25);
    downloadBtn.addActionListener(this);
    add(downloadBtn);

    // upload button
    var uploadBtn = new JButton("Subida");
    uploadBtn.setBounds(475, 80, 100, 25);
    uploadBtn.addActionListener(this);
    add(uploadBtn);

    // delete all button
    var deleteBtn = new JButton("Borrar");
    deleteBtn.setBounds(475, 115, 100, 25);
    deleteBtn.addActionListener(this);
    add(deleteBtn);

    // exit button
    var exitBtn = new JButton("Salir");
    exitBtn.setBounds(475, 325, 100, 25);
    exitBtn.addActionListener(this);
    add(exitBtn);

    // tabla de ficheros
    filesTable = new JTable(new DefaultTableModel(null, new String[] { "Nombre", "Ruta"}));
    var filesAreaScrollPane = new JScrollPane(filesTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    filesAreaScrollPane.setBounds(10, 50, 460, 150); // h: 300
    add(filesAreaScrollPane);

    // textview logs
    resultArea = new JTextArea();
    resultArea.setEditable(false);
    resultArea.setLineWrap(true);
    resultArea.setWrapStyleWord(true);
    var resultAreaScrollPane = new JScrollPane(resultArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    resultAreaScrollPane.setBounds(10, 200, 460, 150);
    add(resultAreaScrollPane);

    setLayout(null);
    setVisible(true);
  }

  public void actionPerformed(ActionEvent e){
    var caption = ((JButton)e.getSource()).getText().toLowerCase();

    // CONEXIÓN
    if (caption.equals("conectar")) {
      login();
    }

    // DESCARGA
    if(caption.equals("descarga")){
      dowloadFiles();
    }

    // SUBIDA
    if(caption.equals("subida")){
      uploadFiles();
    }

    // BORRADO
    if(caption.equals("borrar")){
      deleteFiles();
    }

    // SALIR
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

    if(client != null && client.isConnected()){
      updateRegisters(caption);
    }
  }

  private void login(){
    if(client.isConnected()) return;
    String user = userTextField.getText();
    String pass = passTextField.getText();
    String host = hostTextField.getText();

    try{
      client.connect(host);
      client.enterLocalPassiveMode();
      if(client.login(user, pass)){
        log("autenticación exitosa, recuperando ficheros...");
        client.changeWorkingDirectory(WORKING_DIR);
        populateTable();
      }else {
        log("autenticación fallida");
      }
    }catch (Exception ex){
      log(ex.getMessage());
    }
  }

  private void dowloadFiles(){
    if(!client.isConnected()) return;
    try{
      var files = client.listFiles();
      for(var f:files){
        if(f.isFile()){
          String downloadPath = USER_DIRECTORY + File.separator + f.getName();
          String filePath = client.printWorkingDirectory() + "/" + f.getName();
          var out = new BufferedOutputStream(new FileOutputStream(downloadPath));
          client.retrieveFile(filePath, out);
          out.close();
        }
      }
      log("descarga completada");
    }catch (Exception ex){
      log(ex.getMessage());
    }
  }

  private void deleteFiles(){
    if(!client.isConnected()) return;
    try {
      var serverFiles = client.listFiles();
      var clientFiles = new File(USER_DIRECTORY).listFiles();

      log("borrando archivos del servidor");
      for(var sf:serverFiles){
        if(sf.isFile()){
          client.deleteFile(client.printWorkingDirectory() + "/" + sf.getName());
        }
      }

      log("borrando archivos del cliente");
      for(var cf:clientFiles){
        if(cf.isFile()){
          cf.delete();
        }
      }

      // refrescamos la lista de archivos
      populateTable();
    }catch (Exception ex){
      log(ex.getMessage());
    }
  }

  private void uploadFiles(){
    if(!client.isConnected()) return;

    var filePicker = new JFileChooser();
    filePicker.showOpenDialog(this);
    var file = filePicker.getSelectedFile();

    if(file != null){
      try{
        var inputStream = new BufferedInputStream(new FileInputStream(file.getAbsolutePath()));
        if(client.storeFile(client.printWorkingDirectory() + "/" + file.getName(), inputStream)){
          log("la subida del archivo se ha completado");
          // refrescamos la lista de archivos del servidor
          populateTable();
        }else{
          log("no se ha podido subir el archivo");
        }
      }catch (Exception ex){
        log(ex.getMessage());
      }
    }
  }

  private void populateTable(){
    try{
      var files = Arrays.stream(client.listFiles()).filter(x -> x.isFile()).collect(Collectors.toList());
      var model = (DefaultTableModel)filesTable.getModel();
      model.setRowCount(0);
      for(var f:files){
        model.addRow(new Object[] { f.getName(), String.format("%s/%s", client.printWorkingDirectory(), f.getName()) });
      }
    }catch (Exception ex){
      log(ex.getMessage());
    }
  }

  private void updateRegisters(String actionPerformed){
    try{
      var files = client.listFiles("registros");

      String logPath = client.printWorkingDirectory() + "/registros/log.txt";

      // Si no existe el log lo creamos
      if(!Arrays.stream(files).anyMatch(x -> x.getName().equals("log.txt"))){
        var content = "Registros del usuario";
        var inputStream = new ByteArrayInputStream(content.getBytes());
        client.storeFile(logPath, inputStream);
        inputStream.close();
        // actualizamos la lista de ficheros
        files = client.listFiles("registros");
      }

      var file = Arrays.stream(files).filter(x -> x.getName().equals("log.txt")).findFirst().orElse(null);
      if(file != null){
        // descargamos el archivo de forma temporal, escribimos en el y subimos al servidor
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("temp.txt"));
        client.retrieveFile(logPath, out);
        out.close();

        var fileWriter = new FileWriter("temp.txt", true);
        fileWriter.write(String.format("\n%s %s %s", actionPerformed, userTextField.getText(), new Date()));
        fileWriter.close();

        BufferedInputStream in = new BufferedInputStream(new FileInputStream("temp.txt"));
        client.storeFile(logPath, in);
        in.close();

        // eliminanmos el fichero temporal
        var temp = new File("temp.txt");
        if(temp.exists()){
          temp.delete();
        }
      }
    }catch (Exception ex){
      ex.printStackTrace();
      log(ex.getMessage());
    }
  }

  private void log(String message){
    resultArea.append(String.format(" [%s] %s\r\n", new Date(), message));
  }

  public static void main(String[] args){
    new ExamenFTP();
  }
}
