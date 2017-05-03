package com.mark;//Class codes the main library BookSearchGUI

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BookSearchGUI extends JFrame {

    private JPanel mainPanel;
    private JButton customerDetailsButton;
    private JButton searchButton;
    private JButton checkOutInButton;
    private JTextField isbnField;
    private JLabel isbnLabel;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField dueDate;
    private JTextField outDate;
    private JTextField customerIDField;
    private JTextField searchField;
    private JComboBox searchBar;
    private JTextField statusField;
    private JTextField genreField;
    private JButton bookDetailsButton;
    private JButton clearButton;

    private Controller controller;

    //Configure list model
    private JList<Book> bookList;
    private JButton mainMenuButton;
    private DefaultListModel<Book> allBooksModel;


    BookSearchGUI(Controller controller) {
        super("Mybrarian");

        //create reference to controller
        this.controller = controller;

        //Configure list model
        allBooksModel = new DefaultListModel<>();
        bookList.setModel(allBooksModel);

        //Add selections to search bar
        configureSearchBar();

        //add Listeners
        addListeners();

        //set up JFrame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        pack();
        setVisible(true);
    }



    //Add Listeners
    private void addListeners() {
        //Search Button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Book> currentBooks = controller.getAllBooks();
                ArrayList<Book> search = new ArrayList<>();

                if (searchBar.toString().equals("ISBN") ) {
                    try{
                        int number = Integer.parseInt(searchField.getText());
                        for (Book b: currentBooks) {
                            if (b.isbn == number) {
                                search.add(b);
                            }
                        }
                    } catch (NumberFormatException ne) {
                        JOptionPane.showMessageDialog(BookSearchGUI.this, "Please use numbers for ISBN search.");
                        return;
                    }

                } else if (searchBar.toString().equals("Author") ) {
                    String authorSearch = searchField.getText();
                    for (Book b: currentBooks) {
                        if (b.author.contains(authorSearch)) {
                            search.add(b);
                        }
                    }

                } else if (searchBar.toString().equals("Title") ) {
                    String titleSearch = searchField.getText();
                    for (Book b: currentBooks) {
                        if (b.author.contains(titleSearch)) {
                            search.add(b);
                        }
                    }

                } else if (searchBar.toString().equals("Genre") ) {
                    String genreSearch = searchField.getText();
                    for (Book b: currentBooks) {
                        if (b.author.contains(genreSearch)) {
                            search.add(b);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(BookSearchGUI.this, "Please select a search term.");
                    return;
                }
                setBookListData(search);
            }
        }); //Searches database by combo box selection and search term

        //Clear Button
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Reset inventory list
                ArrayList<Book> currentBooks = controller.getAllBooks();
                setBookListData(currentBooks);

                //Clear all fields
                searchField.setText("");
                isbnField.setText("");
                titleField.setText("");
                authorField.setText("");
                genreField.setText("");
                statusField.setText("");
                customerIDField.setText("");
                outDate.setText("");
                dueDate.setText("");
            }
        });  //Resets JList and clears all text fields

        //Check Out/In Button
        checkOutInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //TODO: Create Checkout Window

            }
        });

        //Customer Details Button
        customerDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //TODO: Create Customer Details Window

            }
        });

        bookDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!bookList.isSelectionEmpty()) {
                    Book selection = bookList.getSelectedValue();
                    controller.BookDetailsGUI(selection);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(BookSearchGUI.this, "No book selected.");
                }
            }
        }); //Opens book details window, disposes current. Sends current JList selection to new GUI

        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.NewMainMenuGUI();
                dispose();
            }
        }); //Opens main menu window, disposes current.

    }


    void configureSearchBar() {
        searchBar.addItem("ISBN");
        searchBar.addItem("Author");
        searchBar.addItem("Title");
        searchBar.addItem("Genre");
    }



    void setBookListData(ArrayList<Book> data) {
        //Display data in allData
        allBooksModel.clear();
        for (Book book : data) {
            allBooksModel.addElement(book);
        }
    }


    /*
    TODO: Add this to Customer Search window
    void setCustListData(ArrayList<Customer> data) {
        //Display data in allData
        allCustModel.clear();
        for (Customer cust : data) {
            allCustModel.addElement(cust);
        }
    }
    */
}
