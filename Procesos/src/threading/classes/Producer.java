package threading.classes;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Producer extends Thread{
  private File file;
  private String fileContent;

  public Producer(String fileName) throws Exception{
    super();
    file = new File(fileName);
    if(!file.exists()) {
      throw new Exception("File does not exist");
    }
  }

  public String getContent(){
    return fileContent;
  }

  @Override
  public void run(){
    try{
      fileContent = this.ReadContent();
    }catch (Exception ex){
      System.out.println(ex.getMessage());
    }
  }

  private String ReadContent() throws IOException {
    var fileReader = new FileReader(file);
    var builder = new StringBuilder();
    int byteValue;
    while((byteValue = fileReader.read()) != -1) {
      builder.append((char)byteValue);
    }
    fileReader.close();
    System.out.println("[CONSUMER]: Finished reading.");
    return builder.toString();
  }
}
