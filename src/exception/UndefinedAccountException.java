package exception;

public class UndefinedAccountException extends Exception{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public UndefinedAccountException(String message) {
    super(message);
  }
}
