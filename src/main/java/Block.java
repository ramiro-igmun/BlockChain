import lombok.Data;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

@Data
public class Block implements Serializable {
  private String hash;
  private String previousHash;
  private int id;
  private long timeStamp;
  private int nonce=0;
  private int miningTime;
  private static final long SERIAL_VERSION_UID = 1L;

  public Block(int id, String previousHash,int complexity) {
    this.previousHash = previousHash;
    this.id = id;
    timeStamp = new Date().getTime();
    mine(complexity);
  }

  private String applyHash() {
  return Sha256.applySha256(getSignature());
  }

  private void mine(int complexity){
    int start = LocalTime.now().toSecondOfDay();
    String targetPrefix = new String(new char[complexity]).replace('\0','0');
    while(!applyHash().substring(0,complexity).equals(targetPrefix)){
      nonce++;
    }
    hash = applyHash();
    miningTime = LocalTime.now().toSecondOfDay() - start;
  }

  public String getSignature() {
    return previousHash + id + timeStamp + nonce;
  }

  @Override
  public String toString() {
    return "\nBlock: " + "\nId: " + id + "\nTimeStamp: " + timeStamp + "\nNonce: " + nonce +
            "\nHash of the previous block:\n" + previousHash + "\nHash of the block:\n" + hash +
            "\nBlock was generating for " + miningTime + " seconds";
  }
}
