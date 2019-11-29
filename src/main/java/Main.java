import java.util.Scanner;

public class Main {
  public static void main(String[] args) {

    BlockChain blockChain = new BlockChain();
    if (blockChain.validateBlockChain()) {
      System.out.println(ConsoleColors.ANSI_GREEN + "\nBlockChain Validated" + ConsoleColors.ANSI_RESET);
    } else {
      System.out.println(ConsoleColors.ANSI_RED + "\nBlockChain Corrupted" +
              "\nDeleting current BlockChain and creating a new one..." + ConsoleColors.ANSI_RESET);
      blockChain.deleteBlockChain();
    }

    Scanner reader = new Scanner(System.in);

    System.out.println("Enter number of blocks: ");
    int number = Integer.parseInt(reader.nextLine());
    System.out.println("Enter the complexity of the Proof of Work: ");
    int complexity = Integer.parseInt(reader.nextLine());

    for (int i = 0; i < number; i++) {
      blockChain.generateBlock(complexity);
    }

    blockChain.printBlocks();

  }
}
