package web;

import java.io.*;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

    HttpSession session = request.getSession(false);

    String title = "Welcome Back to my website";
    Integer visitCount = new Integer(0);
    String visitCountKey = new String("visitCount");
    String userIDKey = new String("userID");
    String userID = new String("ABCD");

    System.out.println(title);


    boolean logged = false;
    String name = "Campinhos";
    response.setContentType("text/html");
    PrintWriter writer = response.getWriter();
    Template template = null;

    if (session == null) {
      session = request.getSession();
      template = new Template(this, "/templates/index.html");
      session.setAttribute(userIDKey, name);
      userID = (String)session.getAttribute(userIDKey);
    }
    else {
      template = new Template(this, "/templates/account.html");
      template.assign("name", name);
      userID = (String)session.getAttribute(userIDKey);
    }
    writer.write(template.out());
    writer.close();
  }

}
