package com.mark;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerDetailsGUI extends JFrame {
    private JTextField firstNameField;
    private JTextField cityField;
    private JTextField zipField;
    private JTextField addressField;
    private JLabel ZIP;
    private JComboBox stateComboBox;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField customerIDField;
    private JTextField dateAddedField;
    private JTextField paymentField;
    private JButton editButton;
    private JTextField lastNameField;
    private JButton updateButton;
    private JPanel mainPanel;
    private JButton deleteCustomerButton;
    private JButton mainMenuButton;
    private JButton checkInButton;
    private JButton renewButton;

    //Configure table model
    private JTable checkedOut;
    private DefaultTableModel tableModel;

    private Controller controller;

    CustomerDetailsGUI(Controller controller, Customer customer) {
        super("Mybrarian: Edit Customer");

        //create reference to controller
        this.controller = controller;

        //Configure table
        tableModel = new DefaultTableModel();
        checkedOut.setModel(tableModel);
        checkedOut.setRowSelectionAllowed(true);


        //Fill state combo box
        for (String x : controller.states) {
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







        //add Listeners
        addListeners();

        //set up JFrame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        pack();
        setVisible(true);

    }

    void addListeners() {
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.NewMainMenuGUI();
                dispose();
            }
        }); //Opens new Main Menu window, disposes current

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                //controller.updateCustomer(customer);

            }
        });


        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: open card window. Need to send Customer information

            }
        });

    }

}
