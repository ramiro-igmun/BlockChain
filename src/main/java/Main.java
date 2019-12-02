import blockchain.BlockChain;
import blockchain.Miner;
import util.ConsoleColors;
import util.FileManager;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) throws InterruptedException {

    FileManager fileManager = new FileManager();
    BlockChain blockChain = new BlockChain(fileManager);
    if (!blockChain.validateBlockChain()) {
      blockChain.deleteBlockChain();
    }
    blockChain.printBlocks();

    Thread[] threads = new Thread[100];
    for (int i = 0; i < 100 ; i++){
      threads[i] = new Thread(new Miner(blockChain), "" + i);
      threads[i].start();
    }

    for (Thread thread : threads){
      thread.join();
    }
  }
}
