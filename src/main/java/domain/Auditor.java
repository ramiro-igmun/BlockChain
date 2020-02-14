package domain;

import util.ConsoleColors;
import util.Sha256;

import java.util.regex.Pattern;

public class Auditor {

   public boolean validateBlockChain(BlockChain blockChain) {
    if (blockChain.isEmpty()) {
      return true;
    }
    if (!ValidateBlockChainHashConsistency(blockChain)) {
      return false;
    }
    if (validateBlockHash(blockChain.get(0))) {
      System.out.println(ConsoleColors.ANSI_GREEN + "\nBlock Chain Validated" + ConsoleColors.ANSI_RESET);
      return true;
    } else {
      System.out.println(ConsoleColors.ANSI_RED + "\nBlock Chain Corrupted");
      return false;
    }
  }

  private boolean ValidateBlockChainHashConsistency(BlockChain blockChain) {
    for (int i = 0; i < blockChain.size() - 1; i++) {
      Block previousBlock = blockChain.get(i);
      Block block = blockChain.get(i + 1);
      if (!previousBlock.getHash().equals(block.getPreviousHash()) || !validateBlockHash(block)) {
        System.out.println(ConsoleColors.ANSI_RED + "\nBlock Chain Corrupted");
        return false;
      }
    }
    return true;
  }

  public boolean validateBlockHash(Block block) {
    String hash = Sha256.applySha256(block.getSignature());
    return block.getHash().equals(hash);
  }

  public boolean validateBlock(Block block, BlockChain blockChain) {
    if (!blockChain.isEmpty()) {
      Block previousBlock = blockChain.get(blockChain.size() - 1);
      return previousBlock.getHash().equals(block.getPreviousHash())
              && validateBlockHash(block)
              && validateComplexity(block, blockChain);
    } else {
      return validateBlockHash(block);
    }
  }

  private boolean validateComplexity(Block block, BlockChain blockChain) {
    return Pattern.matches("[0]{" + blockChain.getMiningComplexity() + "}",
            block.getHash().substring(0, blockChain.getMiningComplexity()));
  }
}
