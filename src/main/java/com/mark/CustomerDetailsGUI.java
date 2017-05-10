package com.mark;

import org.joda.time.LocalDate;

import javax.swing.*;
import java.awt.event.*;
import java.util.Date;
import java.util.Vector;
import java.util.ArrayList;

@SuppressWarnings({"ALL", "unchecked"})
public class CustomerDetailsGUI extends JFrame {
    private JTextField firstNameField;
    private JTextField cityField;
    private JTextField zipField;
    private JTextField addressField;
    private JComboBox stateComboBox;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField customerIDField;
    private JTextField dateAddedField;
    private JTextField paymentField;
    private JButton editPaymentButton;
    private JTextField lastNameField;
    private JPanel mainPanel;
    private JButton deleteCustomerButton;
    private JButton backButton;
    private JButton checkInButton;
    private JButton renewButton;
    private final Controller controller;

    //Configure table model
    private JTable booksTable;
    private JButton editCustomerButton;
    private JTextField amtDueField;
    private Vector<Book> books;

    CustomerDetailsGUI(Controller controller, Customer customer) {
        super("Mybrary: Customer Details");

        //create reference to controller
        this.controller = controller;

        paintTable(customer);

        //Fill state combo box
        for (String x : Controller.states) {
            stateComboBox.addItem(x);
        }

        //Fill fields
        customerIDField.setText(String.valueOf(customer.customerID));
        dateAddedField.setText(customer.addDate.toString());
        firstNameField.setText(customer.firstName);
        lastNameField.setText(customer.lastName);
        addressField.setText(customer.streetAddress);
        cityField.setText(customer.cityAddress);
        stateComboBox.setSelectedItem(customer.stateAddress);
        zipField.setText(String.valueOf(customer.zipAddress));
        phoneField.setText(customer.phone);
        emailField.setText(customer.email);
        paymentField.setText("************" + customer.creditCard);

        Double amtDue = 0.00;
        //Get amount due
        ArrayList<Book> getBooks = controller.getAllBooks();
        for (Book b : getBooks) {
            amtDue += b.getCharged();
        }

        amtDueField.setText(String.valueOf(amtDue));

        //Lock fields for editing
        customerIDField.setEditable(false);
        dateAddedField.setEditable(false);
        paymentField.setEditable(false);
        amtDueField.setEditable(false);

        //Lock fields that can be unlocked for editing
        firstNameField.setEditable(false);
        lastNameField.setEditable(false);
        addressField.setEditable(false);
        cityField.setEditable(false);
        stateComboBox.setEditable(false);
        zipField.setEditable(false);
        phoneField.setEditable(false);
        emailField.setEditable(false);


        //add Listeners
        addListeners(customer);

        //set up JFrame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        pack();
        setVisible(true);
    }

