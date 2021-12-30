package org.but.feec.javafx.api;

import java.util.Arrays;
import java.sql.Timestamp;


public class PersonCreateView {

    private String firstName;
    private String lastName;
    private String email;
    private char[] password;
    private char [] sex;
    private Timestamp registered;

    public char[] getSex() {
        return sex;
    }

    public void setSex(char[] sex) {
        this.sex = sex;
    }

    public Timestamp getRegistered() {
        return registered;
    }

    public void setRegistered(Timestamp registered) {
        this.registered = registered;
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



    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "PersonCreateView{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", sex='" + sex + '\'' +
                ", registered='" + registered + '\''+
                ", password=" + Arrays.toString(password) +
                '}';
    }
}
