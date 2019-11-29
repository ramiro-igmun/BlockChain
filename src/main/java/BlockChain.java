import java.util.LinkedList;
import java.util.List;

public class BlockChain {
  private List<Block> blockChain;
  private FileManager fileManager;

  public BlockChain() {
    fileManager = new FileManager();
    blockChain = fileManager.loadBlockChain();
  }

  public Block generateBlock(int complexity) {
    Block block;
    if (blockChain.isEmpty()) {
      block = new Block(1, "0", complexity);

    } else {
      Block previousBlock = blockChain.get(blockChain.size() - 1);
      block = new Block(previousBlock.getId() + 1, previousBlock.getHash(), complexity);
    }
    blockChain.add(block);
    fileManager.saveBlockChain(blockChain);
    return block;
  }

  public boolean validateBlockChain() {
    if (blockChain.isEmpty()){
      System.out.println("BlockChain is empty");
      return true;
    }
    for (int i = 0; i < blockChain.size() - 1; i++) {
      Block previousBlock = blockChain.get(i);
      Block block = blockChain.get(i + 1);
      if (!previousBlock.getHash().equals(block.getPreviousHash()) || !validateBlockHash(block)) {
        return false;
      }
    }
    return validateBlockHash(blockChain.get(0));
  }

  public boolean validateBlockHash(Block block) {
    String hash = Sha256.applySha256(block.getSignature());
    return block.getHash().equals(hash);
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
}


