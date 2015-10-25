package web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Authenticator;
import model.Account;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private Authenticator auth;
  
  /**
   * @throws SQLException 
   * @throws ClassNotFoundException 
   * @see HttpServlet#HttpServlet()
   */
  public LogoutServlet() throws ClassNotFoundException, SQLException {
    super();
    auth = new Authenticator();
    // TODO Auto-generated constructor stub
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    //String user = request.getAttribute("userID").toString();
    try {
      String tmp = (String)session.getAttribute("user");
      System.out.println("aqui cenas " + tmp);
      auth.logout(auth.get_account(tmp));
      //System.out.println("cenas: " + (Account)session.getAttribute("user"));
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    session.removeAttribute("user");
    session.invalidate();
    response.sendRedirect("/MyServlet/");
  }

}