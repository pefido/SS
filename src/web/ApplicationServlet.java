package web;

import java.io.*;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Util.Template;
import controller.Authenticator;

@WebServlet("/")
public class ApplicationServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @throws SQLException 
   * @throws ClassNotFoundException 
   * @see HttpServlet#HttpServlet()
   */
  public ApplicationServlet() throws ClassNotFoundException, SQLException {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    HttpSession session = request.getSession(false);

    response.setContentType("text/html");
    PrintWriter writer = response.getWriter();
    Template template = null;

    System.out.println("aqui: " + session);
    if (session == null) {
      template = new Template(this, "/templates/index.html");
    }
    else {
      template = new Template(this, "/templates/account.html");
      template.assign("name", (String)session.getAttribute("user"));
    }
    writer.write(template.out());
    writer.close();
  }

}
