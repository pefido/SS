package web;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Authenticator;
import util.Template;

import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private Authenticator auth;
  /**
   * @throws SQLException 
   * @throws ClassNotFoundException 
   * @see HttpServlet#HttpServlet()
   */
  public LoginServlet() throws ClassNotFoundException, SQLException {
    super();
    auth = new Authenticator();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter writer = response.getWriter();
    Template template = new Template(this, "/templates/login.html");
    writer.write(template.out());
    writer.close();
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String email = request.getParameter("email");
    String pw = request.getParameter("password");
    try {
      HttpSession session = request.getSession();
      try{
        String cenas = auth.login(email, pw).getUsername();
        System.out.println(cenas);
        session.setAttribute("user", cenas);
        response.sendRedirect("/MyServlet/");
      } catch(Exception e){
        System.out.println(e.getMessage());
        response.sendRedirect("/MyServlet/login");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
