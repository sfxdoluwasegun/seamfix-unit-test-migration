package com.seamfix.tutorials.test.ejbcentric.service;

import com.seamfix.tutorials.test.ejbcentric.ejb.BookEJB;
import com.seamfix.tutorials.test.ejbcentric.model.Book;
import static com.seamfix.tutorials.test.ejbcentric.view.ViewUtils.PRICE_PER_PAGE;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class BillingService {

    @Inject
    private BookEJB bookService;

    public double getBookPricePerPage(Book book) {

        return book.getNbOfPages() * PRICE_PER_PAGE;

    }

}
