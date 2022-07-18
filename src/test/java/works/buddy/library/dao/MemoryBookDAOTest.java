package works.buddy.library.dao;

import org.junit.Before;
import org.junit.Test;
import works.buddy.library.model.Book;

import javax.lang.model.type.NullType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

public class MemoryBookDAOTest {
    private static final List<Book> SAMPLE_BOOKS = new ArrayList<>(
            List.of(new Book("1984", "Scott Chacon and Ben Straub"), new Book("Thinking in Java", "Bruce Eckel")));


    @Test
    public void getBooksTest(){
        MemoryBookDAO tested =  new MemoryBookDAO(SAMPLE_BOOKS);
        assertEquals("Collections should match", SAMPLE_BOOKS, tested.getBooks());
    }

    @Test
    public void getBooksWithNullConstructorTest(){
        MemoryBookDAO tested =  new MemoryBookDAO(null);
        assertEquals("we shouldn't have a null object as a collection", new ArrayList<Book>(), tested.getBooks());
    }

    @Test
    public void saveTest(){
        Book expected = new Book("Jakaś nazwa", "Jakiś autor");
        MemoryBookDAO tested =  new MemoryBookDAO(new ArrayList<>() );
        tested.save(expected);
        Book actual = tested.getBooks().iterator().next();
        assertEquals(1,tested.getBooks().size());
        assertEquals(expected, actual);
    }
    @Test
    public void saveTestWithNullBook(){
        MemoryBookDAO tested =  new MemoryBookDAO(new ArrayList<>() );
        tested.save(null);
        Book actual = tested.getBooks().iterator().next();
        assertEquals(0,tested.getBooks().size());
    }
}
