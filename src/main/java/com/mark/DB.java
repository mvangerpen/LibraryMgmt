package com.mark;//Contains the database input and functions

import java.sql.*;
import java.sql.Date;
import java.util.*;


public class DB {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/library";
    private static final String USER = "root";
    private static final String PASSWORD = System.getenv("MYSQL_PW");      //Establish variables for connecting to database

    private static final String BOOK_TABLE = "Books";
    private static final String ISBN_COL = "ISBN";
    private static final String TITLE_COL = "Title";
    private static final String AUTHOR_COL = "Author";
    private static final String GENRE_COL = "Genre";
    private static final String PAGES_COL = "Pages";
    private static final String PUBLISHER_COL = "Publisher";
    private static final String YEAR_PUBLISHED_COL = "Year";
    private static final String PRICE_COL = "Price";
    private static final String COPIES_COL = "Copies";
    private static final String STATUS_COL = "Status";
    private static final String CUST_ID_COL = "CustomerID";
    private static final String CHECKOUT_DATE_COL = "CheckedOut";
    private static final String DUE_DATE_COL = "DueDate";
    //Establish variables for books table

    private static final String CUST_TABLE = "Customers";
    private static final String ID_COL = "CustomerID";
    private static final String FNAME_COL = "FirstName";
    private static final String LNAME_COL = "LastName";
    private static final String STREET_COL = "Street";
    private static final String CITY_COL = "City";
    private static final String STATE_COL = "State";
    private static final String ZIP_COL = "ZIP";
    private static final String PHONE_COL = "Phone";
    private static final String EMAIL_COL = "Email";
    private static final String CARD_COL = "CreditCard"; //Displays only last four digits of card. Full card must be called from creditcards table.
    private static final String CHECKOUT_COL = "NumberCheckedOut";
    private static final String CREATED_COL = "DateAdded";      //Establish variables for Customers table

    private static final String CC_TABLE = "CreditCards";  //CustID pulls from customer info.
    private static final String CC_FNAME_COL = "FirstName";
    private static final String CC_LNAME_COL = "LastName";
    private static final String CARD_TYPE_COL = "CardType";
    private static final String NUMBER_COL = "CardNumber";
    private static final String EXP_COL = "ExpDate";
    private static final String CSV_COL = "CSV";
    private static final String CARD_ADDRESS_COL = "Street";
    private static final String CARD_CITY_COL = "City";
    private static final String CARD_STATE_COL = "State";
    private static final String CARD_ZIP_COL = "ZIP";       //Establish variables for Credit Card table

