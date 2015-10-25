package exception;

public class EmailInUseException extends Exception{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public EmailInUseException(String message) {
    super(message);
  }
}
