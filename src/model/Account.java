package model;

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

  // é necessário? ou esta verificação é feita no authenticator?
  public void logIn(String accountName, String password) {
    if(accountName.equals(this.accountName) && password.equals(this.password))
      logged = true;
    //else
    //exception
  }

  // Gets e Sets
  //--------------------

  public String getUsername() { return accountName; }
  public void setUsername(String username) {
    this.accountName = username;
  }

  public String getPassword() { return password; }
  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isLogged() { return logged;  }
  public void    logIn()    { logged = true;  }
  public void    logOut()   { logged = false; }

  public boolean isLocked() { return locked;  }
  public void    lock()     { locked = true;  }
  public void    unlock()   { locked = false; }
}
