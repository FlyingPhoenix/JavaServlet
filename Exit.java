package com.expenses.kia;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by KIA on 22.04.2017.
 */
@WebServlet(name = "Exit", urlPatterns = "/Exit")
public class Exit extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html><body>");

        HttpSession session = request.getSession(false);
        if (session!=null) { session.invalidate();}

        ServletContext ctx = request.getServletContext();
        String url=(String) ctx.getAttribute("address");
        out.println("<form action=\"http://"+url+"/Expenses_war_exploded/start\" method=\"get\">");
        out.println(" <input type=\"submit\" value=\"You log off\"></input>");
        out.println("</form>");

        out.println("</body></html>");
    }
}
