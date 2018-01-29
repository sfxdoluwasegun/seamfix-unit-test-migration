package com.seamfix.tutorials.test.ejbcentric.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@XmlRootElement
public class Author implements Serializable {

    public Author(Book book, String firstname, String surname, String bio, String twitter) {
        this.firstname = firstname;
        this.surname = surname;
        this.bio = bio;
        this.twitter = twitter;
        this.book = book;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id = null;
    @Version
    private int version = 0;

    @Column(nullable = false)
    @NotNull
    private String firstname;

    @Column(nullable = false)
    @NotNull
    private String surname;

    @Column(length = 2000)
    @Size(max = 2000)
    private String bio;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID", nullable = false)
    private Book book;

    private String twitter;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public String getBio() {
        return this.bio;
    }

    public void setBio(final String bio) {
        this.bio = bio;
    }

    public String getTwitter() {
        return this.twitter;
    }

    public void setTwitter(final String twitter) {
        this.twitter = twitter;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        if (id != null) {
            return id.equals(((Author) that).id);
        }
        return super.equals(that);
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        }
        return super.hashCode();
    }

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        if (firstname != null && !firstname.trim().isEmpty()) {
            result += "firstname: " + firstname;
        }
        if (surname != null && !surname.trim().isEmpty()) {
            result += ", surname: " + surname;
        }
        if (bio != null && !bio.trim().isEmpty()) {
            result += ", bio: " + bio;
        }
        if (twitter != null && !twitter.trim().isEmpty()) {
            result += ", twitter: " + twitter;
        }
        return result;
    }
}
