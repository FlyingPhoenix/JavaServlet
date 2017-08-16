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
@WebServlet(name = "EditRecord", urlPatterns = "/EditRecUser")
public class EditRecord extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html><body>");

        ServletContext ctx = request.getServletContext();
        String url=(String) ctx.getAttribute("address");

        HttpSession session = request.getSession(false);
        String login=(String)session.getAttribute("login");
        String rule=(String)session.getAttribute("rule");
        String id=(String)request.getParameter("id");

        String query="";
        if (rule.compareTo("U")==0) { query="select * from expenses where login='"+login+"' AND id='"+id+"'"; }
        if (rule.compareTo("A")==0) { query="select * from expenses where id='"+id+"'"; }

        DBWorkerWithContext dbWorkerWithContext = (DBWorkerWithContext) ctx.getAttribute("DBManager");
        try {
            Statement statement = dbWorkerWithContext.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) { // запись можно отредактировать
                session.setAttribute("id", id);

                out.println("<h2>Edit record # "+id+":</h2>");
                out.println("<form action=\"http://"+url+"/Expenses_war_exploded/EnterEditRecordUser\" method=\"post\">");
                out.println("<table align=\"left\">");

                if (rule.compareTo("A")==0) {
                    out.println("<tr>");
                    out.println("<td>Login:</td>");
                    out.println("<td><input type=\"text\" name=\"login\" value=\""+rs.getString(2)+"\"></input></td>");
                    out.println("</tr>");
                }
                if (rule.compareTo("U")==0) {
                    out.println("<input type=\"hidden\" name=\"login\" value=\""+login+"\"></input>");
                }

                out.println("<tr>");
                out.println("<td>Date:</td>");
                out.println("<td><input type=\"date\" name=\"date\" value=\""+rs.getDate(3)+"\"></input></td>");
                out.println("</tr>");

                out.println("<tr>");
                out.print("<td>Time:</td>");
                out.println("<td><input type=\"time\" name=\"time\" value=\"" + rs.getTime(4) + "\"></input></td>");
                out.println("</tr>");
                out.println("<tr>");

                out.print("<td>Description:</td>");
                out.println("<td><input type=\"text\" name=\"desc\" value=\"" + rs.getString(5) + "\"></input></td>");
                out.println("</tr>");
                out.println("<tr>");

                out.print("<td>Amount:</td>");
                out.println("<td><input type=\"text\" name=\"amount\" value=\"" + rs.getInt(6) + "\"></input></td>");
                out.println("</tr>");
                out.println("<tr>");

                out.print("<td>Comment:</td>");
                out.println("<td><input type=\"text\" name=\"comment\" value=\"" + rs.getString(7) + "\"></input></td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("<input type=\"hidden\" name=\"id\" value=\""+id+"\"></input>");
                out.println("<input type=\"submit\" value=\"OK\"></input>");
                out.println("</form>");

                out.println("<form action=\"http://"+url+"/Expenses_war_exploded/ReadUser\" method=\"get\">");
                out.println(" <input type=\"submit\" value=\"Come back\"></input>");
                out.println("</form>");

            } else {
                out.println("<h2>This record wasn't found</h2>");
                out.println("<form action=\"http://"+url+"/Expenses_war_exploded/ReadUser\" method=\"get\">");
                out.println(" <input type=\"submit\" value=\"OK\"></input>");
                out.println("</form>");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        out.println("</body></html>");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
