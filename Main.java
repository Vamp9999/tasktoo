import javax.xml.parsers.*;
import org.w3c.dom.*;
import com.google.gson.JsonObject;
import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter field(s) to display as JSON: ");
            String line = input.nextLine().trim();
            if (line.isEmpty()) {
                System.out.println("No fields entered. Exiting.");
                return;
            }

            String[] fields = line.split(",");
            for (int i = 0; i < fields.length; i++) fields[i] = fields[i].trim();

            File xmlFile = new File("data.xml");
            if (!xmlFile.exists()) {
                System.out.println("Error: XML file not found.");
                return;
            }

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList users = doc.getElementsByTagName("user");

            if (users.getLength() == 0) {
                System.out.println("No user data found in XML.");
                return;
            }

            for (int i = 0; i < users.getLength(); i++) {
                Element user = (Element) users.item(i);
                JsonObject json = new JsonObject();

                for (String field : fields) {
                    NodeList fieldNode = user.getElementsByTagName(field);
                    if (fieldNode.getLength() > 0) {
                        json.addProperty(field, fieldNode.item(0).getTextContent());
                    } else {
                        json.addProperty(field, "[Not found]");
                    }
                }
                System.out.println(json.toString());
            }
        } catch (Exception e) {
            System.out.println("An error occurred while parsing the XML.");
            e.printStackTrace();
        }
    }
}
