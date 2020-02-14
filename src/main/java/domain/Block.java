package domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Block implements Serializable {
  private String hash;
  private String previousHash;
  private int id;
  private long timeStamp;
  private int nonce = 0;
  private int miningTime;
  private String minerId;
  private static final long SERIAL_VERSION_UID = 1L;

  public Block(int id, String previousHash) {
    this.previousHash = previousHash;
    this.id = id;
    timeStamp = new Date().getTime();
  }

  public void increaseNonce() {
    nonce++;
  }

  public String getSignature() {
    return previousHash + id + timeStamp + nonce;
  }

  @Override
  public String toString() {
    return "\nBlock: " + "\nCreated by miner: #"+ minerId + "\nId: " + id + "\nTimeStamp: " + timeStamp + "\nNonce: " + nonce +
            "\nHash of the previous block:\n" + previousHash + "\nHash of the block:\n" + hash +
            "\nBlock was generating for " + miningTime + " seconds";
  }
}
