package com.mark;//Class contains the program's customer object class

import java.util.Date;

class Customer {

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
    private int checkedOut;
    private Double totalCharge;
    Date addDate;

    //Constructor


    public Customer(int customerID, String firstName, String lastName, String streetAddress, String cityAddress,
                    String stateAddress, int zipAddress, String phone, String email, String creditCard, int checkedOut,
                    Double totalCharge, Date addDate) {

        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetAddress = streetAddress;
        this.cityAddress = cityAddress;
        this.stateAddress = stateAddress;
        this.zipAddress = zipAddress;
        this.phone = phone;
        this.email = email;
        this.creditCard = creditCard;
        this.checkedOut = checkedOut;
        this.totalCharge = totalCharge;
        this.addDate = addDate;
    }

    //Getters and Setters
    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCityAddress() {
        return cityAddress;
    }

    public void setCityAddress(String cityAddress) {
        this.cityAddress = cityAddress;
    }

    public String getStateAddress() {
        return stateAddress;
    }

    public void setStateAddress(String stateAddress) {
        this.stateAddress = stateAddress;
    }

    public int getZipAddress() {
        return zipAddress;
    }

    public void setZipAddress(int zipAddress) {
        this.zipAddress = zipAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public int getCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(int checkedOut) {
        this.checkedOut = checkedOut;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Double getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(Double totalCharge) {
        this.totalCharge = totalCharge;
    }

    //ToString method
    @Override
    public String toString() {
        return "Customer ID: " + customerID + "     " +
                "Name: " + lastName + ", " + firstName + "     " +
                "Phone: " + phone;
    }

}