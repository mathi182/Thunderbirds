package ca.ulaval.glo4002.thunderbird.boarding;

import java.security.SecureRandom;
import java.math.BigInteger;
import java.util.HashSet;


public class Passenger {

    public String firstName = "";
    public String lastName = "";
    public int age;
    public String passportNumber = "";
    public String seatClass;
    private String hash = "";
    private HashSet<String> hashRepository = new HashSet<>();


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
        String possibleHash;
        int nbTries = 0;
        do {
            SecureRandom random = new SecureRandom();
            possibleHash = new BigInteger(130, random).toString(32);
            if (++nbTries > 100)
                throw new RuntimeException("Could not find an available hash in 100 tries.");
        } while (hashRepository.contains(possibleHash));
        hashRepository.add(possibleHash);
        this.hash = possibleHash;
    }

    public String getHash() {
        return hash;
    }
}
