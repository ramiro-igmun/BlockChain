public enum ConsoleColors {
  ANSI_RESET("\u001B[0m"),
  ANSI_RED ("\u001B[31m"),
  ANSI_GREEN ("\u001B[32m");

  private final String ansi;

  ConsoleColors(String ansi) {
    this.ansi = ansi;
  }

  @Override
  public String toString() {
    return ansi;
  }
}
