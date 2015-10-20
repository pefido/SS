package controller;

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

  private Map<String, Account> accounts;

	public Authenticator () throws SQLException, ClassNotFoundException {
		Connection c = getCon();
	    String sqlCreate = 
	      "CREATE TABLE IF NOT EXISTS users "
	    + "(EMAIL TEXT PRIMARY KEY     NOT NULL,"
	    + " PASSWORD        TEXT   NOT NULL )";
	    Statement stmt = c.createStatement();
	    stmt.execute(sqlCreate);
	    stmt.close();
	    c.close();
	    this.accounts = new HashMap<String, Account>();
	}
	
	private Connection getCon() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
	    DriverManager.registerDriver(new org.sqlite.JDBC());
	    return DriverManager.getConnection("jdbc:sqlite:login.db");
	}
	
	public Account login(String name, String pwd) {
		return null;
	}
	
	public void logout (Account a) {
		
	}
	
	public void change_pwd(String name, String pwd1, String pwd2) {
		
	}
	
	public Account get_account(String name) {
		accounts.get(name);
	}
	
	public void delete_account(String name) {
		
	}
	
	public void create_account(String name, String pwd1, String pwd2) throws Exception {
		Connection c = getCon();
		//Confirmar se a conta já existe
		String getsql = "select count(*) from users where email='"+name+"'";
		Statement st = c.createStatement();
		ResultSet tmp = st.executeQuery(getsql);
		tmp.next();
		int count = tmp.getInt(1);
		if (count > 0) {
			System.out.println("Conta já existe!");
		} 
		else if (pwd1.equals(pwd2)){
			getsql = "insert into users values ('"
					+name+"','"
					+AESencrp.encrypt(pwd1)+"')";
			st = c.createStatement();
			st.executeUpdate(getsql);
		} else { System.out.println("Passwords são diferentes!"); }
	    st.close();
	    c.close();
	}
  
}
