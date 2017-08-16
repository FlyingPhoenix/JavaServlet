package com.expenses.kia.CommonFilters;

import com.expenses.kia.DBWorker;
import com.expenses.kia.DBWorkerWithContext;
import com.expenses.kia.ReadConfig;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by i.kalinnikov on 28.04.2017.
 */
@WebFilter(filterName = "OpenMainMenu", urlPatterns = "/MainMenu")
public class OpenMainMenu implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String open = req.getParameter("open");
        boolean check=false;
        ServletContext ctx = req.getServletContext();

        if (open.compareTo("yes")!=0) {
            if (login.compareTo("") != 0 && password.compareTo("") != 0) {

                /*
                DBWorker dbWorker = null;
                try {
                    dbWorker = new DBWorker();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } */

                // ServletContext ctx = req.getServletContext(); Вынесено из IF чтобы ctx был виден ниже
                DBWorkerWithContext dbWorkerWithContext = (DBWorkerWithContext) ctx.getAttribute("DBManager");
                Connection conn;
                Statement statement = null;
                conn = dbWorkerWithContext.getConnection();

                String queryQ = "select password from users where login=" + "'" + login + "'";

                try {
                    statement = conn.createStatement();
                    ResultSet rs = statement.executeQuery(queryQ);

                    PrintWriter out = resp.getWriter();
                    resp.setContentType("text/html");
                    out.println("<html><body>");
                    out.println("<h1>You just have used filtr OpenMainMenu!</h1>");
                    out.println("</body></html>");

                    String s;
                    while (rs.next()) {
                        s = rs.getString("password");
                        if (s.compareTo(password) == 0) {
                            check = true;
                            break;
                        }
                    }

                    // conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else { check=true; } // если уже авторизовывался

        if (check) { chain.doFilter(req, resp); }
        else {
            PrintWriter out = resp.getWriter();
            resp.setContentType("text/html");
            out.println("<html><body>");

            /*
            String url="";
            try {
                ReadConfig config = new ReadConfig();
                url=config.getURL();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } */

            String url=(String) ctx.getAttribute("address");
            out.println("<h1>Invalid username or password!</h1>");
            out.println("<form action=\"http://"+url+"/Expenses_war_exploded/start\">");
            out.println("<input type=\"submit\" value=\"Come back\"></input>");
            out.println("</form>");
            out.println("</body></html>");
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
