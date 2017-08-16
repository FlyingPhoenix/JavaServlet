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
 * Created by i.kalinnikov on 21.04.2017.
 */
@WebServlet(name = "Register",urlPatterns = "/reg")
public class Register extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html><body>");

        ServletContext ctx = request.getServletContext();
        String url=(String) ctx.getAttribute("address");

        out.println("<h1>Register</h1>");
        out.println("<form action=\"http://"+url+"/Expenses_war_exploded/EnReg\" method=\"post\">");
            out.println("<input type=\"text\" name=\"login\" placeholder=\"Enter your login\"></input>");
            out.println("<br>");
            out.println("<input type=\"password\" name=\"password\" placeholder=\"Enter your password\"></input>");
            out.println("<br>");
            out.println("<input type=\"text\" name=\"firstname\" placeholder=\"Enter your firstname\"></input>");
            out.println("<br>");
            out.println("<input type=\"text\" name=\"lastname\" placeholder=\"Enter your lastname\"></input>");
            out.println("<br>");
            out.println("<input type=\"submit\" value=\"OK\"></input>");
        out.println("</form>");

        //out.println("");
        out.println("</body></html>");
    }
}
