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
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by KIA on 04.05.2017.
 */
@WebServlet(name = "EditRecordEnter", urlPatterns = "/EnterEditRecordUser")
public class EditRecordEnter extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html><body>");

        ServletContext ctx = request.getServletContext();
        String url=(String) ctx.getAttribute("address");

        HttpSession session = request.getSession(false);
        String s=(String) session.getAttribute("id");
        int id=Integer.parseInt(s);

        String login=(String)request.getParameter("login");
        String date=(String)request.getParameter("date");
        String time=(String)request.getParameter("time");
        String desc=(String)request.getParameter("desc");
        int amount=Integer.parseInt(request.getParameter("amount"));
        String comment=(String) request.getParameter("comment");

        String query="update expenses set " +
                "login='"+login+"', " +
                "dd='"+date+"', " +
                "time='"+time+"', " +
                "description='"+desc+"', " +
                "amount="+amount+", " +
                "comment='"+comment+"' " +
                "where id='"+id+"'";

        DBWorkerWithContext dbWorkerWithContext = (DBWorkerWithContext) ctx.getAttribute("DBManager");
        try {
            Statement statement = dbWorkerWithContext.getConnection().createStatement();
            int rs=statement.executeUpdate(query);

            out.println("<h2>Operation was completed successful!</h2>");
            out.println("<form action=\"http://"+url+"/Expenses_war_exploded/ReadUser\" method=\"get\">");
            out.println(" <input type=\"submit\" value=\"OK\"></input>");
            out.println("</form>");
        } catch (SQLException e) {  e.printStackTrace();  }

        out.println("</body></html>");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
