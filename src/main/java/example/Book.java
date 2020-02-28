package example;

public class Book {

    private int id;
    private String title;
    private String subject;
    private Author author;

    public Book(int id, String title, String subject, Author author) {
        this.id = id;
        this.title = title;
        this.subject = subject;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
