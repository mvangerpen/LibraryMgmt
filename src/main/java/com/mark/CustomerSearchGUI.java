package com.mark;

import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by admin on 4/25/2017.
 */
public class CustomerSearchGUI extends JFrame {
    private JPanel mainPanel;
    private JComboBox searchByComboBox;
    private JTextField searchField;
    private JButton searchButton;
    private JButton editDetailsButton;
    private JButton mainMenuButton;
    private JScrollPane resultsPane;


    private JTable resultsTable;
    private DefaultTableModel tableModel;
    private DefaultTableColumnModel columnModel;

    private Controller controller;

    CustomerSearchGUI(Controller controller) {
        super("Mybrarian: Search Customer");

        //create reference to controller
        this.controller = controller;

        fillTable();

        //add Listeners
        addListeners();

        //set up JFrame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        pack();
        setVisible(true);

    }

    private void fillTable() {
        ArrayList<Customer> custList = controller.getAllCust();
        resultsTable.setAutoCreateColumnsFromModel(true);



    }

    void addListeners() {

        editDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Customer editCustomer = resultsTable.get
                //controller.NewCustomerDetailsGUI();
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
