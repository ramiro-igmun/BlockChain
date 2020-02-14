import domain.Auditor;
import domain.BlockChain;
import domain.Miner;
import io.FileManager;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) throws InterruptedException {

    Scanner reader = new Scanner(System.in);

    FileManager fileManager = new FileManager();
    Auditor auditor = new Auditor();
    BlockChain blockChain = new BlockChain(fileManager, auditor);

    System.out.println("Delete previous BlockChain?(\"y\",\"n\")");
    String option = reader.next();

    if (option.equals("y")){
      blockChain.deleteBlockChain();
    }else{
      if (!auditor.validateBlockChain(blockChain)) {
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
