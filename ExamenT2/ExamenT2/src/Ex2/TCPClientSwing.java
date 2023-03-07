package Ex2;

import Ex2.classes.Profesor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Date;

public class TCPClientSwing extends JFrame implements ActionListener {
  private Socket clientSocket;
  private DataOutputStream outputStream;
  private ObjectInputStream inputStream;
  private JSpinner numberSpinner;
  private JTextArea resultArea;

  public TCPClientSwing() {
    try {
      setupLayout();
      clientSocket = new Socket("localhost", 6000);
      outputStream = new DataOutputStream(clientSocket.getOutputStream());
      inputStream = new ObjectInputStream(clientSocket.getInputStream());
      log(String.format("[conectado]: %s", new Date()));
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(this, ex.getMessage());
    }
  }

  private void setupLayout(){
    setBounds(0,0, 600,400);
    setResizable(false);

    var label = new JLabel();
    label.setText("Introduce el identificador a consultar:");
    label.setBounds(10,0,250,25);
    add(label);

    // NUMBER INPUT
    numberSpinner = new JSpinner();
    numberSpinner.setBounds(10, 30, 100,25);
    add(numberSpinner);

    // ACTION BUTTONS
    var quitBtn = new JButton("Cerrar");
    quitBtn.setBounds(250, 30, 100, 25);
    quitBtn.addActionListener(this);
    add(quitBtn);

    var clearBtn = new JButton("Limpiar");
    clearBtn.setBounds(360, 30, 100, 25);
    clearBtn.addActionListener(this);
    add(clearBtn);


    var sendBtn = new JButton("Enviar");
    sendBtn.setBounds(470, 30, 100, 25);
    sendBtn.addActionListener(this);
    add(sendBtn);

    // RESULT AREA
    resultArea = new JTextArea();
    resultArea.setEditable(false);
    resultArea.setLineWrap(true);
    resultArea.setWrapStyleWord(true);
    var resultAreaScrollPane = new JScrollPane(resultArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    resultAreaScrollPane.setBounds(10, 70, 560, 280); // h: 300
    add(resultAreaScrollPane);

    setLayout(null);
    setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {
    String caption = ((JButton)e.getSource()).getText().toLowerCase();

    if(caption.equalsIgnoreCase("enviar")){
      int requestID = (Integer)numberSpinner.getValue();
      try{
        outputStream.writeUTF("" + requestID);
        var result = (Profesor)inputStream.readObject();

        if(result.getIdprofesor() > 0){
          // Mostramos la info del profesor recuperado por pantalla
          log(String.format("[request id]: %s\n    %s", requestID, result.toString()));
          for(var asignatura:result.getAsignaturas()){
            log("   - " + asignatura.toString());
          }
        }else{
          // Mostramos el mensaje del servidor en un diálogo nuevo
          JOptionPane.showMessageDialog(this, result.toString());
        }
      }catch (Exception ex){
        log(String.format("[error] %s", ex.getMessage()));
      }
    }

    if(caption.equalsIgnoreCase("limpiar")){
      resultArea.setText("");
    }

    if(caption.equalsIgnoreCase("cerrar")){
      try{
        clientSocket.close();
        System.exit(0);
      }catch (Exception ex){
        JOptionPane.showMessageDialog(this, ex.getMessage());
      }
    }
  }
  private void log(String message){
    resultArea.append(String.format("%s.\r\n", message));
  }

  public static void main(String[] args){
    new TCPClientSwing();
  }
}