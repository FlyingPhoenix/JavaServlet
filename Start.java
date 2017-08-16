package com.expenses.kia;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by KIA on 13.05.2017.
 */
@WebServlet(name = "Start", urlPatterns = "/start")
public class Start extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html><body>");

        ServletContext ctx = request.getServletContext();
        String url=(String) ctx.getAttribute("address");

        // Ввод логина и пароля
        out.println("<h2>HELLO! It is your book of expenses</h2>");
        out.println("<h3>Use your login or register:</h3>");
        out.println("<form action=\"http://"+url+"/Expenses_war_exploded/MainMenu\" method=\"post\">");
            out.println("<input type=\"text\" name=\"login\" placeholder=\"Enter your login\"></input>");
            out.println("<input type=\"password\" name=\"password\" placeholder=\"Enter your password\"></input>");
            out.println("<input type=\"hidden\" name=\"open\" value=\"no\"></input>");
            out.println("<input type=\"submit\" value=\"OK\"></input>");
        out.println("</form>");

        // Регистрация
        out.println("<form action=\"http://"+url+"/Expenses_war_exploded/reg\" method=\"get\">");
            out.println("<input type=\"submit\" value=\"Register\"></input>");
        out.println("</form>");

        out.println("</body></html>");
    }
}
