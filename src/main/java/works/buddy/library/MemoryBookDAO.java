package works.buddy.library;

import java.util.Collection;

public class MemoryBookDAO implements BookDAO {

    private Collection<Book> books;

    public MemoryBookDAO(Collection<Book> books) {
        this.books = books;
    }

    @Override
    public void save(Book book) {
        this.books.add(book);
    }

    @Override
    public Collection<Book> getBooks() {
        return books;
    }
}
