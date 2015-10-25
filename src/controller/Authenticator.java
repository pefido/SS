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

import Util.AESencrp;
import model.Account;

public class Authenticator {

	public Authenticator () throws SQLException, ClassNotFoundException {
		Connection c = getCon();
	    String sqlCreate = 
	      "CREATE  TABLE IF NOT EXISTS users "
	    + "(EMAIL  TEXT PRIMARY KEY     NOT NULL,"
	    + " PWD    TEXT NOT NULL,"
	    + " LOCKED BOOLEAN NOT NULL,"
	    + " LOGGED BOOLEAN NOT NULL )";
	    Statement stmt = c.createStatement();
	    stmt.execute(sqlCreate);
	    stmt.close();
	    c.close();
	}
	
	private Connection getCon() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
	    DriverManager.registerDriver(new org.sqlite.JDBC());
	    return DriverManager.getConnection("jdbc:sqlite:SS.db");
	}
	
	public Account login(String name, String pwd) throws Exception {
		Account tmp = get_account(name);
		if (tmp != null && !tmp.logged() && !tmp.locked()) {
			System.out.println(tmp.getPassword().equals(AESencrp.encrypt(pwd)));
			if (AESencrp.decrypt(tmp.getPassword()).equals(pwd)) {
				tmp.logIn();
				save_acc(tmp);
				return tmp;
			}
		}
		return null;
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
		pstmt.setObject(2, a.getPassword());
		pstmt.setObject(3, a.locked());
		pstmt.setObject(4, a.logged());
		pstmt.executeUpdate();
	    pstmt.close();
	    c.close();
	}
	
	//TODO:
	public void change_pwd(String name, String pwd1, String pwd2) throws Exception {
		Account tmp = get_account(name);
		if (tmp != null && !tmp.logged() && !tmp.locked()) {
			
		}
	}
	
	public Account get_account(String name) throws Exception {
		Connection c = getCon();
		Statement stmt = c.createStatement();
		System.out.println(name);
		ResultSet tmp = stmt.executeQuery("select * from users where email='"+name+"'");
		Account acc = null;
	    while(tmp.next()){
	      System.out.println("sim");
	      String username = tmp.getString(1);
	      String pass = tmp.getString(2);
	      boolean locked = tmp.getBoolean(3);
	      boolean logged = tmp.getBoolean(4);
	      acc = new Account(username,pass,locked,logged);
	    }
	    stmt.close();
	    c.close();
		return acc;
	}
	
	//TODO:
	public void delete_account(String name) throws Exception {
		Account a = get_account(name);
		if (a!=null) {
			a.lock();
			save_acc(a);
			//remover conta
		}
	}
	
	public void create_account(String name, String pwd1, String pwd2) throws Exception {
		Connection c = getCon();
		//Confirmar se a conta já existe
		String getsql = "select count(*) from users where email='"+name+"'";
		Statement st = c.createStatement();
		ResultSet tmp = st.executeQuery(getsql);
		tmp.next();
		int count = tmp.getInt(1);
	    st.close();
	    c.close();
		if (count > 0) {
			System.out.println("Conta já existe!");
		} 
		else if (pwd1.equals(pwd2)){
			Account a = new Account(name, AESencrp.encrypt(pwd1));
			save_acc(a);

		} else { System.out.println("Passwords são diferentes!"); }
	}

}
