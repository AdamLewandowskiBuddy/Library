package works.buddy.library.app.console.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import works.buddy.library.dao.AuthorDAO;
import works.buddy.library.dao.BookDAO;
import works.buddy.library.model.Author;
import works.buddy.library.model.Book;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ConsoleBookManager {

    @Autowired
    @Qualifier("hibernateBookDAO")
    private BookDAO bookDAO;

    @Autowired
    private AuthorDAO AuthorDAO;

    @Autowired
    private LibraryFrontend libraryFrontend;

    public void run() {
        printMainMenu();
        String response = getResponse();
        while (true) {
            switch (response) {
                case "1":
                    libraryFrontend.listBooks(getSortedBooks());
                    break;
                case "2":
                    getBooksByAuthorId();
                    break;
                case "3":
                    getBooksByAuthor();
                    break;
                case "4":
                    getBooksByTitle();
                    break;
                case "5":
                    addBook();
                    break;
                case "6":
                    getAuthorByFullName();
                    break;
                case "exit":
                    libraryFrontend.sayGoodBye();
                    return;
                default:
                    libraryFrontend.handleErrorResponse();
            }
            printMainMenu();
            response = getResponse();
        }
    }

    public List<Book> getSortedBooks() {
        List<Book> sortedBooks = bookDAO.findAll();
        sortedBooks.sort((o1, o2) -> Integer.compare(o1.getAuthor().getLastName().compareTo(o2.getAuthor().getLastName()), 0));
        return sortedBooks;
    }

    private void getAuthorByFullName() {
        libraryFrontend.askAuthorFirstName();
        String firstName = getResponse();
        libraryFrontend.askAuthorLastName();
        libraryFrontend.listAuthor(AuthorDAO.findByFullName(firstName, getResponse()));
    }

    private void printMainMenu() {
        libraryFrontend.printMainMenu();
    }

    private String getResponse() {
        return libraryFrontend.getResponse();
    }

    private void addBook() {
        libraryFrontend.askBookName();
        String bookName = getResponse();
        libraryFrontend.askAuthorFirstName();
        String firstName = getResponse();
        libraryFrontend.askAuthorLastName();
        bookDAO.save(new Book(bookName, new Author(firstName, getResponse())));
    }

    private void getBooksByAuthorId() {
        libraryFrontend.askId();
        libraryFrontend.listBooks(bookDAO.findByAuthorId(Integer.valueOf(getResponse())));
    }

    private void getBooksByTitle() {
        libraryFrontend.askBookName();
        libraryFrontend.listBooks(bookDAO.findByTitle(getResponse()));
    }

    private void getBooksByAuthor() {
        libraryFrontend.askAuthorFirstName();
        String firstName = getResponse();
        libraryFrontend.askAuthorLastName();
        libraryFrontend.listBooks(bookDAO.findByAuthor(new Author(firstName, getResponse())));
    }
}
