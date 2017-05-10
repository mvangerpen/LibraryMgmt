package com.mark;

import org.joda.time.LocalDate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CheckOutGUI extends JFrame {
    private JTextField titleField;
    private JTextField authorField;
    private JTextField myBookIDField;
    private JTextField isbnField;
    private JTextField mybraryIDField;
    private JButton checkInButton;
    private JButton checkOutButton;
    private JButton cancelButton;
    private JTextField phoneField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JButton findButton;
    private JPanel mainPanel;
    private JTextField statusField;
    private JTextField checkedOutField;
    private final Controller controller;


    CheckOutGUI(Controller controller, Book book) {
        super("Mybrarian: Check out, Check in, Renew");

        //create reference to controller
        this.controller = controller;

        //Configure check out/in buttons based on current book status. Disallow checkout if book is renewed or overdue.
        if (book.getStatus().equalsIgnoreCase("IN")) {
            checkInButton.setEnabled(false);

        } else if (book.getStatus().equalsIgnoreCase("OUT")) {
            checkOutButton.setText("Renew");

        } else if (book.getStatus().equalsIgnoreCase("REN")) {
            checkOutButton.setText("Check Out");
            checkOutButton.setEnabled(false);

        } else if (book.getStatus().equalsIgnoreCase("OVD")) {
            checkOutButton.setEnabled(false);
        }

        //Populate book field
        myBookIDField.setText(String.valueOf(book.getMybID()));
        isbnField.setText(book.getIsbn());
        titleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        statusField.setText(book.getStatus());
        //Show customer ID associated with book, if it is not the Librarian (ID 0)
        if (book.getCustID() != 0) {
            checkedOutField.setText(String.valueOf(book.getCustID()));
        }

        //Populate Customer details if book is checked out
        if (book.getStatus().equalsIgnoreCase("OUT") ||
            book.getStatus().equalsIgnoreCase("REN") ||
            book.getStatus().equalsIgnoreCase("OVD")) {

            Customer cust = controller.getCustomer(book.getCustID());
            mybraryIDField.setText(String.valueOf(cust.getCustomerID()));
            phoneField.setText(cust.getPhone());
            firstNameField.setText(cust.getFirstName());
            lastNameField.setText(cust.getLastName());

            //Lock ID additional fields
            mybraryIDField.setEditable(false);
            phoneField.setEditable(false);
        }


        //Lock field editing
        myBookIDField.setEditable(false);
        isbnField.setEditable(false);
        titleField.setEditable(false);
        authorField.setEditable(false);
        firstNameField.setEditable(false);
        lastNameField.setEditable(false);
        statusField.setEditable(false);
        checkedOutField.setEditable(false);

        //add Listeners
        addListeners(book);

        //set up JFrame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        pack();
        setVisible(true);
    }


    private void addListeners(Book book) {

        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Customer> allCust = controller.getAllCust();

                if (!mybraryIDField.getText().isEmpty()) {
                    String searchID = mybraryIDField.getText();
                    for (Customer c : allCust) {
                        if (String.valueOf(c.getCustomerID()).contains(searchID)) {
                            mybraryIDField.setText(String.valueOf(c.getCustomerID()));
                            phoneField.setText(c.getPhone());
                            firstNameField.setText(c.getFirstName());
                            lastNameField.setText(c.getLastName());
                            break;
                        }
                    }

                } else if (!phoneField.getText().isEmpty()) {
                    String searchPhone = phoneField.getText();
                    for (Customer c : allCust) {
                        if (searchPhone.equals(c.getPhone())) {
                            mybraryIDField.setText(String.valueOf(c.getCustomerID()));
                            firstNameField.setText(c.getFirstName());
                            lastNameField.setText(c.getLastName());
                            break;
                        }
                    }
                }
            }
        });     //Searches for customer by ID or phone. Fills fields based on first match.

        checkInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(CheckOutGUI.this, "Check in book " + book.getMybID() + "?\n" +
                        book.getTitle());
                if (choice == JOptionPane.YES_OPTION) {
                    book.setStatus("IN");
                    book.setCustID(0);
                    book.setCheckedOut(null);
                    book.setDueDate(null);
                    phoneField.setText("");
                    firstNameField.setText("");
                    lastNameField.setText("");
                    if (book.getCharged() > 0) {
                        controller.chargeCard(book.mybID, book.custID);
                    }
                    book.setCharged(0.00);

                    //Update book
                    try {
                        controller.updateBook(book);
                        statusField.setText(book.getStatus());
                        mybraryIDField.setText(String.valueOf(book.getCustID()));
                        JOptionPane.showMessageDialog(CheckOutGUI.this, "Check-in complete.");
                    } catch (Exception e1){
                        JOptionPane.showMessageDialog(CheckOutGUI.this, "Error checking in book.\n" + e1.toString());
                    }
                }
            }
        });     //Checks in selected book

        checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Get today's date
                LocalDate today = new LocalDate();

                //Prevent checking out to nobody
                if (mybraryIDField.getText().isEmpty() || lastNameField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(CheckOutGUI.this, "Select a customer for checkout.");
                } else if (checkOutButton.getText().equalsIgnoreCase("Check Out")){
                    int choice = JOptionPane.showConfirmDialog(CheckOutGUI.this, "Check out book " + book.getMybID() + "?\n"
                    + book.getTitle());
                    if (choice == JOptionPane.YES_OPTION) {
                        book.setStatus("OUT");
                        book.setCustID(Integer.parseInt(mybraryIDField.getText()));
                        int daysOut = 14;
                        book.setCheckedOut(today);
                        book.setDueDate(setDueDate(daysOut));

                        //Update DB
                        try {
                            controller.updateBook(book);

                            //Organize GUI. Update book fields. Lock all fields for editing.
                            checkOutButton.setText("Renew");
                            checkInButton.setEnabled(true);
                            statusField.setText(book.getStatus());
                            checkedOutField.setText(String.valueOf(book.getCustID()));
                            mybraryIDField.setEditable(false);
                            phoneField.setEditable(false);

                            //Notify successful.
                            JOptionPane.showMessageDialog(CheckOutGUI.this, "Book checked out.\n Due Date: " + book.getDueDate());
                        } catch (Exception e1) {
                            e1.printStackTrace();
                            JOptionPane.showMessageDialog(CheckOutGUI.this, "Error checking out book.\n" + e1.toString());
                        }
                    }

                } else if (checkOutButton.getText().equalsIgnoreCase("Renew")) {
                    int choice = JOptionPane.showConfirmDialog(CheckOutGUI.this, "Renew book " + book.getMybID() + "?\n"
                            + book.getTitle());
                    if (choice == JOptionPane.YES_OPTION) {
                        int daysOut = 7;
                        book.setDueDate(setDueDate(daysOut));
                        book.setCheckedOut(today);
                        book.setStatus("REN");
                        //Update DB
                        try {
                            controller.updateBook(book);
                            checkOutButton.setText("Check Out");
                            checkOutButton.setEnabled(false);
                            statusField.setText(book.getStatus());
                            mybraryIDField.setText(String.valueOf(book.getCustID()));
                            JOptionPane.showMessageDialog(CheckOutGUI.this, "Book renewed.\n Due Date: " + book.getDueDate());
                        } catch (Exception e1){
                            JOptionPane.showMessageDialog(CheckOutGUI.this, "Error renewing book.\n" + e1.toString());
                        }
                    }
                }
            }

        });     //Checks out or renews selected book

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.NewMainMenuGUI();
                dispose();
            }
        });     //Disposes window, opens main menu GUI
    }

    private LocalDate setDueDate(int daysOut) {

        return new LocalDate().plusDays(daysOut);

    }       //Sets due date 14 days out for initial checkout; 7 days for renewals

}
