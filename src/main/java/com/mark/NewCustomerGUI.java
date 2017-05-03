package com.mark;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class NewCustomerGUI extends JFrame {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField addressField;
    private JTextField cityField;
    private JComboBox stateComboBox;
    private JTextField zipField;
    private JFormattedTextField phoneField;
    private JTextField emailField;
    protected JTextField cardTypeField;
    protected JTextField cardNumberField;
    private JButton addButton;
    private JButton cancelButton;
    private JPanel mainPanel;
    private JButton addPaymentButton;
    private Controller controller;
    private int newID;  // Contains class-specific variables

    NewCustomerGUI(Controller controller) {
        super("Mybrarian: Add New Customer");

        //create reference to controller
        this.controller = controller;

        //set up JFrame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        pack();
        setVisible(true);

        //Remove editing from card field
        cardNumberField.setEditable(false);
        cardTypeField.setEditable(false);

        //Set state combo box options
        for (String x : controller.states) {
            stateComboBox.addItem(x);
        }

        //add Listeners
        addListeners(newID);
    }  //Builds New Customer GUI

    void addListeners(int sendID) {

        addPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sendFName = firstNameField.getText();
                String sendLName = lastNameField.getText();
                String sendStreet = addressField.getText();
                String sendCity = cityField.getText();
                String sendState = (String) stateComboBox.getSelectedItem();
                String sendZIP = zipField.getText();

                controller.CreditCardGUI(sendFName, sendLName, sendStreet, sendCity, sendState, sendZIP);

            }
        }); //Opens new credit card window. Current remains open. Sends name and address info to new window.

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (controller.getHoldCard() == null) {
                    JOptionPane.showMessageDialog(NewCustomerGUI.this, "Payment info required.");
                } else if (firstNameField.getText().isEmpty() ||
                        lastNameField.getText().isEmpty() ||
                        addressField.getText().isEmpty() ||
                        cityField.getText().isEmpty() ||
                        zipField.getText().isEmpty() ||
                        phoneField.getText().isEmpty() ||
                        emailField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(NewCustomerGUI.this, "Please fill out all fields.");
                } else {
                    CreditCard sendCard = controller.getHoldCard();


                    //Create new customer object
                    int newID = getNewID();
                    String firstName = firstNameField.getText();
                    String lastName = lastNameField.getText();
                    String streetAddress = addressField.getText();
                    String cityAddress = cityField.getText();
                    String stateAddress = stateComboBox.getSelectedItem().toString();
                    int zipAddress = Integer.parseInt(zipField.getText());
                    String phone = phoneField.getText();
                    String email = emailField.getText();
                    String creditCard = sendCard.lastFour;
                    int outBooks = 0;
                    Date addDate = new Date();

                    Customer newCust = new Customer(newID, firstName, lastName, streetAddress, cityAddress,
                            stateAddress, zipAddress, phone, email, creditCard, outBooks, addDate);

                    //Set customer ID in credit card profile
                    sendCard.custID = newID;

                    //Send card and customer to DB
                    controller.addCC(sendCard);
                    controller.addCustomer(newCust);

                    //Clear temporary card information and close window
                    controller.setHoldCard(null);
                    dispose();
                }
            }
        }); //Sends credit card and customer info to controller. Verifies all fields are full.

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Clear temporary card information
                controller.setHoldCard(null);
                controller.NewMainMenuGUI();
                dispose();
            }
        }); //Opens new Main Menu window, disposes current. Clears temporary card info.

    }

    private int getNewID() {

        //Create a new customer ID
        ArrayList<Customer> customers = new ArrayList<>(controller.getAllCust());

        Random rand = new Random();
        int newID = rand.nextInt(899999) + 100000;

        //Check integer against existing customers.
        for (Customer c : customers) {
            if (newID == c.customerID) {
                getNewID();
            }
        }
        return newID;
    } //Generates new 6-digit customer ID based on existing IDs
}
