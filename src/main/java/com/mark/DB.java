package com.mark;//Contains the database input and functions

import org.joda.time.Days;
import org.joda.time.LocalDate;

import javax.swing.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;


class DB {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/library";
    private static final String USER = "root";
    private static final String PASSWORD = System.getenv("MYSQL_PW");      //Establish variables for connecting to database

    private static final String BOOK_TABLE = "MyBooks";
    private static final String MYBID_COL = "MyBookID";
    private static final String ISBN_COL = "ISBN";
    private static final String TITLE_COL = "Title";
    private static final String AUTHOR_COL = "Author";
    private static final String CATEGORIES_COL = "Categories";
    private static final String PAGES_COL = "Pages";
    private static final String PUBLISHER_COL = "Publisher";
    private static final String DATE_PUBLISHED_COL = "PubDate";
    private static final String STATUS_COL = "Status";
    private static final String CUST_ID_COL = "CustomerID";
    private static final String CHECKOUT_DATE_COL = "CheckedOut";
    private static final String DUE_DATE_COL = "DueDate";
    private static final String PRICE_COL = "Price";
    private static final String CHARGED_COL = "AmtCharged";     //Establish variables for books table

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
    private static final String TOTAL_CHARGED_COL = "TotalChargeAmt";
    private static final String CREATED_COL = "DateAdded";      //Establish variables for Customers table

    private static final String CC_TABLE = "CreditCards";  //CustID pulls from customer info.
    private static final String CC_NAME_COL = "NameOnCard";
    private static final String CARD_TYPE_COL = "CardType";
    private static final String NUMBER_COL = "CardNumber";
    private static final String EXP_COL = "ExpDate";
    private static final String CSV_COL = "CSV";
    private static final String CARD_ADDRESS_COL = "Street";
    private static final String CARD_CITY_COL = "City";
    private static final String CARD_STATE_COL = "State";
    private static final String CARD_ZIP_COL = "ZIP";       //Establish variables for Credit Card table

    private static final String D_BOOKS_TABLE = "SoldBooks";
    private static final String D_ID_COL = "MyBookID";
    private static final String D_TITLE_COL = "Title";
    private static final String D_AUTHOR_COL = "Author";
    private static final String D_ISBN_COL = "ISBN";
    private static final String D_CUSTID_COL = "SoldToCustomer";
    private static final String D_FNAME_COL = "CustFirstName";
    private static final String D_LNAME_COL = "CustLastName";
    private static final String D_CARD_COL = "CreditCardCharged";
    private static final String D_PRICE_COL = "SalePrice";
    private static final String D_SOLD_COL = "DateSold";        //Establish variables for records table

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


            String createBookTableSQLTemplate = "CREATE TABLE IF NOT EXISTS %s(%s INT, %s VARCHAR(20), %s VARCHAR(50), %s VARCHAR(50), %s VARCHAR(25)," +
                    "%s INT, %s VARCHAR(35), %s VARCHAR(15), %s VARCHAR(3), %s INT, %s DATE, %s DATE, " +
                    "%s DOUBLE, %s DOUBLE, PRIMARY KEY(%s))";
            String createBookTable = String.format(createBookTableSQLTemplate, BOOK_TABLE, MYBID_COL, ISBN_COL, TITLE_COL, AUTHOR_COL,
                                                CATEGORIES_COL, PAGES_COL, PUBLISHER_COL, DATE_PUBLISHED_COL, STATUS_COL, CUST_ID_COL,
                                                CHECKOUT_DATE_COL, DUE_DATE_COL, PRICE_COL, CHARGED_COL, MYBID_COL);

            statement.executeUpdate(createBookTable);

            //Notify if successful
            System.out.println("Created MyBooks table");

            //Create customers table
            String createCustTableSQLTemplate = "CREATE TABLE IF NOT EXISTS %s (%s INT, %s VARCHAR(50), %s VARCHAR(25), %s VARCHAR(100), " +
                    "%s VARCHAR(50), %s VARCHAR(2), %s INT, %s VARCHAR(20), %s VARCHAR(50), %s VARCHAR(4), %s DOUBLE, %s DATE, " +
                    "PRIMARY KEY(%s))";
            String createCustTable = String.format(createCustTableSQLTemplate, CUST_TABLE, ID_COL, FNAME_COL, LNAME_COL, STREET_COL,
                                    CITY_COL, STATE_COL, ZIP_COL, PHONE_COL, EMAIL_COL, CARD_COL, TOTAL_CHARGED_COL, CREATED_COL, ID_COL);
            statement.executeUpdate(createCustTable);

