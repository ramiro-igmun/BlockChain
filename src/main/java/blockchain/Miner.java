package blockchain;

public class Miner implements Runnable {
  BlockChain blockChain;

  public Miner(BlockChain blockChain) {
    this.blockChain = blockChain;
  }

  @Override
  public void run() {

    while (blockChain.getLength() < 5) {
      Block previousBlock = blockChain.getLastBlock();
      Block block;
      if (previousBlock == null) { // if the block chain is empty
        block = new Block(1, "0", 0);
      } else {
        block = new Block(previousBlock.getId() + 1, previousBlock.getHash(), blockChain.getMiningComplexity());
      }
      block.setMinerId(Thread.currentThread().getName());
      blockChain.addBlock(block);
    }
  }
}
