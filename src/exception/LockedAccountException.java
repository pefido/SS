package exception;

public class LockedAccountException extends Exception{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public LockedAccountException(String message) {
    super(message);
  }
}
