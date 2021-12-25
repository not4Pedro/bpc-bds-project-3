package org.but.feec.javafx.api;

import java.util.Arrays;
import java.sql.Timestamp;
import java.util.Date;



public class PersonCreateView {

    private String firstName;
    private String lastName;
    private String email;
    private char[] pwd;
    private char [] gender;
    private Timestamp joined;

    public char[] getGender() {
        return gender;
    }

    public void setGender(char[] gender) {
        this.gender = gender;
    }

    public Timestamp getJoined() {
        return joined;
    }

    public void setJoined(Timestamp joined) {
        this.joined = joined;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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





    public char[] getPwd() {
        return pwd;
    }

    public void setPwd(char[] pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "PersonCreateView{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", joined='" + joined + '\''+
                ", pwd=" + Arrays.toString(pwd) +
                '}';
    }
}
