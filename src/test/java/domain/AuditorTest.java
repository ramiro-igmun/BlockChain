package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import services.Auditor;
import services.BlockChainAuditor;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
class AuditorTest {

  @Mock
  LinkedBlockChain blockChain;
  private Block correctBlock1;
  private Block correctBlock2;
  private Block incorrectBlock;
  private Auditor auditor;

  @BeforeEach
  void setUp() throws NoSuchFieldException, IllegalAccessException {
    //Given
    correctBlock1 = Block.builder().id(1).hash("362abc743e241b84ac5de697733d71f0bf63fe2383dcc29b814aa936b0445347")
            .previousHash("0").timeStamp(1581718042484L).nonce(0).miningTime(0).minerId("26").build();

    correctBlock2 = Block.builder().id(2).hash("0f56434baa309a21fa973b574da0770a8518dc7272029c5809a69ec7bb0082e6")
            .previousHash("362abc743e241b84ac5de697733d71f0bf63fe2383dcc29b814aa936b0445347").timeStamp(1581718042609L)
            .nonce(3).miningTime(15).minerId("94").build();

    incorrectBlock = Block.builder().id(3).hash("0f56434baa309a21fa973b574da0770a8518dc7272029c5809a69ec7bb0582e6")
            .previousHash("362abc743e241b842c5de697733d71f0bf63fe2383dcc29b814aa936b0445347").timeStamp(1581718042611L)
            .nonce(4).miningTime(31).minerId("95").build();

    List<Block> fakeBlocks = new LinkedList<>(Arrays.asList(correctBlock1,correctBlock2,incorrectBlock));

    //We insert the fake blocks in the BlockChain instances private field using reflection
    Field blocks = LinkedBlockChain.class.getDeclaredField("blocks");
    blocks.setAccessible(true);
    blocks.set(blockChain,fakeBlocks);

    Mockito.lenient().when(blockChain.getBlock(anyInt())).thenCallRealMethod();

    auditor = new BlockChainAuditor();
  }

  @Test
  void validateBlockChainWhenCorrectReturnsTrue() {
    //when
    Mockito.when(blockChain.size()).thenReturn(2);//We leave the incorrect block out
    //then
    assertTrue(auditor.validateBlockChain(blockChain));
  }

  @Test
  void validateBlockChainWhenIncorrectReturnsFalse() {
    //when
    Mockito.when(blockChain.size()).thenReturn(3);//We include the incorrect block
    //then
    assertFalse(auditor.validateBlockChain(blockChain));
  }

  @Test
  void validateLastBlockReturnsTrueWhenNewBlockIsCorrect() {
    //when
    Mockito.when(blockChain.size()).thenReturn(1);
    Mockito.when(blockChain.getComplexity()).thenReturn(1);
    //then
    assertTrue(auditor.validateNewBlock(correctBlock2,blockChain));
  }

  @Test
  void validateLastBlockReturnsFalseWhenNewBlockIsInCorrect() {
    //when
    Mockito.when(blockChain.size()).thenReturn(1);
    //then
    assertFalse(auditor.validateNewBlock(incorrectBlock,blockChain));
  }

}