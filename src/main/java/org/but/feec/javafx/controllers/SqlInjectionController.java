package org.but.feec.javafx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.but.feec.javafx.api.PersonBasicView;
import org.but.feec.javafx.data.PersonRepository;
import org.but.feec.javafx.services.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SqlInjectionController {

    private static final Logger logger = LoggerFactory.getLogger(PersonsController.class);

    @FXML
    private Button execute1to1Button;
    @FXML
    private Button executeDropTableButton;
    @FXML
    private Button refreshButton1;
    @FXML
    private Button refreshButton2;
    @FXML
    private Button clearTableButton1;
    @FXML
    private Button clearTableButton2;
    @FXML
    private TextField textField1;
    @FXML
    private TextField textField2;
    @FXML
    private TableColumn<PersonBasicView, Long> personsId1;
    @FXML
    private TableColumn<PersonBasicView, String> personsCity1;
    @FXML
    private TableColumn<PersonBasicView, String> personsEmail1;
    @FXML
    private TableColumn<PersonBasicView, String> personsLastName1;
    @FXML
    private TableColumn<PersonBasicView, String> personsFirstName1;
    @FXML
    private TableColumn<PersonBasicView, Long> personsId2;
    @FXML
    private TableColumn<PersonBasicView, String> personsCity2;
    @FXML
    private TableColumn<PersonBasicView, String> personsEmail2;
    @FXML
    private TableColumn<PersonBasicView, String> personsLastName2;
    @FXML
    private TableColumn<PersonBasicView, String> personsFirstName2;
    @FXML
    private TableView<PersonBasicView> systemPersonsTableView1;
    @FXML
    private TableView<PersonBasicView> systemPersonsTableView2;

    private PersonService personService;
    private PersonRepository personRepository;

    @FXML
    private void initialize() {
        personRepository = new PersonRepository();
        personService = new PersonService(personRepository);

        personsId1.setCellValueFactory(new PropertyValueFactory<PersonBasicView, Long>("id"));
        personsEmail1.setCellValueFactory(new PropertyValueFactory<PersonBasicView, String>("email"));
        personsLastName1.setCellValueFactory(new PropertyValueFactory<PersonBasicView, String>("lastName"));
        personsFirstName1.setCellValueFactory(new PropertyValueFactory<PersonBasicView, String>("firstName"));

        personsId2.setCellValueFactory(new PropertyValueFactory<PersonBasicView, Long>("id"));
        personsEmail2.setCellValueFactory(new PropertyValueFactory<PersonBasicView, String>("email"));
        personsLastName2.setCellValueFactory(new PropertyValueFactory<PersonBasicView, String>("lastName"));
        personsFirstName2.setCellValueFactory(new PropertyValueFactory<PersonBasicView, String>("firstName"));


        logger.info("SqlInjectionController initialized");
    }

    private ObservableList<PersonBasicView> initializePersonsData() {
        List<PersonBasicView> persons = personService.getPersonsBasicView();
        return FXCollections.observableArrayList(persons);
    }

    private ObservableList<PersonBasicView> initializePersonsDataView1() {
        List<PersonBasicView> persons = personService.getPersonsBasicViewCustomer();
        return FXCollections.observableArrayList(persons);
    }

    private ObservableList<PersonBasicView> initializePersonsDataView2() {
        List<PersonBasicView> persons = personService.getPersonsBasicViewCustomer_1();
        return FXCollections.observableArrayList(persons);
    }

    private ObservableList<PersonBasicView> initializePersonsData1() {
        List<PersonBasicView> persons = personService.getPersonsInjectionView1(textField1.getText());
        return FXCollections.observableArrayList(persons);
    }

    private ObservableList<PersonBasicView> initializePersonsData2() {
        List<PersonBasicView> persons = personService.getPersonsInjectionView2(textField2.getText());
        return FXCollections.observableArrayList(persons);
    }

    public void handleExecute1to1Button(){
        ObservableList<PersonBasicView> observablePersonsList = initializePersonsData1();
        systemPersonsTableView1.setItems(observablePersonsList);
        //systemPersonsTableView1.refresh();
        systemPersonsTableView1.sort();
    }

    public void handleExecuteDropTableButton(){
        ObservableList<PersonBasicView> observablePersonsList = initializePersonsData2();
        systemPersonsTableView2.setItems(observablePersonsList);
        //systemPersonsTableView2.refresh();
        systemPersonsTableView2.sort();
    }

    public void handleRefreshButton1(ActionEvent actionEvent) {
        ObservableList<PersonBasicView> observablePersonsList = initializePersonsDataView1();
        systemPersonsTableView1.setItems(observablePersonsList);
        systemPersonsTableView1.refresh();
        systemPersonsTableView1.sort();
    }
    public void handleRefreshButton2(ActionEvent actionEvent) {
        ObservableList<PersonBasicView> observablePersonsList = initializePersonsDataView2();
        systemPersonsTableView2.setItems(observablePersonsList);
        systemPersonsTableView2.refresh();
        systemPersonsTableView2.sort();
    }



    public void handleClearTableButton1(){
        systemPersonsTableView1.setItems(null);
    }

    public void handleClearTableButton2(){
        systemPersonsTableView2.setItems(null);
    }
}
