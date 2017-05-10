package com.mark;//Class contains the program's book object class

import org.joda.time.LocalDate;

public class Book {

    //Establish variables to be held by object
    int mybID;
    String isbn;
    String title;
    String author;
    String categories;
    private int pages;
    private String publisher;
    private String pubDate;
    private String status;  //Status is True while in stock
    int custID;     //Identifies which customer currently has the book
    private LocalDate checkedOut;
    private LocalDate dueDate;
    private double price;
    private double charged;

    //Constructor
    Book(int m, String i, String t, String a, String g, int p, String pub, String pd,
         String s, int id, LocalDate cod, LocalDate dd, double pr, double ch) {

        mybID = m;
        isbn = i;
        title = t;
        author = a;
        categories = g;
        pages = p;
        publisher = pub;
        pubDate = pd;
        status = s;
        custID = id;
        checkedOut = cod;
        dueDate = dd;
        price = pr;
        charged = ch;

    }


    //Getters and Setters
    public int getMybID() {
        return mybID;
    }

    public void setMybID(int libID) {
        this.mybID = libID;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCustID() {
        return custID;
    }

    public void setCustID(int custID) {
        this.custID = custID;
    }

    public LocalDate getCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(LocalDate checkedOut) {
        this.checkedOut = checkedOut;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public double getCharged() {
        return charged;
    }

    public void setCharged(double charged) {
        this.charged = charged;
    }


    //ToString method
    @Override
    public String toString() {
        return  "ID: " + mybID + "\n" +
                "Title: " + title + "\n" +
                "Author: " + author + "\n" +
                "ISBN: " + isbn;

    }


}
