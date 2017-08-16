package com.expenses.kia.User.CRUD;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by KIA on 04.05.2017.
 */
@WebServlet(name = "AddRecord", urlPatterns = "/AddRecordUser")
public class AddRecord extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html><body>");

        ServletContext ctx = request.getServletContext();
        String url=(String) ctx.getAttribute("address");

        HttpSession session = request.getSession(false);
        String login=(String)session.getAttribute("login");
        String rule=(String)session.getAttribute("rule");
        out.println("<h2>Add new record:</h2>");
        out.println("<form action=\"http://"+url+"/Expenses_war_exploded/EnterRecordUser\" method=\"post\">");
        out.println("<table align=\"left\">");
        if (rule.compareTo("A")==0) {
            out.println("<tr>");
            out.println("<td>Enter user:</td>");
            out.println("<td><input type=\"text\" name=\"login\"></input></td>");
            out.println("</tr>");
        }
        if (rule.compareTo("U")==0) {
            out.println("<input type=\"hidden\" name=\"login\" value=\""+login+"\"></input>");
        }
        out.println("<tr>");
        out.println("<td>Choose a date</td>");
        out.println("<td><input type=\"date\" name=\"date\"></input></td>");
        out.println("</tr>");

        out.println("<tr>");
        out.print("<td>Choose a time</td>");
        out.println("<td><input type=\"time\" name=\"time\"></input></td>");
        out.println("</tr>");
        out.println("<tr>");
        out.print("<td>Add description</td>");
        out.println("<td><input type=\"text\" name=\"desc\"></input></td>");
        out.println("</tr>");
        out.println("<tr>");
        out.print("<td>Add amount</td>");
        out.println("<td><input type=\"text\" name=\"amount\"></input></td>");
        out.println("</tr>");
        out.println("<tr>");
        out.print("<td>Add comment </td>");
        out.println("<td><input type=\"text\" name=\"comment\"></input></td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("<input type=\"submit\" value=\"OK\"></input>");
        out.println("</form>");
        // Форма для выхода в основное меню
        out.println("<form action=\"http://"+url+"/Expenses_war_exploded/MainMenu\" method=\"post\">");
        out.println("<input type=\"hidden\" name=\"open\" value=\"yes\"></input>");
        out.println("<input type=\"submit\" value=\"Exit\"></input>");
        out.println("</form>");

        out.println("</body></html>");
    }
}
