package com.mark;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBookGUI extends JFrame {
    private JTextField authorField;
    private JTextField titleField;
    private JTextField genreField;
    private JTextField isbnField;
    private JTextField pagesField;
    private JTextField publisherField;
    private JTextField yearPublishedField;
    private JTextField priceField;
    private JButton addBookButton;
    private JButton cancelButton;
    private JPanel mainPanel;
    private JButton autoFillButton;
    private JSpinner copiesSpinner;

    private Controller controller;

    AddBookGUI(Controller controller) {
        super("Mybrarian: Add a Book");

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

    void addListeners() {

        autoFillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.setISBNForAPI(isbnField.getText());
                    //new BooksRequest();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }); // Searches Google Books API using ISBN, then populates fields from results

        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    copiesSpinner.commitEdit();
                } catch (java.text.ParseException e1){
                    JOptionPane.showMessageDialog(AddBookGUI.this, "Error in Copies field.");
                } //Commits edits to copies spinner if manually entered.

                try {
                    Long nISBN = Long.parseLong(isbnField.getText());
                    String nTitle = titleField.getText();
                    String nAuthor = authorField.getText();
                    String nGenre = genreField.getText();
                    int nPages = Integer.parseInt(pagesField.getText());
                    String nPublisher = publisherField.getText();
                    int nYear = Integer.parseInt(yearPublishedField.getText());
                    double nPrice = Double.parseDouble(priceField.getText());
                    int nCopies = (Integer) copiesSpinner.getValue();
                    Boolean nInStock = true;
                    int nID = 010101;

                    if (isbnField.getText().isEmpty() ||
                            titleField.getText().isEmpty() ||
                            authorField.getText().isEmpty() ||
                            genreField.getText().isEmpty() ||
                            pagesField.getText().isEmpty() ||
                            publisherField.getText().isEmpty() ||
                            yearPublishedField.getText().isEmpty() ||
                            priceField.getText().isEmpty() ||
                            nCopies == 0) {
                        JOptionPane.showMessageDialog(AddBookGUI.this, "Please fill out all fields.");
                    } else {
                        //Create book object
                        Book newBook = new Book(nISBN, nTitle, nAuthor, nGenre, nPages, nPublisher, nYear, nPrice, nCopies, nInStock,
                                nID, null, null);
                        System.out.println(newBook.toString());
                        try {
                            //Send to database
                            controller.addBook(newBook);
                        } catch (Exception e1 ){
                            JOptionPane.showMessageDialog(AddBookGUI.this, "Error updating database.");
                            e1.printStackTrace();
                        }

                        //Confirm send
                        JOptionPane.showMessageDialog(AddBookGUI.this, "Record added.");

                        //Clear fields for next entry
                        isbnField.setText("");
                        titleField.setText("");
                        authorField.setText("");
                        genreField.setText("");
                        pagesField.setText("");
                        publisherField.setText("");
                        yearPublishedField.setText("");
                        priceField.setText("");
                        copiesSpinner.setValue(0);
                    }

                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(AddBookGUI.this, "Please use numbers for ISBN field.");
                    System.out.println();
                } //Gathers field entries and sends to Book object



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
}
