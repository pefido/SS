package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exception.*;
import model.Account;
import util.AESencrp;

public class Authenticator {

  public Authenticator () throws Exception {
    Connection c = getCon();
    PreparedStatement pstmt = c.prepareStatement(
        "CREATE  TABLE IF NOT EXISTS users "
            + "(EMAIL  TEXT PRIMARY KEY     NOT NULL,"
            + " PWD    TEXT NOT NULL,"
            + " LOCKED BOOLEAN NOT NULL,"
            + " LOGGED BOOLEAN NOT NULL )");
    pstmt.executeUpdate();
    pstmt.close();
    pstmt = c.prepareStatement("INSERT OR REPLACE into users values (?, ?, ?, ?)");
    pstmt.setString(1, "root");
    pstmt.setString(2, AESencrp.encrypt("toor"));
    pstmt.setBoolean(3, false);
    pstmt.setBoolean(4, false);
    pstmt.executeUpdate();
    pstmt.close();
    c.close();
    c.close();
  }

  private Connection getCon() throws ClassNotFoundException, SQLException {
    Class.forName("org.sqlite.JDBC");
    DriverManager.registerDriver(new org.sqlite.JDBC());
    return DriverManager.getConnection("jdbc:sqlite:SSS.db");
  }

  public Account login(String name, String pwd) throws Exception {
    Account tmp = get_account(name);
    if(tmp.locked())
      throw new LockedAccountException("Account is locked!");
    else if(!tmp.getPassword().equals(AESencrp.encrypt(pwd)))
      throw new AuthenticationErrorException("Wrong password!");
    else {
      tmp.logIn();
      save_acc(tmp);
      return tmp;
    }
  }

  public void logout (Account a) throws Exception {
    if(!a.logged())
      throw new LoggedAccountException("Account is not logged in!");
    else if(a.locked())
      throw new LockedAccountException("Account is locked!");
    else {
      a.logOut();
    }
    save_acc(a);
  }

  private void save_acc(Account a) throws Exception {
    Connection c = getCon();
    PreparedStatement pstmt = c.prepareStatement("INSERT OR REPLACE into users values (?, ?, ?, ?)");
    pstmt.setString(1, a.getUsername());
    pstmt.setString(2, a.getPassword());
    pstmt.setBoolean(3, a.locked());
    pstmt.setBoolean(4, a.logged());
    pstmt.executeUpdate();
    pstmt.close();
    c.close();
  }

  public void change_pwd(String name, String pwd1, String pwd2) throws Exception {
    Account tmp = get_account(name);
    if (!tmp.logged()) {
      throw new LoggedAccountException("Account is not logged in!");
    }
    else if (tmp.locked()) {
      throw new LockedAccountException("Account is locked!");
    }
    else if (!tmp.getPassword().equals(AESencrp.encrypt(pwd2))) {
      throw new PasswordMismatchException("The passwords don't match!");
    }
    else {
      Connection c = getCon();
      PreparedStatement pstmt = c.prepareStatement("UPDATE users SET pwd=? where email=?");
      pstmt.setString(1, AESencrp.encrypt(pwd1));
      pstmt.setObject(2, tmp.getUsername());
      pstmt.executeUpdate();
      pstmt.close();
      c.close();
    }
  }

  public Account get_account(String name) throws Exception {
    Connection c = getCon();
    PreparedStatement pstmt = c.prepareStatement("SELECT * FROM users WHERE email=?");
    pstmt.setString(1, name);
    ResultSet tmp = pstmt.executeQuery();
    Account acc = null;
    while(tmp.next()){
      String username = tmp.getString(1);
      String pass = tmp.getString(2);
      boolean locked = tmp.getBoolean(3);
      boolean logged = tmp.getBoolean(4);
      acc = new Account(username,pass,locked,logged);
    }
    pstmt.close();
    c.close();
    if(acc == null) throw new UndefinedAccountException("Account doesnt exist!");
    return acc;
  }

  public void delete_account(String name) throws Exception {
    Account a = get_account(name);
    a.lock();
    save_acc(a);
    Connection c = getCon();
    PreparedStatement pstmt = c.prepareStatement("DELETE FROM users WHERE email=?");
    pstmt.setString(1, a.getUsername());
    pstmt.executeUpdate();
    pstmt.close();
    c.close();
  }

  public void create_account(String name, String pwd1, String pwd2) throws Exception {
    Account a = null;
    try {
      a = get_account(name);
    } catch (UndefinedAccountException e) {}
    if (a!=null) {
      throw new EmailInUseException("That email is already registred!");
    }
    else if (!pwd1.equals(pwd2)) {
      throw new PasswordMismatchException("The passwords don't match!");
    }
    else {
      a = new Account(name, AESencrp.encrypt(pwd1));
      save_acc(a);
    }
  }
  
  public Account login(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    HttpSession session = req.getSession(false);
    String email = (String)session.getAttribute("user");
    Account a = null;
    try {
      a = get_account(email);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw new AuthenticationErrorException("Not authenticated!");
    }
    return login(a.getUsername(),AESencrp.decrypt(a.getPassword()));
  }
  
  public boolean isAdmin (Account account){
    boolean isAdmin = false;
    if(account.getUsername().equals("root"))
      isAdmin = true;
    return isAdmin;
  }
}
