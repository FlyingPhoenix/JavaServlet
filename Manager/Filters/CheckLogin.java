package com.expenses.kia.Manager.Filters;

import com.expenses.kia.DBWorker;
import com.expenses.kia.DBWorkerWithContext;
import com.expenses.kia.ReadConfig;

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
 * Created by i.kalinnikov on 03.05.2017.
 */
@WebFilter(filterName = "CheckLogin", urlPatterns = "/AddUserEnter")
public class CheckLogin implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        out.println("<html><body>");

        ServletContext ctx = req.getServletContext();
        String url=(String) ctx.getAttribute("address");

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        boolean check=false;

        if ((login.compareTo("")!=0) && (password.compareTo("")!=0)) {

            // String query = "select * from users where login=" + "'" + login + "'";
            String query = "select * from users";

            DBWorkerWithContext dbWorkerWithContext = (DBWorkerWithContext) ctx.getAttribute("DBManager");
            Connection conn = dbWorkerWithContext.getConnection();
            Statement statement = null;

            try {
                statement = conn.createStatement();
                ResultSet rs = statement.executeQuery(query);
                check = true;
                while (rs.next()) {
                    if (rs.getString("login").compareTo(login) == 0) {
                        check = false;
                        break;
                    }
                }

                if (check) {
                    chain.doFilter(req, resp);
                } else {
                    out.println("<h2>Sorry, This login is already used!</h1>");
                    out.println("<form action=\"http://"+url+"/Expenses_war_exploded/AddUser\" method=\"post\">");
                    out.println("<input type=\"hidden\" name=\"rule\" value=\"M\"></input>");
                    out.println("<input type=\"submit\" value=\"OK\"></input>");
                    out.println("</form>");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            out.println("<h2>Login and password cannot be 'null'!</h2>");
            out.println("<form action=\"http://"+url+"/Expenses_war_exploded/AddUser\" method=\"post\">");
            out.println("<input type=\"hidden\" name=\"rule\" value=\"M\"></input>");
            out.println("<input type=\"submit\" value=\"OK\"></input>");
            out.println("</form>");
        }

        out.println("</body></html>");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
