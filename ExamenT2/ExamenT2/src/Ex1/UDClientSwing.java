package Ex1;

import Ex1.classes.Alumno;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDClientSwing extends JFrame implements ActionListener {
  private final int PORT = 12345;
  private DatagramSocket clientSocket;
  private JTextField inputText;
  private JTextArea resultArea;
  public UDClientSwing(){
    try{
      clientSocket = new DatagramSocket();
      setupLayout();
    }catch (Exception ex){
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
    inputText = new JTextField();
    inputText.setBounds(10, 30, 100,25);
    add(inputText);

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

  public void actionPerformed(ActionEvent e){
    String caption = ((JButton)e.getSource()).getText().toLowerCase();

    if(caption.equalsIgnoreCase("enviar")){
      try{
        String requestID = inputText.getText();

        // Mensaje Cliente => Servidor
        byte[] requestBytes = requestID.getBytes();
        var clientMessage = new DatagramPacket(requestBytes, requestBytes.length, InetAddress.getLocalHost(), PORT);
        clientSocket.send(clientMessage);

        // Mensaje Servidor => Cliente
        byte[] receivedBytes = new byte[1024];
        var serverMessage = new DatagramPacket(receivedBytes, receivedBytes.length);
        clientSocket.receive(serverMessage);

        byte[] inputBytes = serverMessage.getData();
        var byteInputStream = new ByteArrayInputStream(inputBytes);
        var inputStream = new ObjectInputStream(byteInputStream);

        var alumno = (Alumno)inputStream.readObject();

        if(alumno.getIdalumno().length() > 0){
          log(alumno.toString());
        }else{
          JOptionPane.showMessageDialog(this, alumno.toString());
        }

        inputText.setText("");
      }catch (Exception ex){
        JOptionPane.showMessageDialog(this, ex.getMessage());
      }
    }

    if(caption.equalsIgnoreCase("cerrar")){
      clientSocket.close();
      System.exit(0);
    }

    if(caption.equalsIgnoreCase("limpiar")){
      resultArea.setText("");
    }
  }

  public void log(String message){
    resultArea.append(String.format("%s.\r\n", message));
  }

  public static void main(String[] args){
    new UDClientSwing();
  }
}
