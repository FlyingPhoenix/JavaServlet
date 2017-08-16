package com.expenses.kia.User.Filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by i.kalinnikov on 02.05.2017.
 */
@WebFilter(filterName = "OpenEditRecUser", urlPatterns = "/EnterEditRecordUser")
public class OpenEditRecUser implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        ServletContext ctx = req.getServletContext();
        String url=(String) ctx.getAttribute("address");

        String login=(String)req.getParameter("login");
        String date=(String)req.getParameter("date");
        String time=(String)req.getParameter("time");
        String desc=(String)req.getParameter("desc");
        String amount=(String)req.getParameter("amount");
        // int amount=Integer.parseInt(req.getParameter("amount"));
        String comment=(String)req.getParameter("comment");
        String id=(String)req.getParameter("id");

        if ((login.compareTo("")!=0) && (date.compareTo("")!=0) && (time.compareTo("")!=0) &&
            (desc.compareTo("")!=0) && (amount.compareTo("")!=0) && (comment.compareTo("")!=0))
            { chain.doFilter(req, resp);}
        else {
            PrintWriter out = resp.getWriter();
            resp.setContentType("text/html");
            out.println("<html><body>");
            out.println("<h1>Incorrect data!</h1>");
            out.println("<form action=\"http://"+url+"/Expenses_war_exploded/EditRecUser\" method=\"post\">");
            out.println("<input type=\"hidden\" name=\"id\" value=\""+id+"\"></input>");
            out.println("<input type=\"submit\" value=\"Come back\"></input>");
            out.println(" </form>");
            out.println("</body></html>");
        };
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
