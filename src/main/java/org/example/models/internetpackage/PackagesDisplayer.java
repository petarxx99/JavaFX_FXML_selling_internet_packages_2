package org.example.models.internetpackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.example.controllers.ProdajaInternetPaketa;
import org.example.util.MessageDisplayer;
import org.example.util.Util;

public class PackagesDisplayer {

    private TableView<InternetPackage> createTableViewInternetPackages(){
        TableColumn<InternetPackage, String> tcFirstName = new TableColumn<>("first name");
        tcFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<InternetPackage, String> tcLastName = new TableColumn<>("last name");
        tcLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<InternetPackage, String> tcAddress = new TableColumn<>("address");
        tcAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<InternetPackage, Integer> tcInternetSpeedInMBPS = new TableColumn<>("speed (MBPS)");
        tcInternetSpeedInMBPS.setCellValueFactory(new PropertyValueFactory<>("internetSpeedInMegabytesPerSecond"));

        TableColumn<InternetPackage, Bandwidth> tcBandwidth = new TableColumn<>("bandwidth (GBs)");
        tcBandwidth.setCellValueFactory(new PropertyValueFactory<>("bandwidth"));

        TableColumn<InternetPackage, Integer> tcContractDuration = new TableColumn<>("contract lasts (months)");
        tcContractDuration.setCellValueFactory(new PropertyValueFactory<>("contractDurationInMonths"));

        TableView<InternetPackage> tableView = new TableView<>();
        tableView.getColumns().addAll(tcFirstName, tcLastName, tcAddress, tcInternetSpeedInMBPS,
                tcBandwidth, tcContractDuration);
        return tableView;
    }


    public void displayInternetPackages(ObservableList<InternetPackage> packages, ProdajaInternetPaketa prodajaInternetPaketa, MessageDisplayer messenger){
        TableView<InternetPackage> tableView = createTableViewInternetPackages();
        tableView.setItems(packages);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(tableView);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setFitToWidth(true);

        Button deleteButton = new Button("delete selected package");
        ObservableList<InternetPackage> deletedItems = FXCollections.<InternetPackage>observableArrayList();
        deleteButton.setOnAction(actionEvent -> deleteFromTableView(tableView, packages, deletedItems, messenger));
        deleteButton.setStyle(".button:hover{-fx-background-color:red;}");

        VBox root = new VBox();
        root.getChildren().addAll(scrollPane, deleteButton);

        final double SCREEN_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight() * 9/10.0;
        final double SCREEN_WIDTH = Screen.getPrimary().getVisualBounds().getWidth() * 9.5 / 10.0;
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL); /* Da ne mogu istovremeno da se otvore nekoliko prozora i brisu paketi na nekoliko mesta.*/
        stage.setScene(scene);
        stage.setOnCloseRequest(windowEvent -> prodajaInternetPaketa.anotherWindowDeletedPackages(deletedItems));
        stage.show();
    }

    private <T> void deleteFromTableView(TableView<T> tableView, ObservableList<T> data, ObservableList<T> deletedItems, MessageDisplayer messenger){
        var selectedItems = tableView.getSelectionModel().getSelectedItems();

        if(Util.listIsNullOrHasNoElements(selectedItems)) {
            messenger.showErrorMessage("You haven't selected anything to delete.");
        } else {
            deletedItems.addAll(selectedItems);
            data.removeAll(selectedItems);
            tableView.setItems(data);
        }
    }
    /*
    deleteButton.setOnAction(actionEvent -> {
            var selectedPackages = tableView.getSelectionModel().getSelectedItems();
            if(Util.listIsNullOrHasNoElements(selectedPackages)) {
                showErrorMessage("You haven't selected a package");
            } else {
                savedPackages.removeAll(selectedPackages);
                tableView.getItems().removeAll(selectedPackages);
            }
        });
     */

}
