package com.expenses.kia;

import com.mysql.fabric.jdbc.FabricMySQLDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by i.kalinnikov on 21.04.2017.
 */
public class DBWorker {
    // private static final String url="jdbc:mysql://localhost:3306/dbexpenses";
    // private static final String username="root";
    // private static final String pass="root";


    private Connection connection;

    public DBWorker() throws ParserConfigurationException {
        String url="";
        String username="";
        String pass="";
        // String AbsolutePath = new File("config.xml").getAbsolutePath();
        File xmlFile = new File("D:\\config.xml");
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = f.newDocumentBuilder();
        try {
            Document document = builder.parse(xmlFile);
            // Теперь XML полностью загружен в память в виде объекта Document
            document.getDocumentElement().normalize();
            // создадим из него список объектов url
            NodeList nodeList = document.getElementsByTagName("address");
            // Element element = (Element) node;
            Element element = (Element) nodeList.item(0);
            url=getTagValue("db-url", element);
            username=getTagValue("db-user", element);
            pass=getTagValue("db-pass", element);

        } catch (Exception ex) { System.out.print("Caugth Error!"); }

        try {
            connection = DriverManager.getConnection(url,username,pass);
            if (!connection.isClosed()) { System.out.println("You Have Connection!"); }
            else { System.out.println("Error!"); }

        } catch (SQLException e) {System.err.println("No Driver!"); }
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }

    public Connection getConnection() { return connection;}
}
