package org.but.feec.bds.project.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.but.feec.bds.project.api.PersonCreateView;
import org.but.feec.bds.project.data.PersonRepository;
import org.but.feec.bds.project.services.PersonService;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Optional;

public class PersonCreateController {

    private static final Logger logger = LoggerFactory.getLogger(PersonCreateController.class);

    @FXML
    public Button newPersonCreatePerson;
    @FXML
    private TextField newPersonEmail;

    @FXML
    private TextField newPersonFirstName;

    @FXML
    private TextField newPersonLastName;

    @FXML
    private TextField newPersonPassword;

    @FXML
    private TextField newPersonSex;


    private PersonService personService;
    private PersonRepository personRepository;
    private ValidationSupport validation;

    @FXML
    public void initialize() {
        personRepository = new PersonRepository();
        personService = new PersonService(personRepository);

        validation = new ValidationSupport();
        validation.registerValidator(newPersonEmail, Validator.createEmptyValidator("The email must not be empty."));
        validation.registerValidator(newPersonFirstName, Validator.createEmptyValidator("The first name must not be empty."));
        validation.registerValidator(newPersonLastName, Validator.createEmptyValidator("The last name must not be empty."));
        validation.registerValidator(newPersonPassword, Validator.createEmptyValidator("The password must not be empty."));
        validation.registerValidator(newPersonSex, Validator.createEmptyValidator("The gender must not be empty."));

        newPersonCreatePerson.disableProperty().bind(validation.invalidProperty());

        logger.info("PersonCreateController initialized");
    }

    @FXML
    void handleCreateNewPerson(ActionEvent event) {
        String email = newPersonEmail.getText();
        String firstName = newPersonFirstName.getText();
        String lastName = newPersonLastName.getText();
        String password = newPersonPassword.getText();
        String gender = newPersonSex.getText();
        Timestamp registered = new Timestamp(System.currentTimeMillis());

        PersonCreateView personCreateView = new PersonCreateView();
        personCreateView.setPassword(password.toCharArray());
        personCreateView.setEmail(email);
        personCreateView.setFirstName(firstName);
        personCreateView.setLastName(lastName);
        personCreateView.setSex(gender.toCharArray());
        personCreateView.setRegistered(registered);


        personService.createPerson(personCreateView);

        personCreatedConfirmationDialog();
    }

    private void personCreatedConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Person Created Confirmation");
        alert.setHeaderText("Your person was successfully created.");

        Timeline idlestage = new Timeline(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                alert.setResult(ButtonType.CANCEL);
                alert.hide();
            }
        }));
        idlestage.setCycleCount(1);
        idlestage.play();
        Optional<ButtonType> result = alert.showAndWait();
    }

}
