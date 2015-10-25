package web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.omg.Messaging.SyncScopeHelper;

import controller.Authenticator;

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
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    try {
      String tmp = (String)session.getAttribute("user");
      auth.logout(auth.get_account(tmp));
      session.removeAttribute("user");
      session.invalidate();
      response.sendRedirect("/MyServlet/");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      response.sendRedirect("/MyServlet/login");
    }
  }

}