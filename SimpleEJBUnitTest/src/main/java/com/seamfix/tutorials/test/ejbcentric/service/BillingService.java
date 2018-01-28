/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.tutorials.test.ejbcentric.service;

import com.seamfix.tutorials.test.ejbcentric.ejb.BookEJB;
import com.seamfix.tutorials.test.ejbcentric.model.Book;
import static com.seamfix.tutorials.test.ejbcentric.ejb.util.TConstant.PRICE_PER_PAGE;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author uonuoha@seamfix.com
 */
@Stateless
public class BillingService {

    @Inject
    private BookEJB bookService;

    public double getBookPricePerPage(Book book) {

        return book.getNbOfPages() * PRICE_PER_PAGE;

    }

    public double getBookPricePerPage(long id) {
        Book book = bookService.findById(id);
        if (book == null) {
            return 0;
        }
        return book.getNbOfPages() * PRICE_PER_PAGE;

    }

}
