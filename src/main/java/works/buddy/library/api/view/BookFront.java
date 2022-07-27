package works.buddy.library.api.view;

import works.buddy.library.model.Book;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "Book")
public class BookFront implements Serializable {

    private Integer id;

    private String title;

    private AuthorFront author;

    public BookFront() {
    }

    public BookFront(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = new AuthorFront(book.getAuthor());
    }

    public BookFront(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    @XmlElement
    public String getTitle() {
        return title;
    }

    @XmlElement
    public AuthorFront getAuthor() {
        return author;
    }

    public void setAuthor(AuthorFront author) {
        this.author = author;
    }
}