import controller.Controller;
import domain.*;
import io.BlockChainFileManager;
import io.FileManager;
import services.Auditor;
import services.BlockChainAuditor;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class BlockChainInit {
  public static void main(String[] args) throws InterruptedException {

    Scanner reader = new Scanner(System.in);

    //Creating components and injecting dependencies
    FileManager fileManager = new BlockChainFileManager();
    Auditor auditor = new BlockChainAuditor();
    BlockChain blockChain;
    try {
      blockChain = (BlockChain) fileManager.load("blockChain.sr");
    } catch (FileNotFoundException e) {
      blockChain = new MyBlockChain();
    }
    Controller controller = new Controller(blockChain, auditor, fileManager);

    //We give the user the option to reset the Block Chain
    String option = "";
    if (!blockChain.isEmpty()) {
      System.out.println("Delete previous Block Chain?(\"y\",\"n\")");
      option = reader.nextLine();
    }

    setUpBlockChain(auditor, blockChain, option);

    blockChain.printBlocks();

    Thread[] threads = CreateAndRunMiners(controller);

    //wait until all threads are finished
    for (Thread thread : threads){
      thread.join();
    }
  }

  private static void setUpBlockChain(Auditor auditor, BlockChain blockChain, String option) {
    if (option.equals("y")){
      blockChain.deleteBlockChain();
    }else{
      if (!auditor.validateBlockChain(blockChain)) {
        blockChain.deleteBlockChain();
      }
    }
  }

  private static Thread[] CreateAndRunMiners(Controller controller) {
    Thread[] threads = new Thread[100];
    for (int i = 0; i < 100 ; i++){
      threads[i] = new Thread(new Miner(controller), Integer.toString(i));
      threads[i].start();
    }
    return threads;
  }
}
