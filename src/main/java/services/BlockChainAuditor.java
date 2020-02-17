package services;

import domain.Block;
import domain.BlockChain;
import util.ConsoleColors;
import util.Sha256;

import java.util.regex.Pattern;

public class BlockChainAuditor implements Auditor{

  @Override
  public boolean validateBlockChain(BlockChain blockChain) {
    if (blockChain.isEmpty()) {
      return true;
    }
    if (validateBlockHash(blockChain.getBlock(0)) && ValidateBlockChainHashConsistency(blockChain)) {
      System.out.println(ConsoleColors.ANSI_GREEN + "\nBlock Chain Validated" + ConsoleColors.ANSI_RESET);
      return true;
    } else {
      System.out.println(ConsoleColors.ANSI_RED + "\nBlock Chain Corrupted");
      return false;
    }
  }

  @Override
  public boolean validateNewBlock(Block block, BlockChain blockChain) {
    if (!blockChain.isEmpty()) {
      Block previousBlock = blockChain.getBlock(blockChain.size() - 1);
      return previousBlock.getHash().equals(block.getPreviousHash())
              && validateBlockHash(block)
              && validateComplexity(block, blockChain);
    } else {
      return validateBlockHash(block);
    }
  }

  private boolean ValidateBlockChainHashConsistency(BlockChain blockChain) {
    for (int i = 0; i < blockChain.size() - 1; i++) {
      Block previousBlock = blockChain.getBlock(i);
      Block block = blockChain.getBlock(i + 1);
      if (!previousBlock.getHash().equals(block.getPreviousHash()) || !validateBlockHash(block)) {
        return false;
      }
    }
    return true;
  }

  private boolean validateBlockHash(Block block) {
    String hash = Sha256.applySha256(block.getSignature());
    return block.getHash().equals(hash);
  }

  private boolean validateComplexity(Block block, BlockChain blockChain) {
    return Pattern.matches("[0]{" + blockChain.getComplexity() + "}",
            block.getHash().substring(0, blockChain.getComplexity()));
  }
}
