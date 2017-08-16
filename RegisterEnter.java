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
import java.sql.*;

/**
 * Created by KIA on 04.05.2017.
 */
@WebServlet(name = "RegisterEnter", urlPatterns = "/EnReg")
public class RegisterEnter extends HttpServlet {
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


        String queryQ="select * from users where login="+"'"+login+"'";
        String s;
        DBWorkerWithContext dbWorkerWithContext = (DBWorkerWithContext) ctx.getAttribute("DBManager");
        Connection conn;
        Statement statement = null;

        try {
            conn = dbWorkerWithContext.getConnection();
            Boolean p=true;

            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(queryQ);

            while (rs.next()) {
                s=rs.getString("login");
                if (s.compareTo(login)==0) { p=false; break; }
            }

            if (p) {
                String queryU="INSERT INTO users(login, password, firstname,lastname,rule) VALUES(?,?,?,?,?)";
                s="U";
                PreparedStatement preparedStatement = conn.prepareStatement(queryU);
                // индексы - уже из НОВОЙ выбранной таблицы - см. INTO...
                preparedStatement.setString(1,login);
                preparedStatement.setString(2,password);
                preparedStatement.setString(3,firstname);
                preparedStatement.setString(4,lastname);
                preparedStatement.setString(5,s);
                preparedStatement.execute();


                out.println("<h2>Registration was completed successful!</h2>");
                out.println("<form action=\"http://"+url+"/Expenses_war_exploded/MainMenu\" method=\"post\">");
                out.println("<input type=\"hidden\" name=\"login\" value=\""+login+"\"></input>");
                out.println("<input type=\"hidden\" name=\"password\" value=\""+password+"\"></input>");
                out.println("<input type=\"hidden\" name=\"open\" value=\"yes\"></input>");
                out.println("<input type=\"submit\" value=\"OK\"></input>");
                out.println("</form>");
            } else {
                out.println("<h2>Sorry, This login is already used!</h1>");
                out.println("<form action=\"http://"+url+"/Expenses_war_exploded/reg\" method=\"get\">");
                out.println("<input type=\"submit\" value=\"OK\"></input>");
                out.println("</form>");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        // out.println("");
        out.println("</body></html>");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
