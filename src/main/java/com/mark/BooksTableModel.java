package com.mark;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

import com.mark.Book;

public class BooksTableModel extends AbstractTableModel {

    private Vector<Book> booksList;

    private String[] colNames = { "BookID", "Title", "Author", "ISBN", "Status"};

    BooksTableModel(Vector<Book> books) {
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
            return booksList.get(rowIndex).getMybID();
        }
        if (columnIndex == 1) {
            return booksList.get(rowIndex).getTitle();
        }

        if (columnIndex == 2) {
            return booksList.get(rowIndex).getAuthor();
        }

        if (columnIndex == 3) {
            return booksList.get(rowIndex).getIsbn();
        }

        if (columnIndex == 4) {
            return booksList.get(rowIndex).getStatus();
        }

        return null;
    }


    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

}
