//Opens a book details window.
//Options within window are to Edit/Update current record, delete book, or go back to book search.

package com.mark;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookDetailsGUI extends JFrame {
    private JPanel rootPanel;
    private JLabel ISBN;
    private JButton editDetailsButton;
    private JButton backToBookSearchButton;
    private JButton checkOutInButton;
    private JTextField isbnField;
    private JTextField genreField;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField pagesField;
    private JTextField publisherField;
    private JTextField yearField;
    private JTextField priceField;
    private JTextField statusField;
    private JTextField customerField;
    private Controller controller;


    BookDetailsGUI(Controller controller, Book book) {
        super("Mybrarian: Book Details");

        //create reference to controller
        this.controller = controller;

        //set up JFrame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(rootPanel);
        pack();
        setVisible(true);


        //Add data to fields
        isbnField.setText(String.valueOf(book.isbn));
        genreField.setText(book.genre);
        titleField.setText(book.title);
        authorField.setText(book.author);
        pagesField.setText(String.valueOf(book.pages));
        publisherField.setText(book.publisher);
        yearField.setText(String.valueOf(book.yearPublished));
        priceField.setText(String.valueOf(book.price));
        statusField.setText(String.valueOf(book.status));
        customerField.setText(String.valueOf(book.custID));


        //Remove editing from fields
        isbnField.setEditable(false);
        genreField.setEditable(false);
        titleField.setEditable(false);
        authorField.setEditable(false);
        pagesField.setEditable(false);
        publisherField.setEditable(false);
        yearField.setEditable(false);
        priceField.setEditable(false);
        statusField.setEditable(false);
        customerField.setEditable(false);


        //add Listeners
        addListeners(book);
    }

    void addListeners(Book book) {
        editDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editDetailsButton.getText().equals("Edit Details")) {
                    //Turn on editing
                    isbnField.setEditable(true);
                    genreField.setEditable(true);
                    titleField.setEditable(true);
                    authorField.setEditable(true);
                    pagesField.setEditable(true);
                    publisherField.setEditable(true);
                    yearField.setEditable(true);
                    priceField.setEditable(true);
                    statusField.setEditable(true);
                    customerField.setEditable(true);

                    //Change button to "Save" for updating record
                    editDetailsButton.setText("Save");
                } else {
                    //Collect data from fields
                    book.isbn = Long.parseLong(isbnField.getText());
                    book.title = titleField.getText();
                    book.author = authorField.getText();
                    book.genre = genreField.getText();
                    book.pages = Integer.parseInt(pagesField.getText());
                    book.publisher = publisherField.getText();
                    book.yearPublished = Integer.parseInt(yearField.getText());
                    book.price = Double.parseDouble(priceField.getText());
                    book.custID = Integer.parseInt(customerField.getText());
                    book.status = Boolean.parseBoolean(statusField.getText());

                    //Update record in DB
                    controller.updateBook(book);

                    //Reset fields to non-editable
                    isbnField.setEditable(false);
                    genreField.setEditable(false);
                    titleField.setEditable(false);
                    authorField.setEditable(false);
                    pagesField.setEditable(false);
                    publisherField.setEditable(false);
                    yearField.setEditable(false);
                    priceField.setEditable(false);
                    statusField.setEditable(false);
                    customerField.setEditable(false);

                    //Change button to "Edit"
                    editDetailsButton.setText("Edit");
                }
            }
        }); //Opens fields for editing, sends to DB on saving

        backToBookSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.NewBookSearchGUI();
                dispose();

            }
        }); //Opens search window, disposes current
    }

}
