package web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Authenticator;
import exception.AuthenticationErrorException;
import model.Account;
import util.Template;

@WebServlet("/edit")
public class EditServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  Authenticator auth;

  /**
   * @throws Exception 
   * @see HttpServlet#HttpServlet()
   */
  public EditServlet() throws Exception {
    super();
    auth = new Authenticator();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter writer = response.getWriter();
    Template template = new Template(this, "/templates/edit.html");
    writer.write(template.out());
    writer.close();
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    try {
      Account user = auth.login(request, response);
    } catch (AuthenticationErrorException e1) {
      response.sendRedirect("/MyServlet/login");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    String pw = request.getParameter("password");
    String pw2 = request.getParameter("password2");
    try {
      String tmp = (String)session.getAttribute("user");
      auth.change_pwd(tmp, pw2, pw);
      response.sendRedirect("/MyServlet/");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      response.sendRedirect("/MyServlet/edit");
    }
  }

}
