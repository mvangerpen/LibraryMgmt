package com.mark;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

class SoldBooksTableModel extends AbstractTableModel {

    private final Vector<SoldBook> booksList;

    private final String[] colNames = { "MyBook ID", "Title", "Author", "MyBrarian ID", "First Name", "Last Name", "Sold Price", "Date Sold"};

    SoldBooksTableModel(Vector<SoldBook> books) {
        booksList = books;
    }

    @Override
    public int getRowCount() {

        if(booksList.size() > 0){
            return booksList.size();
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
            return booksList.get(rowIndex).getdBookID();
        }

        if (columnIndex == 1) {
            return booksList.get(rowIndex).getdTitle();
        }

        if (columnIndex == 2) {
            return booksList.get(rowIndex).getdAuthor();
        }

        if (columnIndex == 3) {
            return booksList.get(rowIndex).getdCustID();
        }
        if (columnIndex == 4) {
            return booksList.get(rowIndex).getdFirstName();
        }
        if (columnIndex == 5) {
            return booksList.get(rowIndex).getdLastName();
        }
        if (columnIndex == 6) {
            return booksList.get(rowIndex).getdSalePrice();
        }
        if (columnIndex == 7) {
            return booksList.get(rowIndex).getdDateSold();
        }

        return null;
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

}
