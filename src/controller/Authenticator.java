package controller;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import exception.*;
import model.Account;
import util.AESencrp;

public class Authenticator {

public Authenticator () throws SQLException, ClassNotFoundException {
	Connection c = getCon();
	PreparedStatement pstmt = c.prepareStatement(
	  "CREATE  TABLE IF NOT EXISTS users "
    + "(EMAIL  TEXT PRIMARY KEY     NOT NULL,"
    + " PWD    TEXT NOT NULL,"
    + " LOCKED BOOLEAN NOT NULL,"
	+ " LOGGED BOOLEAN NOT NULL )");
	pstmt.executeUpdate();
    pstmt.close();
    c.close();
}

  private Connection getCon() throws ClassNotFoundException, SQLException {
    Class.forName("org.sqlite.JDBC");
    DriverManager.registerDriver(new org.sqlite.JDBC());
    return DriverManager.getConnection("jdbc:sqlite:SSS.db");
  }

  public Account login(String name, String pwd) throws Exception {
    Account tmp = get_account(name);
    if(tmp == null)
      throw new UndefinedAccountException("Account doesnt exist!");
    else if(tmp.locked())
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
    if (a.logged() && !a.locked()) {
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
		if (tmp != null && tmp.logged() && !tmp.locked() && tmp.getPassword().equals(AESencrp.encrypt(pwd2))) {
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
		return acc;
	}

	public void delete_account(String name) throws Exception {
		Account a = get_account(name);
		if (a!=null) {
			a.lock();
			save_acc(a);
			Connection c = getCon();
			PreparedStatement pstmt = c.prepareStatement("DELETE FROM users WHERE email=?");
			pstmt.setString(1, a.getUsername());
			pstmt.executeUpdate();
		    pstmt.close();
		    c.close();
		}
	}
	
	public void create_account(String name, String pwd1, String pwd2) throws Exception {
		Account a = get_account(name);
		if (a!=null) {
			System.out.println("Conta já existe!");
		} 
		else if (pwd1.equals(pwd2)){
			a = new Account(name, AESencrp.encrypt(pwd1));
			save_acc(a);

		} else { System.out.println("Passwords são diferentes!"); }
	}
}
