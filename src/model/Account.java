package model;

import java.io.Serializable;

public class Account implements Serializable {

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
  
  public Account(String accountName, String password, boolean locked, boolean logged) {
	this.accountName = accountName;
	this.password = password;
	this.logged = logged;
    this.locked = locked;
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

  public boolean logged() { return logged;  }
  public void    logIn()    { logged = true;  }
  public void    logOut()   { logged = false; }

  public boolean locked() { return locked;  }
  public void    lock()     { locked = true;  }
  public void    unlock()   { locked = false; }
}
