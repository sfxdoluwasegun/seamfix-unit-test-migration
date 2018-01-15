/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.tutorials.test.ejbcentric;

import com.seamfix.tutorials.test.ejbcentric.ejb.BookEJB;
import com.seamfix.tutorials.test.ejbcentric.model.Book;
import com.seamfix.tutorials.test.ejbcentric.service.BillingService;
import static com.seamfix.tutorials.test.ejbcentric.view.ViewUtils.PRICE_PER_PAGE;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author uonuoha@seamfix.com
 */
public class BillingServiceTest {

    private static final long BOOK_ID = 15;

    @InjectMocks
    BillingService billingService;

    @Mock
    private BookEJB bookDao;

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testBookPricePerPage() {
        Book book = new Book(BOOK_ID, "IBNS-344-42555", "The Papa", 10.5f, 50);
        Mockito.when(bookDao.findById(BOOK_ID)).thenReturn(book);
        double totalCostPerPage = book.getNbOfPages() * PRICE_PER_PAGE;
        //call testing method
        double actualTotalCostPerPage = billingService.getBookPricePerPage(book);
        Assert.assertEquals(actualTotalCostPerPage, totalCostPerPage);
    }

}
