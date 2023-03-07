package smtp;

import org.apache.commons.net.smtp.SMTPClient;
import org.apache.commons.net.smtp.SMTPReply;

public class ClienteSMTP1 {
  public static void main(String[] args){
    SMTPClient client = new SMTPClient();
    try{
      int response;
      client.connect("localhost");

      System.out.println(client.getReplyString());
      response = client.getReplyCode();
      if(!SMTPReply.isPositiveCompletion(response)){
        client.disconnect();
        System.out.println("CONEXIÓN RECHAZADA");
        System.exit(1);
      }

      // TODO...

      client.disconnect();
    }catch (Exception ex){
      ex.printStackTrace();
    }
  }
}
