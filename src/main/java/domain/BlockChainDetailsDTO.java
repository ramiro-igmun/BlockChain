package domain;

public class BlockChainDetailsDTO {

  private final Block lastBlock;
  private final int complexity;
  private final int size;
  private final int maxChainSize;

  public BlockChainDetailsDTO(Block lastBlock, int complexity, int size, int maxChainSize) {
    this.lastBlock = lastBlock;
    this.complexity = complexity;
    this.size = size;
    this.maxChainSize = maxChainSize;
  }

  public Block getLastBlock() {
    return lastBlock;
  }

  public int getComplexity() {
    return complexity;
  }

  public int getSize() {
    return size;
  }

  public int getMaxChainSize() {
    return maxChainSize;
  }
}
