package com.gosecuri.agent;

import com.gosecuri.security.MD5Encryptor;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Agent {

    private final String lastName;
    private final String firstName;
    private final String mission;
    private final String encryptedPassword;
    private final List<String> equipment;

    public Agent(final List<String> lines) throws NoSuchAlgorithmException {
        if(lines.size() < 4) throw new RuntimeException("Failed to create agent. Some information is missing");
        MD5Encryptor md5 = new MD5Encryptor();

        lastName = lines.get(0);
        firstName = lines.get(1);
        mission = lines.get(2);
        encryptedPassword = md5.encrypt(lines.get(3));
        equipment = new ArrayList<>();

        if(lines.size() > 5) {
            for(int i = 5; i < lines.size(); i++) {
                equipment.add(lines.get(i));
            }
        }
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getUsername() {
        return (firstName.charAt(0) + lastName).toLowerCase(Locale.ROOT);
    }

    public String getMission() {
        return mission;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public List<String> getEquipment() {
        return equipment;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", mission='" + mission + '\'' +
                ", password='" + encryptedPassword + '\'' +
                ", equipment=" + equipment +
                '}';
    }
}
