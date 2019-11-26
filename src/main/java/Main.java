import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    BlockChain blockChain = new BlockChain();
    Scanner reader = new Scanner(System.in);

    System.out.println("Enter number of blocks: ");
    int number = Integer.parseInt(reader.nextLine());
    for (int i = 0; i < number; i++) {
      blockChain.generateBlock();
    }

    blockChain.printBlocks();
    System.out.println();

    if (blockChain.validateBlockChain()) {
      System.out.println("BlockChain Validated");
    } else {
      System.out.println("BlockChain Corrupted");
    }
  }
}
