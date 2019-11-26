import static org.junit.jupiter.api.Assertions.*;

class BlockTest {

  @org.junit.jupiter.api.Test
  void testToString() {
    Block block = new Block(1,"0");
    System.out.println(block);
  }
}