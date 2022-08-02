package works.buddy.library.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import works.buddy.library.api.ApiConstants;
import works.buddy.library.api.errors.BadRequestException;
import works.buddy.library.api.view.AuthorFront;
import works.buddy.library.api.view.AuthorsFront;
import works.buddy.library.dao.AuthorDAO;
import works.buddy.library.model.Author;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class DefaultAuthorService implements AuthorService {

    @Autowired
    private AuthorDAO authorDAO;

    @Override
    public AuthorsFront getAuthors() {
        return getAuthorFronts(authorDAO.findAll());
    }

    @Override
    public AuthorFront getAuthor(Integer authorId) {
        return getAuthorFront(authorDAO.findOne(authorId));
    }

    @Override
    public AuthorFront createAuthor(AuthorFront authorFront) {
        validate(authorFront);
        Author author = getAuthor(authorFront);
        authorDAO.save(author);
        return getAuthorFront(author);
    }

    private Author getAuthor(AuthorFront author) {
        return new Author(author);
    }

    private AuthorFront getAuthorFront(Author author) {
        return new AuthorFront(author);
    }

    private AuthorsFront getAuthorFronts(Collection<Author> authorsToMap) {
        Collection<AuthorFront> mappedAuthors = new ArrayList<>();
        for (Author author : authorsToMap) {
            mappedAuthors.add(new AuthorFront(author));
        }
        return new AuthorsFront(mappedAuthors);
    }

    private void validate(AuthorFront author) {
        if (author.getFirstName() == null) {
            throw new BadRequestException("Authors first name is required");
        }
        if (author.getFirstName().length() < ApiConstants.MIN_NAME_LENGTH) {
            throw new BadRequestException("Authors first name has to be longer than " + ApiConstants.MIN_NAME_LENGTH + " characters");
        }
        if (author.getFirstName().length() > ApiConstants.MAX_NAME_LENGTH) {
            throw new BadRequestException("Authors first name has to be longer than " + ApiConstants.MAX_NAME_LENGTH + " characters");
        }
        if (author.getLastName() == null) {
            throw new BadRequestException("Authors last name is required");
        }
        if (author.getLastName().length() < ApiConstants.MIN_NAME_LENGTH) {
            throw new BadRequestException("Authors last name has to be longer than " + ApiConstants.MIN_NAME_LENGTH + " characters");
        }
        if (author.getLastName().length() > ApiConstants.MAX_NAME_LENGTH) {
            throw new BadRequestException("Authors last name has to be longer than " + ApiConstants.MAX_NAME_LENGTH + " characters");
        }
    }
}
