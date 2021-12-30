package org.but.feec.bds.project.api;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PersonDetailView {
    private LongProperty id = new SimpleLongProperty();
    private StringProperty email = new SimpleStringProperty();
    private StringProperty firstName = new SimpleStringProperty();
    private StringProperty lastName = new SimpleStringProperty();
    private StringProperty sex = new SimpleStringProperty();
    private StringProperty streetName = new SimpleStringProperty();
    private StringProperty streetNumber = new SimpleStringProperty();
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

    public String getFirstName() {
        return firstNameProperty().get();
    }

    public void setFirstName(String firstName) {
        this.firstNameProperty().setValue(firstName);
    }

    public String getLastName() {
        return lastNameProperty().get();
    }

    public void setLastName(String lastName) {
        this.lastNameProperty().setValue(lastName);
    }

    public String getCity() {
        return cityProperty().get();
    }

    public void setCity(String city) {
        this.cityProperty().setValue(city);
    }

    public String gethouseNumber() {
        return streetNumberProperty().get();
    }

    public void sethouseNumber(String houseNumber) {
        this.streetNumberProperty().setValue(houseNumber);
    }

    public String getStreetName() {
        return streetNameProperty().get();
    }

    public void setStreetName(String streetName) {
        this.streetNameProperty().setValue(streetName);
    }

    public String getSex() { return sexProperty().get(); }

    public void setSex(String sex){ this.sexProperty().setValue(sex); }

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

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public StringProperty sexProperty() { return sex; }

    public StringProperty cityProperty() {
        return city;
    }

    public StringProperty streetNumberProperty() {
        return streetNumber;
    }

    public StringProperty streetNameProperty() {
        return streetName;
    }

    public StringProperty zipcodeProperty() { return zipcode; }

    public StringProperty countryProperty() { return country; }

    public StringProperty addressTypeProperty() { return addressType; }
}
