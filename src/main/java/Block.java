import java.util.Date;

public class Block {
  private String hash;
  private String previousHash;
  private int id;
  private long timeStamp;

  public Block(int id, String previousHash) {
    this.previousHash = previousHash;
    this.id = id;
    timeStamp = new Date().getTime();
    hash = applyHash();
  }

  private String applyHash(){
    return Sha256.applySha256(getSignature());
  }

  public String getHash() {
    return hash;
  }

  public int getId() {
    return id;
  }

  public String getPreviousHash() {
    return previousHash;
  }

  public long getTimeStamp() {
    return timeStamp;
  }

  public String getSignature() {
    return previousHash + id + timeStamp;
  }

  @Override
  public String toString() {
    return "\nBlock: " + "\nId: " + id + "\nTimeStamp: " + timeStamp +
      "\nHash of the previous block:\n" + previousHash + "\nHash of the block:\n" + hash;
  }
}
