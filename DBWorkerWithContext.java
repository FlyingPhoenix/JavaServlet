package com.expenses.kia;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by i.kalinnikov on 19.05.2017.
 */
public class DBWorkerWithContext {

    private String dbURL;
    private String user;
    private String password;
    private Connection connection;

    public DBWorkerWithContext(String url, String user, String password) {
        this.dbURL=url;
        this.user=user;
        this.password=password;

        try {
            connection = DriverManager.getConnection(url,user,password);
            if (!connection.isClosed()) { System.out.println("You Have Connection!"); }
            else { System.out.println("Error!"); }

        } catch (SQLException e) {System.err.println("No Driver!"); }
    }

    public Connection getConnection() { return connection;}
}
