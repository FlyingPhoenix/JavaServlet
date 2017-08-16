package com.expenses.kia;

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
 * Created by KIA on 04.05.2017.
 */
@WebServlet(name = "MainMenu", urlPatterns = "/MainMenu")
public class MainMenu extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html><body>");

        ServletContext ctx = request.getServletContext();
        String url=(String) ctx.getAttribute("address");

        String login;
        HttpSession session = request.getSession(true);
        login=(String)session.getAttribute("login");

        if (login==null) {
            login = request.getParameter("login");
            session.setAttribute("login", login);
        }

        // выясняем - какая роль у пользователя
        String rule=(String)session.getAttribute("rule");
        if (rule==null) { // если роли в сессии нет - ее нужно узнать
            String query = "select * from users where login=" + "'" + login + "'";

            DBWorkerWithContext dbWorkerWithContext = (DBWorkerWithContext) ctx.getAttribute("DBManager");
            Connection conn = dbWorkerWithContext.getConnection();
            Statement statement = null;

            try {
                statement = conn.createStatement();
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    rule = rs.getString("rule");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if ((rule.compareTo("U")==0) || (rule.compareTo("A")==0)) {
            session.setAttribute("rule", rule);


            out.println("<h2>Main menu:</h2>");
            // добавить новую запись
            out.println("<form action=\"http://"+url+"/Expenses_war_exploded/AddRecordUser\" method=\"get\">");
            out.println("<input type=\"submit\" value=\"Add new record\"></input>");
            out.println("</form>");
            // просмотр всех записей пользователя
            out.println("<form action=\"http://"+url+"/Expenses_war_exploded/ReadUser\" method=\"get\">");
            out.println(" <input type=\"submit\" value=\"Show records\"></input>");
            out.println("</form>");
            // Статистика за неделю
            out.println("<form action=\"http://"+url+"/Expenses_war_exploded/StatisticsUser\" method=\"post\">");
            out.println("<input type=\"submit\" value=\"Show statistics for the week\"></input>");
            out.println("<label>Enter the first day of week: &nbsp;</label>");
            out.println("<input type=\"date\" name=\"date\"></input>");
            out.println("</form>");
        }

        if ((rule.compareTo("M")==0) || (rule.compareTo("A")==0)) { // если это UserManager
            session.setAttribute("rule", rule);

            // добавить новую учетку
            out.println("<form action=\"http://"+url+"/Expenses_war_exploded/AddUser\" method=\"post\">");
            out.println("<input type=\"hidden\" name=\"rule\" value=\"M\"></input>");
            out.println("<input type=\"submit\" value=\"Add new user\"></input>");
            out.println("</form>");
            // просмотр всех пользователей
            out.println("<form action=\"http://"+url+"/Expenses_war_exploded/ReadAllUser\" method=\"post\">");
            out.println("<input type=\"hidden\" name=\"rule\" value=\"M\"></input>");
            out.println("<input type=\"submit\" value=\"Show all users\"></input>");
            out.println("</form>");
            // изменить чью-то учетную запись
            out.println("<form action=\"http://"+url+"/Expenses_war_exploded/UpdateUser\" method=\"post\">");
            out.println("<input type=\"text\" name=\"id\" placeholder=\"Enter id\"></input>");
            out.println("<input type=\"hidden\" name=\"rule\" value=\"M\"></input>");
            out.println("<input type=\"submit\" value=\"Update this user\"></input>");
            out.println("</form>");
            // удалить чью-то учетную запись
            out.println("<form action=\"http://"+url+"/Expenses_war_exploded/DropUser\" method=\"post\">");
            out.println("<input type=\"text\" name=\"id\" placeholder=\"Enter id\"></input>");
            out.println("<input type=\"hidden\" name=\"rule\" value=\"M\"></input>");
            out.println("<input type=\"submit\" value=\"Delete this user\"></input>");
            out.println("</form>");

        }

        // выход
        out.println("<form action=\"http://"+url+"/Expenses_war_exploded/Exit\" method=\"get\">");
        out.println(" <input type=\"submit\" value=\"Exit\"></input>");
        out.println("</form>");
        out.println("</body></html>");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
