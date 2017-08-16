package com.expenses.kia;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;

/**
 * Created by KIA on 12.05.2017.
 */
public class ReadConfig {
    private String s;
    public ReadConfig() throws ParserConfigurationException {
        // String AbsolutePath = new File("D:\\config.xml").getAbsolutePath();
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

            s=getTagValue("url", element);

        } catch (Exception ex) { System.out.print("Caugth Error!"); }

    }

    // получаем значение элемента по указанному тегу
    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }
    // передаем url программу
    public String getURL() { return s;}
}
