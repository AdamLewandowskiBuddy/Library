package works.buddy.library.api.services;

import works.buddy.library.api.view.BookFront;

import java.util.Collection;

public interface BookService {

    Collection<BookFront> getBooks();
    BookFront getBook(Long id);

    Collection<BookFront> getBooksByAuthorId(Long authorId);

    Collection<BookFront> getBooksByAuthor(String authorFirstName, String authorLastName);

    Collection<BookFront> getBooksByTitle(String title);

    void createBook(BookFront book);
}
