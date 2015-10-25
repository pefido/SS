package util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;

public class Template {

  BufferedReader template;
  Map<String,String> variables = new HashMap<String, String>();

  public Template(HttpServlet servlet, String path) throws FileNotFoundException {
    String file = servlet.getServletContext().getRealPath(path);
    this.template = new BufferedReader(new FileReader(file));
  }

  public void assign(String string, String name) {
    if (name==null) {name = "Null";}
    variables.put(string, name);
  }

  private String replace(String str) {
    String res = str;
    for (Map.Entry<String, String> entry : variables.entrySet()) {
      res = str.replaceAll("\\{\\{"+entry.getKey()+"}}", entry.getValue());
    }
    return res;
  }

  public char[] out() throws IOException {
    String str;
    String res = "";
    while((str = template.readLine())!=null) { res += replace(str); }
    return res.toCharArray();
  }

}
