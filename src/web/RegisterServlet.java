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
import model.Account;
import util.Template;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private Authenticator auth;

  /**
   * @throws Exception 
   * @see HttpServlet#HttpServlet()
   */
  public RegisterServlet() throws Exception {
    super();
    auth = new Authenticator();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      HttpSession session = request.getSession();
      if(!session.isNew()){
        Account user = auth.login(request, response);
        if(auth.isAdmin(user)) {
          response.setContentType("text/html");
          PrintWriter writer = response.getWriter();
          Template template = new Template(this, "/templates/register.html");
          writer.write(template.out());
          writer.close();
        }
        else{
          response.sendRedirect("/MyServlet/");
        }
      }
      else {
        session.invalidate();
        response.sendRedirect("/MyServlet/");
      }
    } catch (Exception e1) {e1.printStackTrace();}
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      HttpSession session = request.getSession();
      if(!session.isNew()){
        Account user = auth.login(request, response);
        if(auth.isAdmin(user)) {
          String email = request.getParameter("email");
          String pw = request.getParameter("password");
          String pw2 = request.getParameter("password2");

          try {
            auth.create_account(email,pw,pw2);
            response.sendRedirect("/MyServlet/");
          } catch (Exception e) {
            System.out.println(e.getMessage());
            response.sendRedirect("/MyServlet/register");
          }
        }
        else{
          response.sendRedirect("/MyServlet/");
        }
      }
      else {
        session.invalidate();
        response.sendRedirect("/MyServlet/");
      }
    } catch (Exception e1) {e1.printStackTrace();}
  }

}
