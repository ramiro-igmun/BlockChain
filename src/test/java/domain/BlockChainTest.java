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
    correctBlock1 = Block.builder().id(1).hash("362abc743e241b84ac5de697733d71f0bf63fe2383dcc29b814aa936b0445347")
            .previousHash("0").timeStamp(1581718042484L).nonce(0).miningTime(0).minerId("26").build();

    correctBlock2 = Block.builder().id(2).hash("0f56434baa309a21fa973b574da0770a8518dc7272029c5809a69ec7bb0082e6")
            .previousHash("362abc743e241b84ac5de697733d71f0bf63fe2383dcc29b814aa936b0445347").timeStamp(1581718042609L)
            .nonce(3).miningTime(15).minerId("94").build();

    incorrectBlock = Block.builder().id(3).hash("0f56434baa309a21fa973b574da0770a8518dc7272029c5809a69ec7bb0582e6")
            .previousHash("362abc743e241b842c5de697733d71f0bf63fe2383dcc29b814aa936b0445347").timeStamp(1581718042611L)
            .nonce(4).miningTime(31).minerId("95").build();

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
