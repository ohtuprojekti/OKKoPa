/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.cs.okkopa;
import java.io.File;
import javax.xml.parsers.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author phemmila
 */
public class ConfigReader {
    
   public static void main(String[] args) {
       ConfigReader cf = new ConfigReader();
       cf.readConfigXML();
   }
   
   private  void readConfigXML() {

       try {

           File config = new File("src/main/java/fi/helsinki/cs/okkopa/testdocument.xml");

           DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
           DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
           Document doc = dBuilder.parse(config);
           doc.getDocumentElement().normalize();

           NodeList nodes = doc.getElementsByTagName("root");
           
           for (int i = 0; i < nodes.getLength(); i++) {
               Node currentNode = nodes.item(i);
               
               if (currentNode.getNodeType() == Node.ELEMENT_NODE) {

                   System.out.println(currentNode.getNodeValue());
                   
               }
               
                       
           }
           
       } catch (Exception e) {
           System.out.println(e.getMessage());
       }
   }
   
}
