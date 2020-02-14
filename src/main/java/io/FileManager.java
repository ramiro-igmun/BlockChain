package io;

import domain.Block;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class FileManager {

  public void saveBlockChain(List<Block> blockChain) {
    try (ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream("blockChain.sr"))) {
      objectOut.writeObject(blockChain);
    } catch (IOException e) {
      System.err.println("Couldn't write file. Check your access privileges");
      System.err.println(e.getMessage());
    }
  }

  public List<Block> loadBlockChain() {
    try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream("blockChain.sr"))) {
      return (List<Block>) objectIn.readObject();
    } catch (IOException | ClassNotFoundException e) {
      System.err.println("File does not exist or not enough access privileges\nGenerating new empty block chain...");
      return new LinkedList<>();
    }
  }

  public void deleteFile() {
    File file = new File("blockChain.sr");
    file.delete();
  }
}
