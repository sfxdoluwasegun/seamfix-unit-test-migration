package com.seamfix.tutorials.test.dao;

import com.seamfix.test.tutorial.jee.EjbWithMockitoRunner;
import com.seamfix.tutorials.test.ejbcentric.ejb.AuthorEJB;
import com.seamfix.tutorials.test.ejbcentric.ejb.BookEJB;
import com.seamfix.tutorials.test.ejbcentric.model.Author;
import com.seamfix.tutorials.test.ejbcentric.model.Book;
import java.util.HashSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import javax.ejb.EJB;
import java.util.List;
import java.util.Set;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;

@RunWith(EjbWithMockitoRunner.class)
public class DaoTest {

    public static String ISBN = "ISBN-23455-23";

    @EJB
    private BookEJB bookDao;

    @EJB
    private AuthorEJB authorDao;

    @Test
    public void createBook() {
        Book newBook = new Book(ISBN, "The Prince", 105.0f, 500);
        Author newAuthor = new Author(newBook, "Rooney", "Onuoha", "Female", "@rooney");
        Set<Author> authors = new HashSet<>();
        authors.add(newAuthor);
        newBook.setAuthors(authors);
        bookDao.create(newBook);
        List<Book> entities = bookDao.findAll();
        assertEquals(1, entities.size());
    }

    @Test
    public void findBookByIsbn() {
        Book newBook = new Book(ISBN, "The Prince", 105.0f, 500);
        Author newAuthor = new Author(newBook, "Rooney", "Onuoha", "Female", "@rooney");
        Set<Author> authors = new HashSet<>();
        authors.add(newAuthor);
        newBook.setAuthors(authors);
        bookDao.create(newBook);
        Book dbBook = bookDao.findByIsbn(ISBN);
        Assert.assertNotNull(dbBook);
        assertEquals(dbBook.getIsbn(), ISBN);
    }

    @Test
    public void addBookAuthor() {
        Book newBook = new Book(ISBN, "The Prince", 105.0f, 500);
        Author newAuthor = new Author(newBook, "Rooney", "Onuoha", "Female", "@rooney");
        Set<Author> authors = new HashSet<>();
        authors.add(newAuthor);
        newBook.setAuthors(authors);
        bookDao.create(newBook);
        Book dbBook = bookDao.findByIsbn(ISBN);
        authorDao.create(new Author(dbBook, "JohnBull", "Okoro", "Female", "@rooney"));
        List<Author> entities = authorDao.findAll();
        assertEquals(2, entities.size());
    }

}
