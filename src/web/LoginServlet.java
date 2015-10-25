package web;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Authenticator;

import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private Authenticator auth;
  /**
   * @throws SQLException 
   * @throws ClassNotFoundException 
   * @see HttpServlet#HttpServlet()
   */
  public LoginServlet() throws ClassNotFoundException, SQLException {
    super();
    auth = new Authenticator();
    // TODO Auto-generated constructor stub
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    /*// TODO Auto-generated method stub
      response.getWriter().append("Served at: ").append(request.getContextPath());
      response.getWriter().append("\n" + request.getParameter("email"));
      response.getWriter().append("\n" + request.getParameter("password"));
      String email = request.getParameter("email");
      String pw = request.getParameter("password");
    //try {
    try {
    Class.forName("org.sqlite.JDBC");

    //DriverManager.registerDriver(new org.sqlite.JDBC());
    Connection c = DriverManager.getConnection("jdbc:sqlite:login.db");

    Statement stmt = c.createStatement();
    String create = "CREATE TABLE USERS " +
    "(EMAIL TEXT PRIMARY KEY     NOT NULL," +
    " PASSWORD        TEXT   NOT NULL )";

    String drop = "DROP TABLE USERS";

    String insert = "INSERT INTO USERS VALUES ('" + email + "','" + pw + "')";

    ResultSet tmp = stmt.executeQuery("SELECT * FROM USERS");
    //stmt.executeUpdate(create);
    while(tmp.next()){
    System.out.println(tmp.getString(1));
    }
    //System.out.println(tmp.getString(1));
    stmt.close();
    c.close();
    } catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
    }*/
    response.setContentType("text/html");
    PrintWriter writer = response.getWriter();
    String file = getServletContext().getRealPath("/templates/login.html");
    BufferedReader reader = new BufferedReader(new FileReader(file));
    String line;
    while ((line = reader.readLine()) != null)
    {
      writer.write(line);
    }
    reader.close();
    writer.close();
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String email = request.getParameter("email");
    String pw = request.getParameter("password");
    try {
      //System.out.println("EPA "+auth.login(email,pw).getUsername());
      HttpSession session = request.getSession();
      try{
      String cenas = auth.login(email, pw).getUsername();
      session.setAttribute("user", cenas);
      response.sendRedirect("/MyServlet/");
      } catch(Exception e){
          System.out.println(e.getMessage());
          response.sendRedirect("/MyServlet/login");
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
