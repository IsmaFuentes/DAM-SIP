package crypto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CaesarGUI {
  public static void main(String[] args){
    var frame = new JFrame();
    frame.setBounds(0,0, 400,200);
    frame.setResizable(false);
    frame.setLayout(null);

    // Cadena de entrada
    var label1 = new JLabel("Entrada");
    label1.setBounds(10, 10, 150, 25);
    frame.add(label1);

    var inputTextField1 = new JTextField();
    inputTextField1.setBounds(10,40,300,25);
    frame.add(inputTextField1);

    // Letra
    var label2 = new JLabel("Letra");
    label2.setBounds(320, 10, 50, 25);
    frame.add(label2);

    var inputTextField2 = new JTextField();
    inputTextField2.setBounds(320,40,50,25);
    frame.add(inputTextField2);

    // Botón "Encriptar"
    var inputTextField3 = new JTextField();
    inputTextField3.setEditable(false);
    inputTextField3.setBounds(10,80,360,25);
    frame.add(inputTextField3);

    var button = new JButton("Encriptar");
    button.setBounds(10, 120, 100, 25);
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String param1 = inputTextField1.getText();
        String param2 = inputTextField2.getText();
        try {
          String encryptedString = Caesar.Encrypt(param1, param2);
          inputTextField3.setText(encryptedString);
        } catch (Exception ex){
          JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });
    frame.add(button);

    frame.setVisible(true);
  }
}
