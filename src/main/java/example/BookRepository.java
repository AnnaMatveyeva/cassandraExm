package example;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.UDTValue;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    private Session session;
    private ResultSet rs;

    BookRepository(Session session) {
        this.session = session;
    }

    public void insertBookByTitle(Book book) {
        PreparedStatement statement = session
            .prepare("INSERT INTO library.books (id, title, subject, author) VALUES ( ?,?,?,?);");

        BoundStatement boundStatement = statement
            .bind().setInt("id", book.getId()).setString("title", book.getTitle())
            .setString("subject", book.getSubject()).setUDTValue("author", toUdt(book.getAuthor()));
        session.execute(boundStatement);
    }

    public void createTableBooks() {
        String query =
            "CREATE TABLE IF NOT EXISTS library.books (id int, title text, subject text, author frozen<author>, PRIMARY KEY (id,title));";

        session.execute(query);
    }

    public List<Book> selectByTitle(String title) {
        PreparedStatement statement = session
            .prepare("SELECT * FROM library.books WHERE title=?");
        BoundStatement boundStatement = statement.bind(title);
        rs = session.execute(boundStatement);
        List<Book> books = new ArrayList<>();
        rs.forEach(row -> books.add(
            new Book(row.getInt("id"), row.getString("title"), row.getString("subject"),
                toAuthor(row.getUDTValue("author")))));
        return books;
    }

    public List<Book> selectAll() {
        String statement = "SELECT * FROM library.books;";
        rs = session.execute(statement);
        List<Book> books = new ArrayList<>();
        rs.forEach(row -> books.add(new Book(row.getInt("id"),
            row.getString("title"), row.getString("subject"),
            toAuthor(row.getUDTValue("author")))));
        return books;
    }


    private Author toAuthor(UDTValue value) {
        return value == null ? null : new Author(
            value.getString("first_name"),
            value.getString("last_name"),
            value.getString("country")
        );
    }

    private UDTValue toUdt(Author value) {
        UDTValue udtValue = session.getCluster().getMetadata().getKeyspace("library")
            .getUserType("author").newValue();
        udtValue.setString("first_name", value.getFirstName())
            .setString("last_name", value.getLastName())
            .setString("country", value.getCountry());
        return udtValue;
    }

}
