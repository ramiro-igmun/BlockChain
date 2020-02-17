package domain;

import java.io.Serializable;

public interface BlockChain extends Serializable {

  void addBlock(Block block);
  Block getBlock(int index);
  boolean isEmpty();
  int size();
  int getComplexity();
  void printBlocks();
  int getMaxChainSize();
  void deleteBlockChain();
}
