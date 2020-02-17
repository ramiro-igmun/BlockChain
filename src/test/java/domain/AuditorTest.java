//package domain;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.lang.reflect.Field;
//import java.util.Arrays;
//import java.util.LinkedList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.anyInt;
//
//@ExtendWith(MockitoExtension.class)
//class AuditorTest {
//
//  @Mock
//  MyBlockChain blockChain;
//  private Block correctBlock1;
//  private Block correctBlock2;
//  private Block incorrectBlock;
//  private Auditor auditor;
//
//  @BeforeEach
//  void setUp() throws NoSuchFieldException, IllegalAccessException {
//    //Given
//    correctBlock1 = new Block(1,"0");
//    correctBlock1.setMinerId("26");
//    correctBlock1.setTimeStamp(1581718042484L);
//    correctBlock1.setNonce(0);
//    correctBlock1.setHash("362abc743e241b84ac5de697733d71f0bf63fe2383dcc29b814aa936b0445347");
//
//    correctBlock2 = new Block(2,"362abc743e241b84ac5de697733d71f0bf63fe2383dcc29b814aa936b0445347");
//    correctBlock2.setMinerId("94");
//    correctBlock2.setTimeStamp(1581718042609L);
//    correctBlock2.setNonce(3);
//    correctBlock2.setHash("0f56434baa309a21fa973b574da0770a8518dc7272029c5809a69ec7bb0082e6");
//
//    incorrectBlock = new Block(3,"362abc743e241b842c5de697733d71f0bf63fe2383dcc29b814aa936b0445347");
//    incorrectBlock.setMinerId("93");
//    incorrectBlock.setTimeStamp(1581718042611L);
//    incorrectBlock.setNonce(4);
//    incorrectBlock.setHash("0f56434baa309a21fa973b574da0770a8518dc7272029c5809a69ec7bb0582e6");
//
//    List<Block> fakeBlocks = new LinkedList<>(Arrays.asList(correctBlock1,correctBlock2,incorrectBlock));
//
//    //We insert the fake blocks in the BlockChain instances private field using reflection
//    Field blocks = MyBlockChain.class.getDeclaredField("blocks");
//    blocks.setAccessible(true);
//    blocks.set(blockChain,fakeBlocks);
//
//    Mockito.lenient().when(blockChain.getBlock(anyInt())).thenCallRealMethod();
//
//    auditor = new Auditor();
//  }
//
//  @Test
//  void validateBlockChainWhenCorrectReturnsTrue() {
//    //when
//    Mockito.when(blockChain.size()).thenReturn(2);//We leave the incorrect block out
//    //then
//    assertTrue(auditor.validateBlockChain(blockChain));
//  }
//
//  @Test
//  void validateBlockChainWhenIncorrectReturnsFalse() {
//    //when
//    Mockito.when(blockChain.size()).thenReturn(3);//We include the incorrect block
//    //then
//    assertFalse(auditor.validateBlockChain(blockChain));
//  }
//
//  @Test
//  void validateBlockHash() {
//    //then
//    assertTrue(auditor.validateBlockHash(correctBlock2));
//    assertFalse(auditor.validateBlockHash(incorrectBlock));
//  }
//
//  @Test
//  void validateLastBlockReturnsTrueWhenNewBlockIsCorrect() {
//    //when
//    Mockito.when(blockChain.size()).thenReturn(1);
//    Mockito.when(blockChain.getComplexity()).thenReturn(1);
//    //then
//    assertTrue(auditor.validateNewBlock(correctBlock2,blockChain));
//  }
//
//  @Test
//  void validateLastBlockReturnsFalseWhenNewBlockIsInCorrect() {
//    //when
//    Mockito.when(blockChain.size()).thenReturn(1);
//    //then
//    assertFalse(auditor.validateNewBlock(incorrectBlock,blockChain));
//  }
//
//}