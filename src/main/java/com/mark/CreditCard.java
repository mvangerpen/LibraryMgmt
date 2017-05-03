package com.mark;

public class CreditCard {

    //Establish object variables
    int custID;
    String type;
    String firstName;
    String lastName;
    Long number;
    int expiration;
    int csv;
    String cardAddress;
    String cardCity;
    String cardState;
    int cardZIP;
    String masked;
    String lastFour;

    CreditCard(int cust, String t, String fn, String ln, Long nu, int exp, int c, String ca, String cc, String cs, int cz) {

        custID = cust;
        type = t;
        firstName = fn;
        lastName = ln;
        number = nu;
        expiration = exp;
        csv = c;
        cardAddress = ca;
        cardCity = cc;
        cardState = cs;
        cardZIP = cz;
        masked = hideNumbers(nu);
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
    }

    private String pullLastFour(Long nu) {
        StringBuilder hidden = new StringBuilder();
        String asString = number.toString();
        char[] numArray = asString.toCharArray();
        for (int x = 15; x > 11; x--) {
            hidden.append(numArray[x]);
        }
        return hidden.toString();
    }

    @Override
    public String toString() {
        return lastFour;
    }

}