    private void addListeners(Customer customer) {

        editCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editCustomerButton.getText().equalsIgnoreCase("Edit Customer")) {
                    //Unlock editable detail fields
                    firstNameField.setEditable(true);
                    lastNameField.setEditable(true);
                    addressField.setEditable(true);
                    cityField.setEditable(true);
                    stateComboBox.setEditable(true);
                    zipField.setEditable(true);
                    phoneField.setEditable(true);
                    emailField.setEditable(true);

                    editCustomerButton.setText("Save Changes");

                } else if (editCustomerButton.getText().equalsIgnoreCase("Save Changes")) {
                    //Lock fields that can be unlocked for editing
                    firstNameField.setEditable(false);
                    lastNameField.setEditable(false);
                    addressField.setEditable(false);
                    cityField.setEditable(false);
                    stateComboBox.setEditable(false);
                    zipField.setEditable(false);
                    phoneField.setEditable(false);
                    emailField.setEditable(false);

                    //update customer from fields
                    customer.setFirstName(firstNameField.getText());
                    customer.setLastName(lastNameField.getText());
                    customer.setStreetAddress(addressField.getText());
                    customer.setCityAddress(cityField.getText());
                    customer.setStateAddress(stateComboBox.getSelectedItem().toString());
                    customer.setPhone(phoneField.getText());
                    customer.setEmail(emailField.getText());


                    //Check for empty fields
                    if (customerIDField.getText().isEmpty() ||
                            dateAddedField.getText().isEmpty() ||
                            firstNameField.getText().isEmpty() ||
                            lastNameField.getText().isEmpty() ||
                            addressField.getText().isEmpty() ||
                            cityField.getText().isEmpty() ||
                            zipField.getText().isEmpty() ||
                            phoneField.getText().isEmpty() ||
                            emailField.getText().isEmpty() ||
                            paymentField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(CustomerDetailsGUI.this, "Please fill out all fields.");

                    } else {

                        int custID = Integer.parseInt(customerIDField.getText());

                        //Get current card
                        CreditCard card = controller.getCard(custID);

                        //update customer object
                        String firstName = firstNameField.getText();
                        String lastName = lastNameField.getText();
                        String streetAddress = addressField.getText();
                        String cityAddress = cityField.getText();
                        String stateAddress = stateComboBox.getSelectedItem().toString();
                        int zipAddress = Integer.parseInt(zipField.getText());
                        String phone = phoneField.getText();
                        String email = emailField.getText();
                        String creditCard = card.lastFour;
                        Date addDate = new Date();

                        Customer updateCust = new Customer(custID, firstName, lastName, streetAddress, cityAddress,
                                stateAddress, zipAddress, phone, email, creditCard, customer.getTotalCharge(), addDate);


                        int choice = JOptionPane.showConfirmDialog(CustomerDetailsGUI.this, "Update credit card with this information?");
                        if (choice == JOptionPane.YES_OPTION) {
                            CreditCard updateCard = controller.getCard(custID);
                            updateCard.setNameOnCard(firstName + " " + lastName);
                            updateCard.setCardAddress(streetAddress);
                            updateCard.setCardState(stateAddress);
                            updateCard.setCardZIP(zipAddress);

                            try {
                                //Send updated customer to DB
                                controller.updateCustomer(updateCust);
                                controller.updateCC(updateCard);

                                //Notify if successful
                                JOptionPane.showMessageDialog(CustomerDetailsGUI.this, "Address and card info updated for " +
                                        updateCust.getFirstName() + " " + updateCust.getLastName() + "\nCustomer ID: " + updateCust.getCustomerID());

                            } catch (Exception e1) {
                                JOptionPane.showMessageDialog(CustomerDetailsGUI.this, "Error updating records. Check fields.");
                            }

                        } else if (choice == JOptionPane.NO_OPTION) {
                            try {
                                //Send updated customer to DB
                                controller.updateCustomer(updateCust);

                                //Notify if successful
                                JOptionPane.showMessageDialog(CustomerDetailsGUI.this, "Address updated for " +
                                        updateCust.getFirstName() + " " + updateCust.getLastName() + "\nCustomer ID: " + updateCust.getCustomerID());

                            } catch (Exception e1) {
                                JOptionPane.showMessageDialog(CustomerDetailsGUI.this, "Error updating record. Check fields.");
                            }

                        }

                    }
                    //reset button text
                    editCustomerButton.setText("Edit Customer");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.NewCustomerSearchGUI();
                dispose();
            }
        }); //Opens new customer search window, disposes current

        editPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sendID = Integer.parseInt(customerIDField.getText());

                CreditCard sendCard = controller.getCard(sendID);

                controller.CreditCardGUI(sendCard);
                dispose();

            }
        }); //Opens CC window for payment update. Disposes current.

        deleteCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!books.isEmpty()) {
                    JOptionPane.showMessageDialog(CustomerDetailsGUI.this, "Customer has books checked out. Cannot delete.");
                } else {

                    int choice = JOptionPane.showConfirmDialog(CustomerDetailsGUI.this, "This action cannot be undone. Continue deleting customer?");

                    if (choice == JOptionPane.YES_OPTION) {
                        int custID = Integer.parseInt(customerIDField.getText());

                        try {
                            controller.deleteCustomer(custID);

                            //Empty all fields
                            customerIDField.setText(String.valueOf(""));
                            dateAddedField.setText("");
                            firstNameField.setText("");
                            lastNameField.setText("");
                            addressField.setText("");
                            cityField.setText("");
                            stateComboBox.setSelectedItem("");
                            zipField.setText(String.valueOf(""));
                            phoneField.setText("");
                            emailField.setText("");
                            paymentField.setText("");

                            //Notify successful
                            JOptionPane.showMessageDialog(CustomerDetailsGUI.this, "Customer " + custID + " deleted.");

                        } catch (Exception a) {
                            JOptionPane.showMessageDialog(CustomerDetailsGUI.this, "Error deleting customer.\n" + a.toString());
                            a.printStackTrace();
                        }

                        dispose();
                        controller.NewMainMenuGUI();
                    }
                }
            }
        }); //Deletes customer by custID. Disposes window and opens Main Menu

        checkInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Get book based on selected row
                int column = 0;
                int row = booksTable.getSelectedRow();
                int toEdit = Integer.parseInt(booksTable.getModel().getValueAt(row, column).toString());
                Book book = controller.getBook(toEdit);

                int choice = JOptionPane.showConfirmDialog(CustomerDetailsGUI.this, "Check in book " + book.getMybID() + "?\n" +
                        book.getTitle());
                if (choice == JOptionPane.YES_OPTION) {
                    book.setStatus("IN");
                    book.setCustID(0);
                    book.setCheckedOut(null);
                    book.setDueDate(null);
                    if (book.getCharged() > 0) {
                        controller.chargeCard(book.mybID, book.custID);
                    }
                    book.setCharged(0.00);

                    //Update book and repaint table
                    try {
                        controller.updateBook(book);
                        paintTable(customer);
                        JOptionPane.showMessageDialog(CustomerDetailsGUI.this, "Check-in complete.");
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(CustomerDetailsGUI.this, "Error checking in book.\n" + e1.toString());
                    }
                }
            }
        });

        renewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Establish today's date for renewal purposes
                LocalDate today = new LocalDate();

                int column = 2;
                int row = booksTable.getSelectedRow();
                int toEdit = Integer.parseInt(booksTable.getModel().getValueAt(row, column).toString());
                Book book = controller.getBook(toEdit);
                if (book.getStatus().equals("REN") || book.getStatus().equals("OVD")) {
                    renewButton.setEnabled(false);
                    mainPanel.updateUI();
                }

                int choice = JOptionPane.showConfirmDialog(CustomerDetailsGUI.this, "Renew book " + book.getMybID() + "?\n"
                        + book.getTitle());
                if (choice == JOptionPane.YES_OPTION) {
                    int daysOut = 7;
                    book.setDueDate(setDueDate(book, daysOut));
                    book.setStatus("REN");
                    //Update DB
                    try {
                        controller.updateBook(book);
                        paintTable(customer);
                        JOptionPane.showMessageDialog(CustomerDetailsGUI.this, "Book renewed.\n Due Date: " + book.getDueDate());
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(CustomerDetailsGUI.this, "Error renewing book.\n" + e1.toString());
                    }
                }
            }
        });

        booksTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                int x = booksTable.getSelectedRow();
                Book selectedBook = books.get(x);
                if (selectedBook.getStatus().equals("REN") || selectedBook.getStatus().equals("OVD")) {
                    renewButton.setEnabled(false);
                }
            }
        });

    }

    private LocalDate setDueDate(Book book, int daysOut) {

        LocalDate dueDate = book.getDueDate();
        return dueDate.plusDays(daysOut);

    }       //Sets due date 7 days for past original checkout date for renewals.

    private void paintTable(Customer customer){
        //Configure table model
        books = new Vector<>(controller.getCustBooks(customer.getCustomerID()));
        BooksTableModel booksTableModel = new BooksTableModel(books);
        booksTable.setModel(booksTableModel);

        if (books.isEmpty()) {
            checkInButton.setEnabled(false);
            renewButton.setEnabled(false);
        }

    }       //Repaints table after books are checked in or renewed. Disables book buttons if list is empty.

}

