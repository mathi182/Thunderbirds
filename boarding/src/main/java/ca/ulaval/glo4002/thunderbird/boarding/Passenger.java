package ca.ulaval.glo4002.thunderbird.boarding;

import java.security.SecureRandom;
import java.math.BigInteger;

public class Passenger {

    public String firstName = "";
    public String lastName = "";
    public int age;
    public String passportNumber = "";
    public String seatClass;
    public String hash = "";

    public Passenger(String firstName, String lastName,  int age, String passportNumber, String seatClass) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.passportNumber = passportNumber;
        this.seatClass = seatClass;
        this.generateHash();
    }

    public Passenger(int age, String seatClass) {
        this.age = age;
        this.seatClass = seatClass;
        this.generateHash();

    }

    private void generateHash(){
        SecureRandom random = new SecureRandom();
        this.hash = new BigInteger(130, random).toString(32);
    }
}
