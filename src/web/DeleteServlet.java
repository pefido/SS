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
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      Account user = auth.login(request, response);
      if (auth.isAdmin(user)) {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        Template template = new Template(this, "/templates/delete.html");
        writer.write(template.out());
        writer.close();
      }
      else {
        response.sendRedirect("/MyServlet"); 
      }
    } catch (AuthenticationErrorException e1) {
      System.out.println(e1.getMessage());
      response.sendRedirect("/MyServlet/login");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    String email = request.getParameter("email");
    try {
      Account user = auth.login(request, response);
      if (auth.isAdmin(user)) {
        try {
          auth.delete_account(email);
          response.sendRedirect("/MyServlet/");
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
      }
      else {
        response.sendRedirect("/MyServlet"); 
      }
    } catch (AuthenticationErrorException e1) {
      System.out.println(e1.getMessage());
      response.sendRedirect("/MyServlet/login");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

}