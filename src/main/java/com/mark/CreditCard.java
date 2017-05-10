package com.mark;

import java.util.Date;

class CreditCard {

    //Establish object variables
    int custID;
    String type;
    String nameOnCard;
    Long number;
    private Date expiration;
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

    private String pullLastFour(Long nu) {
        StringBuilder hidden = new StringBuilder();
        String asString = nu.toString();
        char[] numArray = asString.toCharArray();
        for (int x = numArray.length - 1; x > (numArray.length - 4); x--) {
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

    private void setExpiration(Date expiration) {
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
