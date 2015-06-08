package util;

public class FatalError extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public static void die(String msg, Object... args) {
    throw new FatalError(String.format(msg, args));
  }
  
  public FatalError(String msg) {
    super(msg);
  }
  
}
