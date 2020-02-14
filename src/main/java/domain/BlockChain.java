package domain;

import io.FileManager;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BlockChain {
  private List<Block> blockChain;
  private FileManager fileManager;
  private Auditor auditor;
  volatile private AtomicInteger miningComplexity;

  public BlockChain(FileManager fileManager, Auditor auditor) {
    this.fileManager = fileManager;
    this.auditor = auditor;
    blockChain = fileManager.loadBlockChain();
    miningComplexity = new AtomicInteger();
    if (!blockChain.isEmpty()) {
      updateToLastMiningComplexity();
    }
  }

  private void updateToLastMiningComplexity() {
    for (int i = 0; i < getLastBlock().getHash().length() - 1; i++) {
      if (getLastBlock().getHash().charAt(i) != '0') {
        break;
      }
      miningComplexity.incrementAndGet();
    }
  }

  public void printBlocks() {
    blockChain.forEach(System.out::println);
  }

  public void deleteBlockChain() {
    blockChain.clear();
    fileManager.deleteFile();
    miningComplexity.set(0);
  }

  //this method is accessed by the miner threads
  public synchronized void addBlock(Block block) {
    if (auditor.validateBlock(block, this)) {
      blockChain.add(block);
      System.out.println(block);
      updateMiningComplexity(block);
      fileManager.saveBlockChain(blockChain);
    }
  }

  private void updateMiningComplexity(Block block) {
    if (block.getMiningTime() < 15) {
      miningComplexity.incrementAndGet();
      System.out.println("\nN increases by 1");
    } else if (block.getMiningTime() > 30) {
      miningComplexity.decrementAndGet();
      System.out.println("\nN decreases by 1");
    } else {
      System.out.println("\nN stays the same");
    }
  }

  public Block getLastBlock() {
    if (blockChain.isEmpty()) {
      return null;
    }
    return blockChain.get((blockChain.size() - 1));
  }

  public int getMiningComplexity() {
    return miningComplexity.get();
  }

  public int getLength() {
    return blockChain.size();
  }

  public boolean isEmpty() {
    return blockChain.isEmpty();
  }

  public Block get(int index) {
    return blockChain.get(index);
  }

  public int size() {
    return blockChain.size();
  }
}

