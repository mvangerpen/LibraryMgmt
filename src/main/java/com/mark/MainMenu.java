package com.mark;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    private JPanel mainPanel;
    private JButton searchBooksButton;
    private JButton searchCustomersButton;
    private JButton createNewCustomerButton;
    private JButton addBookButton;
    private JButton reportsButton;

    private final Controller controller;

    MainMenu(Controller controller) {
        super("Mybrarian: Main Menu");

        //create reference to controller
        this.controller = controller;

        //add Listeners
        addListeners();

        //set up JFrame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        pack();
        setVisible(true);
    }


    private void addListeners() {

        searchBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.NewBookSearchGUI();
                dispose();
            }
        }); //Opens new search window, disposes menu

        searchCustomersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.NewCustomerSearchGUI();
                dispose();
            }
        }); //Opens new Customer search window, disposes menu

        createNewCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.AddNewCustomerGUI();
                dispose();
            }
        });         //Create new customer button

        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.AddBookGUI();
                dispose();

            }
        });        //Add new book button

        reportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.ReportsGUI();
                dispose();

            }
        });         //Opens reports window

    }

}
