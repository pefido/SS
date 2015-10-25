package exception;

public class AuthenticationErrorException extends Exception{

  public AuthenticationErrorException(String message) {
    super(message);
  }
}
