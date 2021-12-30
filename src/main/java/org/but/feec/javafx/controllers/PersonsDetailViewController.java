package org.but.feec.javafx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.but.feec.javafx.api.PersonDetailView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonsDetailViewController {

    private static final Logger logger = LoggerFactory.getLogger(PersonsDetailViewController.class);

    @FXML
    private TextField idTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField sexTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField zipcodeTextField;

    @FXML
    private TextField streetNumberTextField;

    @FXML
    private TextField streetTextField;

    @FXML
    private TextField countryTextField;

    @FXML
    private TextField addressTypeTextField;

    @FXML
    private TextField registeredTextField;

    // used to reference the stage and to get passed data through it
    public Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        idTextField.setEditable(false);
        emailTextField.setEditable(false);
        firstNameTextField.setEditable(false);
        lastNameTextField.setEditable(false);
        sexTextField.setEditable(false);
        cityTextField.setEditable(false);
        streetNumberTextField.setEditable(false);
        streetTextField.setEditable(false);
        zipcodeTextField.setEditable(false);
        countryTextField.setEditable(false);
        addressTypeTextField.setEditable(false);

        loadPersonsData();

        logger.info("PersonsDetailViewController initialized");
    }

    private void loadPersonsData() {
        Stage stage = this.stage;
        if (stage.getUserData() instanceof PersonDetailView) {
            PersonDetailView personBasicView = (PersonDetailView) stage.getUserData();
            idTextField.setText(String.valueOf(personBasicView.getId()));
            emailTextField.setText(personBasicView.getEmail());
            firstNameTextField.setText(personBasicView.getFirstName());
            lastNameTextField.setText(personBasicView.getLastName());
            sexTextField.setText(personBasicView.getSex());
            cityTextField.setText(personBasicView.getCity());
            zipcodeTextField.setText(personBasicView.getZipcode());
            streetNumberTextField.setText(personBasicView.gethouseNumber());
            streetTextField.setText(personBasicView.getStreetName());
            countryTextField.setText(personBasicView.getCountry());
            addressTypeTextField.setText(personBasicView.getAddressType());
        }
    }

}
