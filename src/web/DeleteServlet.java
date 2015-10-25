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

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  Authenticator auth;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public DeleteServlet() throws ClassNotFoundException, SQLException  {
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
      auth.delete_account((auth.get_account(tmp).getUsername()));
    } catch (Exception e) {
      e.printStackTrace();
    }
    session.invalidate();
    response.sendRedirect("/MyServlet/");
  }

}