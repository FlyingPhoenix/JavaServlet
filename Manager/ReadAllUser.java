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
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by KIA on 03.05.2017.
 */
@WebServlet(name = "ReadAllUser", urlPatterns = "/ReadAllUser")
public class ReadAllUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html><body>");

        ServletContext ctx = request.getServletContext();
        String url=(String) ctx.getAttribute("address");

        // HttpSession session = request.getSession(false);
        String query="select * from users";

        DBWorkerWithContext dbWorkerWithContext = (DBWorkerWithContext) ctx.getAttribute("DBManager");
        Connection conn = dbWorkerWithContext.getConnection();
        Statement statement = null;
        try {
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            out.println("<h2>All users:</h2>");
            while (rs.next()) {
                out.println("<label>"+rs.getInt(1)+" "+"</label>");
                out.println("<label>"+rs.getString(2)+" "+"</label>");
                out.println("<label>"+rs.getString(3)+" "+"</label>");
                out.println("<label>"+rs.getString(4)+" "+"</label>");
                out.println("<label>"+rs.getString(5)+" "+"</label>");
                out.println("<label>"+rs.getString(6)+" "+"</label>");
                out.println("<br>");
            }
            out.println("<form action=\"http://"+url+"/Expenses_war_exploded/MainMenu\" method=\"post\">");
            out.println("<input type=\"hidden\" name=\"open\" value=\"yes\"></input>");
            out.println("<input type=\"submit\" value=\"Come back\"></input>");
            out.println("</form>");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        out.println("</body></html>");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            }
}
