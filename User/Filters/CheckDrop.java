package com.expenses.kia.User.Filters;

import com.expenses.kia.DBWorker;
import com.expenses.kia.DBWorkerWithContext;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by i.kalinnikov on 04.05.2017.
 */
@WebFilter(filterName = "CheckDrop", urlPatterns = "/DropRecUser")
public class CheckDrop implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        out.println("<html><body>");

        ServletContext ctx = req.getServletContext();
        String url=(String) ctx.getAttribute("address");

        String login=(String) req.getParameter("login");
        String rule=(String) req.getParameter("rule");
        String id=(String)req.getParameter("id");

        if (id.compareTo("")!=0) {
            DBWorkerWithContext dbWorkerWithContext = (DBWorkerWithContext) ctx.getAttribute("DBManager");
            Connection conn = dbWorkerWithContext.getConnection();
            Statement statement = null;

            if (rule.compareTo("U")==0) {
                try {
                    String query="select * from expenses where login='" + login + "' AND id='" + id + "'";;
                    statement = conn.createStatement();
                    ResultSet rs = statement.executeQuery(query);
                    if (rs.next()) { chain.doFilter(req, resp); }
                    else {
                        out.println("<h2>This record wasn't found</h2>");
                        out.println("<form action=\"http://"+url+"/Expenses_war_exploded/ReadUser\" method=\"get\">");
                        out.println(" <input type=\"submit\" value=\"OK\"></input>");
                        out.println("</form>");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (rule.compareTo("A")==0) {
                try {
                    String query="select * from expenses where id='" + id + "'";;
                    statement = conn.createStatement();
                    ResultSet rs = statement.executeQuery(query);
                    if (rs.next()) { chain.doFilter(req, resp); }
                    else {
                        out.println("<h2>This record wasn't found</h2>");
                        out.println("<form action=\"http://"+url+"/Expenses_war_exploded/ReadUser\" method=\"get\">");
                        out.println(" <input type=\"submit\" value=\"OK\"></input>");
                        out.println("</form>");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } else {
            out.println("<h2>Error! id = null!</h2>");
            out.println("<form action=\"http://"+url+"/Expenses_war_exploded/ReadUser\" method=\"get\">");
            out.println(" <input type=\"submit\" value=\"OK\"></input>");
            out.println("</form>");
        }

        out.println("</body></html>");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
