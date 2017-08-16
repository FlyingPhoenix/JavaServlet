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
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by i.kalinnikov on 03.05.2017.
 */
@WebServlet(name = "DropUser", urlPatterns = "/DropUser")
public class DropUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html><body>");

        ServletContext ctx = request.getServletContext();
        String url=(String) ctx.getAttribute("address");

        String id=(String)request.getParameter("id");
        String query = "delete from users where id='" + id + "'";

        DBWorkerWithContext dbWorkerWithContext = (DBWorkerWithContext) ctx.getAttribute("DBManager");
        Connection conn = dbWorkerWithContext.getConnection();
        Statement statement = null;

        try {
            statement = conn.createStatement();
            int rs = statement.executeUpdate(query);
            if (rs>0) { out.println("<h2>Operation was completed successful!</h2>"); }
            else {out.println("<h2>User wasn't found!</h2>"); }
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
