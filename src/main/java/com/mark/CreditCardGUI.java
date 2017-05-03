package com.mark;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.mark.Controller.newCustomerGUI;


public class CreditCardGUI extends JFrame{
    private JComboBox cardTypeComboBox;
    private JTextField cardNumberField;
    private JTextField cardFNameField;
    private JTextField csvField;
    private JFormattedTextField expirationField;
    private JTextField addressField;
    private JTextField cityField;
    private JComboBox stateComboBox;
    private JTextField zipField;
    private JButton addPaymentButton;
    private JButton cancelButton;
    private JPanel rootPanel;
    private JCheckBox sameAsCustomerAddressCheckBox;
    private JTextField cardLNameField;

    private Controller controller;
    private CreditCard newCard;     //Contains class-specific variables

    public CreditCardGUI(Controller controller, String sendFName, String sendLName, String sendStreet,
                         String sendCity, String sendState, String sendZIP) {

        super("Mybrarian: Add Payment");

        //create reference to controller
        this.controller = controller;

        //add Listeners
        addListeners(sendStreet, sendCity, sendState, sendZIP);

        //set up JFrame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(rootPanel);
        pack();
        setVisible(true);

        //Add Name from previous
        cardFNameField.setText(sendFName);
        cardLNameField.setText(sendLName);

        //Configure combo box options
        for (String x : controller.states) {
            stateComboBox.addItem(x);
        }

        cardTypeComboBox.addItem("VISA");
        cardTypeComboBox.addItem("MC");
        cardTypeComboBox.addItem("DISC");
        cardTypeComboBox.addItem("AMEX");

    }   //Builds new credit card GUI


    void addListeners(String sendStreet, String sendCity, String sendState, String sendZIP) {
        sameAsCustomerAddressCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addressField.setText(sendStreet);
                cityField.setText(sendCity);
                stateComboBox.setSelectedItem(sendState);
                zipField.setText(sendZIP);
            }
        }); //Fills relevant fields with data from customer GUI

        addPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String number = cardNumberField.getText();
                if (!isValidCreditCard(number)) {
                    JOptionPane.showMessageDialog(CreditCardGUI.this, "Invalid card number. Check number and try again.");
                } else {
                    //Collect card values
                    int custID = 0;
                    String cardType = (String) cardTypeComboBox.getSelectedItem();
                    String fname = cardFNameField.getText();
                    String lname = cardLNameField.getText();
                    Long num = Long.parseLong(cardNumberField.getText());
                    int exp = Integer.parseInt(expirationField.getText());
                    int csv = Integer.parseInt(csvField.getText());
                    String add = addressField.getText();
                    String city = cityField.getText();
                    String state = String.valueOf(stateComboBox.getSelectedItem());
                    int zip = Integer.parseInt(zipField.getText());

                    //Create new card
                    newCard = new CreditCard(custID, cardType, fname, lname, num, exp, csv, add, city, state, zip);

                    //Hold card in controller until Customer object is created
                    controller.setHoldCard(newCard);

                    //Update fields in open Customer window
                    newCustomerGUI.cardTypeField.setText(cardType);
                    newCustomerGUI.cardNumberField.setText(newCard.masked);

                    //Close window
                    dispose();

                }
            }
        }); //Adds new validated credit card to customer class

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Close window
                dispose();

            }
        }); //Disposes window and all field information.
    }

    public static boolean isValidCreditCard(String cc) {
        int total = 0;

        //Create array of integers based on cc
        int[] numArray = new int[cc.length()];

        //Loop through cc
        for (int x = 0; x < cc.length(); x++) {
            //Double even elements & 0 element
            if (x == 0 || x % 2 == 0) {
                numArray[x] = Character.getNumericValue(cc.charAt(x)) * 2;
            }
            else
                numArray[x] = Character.getNumericValue(cc.charAt(x));

            //Value of integers 10 through 19 added together is equivalent to int - 9
            if (numArray[x] >= 10){
                numArray[x] -= 9;
            }

            //Accumulate total
            total += numArray[x];
        }

        if (total % 10 == 0){
            return true;}
        else
            return false;
    } //Validates credit card number
}
