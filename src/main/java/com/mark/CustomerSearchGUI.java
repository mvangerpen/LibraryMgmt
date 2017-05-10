package com.mark;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

@SuppressWarnings("unchecked")
public class CustomerSearchGUI extends JFrame {
    private JPanel mainPanel;
    private JComboBox searchByComboBox;
    private JTextField searchField;
    private JButton searchButton;
    private JButton customerDetailsButton;
    private JButton mainMenuButton;
    private JScrollPane resultsPane;
    private JTable custTable;
    private JButton clearSearchButton;


    private CustTableModel custTableModel;
    private Vector<Customer> customers;

    private final Controller controller;


    CustomerSearchGUI(Controller controller) {
        super("Mybrarian: Search Customer");

        //create reference to controller
        this.controller = controller;

        customers = new Vector<>(controller.getAllCust());
        custTableModel = new CustTableModel(customers);
        custTable.setModel(custTableModel);

        //Configure search combo box
        searchByComboBox.addItem("Customer ID");
        searchByComboBox.addItem("First Name");
        searchByComboBox.addItem("Last Name");
        searchByComboBox.addItem("Phone Number");
        searchByComboBox.addItem("Email");

        addListeners();

        //set up JFrame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        pack();
        setVisible(true);
    }

    private void addListeners() {

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Pull list of all customers
                ArrayList<Customer> allCustomers = controller.getAllCust();

                //Reestablish vector list
                customers = new Vector<>();

                if (searchByComboBox.getSelectedItem().toString().equalsIgnoreCase("Customer ID")) {
                    try {
                        int search = Integer.parseInt(searchField.getText());

                        for (Customer c : allCustomers) {
                            if (String.valueOf(c.customerID).contains(String.valueOf(search))) {
                                customers.add(c);
                            }
                        }
                    } catch (NumberFormatException ne) {
                        JOptionPane.showMessageDialog(CustomerSearchGUI.this, "Please use numbers for ID search.");
                        return;
                    }
                } else if (searchByComboBox.getSelectedItem().toString().equalsIgnoreCase("First Name")) {

                    String search = searchField.getText();
                    for (Customer c : allCustomers) {
                        if (c.getFirstName().toLowerCase().contains(search.toLowerCase())) {
                            customers.add(c);
                        }
                    }
                } else if (searchByComboBox.getSelectedItem().toString().equalsIgnoreCase("Last Name")) {

                    String search = searchField.getText();
                    for (Customer c : allCustomers) {
                        if (c.getLastName().toLowerCase().contains(search.toLowerCase())) {
                            customers.add(c);
                        }
                    }
                } else if (searchByComboBox.getSelectedItem().toString().equalsIgnoreCase("Phone Number")) {

                    String search = searchField.getText();
                    for (Customer c : allCustomers) {
                        if (c.getPhone().toLowerCase().contains(search.toLowerCase())) {
                            customers.add(c);
                        }
                    }
                } else if (searchByComboBox.getSelectedItem().toString().equalsIgnoreCase("Email")) {

                    String search = searchField.getText();
                    for (Customer c : allCustomers) {
                        if (c.getEmail().toLowerCase().contains(search.toLowerCase())) {
                            customers.add(c);
                        }
                    }
                }

                if (customers.size() > 0) {
                    custTableModel = new CustTableModel(customers);
                    custTable.setModel(custTableModel);
                } else {
                    JOptionPane.showMessageDialog(CustomerSearchGUI.this, "No results.");
                }
            }
        }); //Searches Customers list by search term and refreshes table

        clearSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Reset ComboBox
                searchByComboBox.setSelectedItem("Customer ID");
                searchField.setText("");

                customers = new Vector<>(controller.getAllCust());
                custTableModel = new CustTableModel(customers);
                custTable.setModel(custTableModel);
            }
        });

        customerDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int column = 0;
                int row = custTable.getSelectedRow();
                int toEdit = Integer.parseInt(custTable.getModel().getValueAt(row, column).toString());

                //Use custID
                ArrayList<Customer> custList = controller.getAllCust();
                Customer toUse = null;
                for (Customer c: custList) {
                    if (c.customerID == toEdit) {
                        toUse = c;
                        break;
                    }
                }

                controller.NewCustomerDetailsGUI(toUse);
                dispose();
            }
        }); //Opens new window for Editing customers

        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.NewMainMenuGUI();
                dispose();

            }
        }); //Opens main menu, disposes window
    }

}