    DB() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Can't instantiate driver class; check driver and classpath configuration.");
            cnfe.printStackTrace();
            System.exit(-1);  //Close program if not found
        }
    }      //Connect to class

    void createTables() {
        //Try with resources to open the connection and create a statement.
        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
             //Create book table
             Statement statement = conn.createStatement()) {


            String createBookTableSQLTemplate = "CREATE TABLE IF NOT EXISTS %s(%s LONG, %s VARCHAR(50), %s VARCHAR(50), %s VARCHAR(25)," +
                    "%s INT, %s VARCHAR(50), %s INT, %s DOUBLE, %s INT, %s BOOLEAN, %s INT, %s DATE, %s DATE, PRIMARY KEY(%s(13)))";
            String createBookTable = String.format(createBookTableSQLTemplate, BOOK_TABLE, ISBN_COL, TITLE_COL, AUTHOR_COL, GENRE_COL,
                                                PAGES_COL, PUBLISHER_COL, YEAR_PUBLISHED_COL, PRICE_COL, COPIES_COL, STATUS_COL, CUST_ID_COL,
                                                DUE_DATE_COL, CHECKOUT_DATE_COL, ISBN_COL);

            statement.executeUpdate(createBookTable);

            //Notify if successful
            System.out.println("Created books table");


            //Create customers table
            String createCustTableSQLTemplate = "CREATE TABLE IF NOT EXISTS %s (%s INT, %s VARCHAR(50), %s VARCHAR(25), %s VARCHAR(100), " +
                    "%s VARCHAR(50), %s VARCHAR(2), %s INT, %s VARCHAR(20), %s VARCHAR(50), %s VARCHAR(4), %s INT, %s DATE, PRIMARY KEY(%s))";
            String createCustTable = String.format(createCustTableSQLTemplate, CUST_TABLE, ID_COL, FNAME_COL, LNAME_COL, STREET_COL,
                                    CITY_COL, STATE_COL, ZIP_COL, PHONE_COL, EMAIL_COL, CARD_COL, CHECKOUT_COL, CREATED_COL,
                                    ID_COL);
            statement.executeUpdate(createCustTable);

            //Notify if successful
            System.out.println("Created customers table");


            //Create credit card table
            String createCCTableSQLTemplate = "CREATE TABLE IF NOT EXISTS %s (%s INT, %s VARCHAR(50), %s VARCHAR(50), " +
                    "%s VARCHAR(4), %s LONG, %s INT, %s INT, %s VARCHAR(50), %s VARCHAR(50), %s VARCHAR(2), %s INT, " +
                    "PRIMARY KEY(%s))";
            String createCCTable = String.format(createCCTableSQLTemplate, CC_TABLE, CUST_ID_COL, CC_FNAME_COL, CC_LNAME_COL,
                    CARD_TYPE_COL, NUMBER_COL, EXP_COL, CSV_COL, CARD_ADDRESS_COL, CARD_CITY_COL, CARD_STATE_COL,
                    CARD_ZIP_COL, CUST_ID_COL);
            statement.executeUpdate(createCCTable);

            //Notify if successful
            System.out.println("Created credit card table");

            //Close connections
            statement.close();
            conn.close();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }  //Create customer, book, and credit card tables in database


    //LIBRARY MANAGEMENT

    void addBook(Book book) {

        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD)) {

            //Establish prepared statement, set values from Book class
            String addBookSQL = "INSERT INTO " + BOOK_TABLE + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement addBookPS = conn.prepareStatement(addBookSQL);
            addBookPS.setLong(1, book.isbn);
            addBookPS.setString(2, book.title);
            addBookPS.setString(3, book.author);
            addBookPS.setString(4, book.genre);
            addBookPS.setInt(5, book.pages);
            addBookPS.setString(6, book.publisher);
            addBookPS.setInt(7, book.yearPublished);
            addBookPS.setDouble(8, book.price);
            addBookPS.setInt(9, book.copies);
            addBookPS.setBoolean(10, book.status);
            addBookPS.setInt(11, book.custID);
            addBookPS.setDate(12, new java.sql.Date(book.checkedOut.getTime()));  //Cast java date to SQL date format
            addBookPS.setDate(13, new java.sql.Date(book.dueDate.getTime()));


            //Execute prepared statement
            addBookPS.execute();

            //Close connections
            addBookPS.close();
            conn.close();

            //Notify if successful
            System.out.println("Added record for " + book.title);

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }               //Function for adding a book

    void updateBook(Book book) {

        //Connect to DB
        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
             Statement statement = conn.createStatement()) {

            //Prep prepared statement
            String updateBookTemplate = "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, " +
                    "%s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?";
            String updateBook = String.format(updateBookTemplate, BOOK_TABLE, ISBN_COL, TITLE_COL, AUTHOR_COL, GENRE_COL,
                    PAGES_COL, PUBLISHER_COL, YEAR_PUBLISHED_COL, PRICE_COL, COPIES_COL, STATUS_COL, CUST_ID_COL,
                    CHECKOUT_DATE_COL, DUE_DATE_COL, ISBN_COL);
            PreparedStatement updateBookPS = conn.prepareStatement(updateBook);

            updateBookPS.setLong(1, book.isbn);
            updateBookPS.setString(2, book.title);
            updateBookPS.setString(3, book.author);
            updateBookPS.setString(4, book.genre);
            updateBookPS.setInt(5, book.pages);
            updateBookPS.setString(6, book.publisher);
            updateBookPS.setInt(7, book.yearPublished);
            updateBookPS.setDouble(8, book.price);
            updateBookPS.setInt(9, book.copies);
            updateBookPS.setBoolean(10, book.status);
            updateBookPS.setInt(11, book.custID);
            updateBookPS.setDate(12, new java.sql.Date(book.checkedOut.getTime()));  //Cast java date to SQL date format
            updateBookPS.setDate(13, new java.sql.Date(book.dueDate.getTime()));
            updateBookPS.setLong(14, book.isbn);

            //Update record
            updateBookPS.executeUpdate();

            //close connections
            updateBookPS.close();
            statement.close();
            conn.close();

            //Notify if successful
            System.out.println("Record updated for ISBN: " + book.isbn + "    Title: " + book.title);

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }           //Function for updating a book's information

    void deleteBook(Book book) {

        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD)) {

            //Prepare delete statement
            String deleteSQLTemplate = "DELETE FROM %s WHERE %s = ?";
            String deleteSQL = String.format(deleteSQLTemplate, BOOK_TABLE, ISBN_COL);
            PreparedStatement deletePreparedStatement = conn.prepareStatement(deleteSQL);
            deletePreparedStatement.setLong(1, book.isbn);

            //Execute delete
            deletePreparedStatement.execute();

            //close connections
            deletePreparedStatement.close();
            conn.close();

            //Notify if successful
            System.out.println("Record deleted: " + book.toString());

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }           //Delete a book entry


    //CUSTOMER MANAGEMENT

    void addCustomer(Customer cust) {

        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
                Statement statement = conn.createStatement()) {

            //Establish prepared statement, set values from Customer class
            String addCustSQL = "INSERT INTO " + CUST_TABLE + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement addCustPS = conn.prepareStatement(addCustSQL);

            addCustPS.setInt(1, cust.customerID);
            addCustPS.setString(2, cust.firstName);
            addCustPS.setString(3, cust.lastName);
            addCustPS.setString(4, cust.streetAddress);
            addCustPS.setString(5, cust.cityAddress);
            addCustPS.setString(6, cust.stateAddress);
            addCustPS.setInt(7, cust.zipAddress);
            addCustPS.setString(8, cust.phone);
            addCustPS.setString(9, cust.email);
            addCustPS.setString(10, cust.creditCard);
            addCustPS.setInt(11, cust.checkedOut);
            addCustPS.setDate(12, new java.sql.Date(cust.addDate.getTime())); //Cast java date to SQL date format

            //Execute prepared statement
            addCustPS.execute();

            //Close connections
            addCustPS.close();
            conn.close();

            //Notify if successful
            System.out.println("Added record for " + cust.firstName + " " + cust.lastName);

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }       //Function for adding a customer

    void updateCustomer(Customer cust) {

        //Connect to DB
        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
             Statement statement = conn.createStatement()) {

            //Prep prepared statement
            String updateCustTemplate = "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?," +
                    " %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?";
            String updateCust = String.format(updateCustTemplate, CUST_TABLE,ID_COL, FNAME_COL, LNAME_COL, STREET_COL,
                    CITY_COL, STATE_COL, ZIP_COL, PHONE_COL, EMAIL_COL, CARD_COL, CREATED_COL, ID_COL);
            PreparedStatement updateCustPS = conn.prepareStatement(updateCust);

            updateCustPS.setInt(1, cust.customerID);
            updateCustPS.setString(2, cust.firstName);
            updateCustPS.setString(3, cust.lastName);
            updateCustPS.setString(4, cust.streetAddress);
            updateCustPS.setString(5, cust.cityAddress);
            updateCustPS.setString(6, cust.stateAddress);
            updateCustPS.setInt(7, cust.zipAddress);
            updateCustPS.setString(8, cust.phone);
            updateCustPS.setString(9, cust.email);
            updateCustPS.setString(10, cust.creditCard);
            updateCustPS.setInt(11, cust.checkedOut);
            updateCustPS.setDate(12, new java.sql.Date(cust.addDate.getTime())); //Cast java date to SQL date format

            //Update record
            updateCustPS.executeUpdate();

            //close connections
            updateCustPS.close();
            statement.close();
            conn.close();

            //Notify if successful
            System.out.println("Record updated for " + cust.firstName + " " + cust.lastName);

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }   //Function for updating a customer's information

    void deleteCustomer(Customer cust) {

        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD)) {

            //Prepare delete statement
            String deleteSQLTemplate = "DELETE FROM %s WHERE %s = ?";
            String deleteSQL = String.format(deleteSQLTemplate, CUST_TABLE, CUST_ID_COL);
            PreparedStatement deletePreparedStatement = conn.prepareStatement(deleteSQL);
            deletePreparedStatement.setInt(1, cust.customerID);

            //Execute delete
            deletePreparedStatement.execute();

            //close connections
            deletePreparedStatement.close();
            conn.close();

            //Notify if successful
            System.out.println("Record deleted: " + cust.toString());

        } catch (SQLException se) {
            se.printStackTrace();
        }

    }       //Function for deleting a customer


    //CREDIT CARD MANAGEMENT

    void addCard(CreditCard cc) {

        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
             Statement statement = conn.createStatement()) {

            //Establish prepared statement, set values from Customer class
            String addCCSQL = "INSERT INTO " + CC_TABLE + " VALUES(?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement addCCPS = conn.prepareStatement(addCCSQL);

            addCCPS.setInt(1, cc.custID);
            addCCPS.setString(2, cc.firstName);
            addCCPS.setString(3, cc.lastName);
            addCCPS.setString(4, cc.type);
            addCCPS.setLong(5, cc.number);
            addCCPS.setInt(6, cc.expiration);
            addCCPS.setInt(7, cc.csv);
            addCCPS.setString(8, cc.cardAddress);
            addCCPS.setString(9, cc.cardCity);
            addCCPS.setString(10, cc.cardState);
            addCCPS.setInt(11, cc.cardZIP);

            //Execute prepared statement
            addCCPS.execute();

            //Close connections
            addCCPS.close();
            conn.close();

            //Notify if successful
            System.out.println("Added payment for Customer " + cc.custID + ": " + cc.firstName + " " + cc.lastName);

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }       //Function for adding a credit card

    void updateCard(CreditCard cc) {

        //Connect to DB
        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
             Statement statement = conn.createStatement()) {

            //Prep prepared statement
            String updateCCTemplate = "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, " +
                    "%s = ?, %s = ?, %s = ? WHERE %s = ?";
            String updateCC = String.format(updateCCTemplate, CC_TABLE, ID_COL, FNAME_COL, LNAME_COL, CARD_TYPE_COL,
                    NUMBER_COL, EXP_COL, CSV_COL, CARD_ADDRESS_COL, CARD_CITY_COL, CARD_STATE_COL, CARD_ZIP_COL, ID_COL);
            PreparedStatement updateCCPS = conn.prepareStatement(updateCC);

            updateCCPS.setInt(1, cc.custID);
            updateCCPS.setString(2, cc.firstName);
            updateCCPS.setString(3, cc.lastName);
            updateCCPS.setString(4, cc.type);
            updateCCPS.setLong(5, cc.number);
            updateCCPS.setInt(6, cc.expiration);
            updateCCPS.setInt(7, cc.csv);
            updateCCPS.setString(8, cc.cardAddress);
            updateCCPS.setString(9, cc.cardCity);
            updateCCPS.setString(10, cc.cardState);
            updateCCPS.setInt(11, cc.cardZIP);

            //Update record
            updateCCPS.executeUpdate();

            //close connections
            updateCCPS.close();
            statement.close();
            conn.close();

            //Notify if successful
            System.out.println("Record updated for Customer" + cc.custID + ": " + cc.firstName + " " + cc.lastName);

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }   //Function for updating a customer's payment information

    void deleteCard(CreditCard cc) {

        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD)) {

            //Prepare delete statement
            String deleteSQLTemplate = "DELETE FROM %s WHERE %s = ?";
            String deleteSQL = String.format(deleteSQLTemplate, CC_TABLE, ID_COL);
            PreparedStatement deletePreparedStatement = conn.prepareStatement(deleteSQL);
            deletePreparedStatement.setInt(1, cc.custID);

            //Execute delete
            deletePreparedStatement.execute();

            //close connections
            deletePreparedStatement.close();
            conn.close();

            //Notify if successful
            System.out.println("Card deleted for Customer " + cc.custID + ": " + cc.firstName + " " + cc.lastName);

        } catch (SQLException se) {
            se.printStackTrace();
        }

    }       //Function for deleting a credit card


    //RECORD MANAGEMENT

    ArrayList<Book> fetchAllBooks() {
        //Initialize list for holding records
        ArrayList<Book> allBooks = new ArrayList<>();

        //Connect to DB
        try(Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
            Statement statement = conn.createStatement()) {

            String selectAllSQL = "SELECT * FROM " + BOOK_TABLE;
            ResultSet rsAll = statement.executeQuery(selectAllSQL);

            while (rsAll.next()) {
                Long isbn = rsAll.getLong(ISBN_COL);
                String title = rsAll.getString(TITLE_COL);
                String author = rsAll.getString(AUTHOR_COL);
                String genre = rsAll.getString(GENRE_COL);
                int pages = rsAll.getInt(PAGES_COL);
                String publisher = rsAll.getString(PUBLISHER_COL);
                int year = rsAll.getInt(YEAR_PUBLISHED_COL);
                Double retailPrice = rsAll.getDouble(PRICE_COL);
                int numCopies = rsAll.getInt(COPIES_COL);
                Boolean status = rsAll.getBoolean(STATUS_COL);
                int custID = rsAll.getInt(CUST_ID_COL);
                Date checkedOut = rsAll.getDate(CHECKOUT_DATE_COL);
                Date dueDate = rsAll.getDate(DUE_DATE_COL);

                Book bookRecord = new Book(isbn, title, author, genre, pages, publisher, year, retailPrice, numCopies,
                        status, custID, checkedOut, dueDate);
                allBooks.add(bookRecord);
            }

            //Close connections
            rsAll.close();
            statement.close();
            conn.close();

        } catch (SQLException se) {
            se.printStackTrace();
            return null;
        }
        return allBooks;
    }       //Returns a list of all book objects in DB

    ArrayList<Customer> fetchAllCustomers() {
        //Initialize list for holding records
        ArrayList<Customer> allCust = new ArrayList<>();

        //Connect to DB
        try(Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
            Statement statement = conn.createStatement()) {

            String selectAllSQL = "SELECT * FROM " + CUST_TABLE;
            ResultSet rsAll = statement.executeQuery(selectAllSQL);

            while (rsAll.next()) {
                int custID = rsAll.getInt(ID_COL);
                String firstName = rsAll.getString(FNAME_COL);
                String lastName = rsAll.getString(LNAME_COL);
                String streetAddress = rsAll.getString(STREET_COL);
                String cityAddress = rsAll.getString(CITY_COL);
                String stateAddress = rsAll.getString(STATE_COL);
                int zipAddress = rsAll.getInt(ZIP_COL);
                String phone = rsAll.getString(PHONE_COL);
                String email = rsAll.getString(EMAIL_COL);
                String creditCard = rsAll.getString(CARD_COL);
                int checkedOut = rsAll.getInt(CHECKOUT_COL);
                Date addDate = rsAll.getDate(CREATED_COL);

                Customer custRecord = new Customer(custID, firstName, lastName, streetAddress, cityAddress,
                        stateAddress, zipAddress, phone, email, creditCard, checkedOut, addDate);
                allCust.add(custRecord);
            }

            //Close connections
            rsAll.close();
            statement.close();
            conn.close();

        } catch (SQLException se) {
            se.printStackTrace();
            return null;
        }
        return allCust;
    }       //Returns a list of all Customer objects in DB
}