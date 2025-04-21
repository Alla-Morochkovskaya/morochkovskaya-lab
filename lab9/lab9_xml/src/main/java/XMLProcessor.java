import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.io.File;

public class XMLProcessor {
    public static void main(String[] args) {
        try {
            File xmlFile = new File("src/main/resources/library.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            NodeList books = doc.getElementsByTagName("book");
            double totalPrice = 0;

            System.out.println("Список книг:");
            for (int i = 0; i < books.getLength(); i++) {
                Element book = (Element) books.item(i);
                String title = book.getElementsByTagName("title").item(0).getTextContent();
                String author = book.getElementsByTagName("author").item(0).getTextContent();
                int year = Integer.parseInt(book.getElementsByTagName("year").item(0).getTextContent());
                String genre = book.getElementsByTagName("genre").item(0).getTextContent();
                double price = Double.parseDouble(book.getElementsByTagName("price").item(0).getTextContent());
                totalPrice += price;

                System.out.printf("Название: %s, Автор: %s, Год: %d, Жанр: %s, Цена: %.2f%n", title, author, year, genre, price);
            }

            double averagePrice = totalPrice / books.getLength();
            System.out.printf("Средняя цена книг: %.2f%n", averagePrice);

            System.out.println("Фильтрация книг по жанру 'Программирование':");
            for (int i = 0; i < books.getLength(); i++) {
                Element book = (Element) books.item(i);
                String genre = book.getElementsByTagName("genre").item(0).getTextContent();
                if (genre.equals("Программирование")) {
                    String title = book.getElementsByTagName("title").item(0).getTextContent();
                    System.out.println("Название: " + title);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
