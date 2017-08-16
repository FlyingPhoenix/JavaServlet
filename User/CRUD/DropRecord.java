package com.expenses.kia.User.CRUD;

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
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by KIA on 04.05.2017.
 */
@WebServlet(name = "DropRecord", urlPatterns = "/DropRecUser")
public class DropRecord extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html><body>");

        ServletContext ctx = request.getServletContext();
        String url=(String) ctx.getAttribute("address");

        String id=(String)request.getParameter("id");
        String query = "delete from expenses where id='" + id + "'";

        DBWorkerWithContext dbWorkerWithContext = (DBWorkerWithContext) ctx.getAttribute("DBManager");

        try {
            Statement statement = dbWorkerWithContext.getConnection().createStatement();
            int rs = statement.executeUpdate(query);
            out.println("<h2>Operation was completed successful!</h2>");
            out.println("<form action=\"http://"+url+"/Expenses_war_exploded/ReadUser\" method=\"get\">");
            out.println(" <input type=\"submit\" value=\"OK\"></input>");
            out.println("</form>");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        out.println("</body></html>");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
