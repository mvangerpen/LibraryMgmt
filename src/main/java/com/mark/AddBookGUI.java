package com.mark;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class AddBookGUI extends JFrame {
    private JTextField authorField;
    private JTextField titleField;
    private JTextField categoriesField;
    private JTextField isbnField;
    private JTextField pagesField;
    private JTextField publisherField;
    private JTextField datePublishedField;
    private JTextField priceField;
    private JButton addBookButton;
    private JButton cancelButton;
    private JPanel mainPanel;
    private JButton autoFillButton;
    private JSpinner copiesSpinner;

    private final Controller controller;

    AddBookGUI(Controller controller) {
        super("Mybrary: Add a Book");

        //create reference to controller
        this.controller = controller;

        //add Listeners
        addListeners();

        //set up JFrame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        pack();
        setVisible(true);

    }

    private void addListeners() {

        autoFillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BooksRequest.getByISBN(isbnField.getText(),AddBookGUI.this);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }); // Searches Google Books API using ISBN, then populates fields from results

        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Establish accumulator for number of new books added
                int numNew = 0;

                try {
                    copiesSpinner.commitEdit();
                } catch (java.text.ParseException e1){
                    JOptionPane.showMessageDialog(AddBookGUI.this, "Error in Copies field.");
                } //Commits edits to copies spinner if manually entered.

                if (isbnField.getText().isEmpty() ||
                        titleField.getText().isEmpty() ||
                        authorField.getText().isEmpty() ||
                        categoriesField.getText().isEmpty() ||
                        pagesField.getText().isEmpty() ||
                        publisherField.getText().isEmpty() ||
                        datePublishedField.getText().isEmpty() ||
                        priceField.getText().isEmpty() ||
                        (int) copiesSpinner.getValue() == 0) {
                    JOptionPane.showMessageDialog(AddBookGUI.this, "Please fill out all fields.");

                } else {

                    try {
                        for (int x = 0; x < (int) copiesSpinner.getValue(); x++) {
                            Book newBook = null;

                            int nLibID = getNewID();
                            String nISBN = isbnField.getText();
                            String nTitle = titleField.getText();
                            String nAuthor = authorField.getText();
                            String nCategories = categoriesField.getText();
                            int nPages = Integer.parseInt(pagesField.getText());
                            String nPublisher = publisherField.getText();
                            String nPubDate = datePublishedField.getText();
                            String nInStock = "IN";
                            int nID = 0;
                            double nPrice = setPrice(nPages);
                            double nCharged = 0.00;

                            //Create book object
                            newBook = new Book(nLibID, nISBN, nTitle, nAuthor, nCategories, nPages, nPublisher, nPubDate, nInStock,
                                    nID, null, null, nPrice, nCharged);

                            System.out.println(newBook.toString());
                            numNew++;

                            try {
                                //Send to database
                                controller.addBook(newBook);
                            } catch (Exception e1) {
                                JOptionPane.showMessageDialog(AddBookGUI.this, "Error updating database.");
                                e1.printStackTrace();
                            }
                        }

                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(AddBookGUI.this, "Please use numbers for ISBN field.");
                        System.out.println();
                    }

                    //Confirm send
                    JOptionPane.showMessageDialog(AddBookGUI.this, "Added " + numNew + " new record(s).");

                    //Clear fields for next entry
                    isbnField.setText("");
                    titleField.setText("");
                    authorField.setText("");
                    categoriesField.setText("");
                    pagesField.setText("");
                    publisherField.setText("");
                    datePublishedField.setText("");
                    priceField.setText("");
                    copiesSpinner.setValue(0);
                }
            }
        }); // Collects field entries and sends book object to database

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.NewMainMenuGUI();
                dispose();

            }
        }); //Opens main menu, disposes window
    }

    void responseComplete(Book response) {

        isbnField.setText(String.valueOf(response.getIsbn()));
        titleField.setText(response.getTitle());
        authorField.setText(response.getAuthor());
        categoriesField.setText(response.getCategories());
        pagesField.setText(String.valueOf(response.getPages()));
        publisherField.setText(response.getPublisher());
        datePublishedField.setText(response.getPubDate());
        priceField.setText(String.valueOf(setPrice(response.getPages())));

    }

    private Double setPrice(int pages){

        Double price = pages * .05;
        return (double) Math.round(price * 100)/100;

    }

    private int getNewID() {

        //Create a new customer ID
        ArrayList<Book> books = new ArrayList<>(controller.getAllBooks());

        Random rand = new Random();
        int newID = rand.nextInt(89999) + 10000;

        //Check integer against existing customers.
        for (Book b : books) {
            if (newID == b.getMybID()) {
                getNewID();
            }
        }
        return newID;
    }   //Generates new 5-digit ID based on existing IDs

}