            //Notify if successful
            System.out.println("Created customers table");

            //Create credit card table
            String createCCTableSQLTemplate = "CREATE TABLE IF NOT EXISTS %s (%s INT, %s VARCHAR(50), " +
                    "%s VARCHAR(4), %s LONG, %s DATE, %s INT, %s VARCHAR(50), %s VARCHAR(50), %s VARCHAR(2), %s INT, " +
                    "PRIMARY KEY(%s))";
            String createCCTable = String.format(createCCTableSQLTemplate, CC_TABLE, CUST_ID_COL, CC_NAME_COL,
                    CARD_TYPE_COL, NUMBER_COL, EXP_COL, CSV_COL, CARD_ADDRESS_COL, CARD_CITY_COL, CARD_STATE_COL,
                    CARD_ZIP_COL, CUST_ID_COL);
            statement.executeUpdate(createCCTable);

            //Notify if successful
            System.out.println("Created credit card table");

            //Create deleted books table
            String createDeletedTableSQLTemplate = "CREATE TABLE IF NOT EXISTS %s (%s INT, %s VARCHAR(50), %s VARCHAR (75)," +
                    "%s VARCHAR(15), %s INT, %s VARCHAR(25), %s VARCHAR(25), %s LONG, %s DOUBLE, %s DATE, PRIMARY KEY(%s))";

            String createDeletedTable = String.format(createDeletedTableSQLTemplate, D_BOOKS_TABLE, D_ID_COL, D_TITLE_COL,
                    D_AUTHOR_COL, D_ISBN_COL, D_CUSTID_COL, D_FNAME_COL, D_LNAME_COL, D_CARD_COL, D_PRICE_COL, D_SOLD_COL,
                    D_ID_COL);

            statement.executeUpdate(createDeletedTable);

            //Notify if successful
            System.out.println("Created deleted reports table");

