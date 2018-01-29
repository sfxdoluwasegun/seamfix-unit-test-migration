package com.seamfix.tutorials.test.ejbcentric.rest;

import com.seamfix.tutorials.test.ejbcentric.ejb.BookEJB;
import com.seamfix.tutorials.test.ejbcentric.ejb.dto.BookRequest;
import com.seamfix.tutorials.test.ejbcentric.model.Book;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.MediaType;

@Path("/book")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookEndpoint {

    @EJB
    private BookEJB bookService;

    @POST
    @Path("/create")
    public Response create(BookRequest req) {
        Book newBook = new Book(req.getIsbn(), req.getTitle(), req.getPrice(), req.getNbOfPages());
        try {
            bookService.create(newBook);
        } catch (Exception ex) {
            System.out.println("Message=== " + ex.getMessage());
            ex.printStackTrace();
        }
        return Response.ok(String.valueOf(newBook.getId())).build();
    }

    @POST
    @Path("/data")
    public Response createe(BookRequest req) {
        return Response.created(UriBuilder.fromPath("/book/data/").path(String.valueOf(req.getIsbn())).build()).build();
    }

}
