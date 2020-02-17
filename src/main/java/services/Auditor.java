package services;

import domain.Block;
import domain.BlockChain;

public interface Auditor {
  boolean validateBlockChain(BlockChain blockChain);
  boolean validateNewBlock(Block block, BlockChain blockChain);
}
