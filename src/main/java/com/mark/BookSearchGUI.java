package com.mark;//Class codes the main library BookSearchGUI

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

@SuppressWarnings("ALL")
public class BookSearchGUI extends JFrame {

    private JPanel mainPanel;
    private JButton customerDetailsButton;
    private JButton searchButton;
    private JButton checkOutInButton;
    private JTextField dueDate;
    private JTextField outDate;
    private JTextField customerIDField;
    private JTextField searchField;
    private JComboBox searchBar;
    private JTextField statusField;
    private JButton bookDetailsButton;
    private JButton clearButton;
    private JButton mainMenuButton;

    private final Controller controller;

    //Configure list model
    private JTable booksTable;
    private BooksTableModel booksTableModel;
    private Vector<Book> books;


    BookSearchGUI(Controller controller) {
        super("Mybrarian");

        //create reference to controller
        this.controller = controller;

        //Configure table model
        books = new Vector<>(controller.getAllBooks());
        booksTableModel = new BooksTableModel(books);
        booksTable.setModel(booksTableModel);

        //Add selections to search bar
        searchBar.addItem("ISBN");
        searchBar.addItem("Author");
        searchBar.addItem("Title");
        searchBar.addItem("Categories");


        //Lock fields
        statusField.setEditable(false);
        customerIDField.setEditable(false);
        outDate.setEditable(false);
        dueDate.setEditable(false);


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
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Book> currentBooks = controller.getAllBooks();
                books = new Vector<>();

                if (searchBar.getSelectedItem().toString().equals("ISBN") ) {
                    try{
                        int number = Integer.parseInt(searchField.getText());
                        for (Book b: currentBooks) {
                            if (b.isbn.contains(searchField.getText())) {
                                books.add(b);
                            }
                        }
                    } catch (NumberFormatException ne) {
                        JOptionPane.showMessageDialog(BookSearchGUI.this, "Please use numbers for ISBN search.");
                        return;
                    }

                } else if (searchBar.getSelectedItem().toString().equals("Author") ) {
                    String authorSearch = searchField.getText();
                    for (Book b: currentBooks) {
                        if (b.author.toLowerCase().contains(authorSearch.toLowerCase())) {
                            books.add(b);
                        }
                    }

                } else if (searchBar.getSelectedItem().toString().equals("Title") ) {
                    String titleSearch = searchField.getText();
                    for (Book b: currentBooks) {
                        if (b.title.toLowerCase().contains(titleSearch.toLowerCase())) {
                            books.add(b);
                        }
                    }

                } else if (searchBar.getSelectedItem().toString().equals("Categories") ) {
                    String categoriesSearch = searchField.getText();
                    for (Book b : currentBooks) {
                        if (b.categories.toLowerCase().contains(categoriesSearch.toLowerCase())) {
                            books.add(b);
                        }
                    }
                }

                if (books.size() > 0) {
                    booksTableModel = new BooksTableModel(books);
                    booksTable.setModel(booksTableModel);
                } else {
                    JOptionPane.showMessageDialog(BookSearchGUI.this, "No results.");
                }

            }

        }); //Searches database by combo box selection and search term

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Reset inventory list
                books = new Vector<>(controller.getAllBooks());
                booksTableModel = new BooksTableModel(books);
                booksTable.setModel(booksTableModel);

                //Clear all fields
                searchField.setText("");
                statusField.setText("");
                customerIDField.setText("");
                outDate.setText("");
                dueDate.setText("");
            }
        });  //Resets JList and clears all text fields

        checkOutInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int column = 0;
                int row = booksTable.getSelectedRow();
                int toEdit = Integer.parseInt(booksTable.getModel().getValueAt(row, column).toString());
                Book book = controller.getBook(toEdit);
                controller.CheckOutGUI(book);
                dispose();
            }
        });        //Opens Checkout Window, disposes current.

        customerDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (Integer.parseInt(customerIDField.getText()) == 0) {
                    JOptionPane.showMessageDialog(BookSearchGUI.this, "No customer data associated with this book.");
                } else {
                    int id = Integer.parseInt(customerIDField.getText());
                    controller.NewCustomerDetailsGUI(controller.getCustomer(id));
                    dispose();
                }
            }
        }); //Opens Customer Details window, disposes current. Sends book's associated Cust.Id to new GUI

        bookDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int column = 0;
                int row = booksTable.getSelectedRow();
                int selected = Integer.parseInt(booksTable.getModel().getValueAt(row, column).toString());

                //Use bookID
                ArrayList<Book> bookList = controller.getAllBooks();
                for (Book b: bookList) {
                    if (b.getMybID() == selected) {
                        controller.BookDetailsGUI(b);
                        dispose();
                    }
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

        booksTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                int x = booksTable.getSelectedRow();
                Book selectedBook = books.get(x);
                statusField.setText(selectedBook.getStatus());
                customerIDField.setText(String.valueOf(selectedBook.getCustID()));
                if (selectedBook.getCheckedOut() != null) {
                    outDate.setText(selectedBook.getCheckedOut().toString());
                }
                if (selectedBook.getDueDate() != null) {
                    dueDate.setText(selectedBook.getDueDate().toString());
                }
            }
        });

    }

}
