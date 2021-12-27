package org.but.feec.javafx.api;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PersonDetailView {
    private LongProperty id = new SimpleLongProperty();
    private StringProperty email = new SimpleStringProperty();
    private StringProperty givenName = new SimpleStringProperty();
    private StringProperty familyName = new SimpleStringProperty();
    private StringProperty gender = new SimpleStringProperty();
    private StringProperty street = new SimpleStringProperty();
    private StringProperty houseNumber = new SimpleStringProperty();
    private StringProperty zipcode = new SimpleStringProperty();
    private StringProperty city = new SimpleStringProperty();
    private StringProperty country = new SimpleStringProperty();
    private StringProperty addressType = new SimpleStringProperty();


    public Long getId() {
        return idProperty().get();
    }

    public void setId(Long id) {
        this.idProperty().setValue(id);
    }

    public String getEmail() {
        return emailProperty().get();
    }

    public void setEmail(String email) {
        this.emailProperty().setValue(email);
    }

    public String getGivenName() {
        return givenNameProperty().get();
    }

    public void setGivenName(String givenName) {
        this.givenNameProperty().setValue(givenName);
    }

    public String getFamilyName() {
        return familyNameProperty().get();
    }

    public void setFamilyName(String familyName) {
        this.familyNameProperty().setValue(familyName);
    }

    public String getCity() {
        return cityProperty().get();
    }

    public void setCity(String city) {
        this.cityProperty().setValue(city);
    }

    public String gethouseNumber() {
        return houseNumberProperty().get();
    }

    public void sethouseNumber(String houseNumber) {
        this.houseNumberProperty().setValue(houseNumber);
    }

    public String getStreet() {
        return streetProperty().get();
    }

    public void setStreet(String street) {
        this.streetProperty().setValue(street);
    }

    public String getGender() { return genderProperty().get(); }

    public void setGender(String gender){ this.genderProperty().setValue(gender); }

    public String getZipcode() { return zipcodeProperty().get(); }

    public void setZipcode(String zipcode) { this.zipcodeProperty().setValue(zipcode); }

    public String getCountry() { return countryProperty().get(); }

    public void setCountry(String country) { this.countryProperty().setValue(country); }

    public String getAddressType() { return addressTypeProperty().get(); }

    public void setAddressType(String addressType) { this.addressTypeProperty().setValue(addressType); }

    public LongProperty idProperty() {
        return id;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty givenNameProperty() {
        return givenName;
    }

    public StringProperty familyNameProperty() {
        return familyName;
    }

    public StringProperty genderProperty() { return gender; }

    public StringProperty cityProperty() {
        return city;
    }

    public StringProperty houseNumberProperty() {
        return houseNumber;
    }

    public StringProperty streetProperty() {
        return street;
    }

    public StringProperty zipcodeProperty() { return zipcode; }

    public StringProperty countryProperty() { return country; }

    public StringProperty addressTypeProperty() { return addressType; }




}
