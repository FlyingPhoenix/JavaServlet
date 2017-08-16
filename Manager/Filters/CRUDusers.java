package com.expenses.kia.Manager.Filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by i.kalinnikov on 03.05.2017.
 */
@WebFilter(filterName = "CRUDusers")
public class CRUDusers implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        String s=req.getParameter("rule");
        if ((s.compareTo("M")==0) || (s.compareTo("A")==0)){ chain.doFilter(req, resp); }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
