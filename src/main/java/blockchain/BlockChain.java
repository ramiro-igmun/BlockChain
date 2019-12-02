package blockchain;

import util.ConsoleColors;
import util.FileManager;
import util.Sha256;

import java.util.List;
import java.util.regex.Pattern;

public class BlockChain {
  private List<Block> blockChain;
  private FileManager fileManager;
  volatile private int miningComplexity;

  public BlockChain(FileManager fileManager) {
    this.fileManager = fileManager;
    blockChain = fileManager.loadBlockChain();
    miningComplexity = 0;
    if (!blockChain.isEmpty()) {
      miningComplexity=getLastMiningComplexity();
    }
  }

  private int getLastMiningComplexity() {
    for (int i = 0; i < getLastBlock().getHash().length() - 1; i++) {
      if (getLastBlock().getHash().charAt(i) != '0') {
        break;
      }
      miningComplexity++;
    }
    return miningComplexity;
  }

  public boolean validateBlockChain() {
    if (blockChain.isEmpty()) {
      return true;
    }
    for (int i = 0; i < blockChain.size() - 1; i++) {
      Block previousBlock = blockChain.get(i);
      Block block = blockChain.get(i + 1);
      if (!previousBlock.getHash().equals(block.getPreviousHash()) || !validateBlockHash(block)) {
        System.out.println(ConsoleColors.ANSI_RED + "\nBlock Chain Corrupted");
        return false;
      }
    }
    if (validateBlockHash(blockChain.get(0))) {
      System.out.println(ConsoleColors.ANSI_GREEN + "\nBlock Chain Validated" + ConsoleColors.ANSI_RESET);
    } else {
      System.out.println(ConsoleColors.ANSI_RED + "\nBlock Chain Corrupted");
    }
    return validateBlockHash(blockChain.get(0));
  }

  public void printBlocks() {
    for (Block block :
            blockChain) {
      System.out.println(block);
    }
  }

  public void deleteBlockChain() {
    blockChain.clear();
    fileManager.deleteFile();
  }

  public synchronized void addBlock(Block block) {
    if (validateBlock(block) && validateComplexity(block)) {
      blockChain.add(block);
      System.out.println(block);
      updateMiningComplexity(block);
      fileManager.saveBlockChain(blockChain);
    }
  }

  private void updateMiningComplexity(Block block) {
    if (block.getMiningTime() < 15) {
      miningComplexity++;
      System.out.println("\nN increases by 1");
    } else if (block.getMiningTime() > 30) {
      miningComplexity--;
      System.out.println("\nN decreases by 1");
    } else {
      System.out.println("\nN stays the same");
    }
  }

  public boolean validateBlockHash(Block block) {
    String hash = Sha256.applySha256(block.getSignature());
    return block.getHash().equals(hash);
  }

  private boolean validateBlock(Block block) {
    if (!blockChain.isEmpty()) {
      Block previousBlock = blockChain.get(blockChain.size() - 1);
      return previousBlock.getHash().equals(block.getPreviousHash()) && validateBlockHash(block);
    } else {
      return validateBlockHash(block);
    }
  }

  private boolean validateComplexity(Block block) {//TODO correct the regex
    return Pattern.matches("[0]{" + miningComplexity + "}", block.getHash().substring(0, miningComplexity));
  }

  public Block getLastBlock() {
    if (blockChain.isEmpty()) {
      return null;
    }
    return blockChain.get((blockChain.size() - 1));
  }

  public int getMiningComplexity() {
    return miningComplexity;
  }

  public int getLength() {
    return blockChain.size();
  }

}

