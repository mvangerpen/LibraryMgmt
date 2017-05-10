package com.mark;

import com.mark.Customer;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class CustTableModel extends AbstractTableModel{

    private Vector<Customer> customerList;

    private String[] colNames = { "Customer ID", "First Name", "Last Name", "Phone", "Email"};

    CustTableModel(Vector<Customer> customers) {
        customerList = customers;
    }


    @Override
    public int getRowCount() {

        if(customerList.size() > 0){
            return customerList.size();
        }
        else{
            return 0;
        }
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        if (columnIndex == 0) {
            return customerList.get(rowIndex).getCustomerID();
        }

        if (columnIndex == 1) {
            return customerList.get(rowIndex).getFirstName();
        }

        if (columnIndex == 2) {
            return customerList.get(rowIndex).getLastName();
        }

        if (columnIndex == 3) {
            return customerList.get(rowIndex).getPhone();
        }

        if (columnIndex == 4) {
            return customerList.get(rowIndex).getEmail();
        }

        return null;
    }


    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

}