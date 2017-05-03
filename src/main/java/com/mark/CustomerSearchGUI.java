package com.mark;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

        resultsTable = fillTable();

        //add Listeners
        addListeners();

        //set up JFrame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        pack();
        setVisible(true);

    }

    private JTable fillTable() {
        resultsTable = new JTable(new TableModel());
        return resultsTable;
        }

    class TableModel extends AbstractTableModel {

        private String[] columnNames = {"Customer ID", "First Name", "Last Name", "Phone", "Amt Checked Out"};

        private Object[] truncated = truncateCust();

        private Object[][] rowData = {truncated};


        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() { return rowData.length; }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object[] truncateCust() {
            ArrayList<Customer> custList = controller.getAllCust();
            Object[] shortened = new Object[custList.size()];
            String[] data = new String[5];

            for (int x =0; x < custList.size(); x++) {
                //Build truncated individual objects
                data[0] = String.valueOf(custList.get(x).customerID);
                data[1] = (custList.get(x).firstName);
                data[2] = custList.get(x).lastName;
                data[3] = custList.get(x).phone;
                data[4] = String.valueOf(custList.get(x).checkedOut);

                //put them in a List
                shortened[x] = data;
            }

            return shortened;
        }

        public Object getValueAt(int row, int column) {
            return rowData[row];
        }
    }

    void addListeners() {

        editDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int column = 0;
                int row = resultsTable.getSelectedRow();
                int toEdit = Integer.parseInt(resultsTable.getModel().getValueAt(row, column).toString());

                //Use custID
                ArrayList<Customer> custList = controller.getAllCust();
                Customer toUse = null;
                for (Customer c: custList) {
                    if (c.customerID == toEdit) {
                        toUse = c;
                        break;
                    }
                }

                controller.NewCustomerDetailsGUI(toUse);

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
