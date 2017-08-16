package com.expenses.kia.User.Filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by i.kalinnikov on 02.05.2017.
 */
@WebFilter(filterName = "OpenEnReg", urlPatterns = "/EnReg")
public class OpenEnReg implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        ServletContext ctx = req.getServletContext();
        String url=(String) ctx.getAttribute("address");

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String firstname = req.getParameter("firstname");
        String lastname = req.getParameter("lastname");
        if ((login.compareTo("")!=0) && (password.compareTo("")!=0) && (firstname.compareTo("")!=0)
                && (lastname.compareTo("")!=0)) { chain.doFilter(req, resp);}
        else {
            PrintWriter out = resp.getWriter();
            resp.setContentType("text/html");
            out.println("<html><body>");
            out.println("<h1>Incorrect data!</h1>");
            out.println("<form action=\"http://"+url+"/Expenses_war_exploded/reg\">");
            out.println("<input type=\"submit\" value=\"Come back\"></input>");
            out.println(" </form>");
            out.println("</body></html>");
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
