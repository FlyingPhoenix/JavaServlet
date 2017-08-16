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
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by KIA on 04.05.2017.
 */
@WebServlet(name = "ReadRecords", urlPatterns = "/ReadUser")
public class ReadRecords extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html><body>");

        ServletContext ctx = request.getServletContext();
        String url=(String) ctx.getAttribute("address");

        String login="", query="";
        HttpSession session = request.getSession(false);
        String rule=(String)session.getAttribute("rule");
        login=(String)session.getAttribute("login");

        if (rule.compareTo("U")==0) { query="select * from expenses where login="+"'"+login+"'"; }
        if (rule.compareTo("A")==0) { query="select * from expenses"; }

        DBWorkerWithContext dbWorkerWithContext = (DBWorkerWithContext) ctx.getAttribute("DBManager");
        int k=0;
        try {
            Statement statement = dbWorkerWithContext.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(query);
            if (rule.compareTo("U")==0) { out.println("<h2>All records of user "+login+":</h2>"); }
            if (rule.compareTo("A")==0) { out.println("<h2>All records of users:</h2>");}
            while (rs.next()) {
                k++;
                out.println("<label>"+Integer.toString(rs.getInt(1))+" "+"</label>");
                out.println("<label>"+rs.getString(2)+" "+"</label>");
                out.println("<label>"+rs.getDate(3)+" "+"</label>");
                out.println("<label>"+rs.getTime(4)+" "+"</label>");
                out.println("<label>"+rs.getString(5)+" "+"</label>");
                out.println("<label>"+Integer.toString(rs.getInt(6))+" "+"</label>");
                out.println("<label>"+rs.getString(7)+" "+"</label>");
                out.println("<br>");
            }
            out.println("<br>");
            if (k==0) { out.println("<h2>You haven't records</h2>");}

            out.println("<form action=\"http://"+url+"/Expenses_war_exploded/SortRecUser\" method=\"get\">");
            out.println("<input type=\"submit\" value=\"Sort by Date\"></input>");
            out.println("</form>");

            out.println("<form action=\"http://"+url+"/Expenses_war_exploded/FilterRecUser\" method=\"post\">");
            out.println("<label>Filtr between:&nbsp</label>");
            out.println("<input type=\"date\" name=\"date1\"></input>");
            out.println("<label>&nbsp and : &nbsp</label>");
            out.println("<input type=\"date\" name=\"date2\"></input>");
            out.println("<input type=\"submit\" value=\"Filter by Date\"></input>");
            out.println("</form>");

            out.println("<form action=\"http://"+url+"/Expenses_war_exploded/EditRecUser\" method=\"post\">");
            out.println("<input type=\"text\" name=\"id\" placeholder=\"Enter id\"></input>");
            // out.println("<input type=\"hidden\" name=\"login\" value=\""+login+"\"></input>");
            // out.println("<input type=\"hidden\" name=\"rule\" value=\""+rule+"\"></input>");
            out.println(" <input type=\"submit\" value=\"Edit this record\"></input>");
            out.println("</form>");

            out.println("<form action=\"http://"+url+"/Expenses_war_exploded/DropRecUser\" method=\"post\">");
            out.println("<input type=\"text\" name=\"id\" placeholder=\"Enter id\"></input>");
            out.println("<input type=\"hidden\" name=\"login\" value=\""+login+"\"></input>");
            out.println("<input type=\"hidden\" name=\"rule\" value=\""+rule+"\"></input>");
            out.println("<input type=\"submit\" value=\"Drop this record\"></input>");
            out.println("</form>");

            out.println("<form action=\"http://"+url+"/Expenses_war_exploded/MainMenu\" method=\"post\">");
            out.println("<input type=\"hidden\" name=\"open\" value=\"yes\"></input>");
            out.println("<input type=\"submit\" value=\"Exit\"></input>");
            out.println("</form>");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        out.println("</body></html>");
    }
}
