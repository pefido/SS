
public class Account {

  private String accountName;
  private String password;
  private boolean logged;
  private boolean locked;

  public Account(String accountName, String password) {
    this.accountName = accountName;
    this.password = password;
    logged = false;
    locked = false;
  }

  public void logIn(String accountName, String password) {
    if(accountName.equals(this.accountName) && password.equals(this.password))
      logged = true;
    //else
    //exception
  }

  public void logOut() {
    if(logged)
      logged = false;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean getLogged() {
    return logged;
  }

  public boolean getLocked() {
    return locked;
  }
}
