package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BlockChainTest {

  private Block correctBlock1;
  private Block correctBlock2;
  private Block incorrectBlock;
  private LinkedBlockChain blockChain;

  @BeforeEach
  void setup() {

    //Given
    correctBlock1 = new Block(1,
            "362abc743e241b84ac5de697733d71f0bf63fe2383dcc29b814aa936b0445347",
            "0",
            0,
            0,
            "26",
            1581718042484L);

    correctBlock2 = new Block(2,
            "0f56434baa309a21fa973b574da0770a8518dc7272029c5809a69ec7bb0082e6",
            "362abc743e241b84ac5de697733d71f0bf63fe2383dcc29b814aa936b0445347",
            3,
            15,
            "94",
            1581718042609L);

    incorrectBlock = new Block(3,
            "0f56434baa309a21fa973b574da0770a8518dc7272029c5809a69ec7bb0582e6",
            "362abc743e241b842c5de697733d71f0bf63fe2383dcc29b814aa936b0445347",
            4,
            31,
            "94",
            1581718042611L);

    blockChain = new LinkedBlockChain(15);
  }

  @Test
  void upgradesComplexityWhenAddingNewBlock() {
    assertEquals(0, blockChain.getComplexity());
    blockChain.addBlock(correctBlock1);
    assertEquals(1, blockChain.getComplexity());
    blockChain.addBlock(correctBlock2);
    assertEquals(1, blockChain.getComplexity());
    blockChain.addBlock(incorrectBlock);
    assertEquals(0, blockChain.getComplexity());
  }

  @Test
  void returnsSpecialCaseBlockWhenIndexOutOfBound() {
    Block block = blockChain.getBlock(15);
    assertEquals(0, block.getId());
  }
}
