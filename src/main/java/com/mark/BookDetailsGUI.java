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
    private JTextField categoriesField;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField pagesField;
    private JTextField publisherField;
    private JTextField dateField;
    private JTextField priceField;
    private JTextField statusField;
    private JTextField customerField;
    private final Controller controller;


    BookDetailsGUI(Controller controller, Book book) {
        super("Mybrary: MyBook Details");

        //create reference to controller
        this.controller = controller;

        //set up JFrame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(rootPanel);
        pack();
        setVisible(true);


        //Add data to fields
        isbnField.setText(String.valueOf(book.getIsbn()));
        categoriesField.setText(book.getCategories());
        titleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        pagesField.setText(String.valueOf(book.getPages()));
        publisherField.setText(book.getPublisher());
        dateField.setText(book.getPubDate());
        priceField.setText(String.valueOf(book.getPrice()));
        statusField.setText(String.valueOf(book.getStatus()));
        customerField.setText(String.valueOf(book.getCustID()));


        //Remove editing from fields
        isbnField.setEditable(false);
        categoriesField.setEditable(false);
        titleField.setEditable(false);
        authorField.setEditable(false);
        pagesField.setEditable(false);
        publisherField.setEditable(false);
        dateField.setEditable(false);
        priceField.setEditable(false);
        statusField.setEditable(false);
        customerField.setEditable(false);


        //add Listeners
        addListeners(book);

    }

    private void addListeners(Book book) {

        editDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editDetailsButton.getText().equals("Edit Details")) {
                    //Turn on editing
                    isbnField.setEditable(true);
                    categoriesField.setEditable(true);
                    titleField.setEditable(true);
                    authorField.setEditable(true);
                    pagesField.setEditable(true);
                    publisherField.setEditable(true);
                    dateField.setEditable(true);
                    priceField.setEditable(true);

                    //Change button to "Save" for updating record
                    editDetailsButton.setText("Save");
                } else {
                    //Collect data from fields
                    book.setIsbn(isbnField.getText());
                    book.setTitle(titleField.getText());
                    book.setAuthor(authorField.getText());
                    book.setCategories(categoriesField.getText());
                    book.setPages(Integer.parseInt(pagesField.getText()));
                    book.setPublisher(publisherField.getText());
                    book.setPubDate(dateField.getText());
                    book.setCustID(Integer.parseInt(customerField.getText()));
                    book.setStatus(statusField.getText());
                    book.setPrice(Double.parseDouble(priceField.getText()));

                    //Update record in DB
                    controller.updateBook(book);

                    //Reset fields to non-editable
                    isbnField.setEditable(false);
                    categoriesField.setEditable(false);
                    titleField.setEditable(false);
                    authorField.setEditable(false);
                    pagesField.setEditable(false);
                    publisherField.setEditable(false);
                    dateField.setEditable(false);
                    priceField.setEditable(false);

                    //Change button to "Edit"
                    editDetailsButton.setText("Edit Details");
                }
            }
        }); //Opens fields for editing, sends to DB on saving

        checkOutInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.CheckOutGUI(book);
                dispose();
            }
        }); //Opens checkout window, disposes current

        backToBookSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.NewBookSearchGUI();
                dispose();

            }
        }); //Opens search window, disposes current
    }

}
