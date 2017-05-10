package com.mark;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

public class ReportsGUI extends JFrame {
    private JPanel mainPanel;
    private JComboBox reportOptionsBox;
    private JButton runButton;
    private JButton detailsButton;
    private JButton backButton;
    private Controller controller;

    //Configure list model
    private JTable booksTable;
    private SoldBooksTableModel soldBooksTableModel;
    private Vector<SoldBook> soldBooks;

    //Configure secondary list model
    private BooksTableModel booksTableModel;
    private Vector<Book> books;

    ArrayList<Book> allBooks;
    ArrayList<Book> booksReport;

    ReportsGUI(Controller controller) {
        super("MyReports");

        this.controller = controller;

        //Bring in all books for running reports
        allBooks = controller.getAllBooks();
        booksReport = new ArrayList<>();


        //Configure combo box
        reportOptionsBox.addItem("Sold Books");
        reportOptionsBox.addItem("Overdue Books");
        reportOptionsBox.addItem("Checked Out Books");
        reportOptionsBox.addItem("Renewed Books");
        reportOptionsBox.addItem("In Stock Books");

        //add Listeners
        addListeners();

        //set up JFrame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        pack();
        setVisible(true);
    }

    private void addListeners() {

        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (reportOptionsBox.getSelectedItem().toString().equals("Sold Books")) {

                    //Configure table model
                    soldBooks = new Vector<>(controller.fetchAllSoldBooks());
                    soldBooksTableModel = new SoldBooksTableModel(soldBooks);
                    booksTable.setModel(soldBooksTableModel);

                } else if (reportOptionsBox.getSelectedItem().toString().equals("Overdue Books")) {

                    for (Book b: allBooks) {
                        if (b.getStatus().equals("OVD")) {
                            booksReport.add(b);
                        }
                    }

                    //Configure table model
                    books = new Vector<>(booksReport);
                    booksTableModel = new BooksTableModel(books);
                    booksTable.setModel(booksTableModel);

                } else if (reportOptionsBox.getSelectedItem().toString().equals("Checked Out Books")) {

                    for (Book b: allBooks) {
                        if (b.getStatus().equals("OUT")) {
                            booksReport.add(b);
                        }
                    }

                    //Configure table model
                    books = new Vector<>(booksReport);
                    booksTableModel = new BooksTableModel(books);
                    booksTable.setModel(booksTableModel);


                } else if (reportOptionsBox.getSelectedItem().toString().equals("In Stock Books")) {

                    for (Book b: allBooks) {
                        if (b.getStatus().equals("IN")) {
                            booksReport.add(b);
                        }
                    }

                    //Configure table model
                    books = new Vector<>(booksReport);
                    booksTableModel = new BooksTableModel(books);
                    booksTable.setModel(booksTableModel);
                }
            }
        }); //Displays table based on selected report

        detailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if (reportOptionsBox.getSelectedItem().equals("Sold Books")) {

                    int column = 0;
                    int row = booksTable.getSelectedRow();
                    int selected = Integer.parseInt(booksTable.getModel().getValueAt(row, column).toString());

                    //Use bookID
                    for (Book b : allBooks) {
                        if (b.getMybID() == selected) {
                            controller.BookDetailsGUI(b);
                            dispose();
                        }
                    }
               // }

            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.NewMainMenuGUI();
                dispose();
            }
        });

    }

}
