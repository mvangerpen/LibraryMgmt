package com.mark;

import java.util.ArrayList;
import java.util.Date;

public class CreditCard {

    //Establish object variables
    int custID;
    String type;
    String nameOnCard;
    Long number;
    Date expiration;
    int csv;
    String cardAddress;
    String cardCity;
    String cardState;
    int cardZIP;
    String lastFour;

    CreditCard(int cust, String t, String cn, Long nu, Date exp, int c, String ca, String cc, String cs, int cz) {

        custID = cust;
        type = t;
        nameOnCard = cn;
        number = nu;
        setExpiration(exp);
        csv = c;
        cardAddress = ca;
        cardCity = cc;
        cardState = cs;
        cardZIP = cz;
        lastFour = pullLastFour(nu);

    }

    private String hideNumbers(Long number) {

        //replace first 12 numbers with * symbols
        StringBuilder hidden = new StringBuilder();
        String asString = number.toString();
        char[] numArray = asString.toCharArray();

        for (int x = 0; x < 16; x++) {
            if (x <= 11) {
                hidden.append("*");
            }
            else if (x > 11 && x < 16) {
                hidden.append(numArray[x]);
            }
        }

        return hidden.toString();
    }   //Replaces first 12 digits of card number with * symbol

    private String pullLastFour(Long nu) {
        StringBuilder hidden = new StringBuilder();
        String asString = number.toString();
        char[] numArray = asString.toCharArray();
        for (int x = 15; x > 11; x--) {
            hidden.append(numArray[x]);
        }
        return hidden.toString();
    }   //Pulls only last four digits of card number


    //Getters and Setters
    public int getCustID() {
        return custID;
    }

    public void setCustID(int custID) {
        this.custID = custID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Date getExpiration() { return expiration; }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public int getCsv() {
        return csv;
    }

    public void setCsv(int csv) {
        this.csv = csv;
    }

    public String getCardAddress() {
        return cardAddress;
    }

    public void setCardAddress(String cardAddress) {
        this.cardAddress = cardAddress;
    }

    public String getCardCity() {
        return cardCity;
    }

    public void setCardCity(String cardCity) {
        this.cardCity = cardCity;
    }

    public String getCardState() {
        return cardState;
    }

    public void setCardState(String cardState) {
        this.cardState = cardState;
    }

    public int getCardZIP() {
        return cardZIP;
    }

    public void setCardZIP(int cardZIP) {
        this.cardZIP = cardZIP;
    }

    public String getLastFour() {
        return lastFour;
    }

    public void setLastFour(String lastFour) {
        this.lastFour = lastFour;
    }


    @Override
    public String toString() {
        return lastFour;
    }

}
