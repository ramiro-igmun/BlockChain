import java.util.LinkedList;
import java.util.List;

public class BlockChain {
  private List<Block> blockChain;

  public BlockChain() {
    blockChain = new LinkedList<>();
  }

  public Block generateBlock(int complexity){
    Block block;
    if (blockChain.isEmpty()){
      block = new Block(1,"0",complexity);
      blockChain.add(block);

    }else {
      Block previousBlock = blockChain.get(blockChain.size() - 1);
      block = new Block(previousBlock.getId() + 1, previousBlock.getHash(),complexity);
      blockChain.add(block);
    }
    return block;
  }

  public boolean validateBlockChain(){
    for (int i = 0; i < blockChain.size() - 1; i++) {
      Block previousBlock = blockChain.get(i);
      Block block = blockChain.get(i+1);
      if (!previousBlock.getHash().equals(block.getPreviousHash())||!validateBlockHash(block)){
        return false;
      }
    }
    return validateBlockHash(blockChain.get(0));
  }

  public boolean validateBlockHash(Block block){
    String hash = Sha256.applySha256(block.getSignature());
    return block.getHash().equals(hash);
  }

  public void printBlocks(){
    for (Block block :
        blockChain) {
      System.out.println(block);
    }
  }
}


