package io;

import domain.Block;
import domain.BlockChain;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class BlockChainFileManager implements FileManager{

  @Override
  public void save(Object object, String path) {
    try (ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(path))) {
      objectOut.writeObject(object);
    } catch (IOException e) {
      System.err.println("Couldn't write file. Check your access privileges");
      System.err.println(e.getMessage());
    }
  }

  @Override
  public Object load(String path) throws FileNotFoundException {
    try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(path))) {
      return objectIn.readObject();
    } catch (IOException | ClassNotFoundException e) {
      System.err.println("Block Chain could not be loaded\nGenerating new empty block chain...");
      throw new FileNotFoundException();
    }
  }
}
