//package domain;
//
//import io.FileManager;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//class MyBlockChainTest {
//
//  private Block correctBlock1;
//  private Block correctBlock2;
//  private Block incorrectBlock;
//  private MyBlockChain blockChain;
//  private List<Block> fakeBlocks;
//  @Mock
//  private FileManager fileManager;
//  @Mock
//  private Auditor auditor;
//
//  @BeforeEach
//  void setup() throws NoSuchFieldException, IllegalAccessException {
//
//    //Setting up our test class
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
//    incorrectBlock.setHash("0006434baa309a21fa973b574da0770a8518dc7272029c5809a69ec7bb0582e6");
//
//  }
//
//  @Test
//  void upgradesToLastMiningComplexity(){
//    //given
//    fakeBlocks = Arrays.asList(correctBlock1, incorrectBlock);
//    //when
//    Mockito.when(fileManager.loadBlockChain()).thenReturn(fakeBlocks);
//    blockChain = new MyBlockChain(fileManager,auditor);
//    //then
//    assertEquals(3,blockChain.getComplexity());
//  }
//
//  @Test
//  void addsNewBlock() {
//    fakeBlocks = Arrays.asList(correctBlock1,correctBlock2);
//
//    Mockito.when(auditor.validateNewBlock(Mockito.any(Block.class),Mockito.eq(blockChain))).thenReturn(true);
//    blockChain = new MyBlockChain(fileManager,auditor);
//    blockChain.addBlock(incorrectBlock);
//
//    assertEquals(3,blockChain.getComplexity());
//    assertEquals(3,blockChain.size());
//  }
//
//  @Test
//  void getLastBlock() {
//  }
//
//  @Test
//  void getMiningComplexity() {
//  }
//}