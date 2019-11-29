import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class FileManager {

  public void saveBlockChain(List<Block> blockChain) {
    try (ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream("blockChain.sr"))) {
      objectOut.writeObject(blockChain);
    } catch (IOException e) {
      System.err.println("Couldn't write file. Check your access privileges");
    }
  }

  public List<Block> loadBlockChain() {
    try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream("blockChain.sr"))) {
      return (List<Block>) objectIn.readObject();
    } catch (IOException | ClassNotFoundException e) {
      System.err.println("File does not exist or not enough access privileges");
    }
    return new LinkedList<>();
  }

  public void deleteFile() {
    File file = new File("blockChain.sr");
    file.delete();
  }
}
