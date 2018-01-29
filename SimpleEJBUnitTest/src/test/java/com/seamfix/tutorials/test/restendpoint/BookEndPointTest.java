/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.tutorials.test.restendpoint;

import com.seamfix.test.tutorial.jee.EjbWithMockitoRunner;
import com.seamfix.test.tutorial.jee.JaxRsServer;
import com.seamfix.tutorials.test.ejbcentric.ejb.dto.BookRequest;
import com.seamfix.tutorials.test.ejbcentric.rest.BookEndpoint;
import javax.ws.rs.core.Response;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

/**
 *
 * @author uonuoha@seamfix.com
 */
@RunWith(EjbWithMockitoRunner.class)
public class BookEndPointTest {

    @Rule
    public JaxRsServer jaxRsServer = JaxRsServer.forResources(BookEndpoint.class);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createBookData() {
        jaxRsServer
                .jsonRequest("/book/data")
                .body(new BookRequest("ISBN-234555", "The Prince", 120f, 700))
                .expectStatus(Response.Status.CREATED)
                .expectLocation("/book/data/ISBN-234555")
                .post();
    }

    @Test
    public void createBook() {
        String id = jaxRsServer
                .jsonRequest("/book/create")
                .body(new BookRequest("ISBN-234555", "The Prince", 120f, 700))
                .expectStatus(Response.Status.OK)
                .post();
         Assert.assertNotNull(id);
        Assert.assertNotEquals(id, "0");

    }

}
