package domain;

import controller.Controller;
import util.Sha256;

import java.time.LocalTime;
import java.util.Date;

public class Miner implements Runnable {
  private Controller controller;

  public Miner(Controller controller) {
    this.controller = controller;
  }

  @Override
  public void run() {
    BlockChainDetailsDTO blockChain = controller.getBlockChainDetails();

    while (blockChain.getSize() < blockChain.getMaxChainSize()) {
      Block lastBlock = blockChain.getLastBlock();
      Block newBlock;
      //declaring the new Block's fields
      String previousHash;
      int id;

      if (lastBlock.getId() == 0) { // if the block chain is empty
        id = 1;
        previousHash = "0";
        newBlock = mine(id, previousHash, 0);
      } else {
        id = lastBlock.getId() + 1;
        previousHash = lastBlock.getHash();
        newBlock = mine(id, previousHash, blockChain.getComplexity());
      }
      controller.addMinedBlock(newBlock);
      blockChain = controller.getBlockChainDetails();
    }
  }

    public Block mine(int id, String previousHash, int complexity) {
    //start chronometer
    int start = LocalTime.now().toSecondOfDay();
    //initializing fields
    String targetPrefix = new String(new char[complexity]).replace('\0', '0');
    int nonce = 0;
    String hash, minerId = Thread.currentThread().getName();
    long timeStamp = new Date().getTime();

    /*
    * While applying sha256 hashing to the blocks signature
    * doesn't match the target complexity(prefix of 0s), increment the nonce
    */
    while (!(hash=Sha256.applySha256(previousHash + id + timeStamp + nonce)).substring(0, complexity).equals(targetPrefix)) {
     nonce++;
     timeStamp = new Date().getTime();
    }
    //stop chronometer
    int miningTime = LocalTime.now().toSecondOfDay() - start;
      return Block.builder()
              .id(id)
              .hash(hash)
              .minerId(minerId)
              .miningTime(miningTime)
              .nonce(nonce)
              .previousHash(previousHash)
              .timeStamp(timeStamp)
              .build();
  }
}
