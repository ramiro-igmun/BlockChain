import blockchain.BlockChain;
import blockchain.Miner;
import util.ConsoleColors;
import util.FileManager;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) throws InterruptedException {

    Scanner reader = new Scanner(System.in);

    FileManager fileManager = new FileManager();
    BlockChain blockChain = new BlockChain(fileManager);

    System.out.println("Delete previous BlockChain?(\"y\",\"n\")");
    String option = reader.next();

    if (option.equals("y")){
      blockChain.deleteBlockChain();
    }else{
      if (!blockChain.validateBlockChain()) {
        blockChain.deleteBlockChain();
      }
      blockChain.printBlocks();
    }

    //Create the miners
    Thread[] threads = new Thread[100];
    for (int i = 0; i < 100 ; i++){
      threads[i] = new Thread(new Miner(blockChain), Integer.toString(i));
      threads[i].start();
    }

    //wait for the threads to continue
    for (Thread thread : threads){
      thread.join();
    }
  }
}
