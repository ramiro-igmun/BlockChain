import java.io.Console;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    BlockChain blockChain = new BlockChain();
    Scanner reader = new Scanner(System.in);

    System.out.println("Enter number of blocks: ");
    int number = Integer.parseInt(reader.nextLine());
    System.out.println("Enter the complexity of the Proof of Work: ");
    int complexity = Integer.parseInt(reader.nextLine());

    for (int i = 0; i < number; i++) {
      System.out.println(blockChain.generateBlock(complexity));
    }

    if (blockChain.validateBlockChain()) {
      System.out.println(ConsoleColors.ANSI_GREEN + "\nBlockChain Validated" + ConsoleColors.ANSI_RESET);
    } else {
      System.out.println(ConsoleColors.ANSI_RED + "\nBlockChain Corrupted" + ConsoleColors.ANSI_RESET);
    }
  }
}
