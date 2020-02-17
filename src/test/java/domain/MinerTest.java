package domain;

import controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import util.Sha256;

import static org.junit.jupiter.api.Assertions.*;

class MinerTest {

  @Mock
  Controller controller;
  Miner miner;
  Block block;

  @BeforeEach
  void setup(){
    miner = new Miner(controller);
    block = miner.mine(1,"aaaabbb",2);

  }

  @Test
  void minedBlockHashIsCorrect(){
    String actualHash = block.getHash();
    String expectedHash = Sha256.applySha256(block.getSignature());
    assertEquals(expectedHash,actualHash);
  }

  @Test
  void minedBlockComplexityIsCorrect(){
    assertEquals("00",block.getHash().substring(0,2));
  }

  @Test
  void minedPreviousBlockHashIsCorrect(){
    assertEquals("aaaabbb",block.getPreviousHash());
  }

}