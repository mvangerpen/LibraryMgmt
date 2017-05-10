package com.mark;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@SuppressWarnings("unchecked")
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
    private JTextField cardNameField;
    private JTextField cardCityAddressField;
    private JComboBox cardStateComboBox;
    private JTextField cardZIPAddressField;
    private JComboBox cardTypeComboBox;
    private JCheckBox useSameAddressCheckBox;
    private JTextField cardStreetAddressField;
    private JTextField csvField;
    private JComboBox monthBox;
    private JComboBox yearBox;
    private JFormattedTextField cardExpirationField;
    private final Controller controller;
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

        //Set state combo box options
        for (String x : Controller.states) {
            stateComboBox.addItem(x);
            cardStateComboBox.addItem(x);
        }

        cardTypeComboBox.addItem("VISA");
        cardTypeComboBox.addItem("MC");
        cardTypeComboBox.addItem("DISC");
        cardTypeComboBox.addItem("AMEX");

        //Expiration boxes - tracks five years out
        LocalDateTime now = LocalDateTime.now();
        int thisYear = now.getYear();
        for (int x = 1; x < 13; x++) {
            monthBox.addItem(x);
        }
        for (int x = thisYear; x < thisYear + 5; x++) {
            yearBox.addItem(x);
        }


        //add Listeners
        addListeners();
    }  //Builds New Customer GUI

    private void addListeners() {


        useSameAddressCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardNameField.setText(firstNameField.getText() + " " + lastNameField.getText());
                cardStreetAddressField.setText(addressField.getText());
                cardCityAddressField.setText(cityField.getText());
                cardStateComboBox.setSelectedItem(stateComboBox.getSelectedItem());
                cardZIPAddressField.setText(String.valueOf(zipField.getText()));
            }
        }); //Fills relevant fields with data from customer fields

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Check for empty fields
                if (firstNameField.getText().isEmpty() ||
                        lastNameField.getText().isEmpty() ||
                        addressField.getText().isEmpty() ||
                        cityField.getText().isEmpty() ||
                        zipField.getText().isEmpty() ||
                        phoneField.getText().isEmpty() ||
                        emailField.getText().isEmpty() ||
                        cardNameField.getText().isEmpty() ||
                        cardStreetAddressField.getText().isEmpty() ||
                        cardCityAddressField.getText().isEmpty() ||
                        cardStateComboBox.getSelectedItem().toString().isEmpty() ||
                        cardZIPAddressField.getText().isEmpty() ||
                        cardNumberField.getText().isEmpty() ||
                        monthBox.getSelectedItem().toString().isEmpty() ||
                        yearBox.getSelectedItem().toString().isEmpty() ||
                        csvField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(NewCustomerGUI.this, "Please fill out all fields.");

                } else {


                    //Create new customer ID
                    int newID = getNewID();

                    //Create new credit card object
                    String nameOnCard = cardNameField.getText();
                    String cardStreet = cardStreetAddressField.getText();
                    String cardCity = cardCityAddressField.getText();
                    String cardState = cardStateComboBox.getSelectedItem().toString();
                    int cardZIP = Integer.parseInt(cardZIPAddressField.getText());
                    String cardType = cardTypeComboBox.getSelectedItem().toString();
                    Long cardNumber = Long.parseLong(cardNumberField.getText());

                    //Save integers from expiration boxes as arraylist
                    int monthExp = (int) monthBox.getSelectedItem();
                    int yearExp = (int) yearBox.getSelectedItem();

                    Calendar newCal = Calendar.getInstance();
                    newCal.clear();
                    newCal.set(Calendar.MONTH, monthExp);
                    newCal.set(Calendar.YEAR, yearExp);
                    Date cardExp = newCal.getTime();

                    int csv = Integer.parseInt(csvField.getText());

                    CreditCard newCard = new CreditCard(newID, cardType, nameOnCard, cardNumber, cardExp, csv,
                            cardStreet, cardCity, cardState, cardZIP);

                    //create new customer object
                    String firstName = firstNameField.getText();
                    String lastName = lastNameField.getText();
                    String streetAddress = addressField.getText();
                    String cityAddress = cityField.getText();
                    String stateAddress = stateComboBox.getSelectedItem().toString();
                    int zipAddress = Integer.parseInt(zipField.getText());
                    String phone = phoneField.getText();
                    String email = emailField.getText();
                    String creditCard = newCard.lastFour;
                    int outBooks = 0;
                    Double totalCharged = 0.00;
                    Date addDate = new Date();

                    Customer newCust = new Customer(newID, firstName, lastName, streetAddress, cityAddress,
                            stateAddress, zipAddress, phone, email, creditCard, outBooks, totalCharged, addDate);

                    try {
                        //Send card and customer to DB
                        controller.addCustomer(newCust);
                        controller.addCC(newCard);
                        JOptionPane.showMessageDialog(NewCustomerGUI.this, "Created new Mybrarian.\nMybrary ID: " + newCust.getCustomerID());
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(NewCustomerGUI.this, "Error creating customer:\n" + e1.toString());
                    }

                    controller.NewMainMenuGUI();
                    dispose();
                }
            }
        }); //Sends credit card and customer info to controller. Verifies all fields are full.

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Clear temporary card information
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
    }   //Generates new 6-digit customer ID based on existing IDs
}