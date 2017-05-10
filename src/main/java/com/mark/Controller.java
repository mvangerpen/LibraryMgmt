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
    static CheckOutGUI checkOutGUI;
    static ReportsGUI reportsGUI;
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
        db.trackOverdue();
        mainMenu = new MainMenu(this);

    }

    //Book management
    ArrayList<Book> getAllBooks() { return db.fetchAllBooks(); }
    ArrayList<Book> getCustBooks(int custID) { return db.fetchCustBooks(custID); }
    void addBook(Book book) { db.addBook(book); }
    void updateBook(Book book) { db.updateBook(book); }
    void deleteBook(Book book) { db.deleteBook(book); }
    Book getBook(int bookID) { return db.getBook(bookID); }


    //Customer management
    ArrayList<Customer> getAllCust() { return db.fetchAllCustomers(); }
    void addCustomer(Customer customer) { db.addCustomer(customer); }
    void updateCustomer(Customer customer) { db.updateCustomer(customer); }
    void deleteCustomer(int customerID) { db.deleteCustomer(customerID); }
    Customer getCustomer(int customerID) { return db.getCustomer(customerID); }

    //Card management
    void addCC(CreditCard cc) { db.addCard(cc); }
    void updateCC(CreditCard cc) { db.updateCard(cc); }
    void deleteCC(int customerID) { db.deleteCard(customerID); }
    CreditCard getCard(int custID) { return db.getCreditCard(custID);  }
    void chargeCard(int bookID, int custID) { db.chargeCard(bookID, custID); }

    //Reports management
    ArrayList<SoldBook> fetchAllSoldBooks() { return db.fetchAllSoldBooks(); }


    public static boolean isValidCreditCard(String cc) {
        int total = 0;

        //Create array of integers based on cc
        int[] numArray = new int[cc.length()];

        //Loop through cc
        for (int x = 0; x < cc.length(); x++) {
            //Double even elements & 0 element
            if (x == 0 || x % 2 == 0) {
                numArray[x] = Character.getNumericValue(cc.charAt(x)) * 2;
            }
            else
                numArray[x] = Character.getNumericValue(cc.charAt(x));

            //Value of integers 10 through 19 added together is equivalent to int - 9
            if (numArray[x] >= 10){
                numArray[x] -= 9;
            }

            //Accumulate total
            total += numArray[x];
        }

        if (total % 10 == 0){
            return true;}
        else
            return false;
    } //Validates credit card number


    //New window creation
    void NewBookSearchGUI() {
        bookSearchGUI = new BookSearchGUI(this);
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

    void CreditCardGUI(CreditCard cc) { creditCardGUI = new CreditCardGUI(this, cc);}

    void CheckOutGUI(Book book) { checkOutGUI = new CheckOutGUI(this, book); }

    void ReportsGUI() { reportsGUI = new ReportsGUI(this); }

}
