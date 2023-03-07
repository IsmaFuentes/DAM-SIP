package smtp;

import org.apache.commons.net.smtp.SMTPClient;
import org.apache.commons.net.smtp.SMTPReply;
import org.apache.commons.net.smtp.SimpleSMTPHeader;

import java.io.IOException;
import java.io.Writer;

public class ClienteSMTP2 {
  public static void main(String[] args){
    SMTPClient client = new SMTPClient();
    try {
      int response;
      client.connect("localhost");
      System.out.print(client.getReplyString());
      response = client.getReplyCode();

      if(!SMTPReply.isPositiveCompletion(response)){
        client.disconnect();
        System.out.println("SMTP server refused connection.");
        System.exit(1);
      }

      client.login();

      String from ="ifuentes16730@iesjoanramis.org";
      String[] recipients = new String[]{ "ismaelkayak@gmail.com", "ismafuentes@hotmail.es" };
      String subject = "Prueba SMTP";
      String message = "Hola. \nEnviando saludos.\nAdios.";

      //se crea la cabecera
      SimpleSMTPHeader header = new SimpleSMTPHeader(from , recipients[0], subject);
      header.addCC(recipients[1]);

      //establecer el correo de origen
      client.setSender(from);

      //añadir correos destino
      for(var rec:recipients){
        client.addRecipient(rec);
      }

      // se envia DATA
      Writer writer = client.sendMessageData();
      if(writer == null) {
        System.out.println("FALLO AL ENVIAR DATA.");
        System.exit(1);
      }

      System.out.println(header.toString());
      writer.write(header.toString());        // primero escribo cabecera
      writer.write(message);                  // luego mensaje
      writer.close();

      if(!client.completePendingCommand())  { // fallo
        System.out.println("FALLO AL FINALIZAR LA TRANSACCIÓN.");
        System.exit(1);
      }

      client.logout();
      client.disconnect();

    } catch (IOException e) {
      System.err.println("NO SE PUEDE CONECTAR AL SERVIDOR.");
      e.printStackTrace();
      System.exit(2);
    }

    System.exit(0);
  }
}
