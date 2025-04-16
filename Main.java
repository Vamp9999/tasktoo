import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter field(s) to display (comma separated): ");
            String[] selectedFields = input.nextLine().split(",");

            for (int i = 0; i < selectedFields.length; i++) {
                selectedFields[i] = selectedFields[i].trim();
            }

            File xmlFile = new File("data.xml");
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList users = doc.getElementsByTagName("user");

            for (int i = 0; i < users.getLength(); i++) {
                Element user = (Element) users.item(i);
                System.out.println("User " + (i + 1) + ":");
                for (String field : selectedFields) {
                    NodeList fieldNode = user.getElementsByTagName(field);
                    if (fieldNode.getLength() > 0) {
                        System.out.println(field + ": " + fieldNode.item(0).getTextContent());
                    } else {
                        System.out.println(field + ": [Not found]");
                    }
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}