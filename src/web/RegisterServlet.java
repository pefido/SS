package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Util.AESencrp;
import Util.Template;
import controller.Authenticator;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private Authenticator auth;

  /**
   * @throws SQLException 
   * @throws ClassNotFoundException 
   * @see HttpServlet#HttpServlet()
   */
  public RegisterServlet() throws ClassNotFoundException, SQLException {
    super();
    auth = new Authenticator();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter writer = response.getWriter();
    Template template = new Template(this, "/templates/register.html");
    writer.write(template.out());
    writer.close();
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String email = request.getParameter("email");
    String pw = request.getParameter("password");
    String pw2 = request.getParameter("password2");

    try {
      auth.create_account(email,pw,pw2);
    } catch (Exception e) {
      e.printStackTrace();
    }
    response.sendRedirect("/MyServlet/");
  }

}
