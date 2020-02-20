package domain;

import java.util.LinkedList;
import java.util.List;

public class LinkedBlockChain implements BlockChain {
  private List<Block> blocks;
  public final int maxChainSize;
  private int miningComplexity;

  public LinkedBlockChain(int maxChainSize) {
    this.maxChainSize = maxChainSize;
    this.blocks = new LinkedList<>();
    miningComplexity = 0;//TODO is AtomicInteger necessary yet?
  }

  @Override
  public void addBlock(Block block) {
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
      return Block.builder().id(0).hash("").previousHash("").timeStamp(0).nonce(0).miningTime(0).minerId("").build();
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

