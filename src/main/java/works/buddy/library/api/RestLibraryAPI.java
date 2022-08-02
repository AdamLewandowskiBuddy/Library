package works.buddy.library.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import works.buddy.library.api.services.AuthorService;
import works.buddy.library.api.services.BookService;
import works.buddy.library.api.view.AuthorFront;
import works.buddy.library.api.view.AuthorsFront;
import works.buddy.library.api.view.BookFront;
import works.buddy.library.api.view.BooksFront;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("library")
@Produces("application/json")
@Consumes("application/json")
@Service
public class RestLibraryAPI implements LibraryAPI {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Override
    @GET
    @Path("books")
    public BooksFront getBooks() {
        return bookService.getBooks();
    }

    @Override
    @GET
    @Path("books/{id}")
    public BookFront getBook(@PathParam("id") String bookId) {
        return bookService.getBook(bookId);
    }

    @Override
    @POST
    @Path("books")
    public Response createBook(BookFront book) {
        return Response.ok(bookService.createBook(book)).build();
    }

    @Override
    @PUT
    @Path("books/{id}")
    public Response updateBook(@PathParam("id") String id, BookFront book) {
        BookFront savedBookFront = bookService.updateBook(id, book);
        return Response.ok(savedBookFront).build();
    }

    @Override
    @DELETE
    @Path("books/{id}")
    public Response deleteBook(@PathParam("id") String id){
        return Response.ok(bookService.deleteBook(id)).build();
    }


    @Override
    @GET
    @Path("authors")
    public AuthorsFront getAuthors() {
        return authorService.getAuthors();
    }

    @Override
    @GET
    @Path("authors/{id}")
    public AuthorFront getAuthor(@PathParam("id") String authorId) {
        return authorService.getAuthor(authorId);
    }

    @Override
    @POST
    @Path("authors")
    public Response createAuthor(AuthorFront author) {
        AuthorFront savedAuthor = authorService.createAuthor(author);
        return Response.ok(savedAuthor).build();
    }

    @Override
    @PUT
    @Path("authors/{id}")
    public Response updateAuthor(@PathParam("id") String id, AuthorFront author)
    {
        AuthorFront returned = authorService.updateAuthor( id, author);
        return Response.ok(returned).build();
    }
}
