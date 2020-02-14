package blockchain;

import util.ConsoleColors;
import util.FileManager;
import util.Sha256;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class BlockChain {
  private List<Block> blockChain;
  private FileManager fileManager;
  volatile private AtomicInteger miningComplexity;

  public BlockChain(FileManager fileManager) {
    this.fileManager = fileManager;
    blockChain = fileManager.loadBlockChain();
    miningComplexity = new AtomicInteger();
    if (!blockChain.isEmpty()) {
      updateToLastMiningComplexity();
    }
  }

  private void updateToLastMiningComplexity() {
    for (int i = 0; i < getLastBlock().getHash().length() - 1; i++) {
      if (getLastBlock().getHash().charAt(i) != '0') {
        break;
      }
      miningComplexity.incrementAndGet();
    }
  }

  public boolean validateBlockChain() {
    if (blockChain.isEmpty()) {
      return true;
    }
    if (!ValidateBlockChainHashConsistency()) {
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

  private boolean ValidateBlockChainHashConsistency() {
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

  public void printBlocks() {
    blockChain.forEach(System.out::println);
  }

  public void deleteBlockChain() {
    blockChain.clear();
    fileManager.deleteFile();
    miningComplexity.set(0);
  }

  //this method is accessed by the miner threads
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
      miningComplexity.incrementAndGet();
      System.out.println("\nN increases by 1");
    } else if (block.getMiningTime() > 30) {
      miningComplexity.decrementAndGet();
      System.out.println("\nN decreases by 1");
    } else {
      System.out.println("\nN stays the same");
    }
  }

  private boolean validateBlock(Block block) {
    if (!blockChain.isEmpty()) {
      Block previousBlock = blockChain.get(blockChain.size() - 1);
      return previousBlock.getHash().equals(block.getPreviousHash()) && validateBlockHash(block);
    } else {
      return validateBlockHash(block);
    }
  }

  private boolean validateComplexity(Block block) {
    return Pattern.matches("[0]{" + getMiningComplexity() + "}", block.getHash().substring(0, getMiningComplexity()));
  }

  public Block getLastBlock() {
    if (blockChain.isEmpty()) {
      return null;
    }
    return blockChain.get((blockChain.size() - 1));
  }

  public int getMiningComplexity() {
    return miningComplexity.get();
  }

  public int getLength() {
    return blockChain.size();
  }

}

