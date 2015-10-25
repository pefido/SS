package exception;

public class LockedAccountException extends Exception{

  public LockedAccountException(String message) {
    super(message);
  }
}
