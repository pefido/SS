package exception;

public class LoggedAccountException extends Exception{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public LoggedAccountException(String message) {
    super(message);
  }
}
