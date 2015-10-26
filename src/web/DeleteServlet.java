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

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  Authenticator auth;

  /**
   * @throws Exception 
   * @see HttpServlet#HttpServlet()
   */
  public DeleteServlet() throws Exception  {
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
    } catch (AuthenticationErrorException e1) {
      System.out.println(e1.getMessage());
      response.sendRedirect("/MyServlet/login");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    try {
      String tmp = (String)session.getAttribute("user");
      auth.delete_account((auth.get_account(tmp).getUsername()));
      session.invalidate();
      response.sendRedirect("/MyServlet/");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      response.sendRedirect("/MyServlet/login");
    }
  }

}