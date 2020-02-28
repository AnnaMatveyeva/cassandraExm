package example;

import com.datastax.driver.core.Session;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;

public class Test {

    private BookRepository bookRepository;
    private Session session;
    CasandraConnector client;
    String keyspaceName = "library";

    @Before
    public void connect() {
        client = new CasandraConnector();
        client.connect("127.0.0.1", 9042);
        this.session = client.getSession();
        bookRepository = new BookRepository(session);
    }

    @org.junit.Test
    public void test() {
//        bookRepository.createTableBooks();
        Author author = new Author("Egor", "Egorov", "BY");
//        Set<String> strings = new HashSet<String>(Arrays.asList("first", "second"));
//        Book b = new Book(1, "Rabbit hat", "Fiction", author);
//        bookRepository.insertBookByTitle(b);
//        strings.add("third");
        Book b = new Book(3, "C####", "Programming", author);
        bookRepository.insertBookByTitle(b);
        bookRepository.selectAll()
            .forEach(book -> System.out.println(
                book.getTitle() + ", " + book.getSubject() + " " + book.getAuthor().getFirstName()
                    + " " + book.getAuthor().getLastName()));

        client.close();
    }
}
