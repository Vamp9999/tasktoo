import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.*;
import com.google.gson.JsonObject;
import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter field(s) to display as JSON: ");
            String[] fields = input.nextLine().split(",");
            Set<String> fieldSet = new HashSet<>();
            for (String f : fields) fieldSet.add(f.trim());

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            parser.parse(new File("data.xml"), new DefaultHandler() {
                JsonObject json = null;
                String currentElement = "";

                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    if (qName.equals("user")) {
                        json = new JsonObject();
                    }
                    currentElement = qName;
                }

                public void characters(char[] ch, int start, int length) {
                    if (json != null && fieldSet.contains(currentElement)) {
                        String value = new String(ch, start, length).trim();
                        if (!value.isEmpty()) {
                            json.addProperty(currentElement, value);
                        }
                    }
                }

                public void endElement(String uri, String localName, String qName) {
                    if (qName.equals("user") && json != null) {
                        System.out.println(json.toString());
                        json = null;
                    }
                    currentElement = "";
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
