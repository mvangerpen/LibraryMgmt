package com.mark;//Class contains the program's book object class

import java.util.Date;

public class Book {

    //Establish variables to be held by object
    Long isbn;
    String title;
    String author;
    String genre;
    int pages;
    String publisher;
    int yearPublished;
    double price;
    int copies;
    Boolean status;  //Status is True while in stock
    int custID;     //Identifies which customer currently has the book
    Date checkedOut;
    Date dueDate;

    Book(Long i, String t, String a, String g, int p, String pub, int pubYear, double pr, int c,
         Boolean s, int id, Date cod, Date dd) {

        isbn = i;
        title = t;
        author = a;
        genre = g;
        pages = p;
        publisher = pub;
        yearPublished = pubYear;
        price = pr;
        copies = c;
        status = s;
        custID = id;
        checkedOut = cod;
        dueDate = dd;

    }

    @Override
    public String toString() {
        return  "Author: " + author + "     " +
                "Title: " + title + "     " +
                "ISBN: " + isbn + "     " +
                "Status: " + status;

    }


}
