package web;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Util.Template;

@WebServlet("/")
public class ApplicationServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public ApplicationServlet() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    boolean logged = false;
    String name = "Campinhos";
    response.setContentType("text/html");
    PrintWriter writer = response.getWriter();
    Template template = null;
    if (logged) {
      template = new Template(this, "/templates/account.html");
      template.assign("name", name);
    }
    else {
      template = new Template(this, "/templates/index.html");
    }
    writer.write(template.out());
    writer.close();
  }

}
