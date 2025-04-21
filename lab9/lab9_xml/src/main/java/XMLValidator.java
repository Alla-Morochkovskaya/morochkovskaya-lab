import java.io.File;
import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.transform.stream.StreamSource;

public class XMLValidator {
    public static void main(String[] args) {
        File xmlFile = new File("src/main/resources/library.xml");
        File xsdFile = new File("src/main/resources/library.xsd");

        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(xsdFile);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlFile));
            System.out.println("XML документ валиден!");
        } catch (Exception e) {
            System.out.println("Ошибка валидации XML: " + e.getMessage());
        }
    }
}