            //Close connections
            statement.close();
            conn.close();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }  //Create customer, book, credit card, and deleted books tables in database


    //LIBRARY MANAGEMENT

    void addBook(Book book) {

        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD)) {

            //Establish prepared statement, set values from Book class
            String addBookSQL = "INSERT INTO " + BOOK_TABLE + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement addBookPS = conn.prepareStatement(addBookSQL);
            addBookPS.setInt(1, book.getMybID());
            addBookPS.setString(2, book.getIsbn());
            addBookPS.setString(3, book.getTitle());
            addBookPS.setString(4, book.getAuthor());
            addBookPS.setString(5, book.getCategories());
            addBookPS.setInt(6, book.getPages());
            addBookPS.setString(7, book.getPublisher());
            addBookPS.setString(8, book.getPubDate());
            addBookPS.setString(9, book.getStatus());
            addBookPS.setInt(10, book.getCustID());

            //Handle missing dates
            if (book.getCheckedOut() != null) {
                addBookPS.setDate(11, new java.sql.Date(book.getCheckedOut().toDate().getTime()));  //Cast java date to SQL date format
            } else {
                addBookPS.setDate(11, null);
            }

            if (book.getDueDate() != null) {
                addBookPS.setDate(12, new java.sql.Date(book.getDueDate().toDate().getTime()));
            } else {
                addBookPS.setDate(12, null);
            }

            addBookPS.setDouble(13, book.getPrice());
            addBookPS.setDouble(14, book.getCharged());

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
            String updateBookTemplate = "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, " +
                    "%s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?";
            String updateBook = String.format(updateBookTemplate, BOOK_TABLE, MYBID_COL, ISBN_COL, TITLE_COL, AUTHOR_COL, CATEGORIES_COL,
                    PAGES_COL, PUBLISHER_COL, DATE_PUBLISHED_COL, STATUS_COL, CUST_ID_COL,
                    CHECKOUT_DATE_COL, DUE_DATE_COL, PRICE_COL, CHARGED_COL, MYBID_COL);
            PreparedStatement updateBookPS = conn.prepareStatement(updateBook);

            updateBookPS.setInt(1,book.getMybID());
            updateBookPS.setString(2, book.getIsbn());
            updateBookPS.setString(3, book.getTitle());
            updateBookPS.setString(4, book.getAuthor());
            updateBookPS.setString(5, book.getCategories());
            updateBookPS.setInt(6, book.getPages());
            updateBookPS.setString(7, book.getPublisher());
            updateBookPS.setString(8, book.getPubDate());
            updateBookPS.setString(9, book.getStatus());
            updateBookPS.setInt(10, book.getCustID());

            //Handle missing dates
            if (book.getCheckedOut() != null) {
                updateBookPS.setDate(11, new java.sql.Date(book.getCheckedOut().toDate().getTime()));  //Cast java date to SQL date format
            } else {
                updateBookPS.setDate(11, null);
            }

            if (book.getDueDate() != null) {
                updateBookPS.setDate(12, new java.sql.Date(book.getDueDate().toDate().getTime()));
            } else {
                updateBookPS.setDate(12, null);
            }

            updateBookPS.setDouble(13, book.getPrice());
            updateBookPS.setDouble(14, book.getCharged());
            updateBookPS.setInt(15, book.getMybID());

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
            String deleteSQL = String.format(deleteSQLTemplate, BOOK_TABLE, MYBID_COL);
            PreparedStatement deletePreparedStatement = conn.prepareStatement(deleteSQL);
            deletePreparedStatement.setInt(1, book.getMybID());

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

            addCustPS.setInt(1, cust.getCustomerID());
            addCustPS.setString(2, cust.getFirstName());
            addCustPS.setString(3, cust.getLastName());
            addCustPS.setString(4, cust.getStreetAddress());
            addCustPS.setString(5, cust.getCityAddress());
            addCustPS.setString(6, cust.getStateAddress());
            addCustPS.setInt(7, cust.getZipAddress());
            addCustPS.setString(8, cust.getPhone());
            addCustPS.setString(9, cust.getEmail());
            addCustPS.setString(10, cust.getCreditCard());
            addCustPS.setDouble(11, cust.getTotalCharge());
            addCustPS.setDate(12, new java.sql.Date(cust.getAddDate().getTime())); //Cast java date to SQL date format

            //Execute prepared statement
            addCustPS.execute();

            //Close connections
            addCustPS.close();
            conn.close();

            //Notify if successful
            System.out.println("Added record for " + cust.getFirstName() + " " + cust.getLastName());

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
            String updateCust = String.format(updateCustTemplate, CUST_TABLE, ID_COL, FNAME_COL, LNAME_COL, STREET_COL,
                    CITY_COL, STATE_COL, ZIP_COL, PHONE_COL, EMAIL_COL, CARD_COL, TOTAL_CHARGED_COL, CREATED_COL, ID_COL);
            PreparedStatement updateCustPS = conn.prepareStatement(updateCust);

            updateCustPS.setInt(1, cust.getCustomerID());
            updateCustPS.setString(2, cust.getFirstName());
            updateCustPS.setString(3, cust.getLastName());
            updateCustPS.setString(4, cust.getStreetAddress());
            updateCustPS.setString(5, cust.getCityAddress());
            updateCustPS.setString(6, cust.getStateAddress());
            updateCustPS.setInt(7, cust.getZipAddress());
            updateCustPS.setString(8, cust.getPhone());
            updateCustPS.setString(9, cust.getEmail());
            updateCustPS.setString(10, cust.getCreditCard());
            updateCustPS.setDouble(11, cust.getTotalCharge());
            updateCustPS.setDate(12, new java.sql.Date(cust.getAddDate().getTime())); //Cast java date to SQL date format
            updateCustPS.setInt(13, cust.getCustomerID());

            //Update record
            updateCustPS.executeUpdate();

            //close connections
            updateCustPS.close();
            statement.close();
            conn.close();

            //Notify if successful
            System.out.println("Customer information updated for Customer " + cust.getCustomerID() + ": " +
                    cust.getFirstName() + " " + cust.getLastName());

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }   //Function for updating a customer's information

    void deleteCustomer(int customerID) {

        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD)) {

            //Prepare delete statement
            String deleteSQLTemplate = "DELETE FROM %s WHERE %s = ?";
            String deleteSQL = String.format(deleteSQLTemplate, CUST_TABLE, CUST_ID_COL);
            PreparedStatement deletePreparedStatement = conn.prepareStatement(deleteSQL);
            deletePreparedStatement.setInt(1, customerID);

            //Execute delete
            deletePreparedStatement.execute();

            //Delete associated payment
            deleteCard(customerID);

            //close connections
            deletePreparedStatement.close();
            conn.close();

            //Notify if successful
            System.out.println("Record deleted for Customer " + String.valueOf(customerID));

        } catch (SQLException se) {
            se.printStackTrace();
        }

    }       //Function for deleting a customer. Automatically deletes associated credit card.


    //CREDIT CARD MANAGEMENT

    void addCard(CreditCard cc) {

        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
             Statement statement = conn.createStatement()) {

            //Establish prepared statement, set values from Customer class
            String addCCSQL = "INSERT INTO " + CC_TABLE + " VALUES(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement addCCPS = conn.prepareStatement(addCCSQL);

            addCCPS.setInt(1, cc.custID);
            addCCPS.setString(2, cc.nameOnCard);
            addCCPS.setString(3, cc.type);
            addCCPS.setLong(4, cc.number);
            addCCPS.setDate(5, new Date(cc.getExpiration().getTime()));
            addCCPS.setInt(6, cc.csv);
            addCCPS.setString(7, cc.cardAddress);
            addCCPS.setString(8, cc.cardCity);
            addCCPS.setString(9, cc.cardState);
            addCCPS.setInt(10, cc.cardZIP);

            //Execute prepared statement
            addCCPS.execute();

            //Close connections
            addCCPS.close();
            conn.close();

            //Notify if successful
            System.out.println("Added payment for Customer " + cc.custID + ": " + cc.nameOnCard);

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }       //Function for adding a credit card

    void updateCard(CreditCard cc) {

        //Connect to DB
        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
             Statement statement = conn.createStatement()) {

            //Prep prepared statement
            String updateCCTemplate = "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, " +
                    "%s = ?, %s = ?, %s = ? WHERE %s = ?";
            String updateCC = String.format(updateCCTemplate, CC_TABLE, ID_COL, CC_NAME_COL, CARD_TYPE_COL,
                    NUMBER_COL, EXP_COL, CSV_COL, CARD_ADDRESS_COL, CARD_CITY_COL, CARD_STATE_COL, CARD_ZIP_COL, ID_COL);
            PreparedStatement updateCCPS = conn.prepareStatement(updateCC);

            updateCCPS.setInt(1, cc.getCustID());
            updateCCPS.setString(2, cc.getNameOnCard());
            updateCCPS.setString(3, cc.getType());
            updateCCPS.setLong(4, cc.getNumber());
            updateCCPS.setDate(5, new Date(cc.getExpiration().getTime()));
            updateCCPS.setInt(6, cc.getCsv());
            updateCCPS.setString(7, cc.getCardAddress());
            updateCCPS.setString(8, cc.getCardCity());
            updateCCPS.setString(9, cc.getCardState());
            updateCCPS.setInt(10, cc.getCardZIP());
            updateCCPS.setInt(11, cc.getCustID());

            //Update record
            updateCCPS.executeUpdate();


            //Update Credit Card field in corresponding customer
            String updateCustomerTemplate = "UPDATE %s SET %s = ? WHERE %s = ?";
            String updateCustomer = String.format(updateCustomerTemplate, CUST_TABLE, CARD_COL, ID_COL);
            PreparedStatement updateCustomerPS = conn.prepareStatement(updateCustomer);

            updateCustomerPS.setString(1, cc.getLastFour());
            updateCustomerPS.setInt(2, cc.getCustID());

            //Update
            updateCustomerPS.executeUpdate();

            //close connections
            updateCCPS.close();
            updateCustomerPS.close();
            statement.close();
            conn.close();

            //Notify if successful
            System.out.println("Credit card information updated for Customer " + cc.custID + ": " + cc.nameOnCard);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }   //Function for updating a customer's payment information

    void deleteCard(int customerID) {

        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD)) {
            //Prepare delete statement
            String deleteSQLTemplate = "DELETE FROM %s WHERE %s = ?";
            String deleteSQL = String.format(deleteSQLTemplate, CC_TABLE, ID_COL);
            PreparedStatement deletePreparedStatement = conn.prepareStatement(deleteSQL);
            deletePreparedStatement.setInt(1, customerID);

            //Execute delete
            deletePreparedStatement.execute();

            //close connections
            deletePreparedStatement.close();
            conn.close();


        } catch (SQLException se) {
            se.printStackTrace();
        }

        //Notify if successful
        System.out.println("Card deleted for Customer " + customerID);

    }       //Function for deleting a credit card

    void chargeCard(int bookID, int customerID) {

        Book bookToCharge = getBook(bookID);
        Customer custToCharge = getCustomer(customerID);

        //Add book charge to customer charge
        custToCharge.setTotalCharge(custToCharge.getTotalCharge() + bookToCharge.getCharged());

        //Reset book charged amount
        bookToCharge.setCharged(0.00);

        //Send updated amounts to database
        updateCustomer(custToCharge);
        updateBook(bookToCharge);

    }       //Function for processing overdue fees


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
                int mybID = rsAll.getInt(MYBID_COL);
                String isbn = rsAll.getString(ISBN_COL);
                String title = rsAll.getString(TITLE_COL);
                String author = rsAll.getString(AUTHOR_COL);
                String categories = rsAll.getString(CATEGORIES_COL);
                int pages = rsAll.getInt(PAGES_COL);
                String publisher = rsAll.getString(PUBLISHER_COL);
                String pubDate = rsAll.getString(DATE_PUBLISHED_COL);
                String status = rsAll.getString(STATUS_COL);
                int custID = rsAll.getInt(CUST_ID_COL);
                LocalDate checkedOut = new LocalDate(rsAll.getDate(CHECKOUT_DATE_COL));
                LocalDate dueDate = new LocalDate(rsAll.getDate(DUE_DATE_COL));
                Double price = rsAll.getDouble(PRICE_COL);
                Double charged = rsAll.getDouble(CHARGED_COL);

                Book bookRecord = new Book(mybID, isbn, title, author, categories, pages, publisher, pubDate, status, custID,
                        checkedOut, dueDate, price, charged);
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

    public ArrayList<Book> fetchCustBooks(int custID) {
        //Initialize list for holding records
        ArrayList<Book> books = new ArrayList<>();

        //Connect to DB
        try(Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
            Statement statement = conn.createStatement()) {

            String selectSQLTemplate = "SELECT * FROM %s WHERE %s = ?";
            String selectSQL = String.format(selectSQLTemplate, BOOK_TABLE, ID_COL);
            PreparedStatement selectCustPS = conn.prepareStatement(selectSQL);
            selectCustPS.setInt(1, custID);
            ResultSet result = selectCustPS.executeQuery();

            while (result.next()) {
                int mybID = result.getInt(MYBID_COL);
                String isbn = result.getString(ISBN_COL);
                String title = result.getString(TITLE_COL);
                String author = result.getString(AUTHOR_COL);
                String categories = result.getString(CATEGORIES_COL);
                int pages = result.getInt(PAGES_COL);
                String publisher = result.getString(PUBLISHER_COL);
                String pubDate = result.getString(DATE_PUBLISHED_COL);
                String status = result.getString(STATUS_COL);
                int cID = result.getInt(CUST_ID_COL);
                LocalDate checkedOut = new LocalDate(result.getDate(CHECKOUT_DATE_COL));
                LocalDate dueDate = new LocalDate(result.getDate(DUE_DATE_COL));
                Double price = result.getDouble(PRICE_COL);
                Double charged = result.getDouble(CHARGED_COL);

                Book bookRecord = new Book(mybID, isbn, title, author, categories, pages, publisher, pubDate, status, cID,
                        checkedOut, dueDate, price, charged);
                books.add(bookRecord);
            }

            //Close connections
            result.close();
            statement.close();
            conn.close();

        } catch (SQLException se) {
            se.printStackTrace();
            return null;
        }
        return books;


    } //Returns a list of books associated with a single customer

    void trackOverdue() {
        Double OverdueRate = 0.25;

        ArrayList<Book> allBooks = fetchAllBooks();
        LocalDate today =  new LocalDate();

        StringBuilder builder = new StringBuilder();
        StringBuilder deleted = new StringBuilder();

        for (Book b : allBooks) {
            LocalDate dueDate = new LocalDate(b.getDueDate());
            if (today.compareTo(dueDate) > 0) {
                int daysBtwn = Days.daysBetween(today, dueDate).getDays();
                if (daysBtwn >= -14 && daysBtwn < 0) {
                    b.setCharged(daysBtwn * OverdueRate);
                    updateBook(b);
                    builder.append(b.getMybID()).append(" ").append(b.getTitle()).append("\nCust: ").append(b.getCustID()).append("\n\n");
                } else if (daysBtwn < -14) {
                    deleted.append(b.getMybID()).append(" ").append(b.getTitle()).append("\nTo Cust: ").append(b.getCustID()).append("\n\n");
                    deleteBook(b);
                }

            }
        }

        if (!builder.toString().equals("")) {
            JOptionPane.showMessageDialog(Controller.mainMenu,"Updated overdue charges:\n" + builder.toString());
        }
        if (!deleted.toString().equals("")) {
            JOptionPane.showMessageDialog(Controller.mainMenu, "Books sold:\n" + deleted.toString());
        }
        if (builder.toString().equals("") && deleted.toString().equals("")) {
            JOptionPane.showMessageDialog(Controller.mainMenu, "Checkout inspection complete. No overdue charges processed.");
        }

    }       // Updates all overdue fees. Updates only the books that are overdue.

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
                Double totalCharged = rsAll.getDouble(TOTAL_CHARGED_COL);
                Date addDate = rsAll.getDate(CREATED_COL);

                Customer custRecord = new Customer(custID, firstName, lastName, streetAddress, cityAddress,
                        stateAddress, zipAddress, phone, email, creditCard, totalCharged, addDate);
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

    CreditCard getCreditCard(int custID) {

        //initialize card object
        CreditCard card = null;

        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
             Statement statement = conn.createStatement()) {

            //Prepare retrieve statement
            String getCardSQLTemplate = "SELECT * FROM %s WHERE %s = ?";
            String getCardSQL = String.format(getCardSQLTemplate, CC_TABLE, ID_COL);
            PreparedStatement getCardPreparedStatement = conn.prepareStatement(getCardSQL);
            getCardPreparedStatement.setInt(1, custID);

            //Execute retrieve
            ResultSet response = getCardPreparedStatement.executeQuery();

            while (response.next()) {
                int id = response.getInt(CUST_ID_COL);
                String type = response.getString(CARD_TYPE_COL);
                String n = response.getString(CC_NAME_COL);
                Long nu = response.getLong(NUMBER_COL);
                Date exp = response.getDate(EXP_COL);
                int csv = response.getInt(CSV_COL);
                String ca = response.getString(STREET_COL);
                String ci = response.getString(CITY_COL);
                String cs = response.getString(STATE_COL);
                int zip = response.getInt(ZIP_COL);

                card = new CreditCard(id, type, n, nu, exp, csv, ca, ci, cs, zip);
            }

            //close connections
            getCardPreparedStatement.close();
            conn.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }

        return card;

    }       //Function for retrieving a credit card

    Customer getCustomer(int customerID) {

        Customer fetched = null;

        //Connect to DB
        try(Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
            Statement statement = conn.createStatement()) {

            String selectCustomerTemplate = "SELECT * FROM %s WHERE %s = ?";
            String selectCust = String.format(selectCustomerTemplate, CUST_TABLE, ID_COL);
            PreparedStatement selectCustomerPS = conn.prepareStatement(selectCust);
            selectCustomerPS.setInt(1, customerID);
            ResultSet result = selectCustomerPS.executeQuery();

            while (result.next()) {
                int custID = result.getInt(ID_COL);
                String firstName = result.getString(FNAME_COL);
                String lastName = result.getString(LNAME_COL);
                String streetAddress = result.getString(STREET_COL);
                String cityAddress = result.getString(CITY_COL);
                String stateAddress = result.getString(STATE_COL);
                int zipAddress = result.getInt(ZIP_COL);
                String phone = result.getString(PHONE_COL);
                String email = result.getString(EMAIL_COL);
                String creditCard = result.getString(CARD_COL);
                Double totalCharged = result.getDouble(TOTAL_CHARGED_COL);
                Date addDate = result.getDate(CREATED_COL);

                fetched = new Customer(custID, firstName, lastName, streetAddress, cityAddress,
                        stateAddress, zipAddress, phone, email, creditCard, totalCharged, addDate);
            }

            //Close connections
            result.close();
            statement.close();
            conn.close();

        } catch (SQLException se) {
            se.printStackTrace();
            return null;
        }
        return fetched;
    }       //Function for retrieving a single customer record

    Book getBook(int bookID) {

        Book fetched = null;

        //Connect to DB
        try(Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
            Statement statement = conn.createStatement()) {

            String selectCustomerTemplate = "SELECT * FROM %s WHERE %s = ?";
            String selectCust = String.format(selectCustomerTemplate, BOOK_TABLE, MYBID_COL);
            PreparedStatement selectCustomerPS = conn.prepareStatement(selectCust);
            selectCustomerPS.setInt(1, bookID);
            ResultSet result = selectCustomerPS.executeQuery();

            while (result.next()) {
                int nid = result.getInt(MYBID_COL);
                String nIsbn = result.getString(ISBN_COL);
                String nTitle = result.getString(TITLE_COL);
                String nAuthor = result.getString(AUTHOR_COL);
                String nCategories = result.getString(CATEGORIES_COL);
                int nPages = result.getInt(PAGES_COL);
                String nPub = result.getString(PUBLISHER_COL);
                String nPubDate = result.getString(DATE_PUBLISHED_COL);
                String nStatus = result.getString(STATUS_COL);
                int nCustID = result.getInt(CUST_ID_COL);
                LocalDate nCheckedOut = new LocalDate(result.getString(CHECKOUT_DATE_COL));
                LocalDate nDueDate = new LocalDate(result.getString(DUE_DATE_COL));
                Double nPrice = result.getDouble(PRICE_COL);
                Double nCharged = result.getDouble(CHARGED_COL);

                fetched = new Book(nid, nIsbn, nTitle, nAuthor, nCategories, nPages, nPub, nPubDate, nStatus, nCustID, nCheckedOut,
                        nDueDate, nPrice, nCharged);
            }

            //Close connections
            result.close();
            statement.close();
            conn.close();

        } catch (SQLException se) {
            se.printStackTrace();
            return null;
        }
        return fetched;
    }       //Function for retrieving a single book record


    //REPORTS

    ArrayList<SoldBook> fetchAllSoldBooks() {
        //Initialize list for holding records
        ArrayList<SoldBook> allSold = new ArrayList<>();

        //Connect to DB
        try(Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
            Statement statement = conn.createStatement()) {

            String selectAllSQL = "SELECT * FROM " + D_BOOKS_TABLE;
            ResultSet rsAll = statement.executeQuery(selectAllSQL);

            while (rsAll.next()) {
                int dBookID = rsAll.getInt(D_ID_COL);
                String dTitle = rsAll.getString(D_TITLE_COL);
                String dAuthor = rsAll.getString(D_AUTHOR_COL);
                String dISBN = rsAll.getString(D_ISBN_COL);
                int dCustID = rsAll.getInt(D_CUSTID_COL);
                String dFirstName = rsAll.getString(D_FNAME_COL);
                String dLastName = rsAll.getString(D_LNAME_COL);
                Long dCreditCard = rsAll.getLong(D_CARD_COL);
                Double dSalePrice = rsAll.getDouble(D_PRICE_COL);
                LocalDate dDateSold =   new LocalDate(rsAll.getDate(D_SOLD_COL));


                SoldBook soldBook = new SoldBook(dBookID, dTitle, dAuthor, dISBN, dCustID, dFirstName, dLastName,
                        dCreditCard, dSalePrice, dDateSold);
                allSold.add(soldBook);
            }

            //Close connections
            rsAll.close();
            statement.close();
            conn.close();

        } catch (SQLException se) {
            se.printStackTrace();
            return null;
        }
        return allSold;
    }   //Returns a list of all books in Deleted Records table


}