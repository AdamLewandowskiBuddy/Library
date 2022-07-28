package works.buddy.library.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import works.buddy.library.api.view.AuthorFront;
import works.buddy.library.api.view.BookFront;
import works.buddy.library.dao.AuthorDAO;
import works.buddy.library.dao.BookDAO;
import works.buddy.library.model.Author;
import works.buddy.library.model.Book;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class DefaultBookService implements BookService {

    @Autowired
    @Qualifier("hibernateBookDAO")
    private BookDAO bookDAO;

    @Autowired
    private AuthorDAO authorDAO;

    @Override
    public Collection<BookFront> getBooks() {
        return getBookFronts(bookDAO.getBooks());
    }

    @Override
    public Collection<BookFront> getBooksByAuthorId(Integer authorId) {
        return getBookFronts(bookDAO.getBooksByAuthorId(authorId));
    }

    @Override
    public Collection<BookFront> getBooksByAuthor(String authorFirstName, String authorLastName) {
        return getBookFronts(bookDAO.getBooksByAuthor(new Author(authorFirstName, authorLastName)));
    }

    @Override
    public Collection<BookFront> getBooksByTitle(String title) {
        return getBookFronts(bookDAO.getBooksByTitle(title));
    }

    @Override
    public void createBook(BookFront book) {
        validate(book);
        bookDAO.save(getBook(book));
    }

    private Collection<BookFront> getBookFronts(Collection<Book> booksToMap) {
        Collection<BookFront> mappedBooks = new ArrayList<>();
        for (Book book : booksToMap) {
            mappedBooks.add(new BookFront(book));
        }
        return mappedBooks;
    }

    private AuthorFront getAuthorFront(Author authorToMap) {
        return new AuthorFront(authorToMap);
    }

    private Book getBook(BookFront book) {
        return new Book(book);
    }

    private void validate(BookFront book) {
        AuthorFront author = book.getAuthor();
        if (author == null) {
            throw new IllegalArgumentException("Author cannot be null");
        }
        // tries to get author with this full name - if exists then sets existing author in the book
        AuthorFront existingAuthor = getAuthorFront(authorDAO.getAuthorByFullName(book.getAuthor().getFirstName(), book.getAuthor().getLastName()));
        if (book.getAuthor().equals(existingAuthor)) {
            book.setAuthor(existingAuthor);
        }
    }

}
