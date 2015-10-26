package web;

import java.io.*;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Authenticator;
import util.Template;

@WebServlet("/")
public class ApplicationServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  Authenticator auth;

  /**
   * @throws Exception 
   * @see HttpServlet#HttpServlet()
   */
  public ApplicationServlet() throws Exception {
    super();
    auth = new Authenticator();
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
      try {
        if (auth.isAdmin(auth.get_account((String)session.getAttribute("user")))) {
          System.out.println("yup");
          template.assign("admin.stuff", "<h3>Admin Stuff</h3>");
          template.assign("admin.register","<a class='btn btn-default' href='register'>Register Account</a>"); 
          template.assign("admin.delete","<a class='btn btn-default' href='delete'>Delete Account</a>"); 
        }
        else {
          template.assign("admin.stuff","");
          template.assign("admin.register","");
          template.assign("admin.delete","");
        }
      } catch (Exception e) {e.printStackTrace();}
      template.assign("name", (String)session.getAttribute("user"));
    }
    writer.write(template.out());
    writer.close();
  }

}
