package com.expenses.kia.Manager;

import com.expenses.kia.DBWorker;
import com.expenses.kia.DBWorkerWithContext;
import com.expenses.kia.ReadConfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by i.kalinnikov on 03.05.2017.
 */
@WebServlet(name = "AddUserEnter", urlPatterns = "/AddUserEnter")
public class AddUserEnter extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html><body>");

        ServletContext ctx = request.getServletContext();
        String url=(String) ctx.getAttribute("address");

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String rule = request.getParameter("rule");

        String query="INSERT INTO users(login, password, firstname,lastname,rule) VALUES(?,?,?,?,?)";

        DBWorkerWithContext dbWorkerWithContext = (DBWorkerWithContext) ctx.getAttribute("DBManager");
        Connection conn=dbWorkerWithContext.getConnection();
        PreparedStatement preparedStatement=null;
        try {

            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1,login);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,firstname);
            preparedStatement.setString(4,lastname);
            preparedStatement.setString(5,rule);
            preparedStatement.execute();

            out.println("<h2>User was added completed successful!</h2>");

            out.println("<form action=\"http://"+url+"/Expenses_war_exploded/AddUser\" method=\"post\">");
            out.println("<input type=\"hidden\" name=\"rule\" value=\"M\"></input>");
            out.println("<input type=\"submit\" value=\"OK\"></input>");
            out.println("</form>");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        out.println("</body></html>");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
