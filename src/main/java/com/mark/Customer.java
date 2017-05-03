package com.mark;//Class contains the program's customer object class

import java.util.Date;

public class Customer {

    //Establish variables to be held by object
    int customerID;
    String firstName;
    String lastName;
    String streetAddress;
    String cityAddress;
    String stateAddress;
    int zipAddress;
    String phone;
    String email;
    String creditCard;
    int checkedOut;
    Date addDate;

    Customer(int id, String fn, String ln, String st, String ca, String sa, int za, String ph, String em, String cc, int co, Date added) {

        customerID = id;
        firstName = fn;
        lastName = ln;
        streetAddress = st;
        cityAddress = ca;
        stateAddress = sa;
        zipAddress = za;
        phone = ph;
        email = em;
        creditCard = cc;
        checkedOut = co;
        addDate = added;
    }

    @Override
    public String toString() {
        return "Customer ID: " + customerID + "     " +
                "Name: " + lastName + ", " + firstName + "     " +
                "Phone: " + phone;
    }

}