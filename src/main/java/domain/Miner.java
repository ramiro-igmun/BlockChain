package domain;

import util.Sha256;

import java.time.LocalTime;

public class Miner implements Runnable {
  BlockChain blockChain;

  public Miner(BlockChain blockChain) {
    this.blockChain = blockChain;
  }

  @Override
  public void run() {

    while (blockChain.getLength() < 15) {
      Block previousBlock = blockChain.getLastBlock();
      Block block;
      if (previousBlock == null) { // if the block chain is empty
        block = new Block(1, "0");
        mine(block, 0);
      } else {
        block = new Block(previousBlock.getId() + 1, previousBlock.getHash());
        mine(block,blockChain.getMiningComplexity());
      }
      block.setMinerId(Thread.currentThread().getName());
      blockChain.addBlock(block);
    }
  }
  private String applyHash(Block block) {
    return Sha256.applySha256(block.getSignature());
  }

  private void mine(Block block, int complexity) {
    int start = LocalTime.now().toSecondOfDay();
    String targetPrefix = new String(new char[complexity]).replace('\0', '0');
    while (!applyHash(block).substring(0, complexity).equals(targetPrefix)) {
      block.increaseNonce();
    }
    block.setHash(applyHash(block));
    block.setMiningTime(LocalTime.now().toSecondOfDay() - start);
  }
}
