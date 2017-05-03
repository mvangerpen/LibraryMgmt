package com.mark;//Controller contains references to all program methods. Contains Main function.

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller {

    static DB db;
    static MainMenu mainMenu;
    static BookSearchGUI bookSearchGUI;
    static CustomerSearchGUI customerSearchGUI;
    static CustomerDetailsGUI customerDetailsGUI;
    static NewCustomerGUI newCustomerGUI;
    static AddBookGUI addBookGUI;
    static BookDetailsGUI newBookDetailsGUI;
    static CreditCardGUI creditCardGUI;
    static CreditCard holdCard;
    static final List<String> states = Arrays.asList("MN","AK","AL","AR","AZ","CA","CO","CT","DC","DE","FL","GA",
            "GU","HI", "IA","ID","IL","IN","KS","KY","LA","MA","MD","ME","MH","MI","MN","MO","MS","MT","NC","ND","NE",
            "NH","NJ","NM","NV","NY", "OH","OK","OR","PA","PR","PW","RI","SC","SD","TN","TX","UT","VA","VI","VT","WA",
            "WI","WV","WY");

    //static BooksRequest requestInitializer;
    static String isbn = "";

    public static void main(String[] args) {

        Controller controller = new Controller();
        controller.startApp();
    }

    private void startApp() {
        db = new DB();
        db.createTables();
        ArrayList<Book> allBookData = db.fetchAllBooks();
        ArrayList<Customer> allCustData = db.fetchAllCustomers();
        mainMenu = new MainMenu(this);

        //bookSearchGui.setCustListData(allCustData);

    }

    //Book management
    ArrayList<Book> getAllBooks() { return db.fetchAllBooks(); }
    void addBook(Book book) { db.addBook(book); }
    void updateBook(Book book) { db.updateBook(book); }
    void deleteBook(Book book) { db.deleteBook(book); }

    //Customer management
    ArrayList<Customer> getAllCust() { return db.fetchAllCustomers(); }
    void addCustomer(Customer customer) { db.addCustomer(customer); }
    void updateCustomer(Customer customer) { db.addCustomer(customer); }
    void deleteCustomer(Customer customer) { db.deleteCustomer(customer); }

    //Card management
    void addCC(CreditCard cc) { db.addCard(cc); }
    void updateCC(CreditCard cc) { db.updateCard(cc); }
    void deleteCC(CreditCard cc) { db.deleteCard(cc); }

    //Temporarily holds credit card information to pass to new customer GUI
    public static void setHoldCard(CreditCard holdCard) { Controller.holdCard = holdCard; }
    public static CreditCard getHoldCard() { return holdCard; }



    //API
    void setISBNForAPI(String isbn){
        this.isbn = isbn;
        }
    protected static String getISBNForAPI() {
        return isbn;
    }


    //New window creation
    void NewBookSearchGUI() {
        bookSearchGUI = new BookSearchGUI(this);
        bookSearchGUI.setBookListData(getAllBooks());
    }   //New Book Search window

    void NewMainMenuGUI() {
        mainMenu = new MainMenu(this);
    } //New Main Menu window

    void NewCustomerSearchGUI() {
        customerSearchGUI = new CustomerSearchGUI(this);
    } //New Customer Search window

    void NewCustomerDetailsGUI(Customer customer) { customerDetailsGUI = new CustomerDetailsGUI(this, customer); } //New Main Menu window

    void AddNewCustomerGUI() { newCustomerGUI = new NewCustomerGUI(this); }

    void AddBookGUI() { addBookGUI = new AddBookGUI(this); }

    void BookDetailsGUI(Book book) { newBookDetailsGUI = new BookDetailsGUI(this, book);}

    void CreditCardGUI(String sendFName, String sendLName, String sendStreet, String sendCity, String sendState, String sendZIP) { creditCardGUI = new CreditCardGUI(this, sendFName, sendLName, sendStreet, sendCity, sendState, sendZIP);}

}
