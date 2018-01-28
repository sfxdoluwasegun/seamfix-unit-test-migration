/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.tutorials.test.ejbcentric;

import com.seamfix.tutorial.test.jee.EjbWithMockitoRunner;
import com.seamfix.tutorials.test.ejbcentric.ejb.BookEJB;
import static com.seamfix.tutorials.test.ejbcentric.ejb.util.TConstant.PRICE_PER_PAGE;
import com.seamfix.tutorials.test.ejbcentric.model.Book;
import com.seamfix.tutorials.test.ejbcentric.service.BillingService;
import javax.ejb.EJB;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author uonuoha@seamfix.com
 */
@RunWith(EjbWithMockitoRunner.class)
public class BillingServiceTest {
    
    private static final long BOOK_ID = 15;
    
    @EJB
    BillingService billingService;
    
    @Mock
    private BookEJB bookDao;
    
    @Test
    public void testTotalBookPricePerPage() {
        Book book = new Book(BOOK_ID, "IBNS-344-42555", "The Papa", 10.5f, 50);
        when(bookDao.findById(BOOK_ID)).thenReturn(book);
        double expectedTotalPrice = book.getNbOfPages() * PRICE_PER_PAGE;
        //call testing method
        double actualTotalPrice = billingService.getBookPricePerPage(book);
        Assert.assertNotNull(actualTotalPrice);
        // Assert.assertEquals(expectedTotalPrice, actualTotalPrice);
        Assert.assertEquals(String.valueOf(expectedTotalPrice), String.valueOf(actualTotalPrice));
        
    }
    
}
