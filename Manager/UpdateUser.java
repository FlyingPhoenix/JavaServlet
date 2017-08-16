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
 * Created by i.kalinnikov on 03.05.2017.
 */
@WebServlet(name = "UpdateUser", urlPatterns = "/UpdateUser")
public class UpdateUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html><body>");

        ServletContext ctx = request.getServletContext();
        String url=(String) ctx.getAttribute("address");

        String id=(String)request.getParameter("id");
        String query="select * from users where id='"+id+"'";

        DBWorkerWithContext dbWorkerWithContext = (DBWorkerWithContext) ctx.getAttribute("DBManager");
        Connection conn = dbWorkerWithContext.getConnection();
        Statement statement = null;

        try {
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) { // запись можно отредактировать
                HttpSession session = request.getSession(false);
                session.setAttribute("id", id);

                out.println("<h2>Edit user # " + id + ":</h2>");
                out.println("<form action=\"http://"+url+"/Expenses_war_exploded/UpdateUserEnter\" method=\"post\">");
                out.println("<table align=\"left\">");

                out.println("<tr>");
                out.println("<td>Login:</td>");
                out.println("<td><input type=\"text\" name=\"login\" value=\"" + rs.getString(2) + "\"></input></td>");
                out.println("</tr>");

                out.println("<tr>");
                out.print("<td>Password:</td>");
                out.println("<td><input type=\"text\" name=\"password\" value=\"" + rs.getString(3) + "\"></input></td>");
                out.println("</tr>");

                out.println("<tr>");
                out.print("<td>Firstname:</td>");
                out.println("<td><input type=\"text\" name=\"firstname\" value=\"" + rs.getString(4) + "\"></input></td>");
                out.println("</tr>");

                out.println("<tr>");
                out.print("<td>Lastname:</td>");
                out.println("<td><input type=\"text\" name=\"lastname\" value=\"" + rs.getString(5) + "\"></input></td>");
                out.println("</tr>");

                out.println("<tr>");
                out.print("<td>Rule:</td>");

                if (rs.getString(6).compareTo("U")==0) {
                    out.println("<td><input type=\"radio\" name=\"rule\" value=\"U\" checked></input>U" +
                            "<input type=\"radio\" name=\"rule\" value=\"M\"></input>M" +
                            "<input type=\"radio\" name=\"rule\" value=\"A\"></input>A" +
                            "</td>");
                }
                if (rs.getString(6).compareTo("M")==0) {
                    out.println("<td><input type=\"radio\" name=\"rule\" value=\"U\"></input>U" +
                            "<input type=\"radio\" name=\"rule\" value=\"M\" checked></input>M" +
                            "<input type=\"radio\" name=\"rule\" value=\"A\"></input>A" +
                            "</td>");
                }
                if (rs.getString(6).compareTo("A")==0) {
                    out.println("<td><input type=\"radio\" name=\"rule\" value=\"U\"></input>U" +
                            "<input type=\"radio\" name=\"rule\" value=\"M\"></input>M" +
                            "<input type=\"radio\" name=\"rule\" value=\"A\" checked></input>A" +
                            "</td>");
                }

                out.println("</tr>");
                out.println("</table>");
                out.println("<input type=\"hidden\" name=\"id\" value=\"" + id + "\"></input>");
                out.println("<input type=\"submit\" value=\"OK\"></input>");
                out.println("</form>");

                // вернуться назад
                out.println("<form action=\"http://"+url+"/Expenses_war_exploded/MainMenu\" method=\"post\">");
                out.println("<input type=\"hidden\" name=\"open\" value=\"yes\"></input>");
                out.println("<input type=\"submit\" value=\"Come back\"></input>");
                out.println("</form>");
            } else {
                out.println("<h2>This record wasn't found</h2>");
                out.println("<form action=\"http://"+url+"/Expenses_war_exploded/MainMenu\" method=\"post\">");
                out.println("<input type=\"hidden\" name=\"open\" value=\"yes\"></input>");
                out.println("<input type=\"submit\" value=\"Come back\"></input>");
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
