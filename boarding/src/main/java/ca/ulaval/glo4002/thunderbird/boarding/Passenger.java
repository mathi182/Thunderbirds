package ca.ulaval.glo4002.thunderbird.boarding;

import ca.ulaval.glo4002.thunderbird.boarding.domain.Identity;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Passenger extends Identity {

    public String firstName = "";
    public String lastName = "";
    public int age;
    public String passportNumber = "";
    public String seatClass;
    private String hash = "";

    public Passenger(String firstName, String lastName, int age, String passportNumber, String seatClass) {
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

    public String getHash() {
        return hash;
    }
}
