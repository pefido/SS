import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

@WebServlet("/login")
public class LoginController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public LoginController() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // TODO Auto-generated method stub
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
      //stmt.executeUpdate(insert);
      while(tmp.next()){
        System.out.println(tmp.getString(1));
      }
      //System.out.println(tmp.getString(1));
      stmt.close();
      c.close();
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // TODO Auto-generated method stub
    doGet(request, response);
  }

}