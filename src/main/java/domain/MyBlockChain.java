package domain;

import java.util.LinkedList;
import java.util.List;

public class MyBlockChain implements BlockChain {
  private List<Block> blocks;
  public final int maxChainSize = 15;
  private int miningComplexity;

  public MyBlockChain() {
    this.blocks = new LinkedList<>();
    miningComplexity = 0;//TODO is AtomicInteger necessary yet?
  }

  @Override
  public synchronized void addBlock(Block block) {
    blocks.add(block);
    System.out.println(block);
    updateMiningComplexity(block);
  }

  private void updateMiningComplexity(Block block) {
    if (block.getMiningTime() < 15) {
      ++miningComplexity;
      System.out.println("\nComplexity increases by 1" + "\nComplexity: " + miningComplexity);
    } else if (block.getMiningTime() > 30) {
      --miningComplexity;
      System.out.println("\nComplexity decreases by 1" + "\nComplexity: " + miningComplexity);
    } else {
      System.out.println("\nComplexity stays the same" + "\nComplexity: " + miningComplexity);
    }
  }

  @Override
  public Block getBlock(int index) {
    if (blocks.isEmpty() || blocks.size() < (index + 1)) {
      return new Block(0, "", "", 0, 0, "", 0);//Special case Block -> indicates BlockChain empty
    }
    return blocks.get(index);
  }

  @Override
  public int getComplexity() {
    return miningComplexity;
  }

  @Override
  public boolean isEmpty() {
    return blocks.isEmpty();
  }

  @Override
  public int size() {
    return blocks.size();
  }

  @Override
  public void printBlocks() {
    blocks.forEach(System.out::println);
  }

  @Override
  public void deleteBlockChain() {
    blocks.clear();
    miningComplexity = 0;
  }

  public int getMaxChainSize() {
    return maxChainSize;
  }
}

