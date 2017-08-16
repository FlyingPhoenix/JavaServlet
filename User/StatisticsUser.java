package com.expenses.kia.User;

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
 * Created by i.kalinnikov on 28.04.2017.
 */
@WebServlet(name = "StatisticsUser", urlPatterns = "/StatisticsUser")
public class StatisticsUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html><body>");

        ServletContext ctx = request.getServletContext();
        String url=(String) ctx.getAttribute("address");

        String login="", query="";
        HttpSession session = request.getSession(false);
        login=(String)session.getAttribute("login");
        String rule=(String)session.getAttribute("rule");
        String date=(String)request.getParameter("date");

        if (rule.compareTo("U")==0) {
            query = "select * from expenses where login='" + login + "' AND dd BETWEEN '" + date + "' AND " +
                    "adddate('" + date + "','7') ORDER BY dd";
        }
        if (rule.compareTo("A")==0) {
            query = "select * from expenses where dd BETWEEN '" + date + "' AND " +
                    "adddate('" + date + "','7') ORDER BY dd";
        }

        int k=0,amount=0,sum=0,t=0;
        String s="",dd;
        float[] masSum= new float[1000];
        String[] masDate= new String[1000];

        DBWorkerWithContext dbWorkerWithContext = (DBWorkerWithContext) ctx.getAttribute("DBManager");

        try {
            Statement statement1 = dbWorkerWithContext.getConnection().createStatement();
            ResultSet rs1 = statement1.executeQuery(query);
            out.println("<h2>All records for week:</h2>");
        while (rs1.next()) {
            out.println("<label>"+Integer.toString(rs1.getInt(1))+" "+"</label>");
            out.println("<label>"+rs1.getString(2)+" "+"</label>");
            dd=rs1.getString(3);
            out.println("<label>"+dd+" "+"</label>");
            out.println("<label>"+rs1.getTime(4)+" "+"</label>");
            out.println("<label>"+rs1.getString(5)+" "+"</label>");
            amount=rs1.getInt(6);
            out.println("<label>"+Integer.toString(amount)+" "+"</label>");
            out.println("<label>"+rs1.getString(7)+" "+"</label>");
            out.println("<br>");
            sum=sum+amount;
            if (s.compareTo(dd)!=0) { // перввая запись за этот день
                if (t!=0) { masSum[k]=masSum[k]/t; }
                s=dd; k++; t=1;
                masSum[k]=amount;
                masDate[k]=dd;
            } else { // записи за этот день уже есть
                masSum[k]=masSum[k]+amount; t++;
            }
        }
           if (k>0) { // если есть записи за эту неделю
               out.println("<h2>Average day spending:</h2>");
               for (int i=1; i<=k; i++) {
                   out.println("<label>"+masDate[i]+" average: "+masSum[i]+"</label>");
                   out.println("<br>");
               }
               out.println("<h2>Total amount=" + sum + "</h2>");
           }
           if (k==0) {
               out.println("<h2>There were not expenses this week</h2>");
           }

            out.println("<h2>Operation was completed successful!</h2>");
            out.println("<form action=\"http://"+url+"/Expenses_war_exploded/MainMenu\" method=\"post\">");
            out.println("<input type=\"hidden\" name=\"open\" value=\"yes\"></input>");
            out.println(" <input type=\"submit\" value=\"Main Menu\"></input>");
            out.println("</form>");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        out.println("</body></html>");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
