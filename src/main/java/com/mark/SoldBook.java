package com.mark;


import org.joda.time.LocalDate;

public class SoldBook {

    int dBookID;
    String dTitle;
    String dAuthor;
    String dISBN;
    int dCustID;
    String dFirstName;
    String dLastName;
    Long dCreditCard;
    Double dSalePrice;
    LocalDate dDateSold;

    //Constructor
    public SoldBook(int dBookID, String dTitle, String dAuthor, String dISBN, int dCustID, String dFirstName,
                    String dLastName, Long dCreditCard, Double dSalePrice, LocalDate dDateSold) {
        this.dBookID = dBookID;
        this.dTitle = dTitle;
        this.dAuthor = dAuthor;
        this.dISBN = dISBN;
        this.dCustID = dCustID;
        this.dFirstName = dFirstName;
        this.dLastName = dLastName;
        this.dCreditCard = dCreditCard;
        this.dSalePrice = dSalePrice;
        this.dDateSold = dDateSold;
    }

    //Getters and Setters
    public int getdBookID() {
        return dBookID;
    }

    public void setdBookID(int dBookID) {
        this.dBookID = dBookID;
    }

    public String getdTitle() {
        return dTitle;
    }

    public void setdTitle(String dTitle) {
        this.dTitle = dTitle;
    }

    public String getdAuthor() {
        return dAuthor;
    }

    public void setdAuthor(String dAuthor) {
        this.dAuthor = dAuthor;
    }

    public String getdISBN() {
        return dISBN;
    }

    public void setdISBN(String dISBN) {
        this.dISBN = dISBN;
    }

    public int getdCustID() {
        return dCustID;
    }

    public void setdCustID(int dCustID) {
        this.dCustID = dCustID;
    }

    public String getdFirstName() {
        return dFirstName;
    }

    public void setdFirstName(String dFirstName) {
        this.dFirstName = dFirstName;
    }

    public String getdLastName() {
        return dLastName;
    }

    public void setdLastName(String dLastName) {
        this.dLastName = dLastName;
    }

    public Long getdCreditCard() {
        return dCreditCard;
    }

    public void setdCreditCard(Long dCreditCard) {
        this.dCreditCard = dCreditCard;
    }

    public Double getdSalePrice() {
        return dSalePrice;
    }

    public void setdSalePrice(Double dSalePrice) {
        this.dSalePrice = dSalePrice;
    }

    public LocalDate getdDateSold() {
        return dDateSold;
    }

    public void setdDateSold(LocalDate dDateSold) {
        this.dDateSold = dDateSold;
    }

    //ToString method
    @Override
    public String toString() {
        return "SoldBook{" +
                "dBookID=" + dBookID +
                ", dTitle='" + dTitle + '\'' +
                ", dAuthor='" + dAuthor + '\'' +
                ", dISBN='" + dISBN + '\'' +
                ", dCustID=" + dCustID +
                ", dFirstName='" + dFirstName + '\'' +
                ", dLastName='" + dLastName + '\'' +
                ", dCreditCard=" + dCreditCard +
                ", dSalePrice=" + dSalePrice +
                ", dDateSold=" + dDateSold +
                '}';
    }
}


