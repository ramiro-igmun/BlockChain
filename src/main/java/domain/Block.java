package domain;

import lombok.Builder;
import lombok.Getter;
import lombok.With;
import util.ConsoleColors;

import java.io.Serializable;

@Getter
@Builder
public class Block implements Serializable {
  private final int id;
  private final String hash;
  private final String previousHash;
  private final long timeStamp;
  private final int nonce;
  private final int miningTime;
  private final String minerId;
  private static final long SERIAL_VERSION_UID = 17111323L;

  public String getSignature() {
    return previousHash + id + timeStamp + nonce;
  }

  @Override
  public String toString() {
    return "\nBlock: " + "\nCreated by miner: #" + ConsoleColors.ANSI_GREEN + minerId + ConsoleColors.ANSI_RESET +
            "\nId: " + ConsoleColors.ANSI_GREEN + id + ConsoleColors.ANSI_RESET +
            "\nTimeStamp: " + ConsoleColors.ANSI_GREEN + timeStamp + ConsoleColors.ANSI_RESET +
            "\nNonce: " + ConsoleColors.ANSI_GREEN + nonce + ConsoleColors.ANSI_RESET +
            "\nHash of the previous block:\n" + ConsoleColors.ANSI_GREEN + previousHash + ConsoleColors.ANSI_RESET +
            "\nHash of the block:\n" + ConsoleColors.ANSI_GREEN + hash + ConsoleColors.ANSI_RESET +
            "\nBlock was generating for " + ConsoleColors.ANSI_GREEN + miningTime + ConsoleColors.ANSI_RESET + " seconds";
  }
}
