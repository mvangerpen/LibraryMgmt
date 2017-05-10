package com.mark;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.*;


@SuppressWarnings("ALL")
public class CreditCardGUI extends JFrame {
    private JComboBox cardTypeComboBox;
    private JTextField cardNumberField;
    private JTextField nameOnCardField;
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
    private JComboBox expMonthBox;
    private JComboBox expYearBox;
    private JTextField cardLNameField;

    private final Controller controller;
    private CreditCard newCard;     //Contains class-specific variables

    public CreditCardGUI(Controller controller, CreditCard oldCard) {

        super("Mybrarian: Edit Payment");

        //create reference to controller
        this.controller = controller;

        //add Listeners
        addListeners(oldCard);

        //set up JFrame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(rootPanel);
        pack();
        setVisible(true);

        //Configure combo box options
        //States
        for (String x : Controller.states) {
            stateComboBox.addItem(x);
        }

        //Card Type box
        cardTypeComboBox.addItem("VISA");
        cardTypeComboBox.addItem("MC");
        cardTypeComboBox.addItem("DISC");
        cardTypeComboBox.addItem("AMEX");

        //Expiration boxes - tracks five years out
        LocalDateTime now = LocalDateTime.now();
        int thisYear = now.getYear();
        for (int x = 1; x < 13; x++) {
            expMonthBox.addItem(x);
        }
        for (int x = thisYear; x < thisYear + 5; x++) {
            expYearBox.addItem(x);
        }

        addInfo(oldCard);

    }   //Builds new credit card GUI


    private void addListeners(CreditCard oldCard) {

        sameAsCustomerAddressCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Get customer info
                int custID = oldCard.getCustID();

                Customer storedCust = controller.getCustomer(custID);

                addressField.setText(storedCust.getStreetAddress());
                cityField.setText(storedCust.getCityAddress());
                stateComboBox.setSelectedItem(storedCust.getStateAddress());
                zipField.setText(String.valueOf(storedCust.getZipAddress()));
            }
        });

        addPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String number = cardNumberField.getText();
                if (!Controller.isValidCreditCard(number)) {
                    JOptionPane.showMessageDialog(CreditCardGUI.this, "Invalid card number. Check number and try again.");
                } else {
                    //Collect card values
                    int custID = oldCard.getCustID();
                    String cardType = (String) cardTypeComboBox.getSelectedItem();
                    String name = nameOnCardField.getText();
                    Long num = Long.parseLong(cardNumberField.getText());

                    int monthExp = (int) expMonthBox.getSelectedItem();
                    int yearExp = (int) expYearBox.getSelectedItem();

                    Calendar newCal = Calendar.getInstance();
                    newCal.clear();
                    newCal.set(Calendar.MONTH, monthExp);
                    newCal.set(Calendar.YEAR, yearExp);
                    Date exp = newCal.getTime();

                    int csv = Integer.parseInt(csvField.getText());
                    String add = addressField.getText();
                    String city = cityField.getText();
                    String state = String.valueOf(stateComboBox.getSelectedItem());
                    int zip = Integer.parseInt(zipField.getText());

                    //Create new card
                    newCard = new CreditCard(custID, cardType, name, num, exp, csv, add, city, state, zip);

                    //Update card in DB
                    try {
                        controller.updateCC(newCard);
                        JOptionPane.showMessageDialog(CreditCardGUI.this, "Card updated.");

                        //Close window
                        controller.NewCustomerDetailsGUI(controller.getCustomer(custID));
                        dispose();

                    } catch (Exception e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(CreditCardGUI.this, "Error updating card.");
                    }

                }
            }
        }); //Adds new validated credit card to customer class

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Close window
                controller.NewMainMenuGUI();
                dispose();

            }
        }); //Disposes window and all field information.
    }


    private void addInfo(CreditCard card) {
        nameOnCardField.setText(card.getNameOnCard());
        cardTypeComboBox.setSelectedItem(card.getType());
        cardNumberField.setText(String.valueOf(card.getNumber()));

        Date expDate = card.getExpiration();
        Calendar cal = Calendar.getInstance();
        cal.setTime(expDate);
        expMonthBox.setSelectedItem(cal.get(Calendar.MONTH));
        expYearBox.setSelectedItem(cal.get(Calendar.YEAR));

        csvField.setText(String.valueOf(card.getCsv()));
        addressField.setText(card.getCardAddress());
        cityField.setText(card.getCardCity());
        stateComboBox.setSelectedItem(card.getCardState());
        zipField.setText(String.valueOf(card.cardZIP));
    }


}