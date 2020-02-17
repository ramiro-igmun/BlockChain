package controller;

import io.FileManager;
import services.Auditor;
import domain.Block;
import domain.BlockChain;
import domain.BlockChainDetailsDTO;

public class Controller {

  private BlockChain blockChain;
  private Auditor auditor;
  private FileManager fileManager;

  public Controller(BlockChain blockChain, Auditor auditor, FileManager fileManager) {
    this.blockChain = blockChain;
    this.auditor = auditor;
    this.fileManager = fileManager;
  }

  public BlockChainDetailsDTO getBlockChainDetails() {
    return new BlockChainDetailsDTO(
            blockChain.getBlock(blockChain.size()-1),
            blockChain.getComplexity(),
            blockChain.size(), blockChain.getMaxChainSize());
  }

  public synchronized void addMinedBlock(Block block) {
    if (auditor.validateNewBlock(block, blockChain)) {
      blockChain.addBlock(block);
      fileManager.save(blockChain, "blockChain.sr");
    }
  }
}
