package com.expenses.kia.Manager;

import com.expenses.kia.ReadConfig;

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
 * Created by i.kalinnikov on 03.05.2017.
 */
@WebServlet(name = "AddUser", urlPatterns = "/AddUser")
public class AddUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html><body>");

        ServletContext ctx = request.getServletContext();
        String url=(String) ctx.getAttribute("address");

        out.println("<h2>Add new user:</h2>");
        out.println("<form action=\"http://"+url+"/Expenses_war_exploded/AddUserEnter\" method=\"post\">");
            out.println("<table align=\"left\">");
            out.println("<tr>");
            out.println("<td>Enter Login: &nbsp;</td>");
            out.println("<td><input type=\"text\" name=\"login\"></input></td>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td>Enter password: &nbsp;</td>");
            out.println("<td><input type=\"password\" name=\"password\"></input></td>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td>Enter firstname: &nbsp;</td>");
            out.println("<td><input type=\"text\" name=\"firstname\"></input></td>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td>Enter lastname: &nbsp;</td>");
            out.println("<td><input type=\"text\" name=\"lastname\"></input></td>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td>Choose rule: &nbsp;</td>");
            out.println("<td><input type=\"radio\" name=\"rule\" value=\"U\" checked></input>U" +
                        "<input type=\"radio\" name=\"rule\" value=\"M\"></input>M" +
                         "<input type=\"radio\" name=\"rule\" value=\"A\"></input>A"+
                         "</td>");
            out.println("</tr>");
            out.println("</table>");
        out.println("<input type=\"submit\" value=\"OK\"></input>");
        out.println("</form>");

        out.println("<form action=\"http://"+url+"/Expenses_war_exploded/MainMenu\" method=\"post\">");
        out.println("<input type=\"hidden\" name=\"open\" value=\"yes\"></input>");
        out.println("<input type=\"submit\" value=\"Exit\"></input>");
        out.println("</form>");

        out.println("</body></html>");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
