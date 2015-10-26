package web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Authenticator;
import exception.AuthenticationErrorException;
import model.Account;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private Authenticator auth;

  /**
   * @throws Exception 
   * @see HttpServlet#HttpServlet()
   */
  public LogoutServlet() throws Exception {
    super();
    auth = new Authenticator();
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    try {
      Account user = auth.login(request, response);
      auth.logout(user);
      session.removeAttribute("user");
      session.invalidate();
      response.sendRedirect("/MyServlet/");
    } catch (AuthenticationErrorException e1) {
      System.out.println(e1.getMessage());
      response.sendRedirect("/MyServlet/login");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      response.sendRedirect("/MyServlet/login");
    }
  }

}