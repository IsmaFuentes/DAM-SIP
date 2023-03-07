package sockets.threads;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends JFrame implements ActionListener, Runnable {
  private Socket socket;
  private DataInputStream inpStream;
  private DataOutputStream outStream;
  private boolean isExecuting;
  private JTextArea textArea;
  private JTextField inputText;

  public Client(Socket socket){
    super("Cliente");
    this.socket = socket;
    this.isExecuting = true;

    // layout
    setBounds(0, 0, 540, 400);
    setResizable(false);

    // buttons
    var button1 = new JButton("Enviar");
    button1.setBounds(420, 10, 100, 30);
    button1.addActionListener(this);
    add(button1);

    var button2 = new JButton("Salir");
    button2.setBounds(420, 50, 100, 30);
    button2.addActionListener(this);
    add(button2);

    var button3 = new JButton("Limpiar");
    button3.setBounds(420, 90, 100, 30);
    button3.addActionListener(this);
    add(button3);

    // inputs
    textArea = new JTextArea();
    textArea.setEditable(true);
    textArea.setBounds(10, 50, 400, 300);
    add(textArea);

    inputText = new JTextField();
    inputText.setBounds(10,10,400,30);
    add(inputText);

    setLayout(null);
    setVisible(true);

    // Listener para el botón de cerrar ventana
    addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent windowEvent) {
        try{
          outStream.writeUTF("*");
          isExecuting = false;
        }catch (Exception ex){
          ex.printStackTrace();
        }
      }
    });


    try{
      inpStream = new DataInputStream(socket.getInputStream());
      outStream = new DataOutputStream(socket.getOutputStream());
    }catch (IOException ex){
      ex.printStackTrace();
      System.exit(0);
    }
  }

  public void actionPerformed(ActionEvent e){
    try{
      var caption = ((JButton)e.getSource()).getText().toLowerCase();

      if(caption.equals("enviar")){
        String message = inputText.getText().trim();
        if(message.length() > 0){
          inputText.setText("");
          outStream.writeUTF(message);
        }
      }

      if(caption.equals("salir")){
        outStream.writeUTF("*");
        isExecuting = false;
      }

      if(caption.equals("limpiar")){
        textArea.setText("");
      }
    }catch (Exception ex){
      ex.printStackTrace();
    }
  }

  public void run(){
    while(isExecuting){
      try{
        String text = inpStream.readUTF();
        if(!text.trim().equals("*")){
          textArea.append(text + "\n");
        }else{
          isExecuting = false;
        }
      } catch (IOException ex){
        JOptionPane.showMessageDialog(null, "No ha sido posible conectarse con el servidor.\n" + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        isExecuting = false;
      }
    }

    try{
      socket.close();
      System.exit(0);
    }catch (IOException ex){
      ex.printStackTrace();
    }
  }

  public static void main(String[] args){
    try{
      int port = 44444;
      var socket = new Socket("localhost", port);
      new Thread(new Client(socket)).start();
    } catch (Exception ex){
      ex.printStackTrace();
    }
  }
}
