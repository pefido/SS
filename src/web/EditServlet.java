package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Util.Template;
import controller.Authenticator;

@WebServlet("/edit")
public class EditServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  Authenticator auth;

  /**
   * @throws SQLException 
   * @throws ClassNotFoundException 
   * @see HttpServlet#HttpServlet()
   */
  public EditServlet() throws ClassNotFoundException, SQLException {
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
	
	String pw = request.getParameter("password");
    String pw2 = request.getParameter("password2");
   
    try {
      String tmp = (String)session.getAttribute("user");
      auth.change_pwd(tmp, pw2, pw);
    } catch (Exception e) {
      e.printStackTrace();
    }
    response.sendRedirect("/MyServlet/");
  }

}
