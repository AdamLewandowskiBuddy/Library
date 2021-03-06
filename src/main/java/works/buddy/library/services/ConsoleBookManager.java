package works.buddy.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import works.buddy.library.dao.BookDAO;
import works.buddy.library.model.Author;
import works.buddy.library.model.Book;
import works.buddy.library.ui.LibraryFrontend;

import javax.transaction.Transactional;

@Service
@Transactional
public class ConsoleBookManager {

    @Autowired
    @Qualifier("hibernateBookDAO")
    private BookDAO bookDAO;

    @Autowired
    private LibraryFrontend libraryFrontend;

    public void run() {
        printMainMenu();
        String response = getResponse();
        while (true) {
            switch (response) {
                case "1":
                    libraryFrontend.listBooks(bookDAO.getBooks());
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
        libraryFrontend.listBooks(bookDAO.getBooksByAuthorId(Integer.valueOf(getResponse())));
    }

    private void getBooksByTitle() {
        libraryFrontend.askBookName();
        libraryFrontend.listBooks(bookDAO.getBooksByTitle(getResponse()));
    }

    private void getBooksByAuthor() {
        libraryFrontend.askAuthorFirstName();
        String firstName = getResponse();
        libraryFrontend.askAuthorLastName();
        libraryFrontend.listBooks(bookDAO.getBooksByAuthor(new Author(firstName, getResponse())));
    }
}
