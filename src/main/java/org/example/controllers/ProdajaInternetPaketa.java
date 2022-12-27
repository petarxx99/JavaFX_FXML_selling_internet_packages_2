package org.example.controllers;


import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.ValidOrInvalidData;
import org.example.models.internetpackage.Bandwidth;
import org.example.models.internetpackage.InternetPackage;
import org.example.models.internetpackage.PackagesDisplayer;
import org.example.util.AlertMessages;
import org.example.util.MessageDisplayer;
import org.example.util.Util;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ProdajaInternetPaketa {

    InternetPackage userInput;

    ObservableList<InternetPackage> savedPackages = FXCollections.<InternetPackage>observableArrayList();

    MessageDisplayer messageDisplayer = new AlertMessages();
    PackagesDisplayer packagesDisplayer = new PackagesDisplayer();
 
    @FXML
    private Parent root;
    @FXML
    private TextField firstName, lastName, address;

    @FXML
    private ToggleGroup internetSpeed, bandwidth, contractDuration;

    @FXML
    private ToggleButton MBPS2, MBPS5, MBPS10, MBPS20, MBPS50, MBPS100,
    GB1, GB2, GB5, GB10, GB100, FLAT,
    durationInMonths12, durationInMonths24;

    @FXML
    private Button saveButton, clearButton, deleteTableRowButton, seePackagesThatCorrespondToInputData;

    @FXML
    private TextField idToDelete;

    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    public ProdajaInternetPaketa() {
    }

   
    @FXML
    private void initialize() {
        userInput = new InternetPackage();

        userInput.firstNameProperty().bindBidirectional(firstName.textProperty());
        userInput.lastNameProperty().bindBidirectional(lastName.textProperty());
        userInput.addressProperty().bindBidirectional(address.textProperty());

        internetSpeed.selectedToggleProperty().addListener((obsValue, oldValue, newValue)->
                internetSpeedChanged(newValue));

        bandwidth.selectedToggleProperty().addListener((obsValue, oldValue, newValue)->{
            bandwidthChanged(newValue);
        });

        contractDuration.selectedToggleProperty().addListener((obsValue, oldValue, newValue)->
                contractDurationChanged(newValue));

        System.out.println("Initializing ProdajaInternetPaketa...");
        System.out.println("Location = " + location);
        System.out.println("Resources = " + resources);
    }

    private void internetSpeedChanged(Toggle toggle){
        if(toggle == null) {
            userInput.resetInternetSpeedInMegabytesPerSecond();
        } else {
            ToggleButton clickedButton = (ToggleButton) toggle;
            final int NEW_INTERNET_SPEED_IN_MBPS = toMegabytesPerSecond(clickedButton);
            userInput.setInternetSpeedInMegabytesPerSecond(NEW_INTERNET_SPEED_IN_MBPS);
        }
    }

    private void bandwidthChanged(Toggle toggle){
        if(toggle == null){
            userInput.resetBandwidth();
        } else {
            ToggleButton clickedButton = (ToggleButton) toggle;
            userInput.setBandwidth(Bandwidth.valueOf(clickedButton.getId()));
        }
    }

    private void contractDurationChanged(Toggle toggle){
        if(toggle == null){
            userInput.resetContractDurationInMonths();
        } else {
            ToggleButton clickedButton = (ToggleButton) toggle;
            final int NEW_CONTRACT_DURATION_IN_MONTHS = toContractDurationInMonths(clickedButton);
            userInput.setContractDurationInMonths(NEW_CONTRACT_DURATION_IN_MONTHS);
        }
    }

    public static <T> ObservableList<T> copyList(ObservableList<T> observableList){
        return observableList.stream().
                collect(Collectors.toCollection(FXCollections::<T>observableArrayList));
    }

    @FXML
    private void seeSavedPackages(){
        ObservableList<InternetPackage> copyOfSavedPackages = copyList(savedPackages);
        packagesDisplayer.displayInternetPackages(copyOfSavedPackages, this, messageDisplayer);
    }


    @FXML
    private void savePackage(){
        root.setVisible(false);
        
        ValidOrInvalidData data = userInput.isValid();
        if(data.isntValid()){
            messageDisplayer.showErrorMessage(data.getMessageWhyDataIsInvalid());
        } else {
            savedPackages.add(userInput.copy());
            messageDisplayer.showMessage("Package has been saved. Here is the internet package that was saved: \n" + userInput);
        }
        root.setVisible(true);
    }

    @FXML
    private void clearForm(){
        userInput.reset();

        internetSpeed.selectToggle(null);
        bandwidth.selectToggle(null);
        contractDuration.selectToggle(null);
    }


    @FXML
    private void showSavedPackagesThatMatchGUIinputData() {
        ObservableList<InternetPackage> packagesThatMatchUserInput = savedPackages.stream().
                filter(userInput::matchesThisValidPackage).
                collect(Collectors.toCollection(FXCollections::<InternetPackage>observableArrayList));

        if (Util.listIsNullOrHasNoElements(packagesThatMatchUserInput)) {
            messageDisplayer.showErrorMessage("There are no saved packages that correspond to the data that you have inputed into this window.");
        } else {
            packagesDisplayer.displayInternetPackages(packagesThatMatchUserInput, this, messageDisplayer);
        }
    }
    
    private int toMegabytesPerSecond(ToggleButton clickedButton){
        final String BUTTON_ID = clickedButton.getId();
        final String SPEED_IN_MBPS_AS_STRING = BUTTON_ID.substring("MBPS".length());
        return Integer.parseInt(SPEED_IN_MBPS_AS_STRING);
    }

    private int toContractDurationInMonths(ToggleButton clickedButton){
        final String BUTTON_ID = clickedButton.getId();
        final String DURATION_IN_MONTHS_AS_STRING = BUTTON_ID.substring("durationInMonths".length());
        return Integer.parseInt(DURATION_IN_MONTHS_AS_STRING);
    }

     public void anotherWindowDeletedPackages(
            ObservableList<InternetPackage> deletedPackages){

        savedPackages = savedPackages.stream().
                filter(savedPackage -> !deletedPackages.contains(savedPackage)).
                collect(Collectors.toCollection(FXCollections::<InternetPackage>observableArrayList));
    }

}
