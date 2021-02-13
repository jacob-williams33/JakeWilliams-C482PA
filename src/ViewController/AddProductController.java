package ViewController;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class AddProductController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TextField addProdSearch;

    @FXML
    private TextField addProdID;

    @FXML
    private TextField addProdName;

    @FXML
    private TextField addProdInv;

    @FXML
    private TextField addProdPrice;

    @FXML
    private TextField addProdMax;

    @FXML
    private TextField addProdMin;

    @FXML
    private TableView invPartsTable;

    @FXML
    private TableColumn invPartID;

    @FXML
    private TableColumn invPartName;

    @FXML
    private TableColumn invPartInvLevel;

    @FXML
    private TableColumn invPartPPU;

    @FXML
    private TableView associatedPartsTable;

    @FXML
    private TableColumn associatedPartID;

    @FXML
    private TableColumn associatedPartName;

    @FXML
    private TableColumn associatedPartInvLevel;

    @FXML
    private TableColumn associatedPartPPU;

    ObservableList<Part> associatedPartList = FXCollections.observableArrayList();

    @FXML

    void searchProd(ActionEvent event) {
        String searchTerm = addProdSearch.getText();
        try {
            int searchId = Integer.valueOf(searchTerm);
            ObservableList<Part> searchResult = FXCollections.observableArrayList();
            searchResult.add(Inventory.lookupPart(searchId));

            if (searchResult.get(0) == null) {
                invPartsTable.setItems(Inventory.getAllParts());
            } else {
                invPartsTable.setItems(searchResult);
            }
        } catch (NumberFormatException e) {
            invPartsTable.setItems(Inventory.lookupPart(searchTerm));
        }

    }
    @FXML
    void addAssociatedPart(ActionEvent event) {
        associatedPartList.add((Part) invPartsTable.getSelectionModel().getSelectedItem());

    }
    @FXML
    void deletePart(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setHeaderText("This will permanently delete the selected part");
        alert.setContentText("Do you wish to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            associatedPartList.remove(associatedPartsTable.getSelectionModel().getSelectedItem());
        }
    }
    @FXML
    void saveProd(ActionEvent event) {
        try {
            int id = (int) Math.random() * 1000 + 9999;
            String name = addProdName.getText();
            int partInv = Integer.parseInt(addProdInv.getText());
            double priceCost = Double.parseDouble(addProdPrice.getText());
            int max = Integer.parseInt(addProdMax.getText());
            int min = Integer.parseInt(addProdMin.getText());

            if (max > min) {

                Inventory.addProduct(new Product(id, name, priceCost, partInv, max, min));
                for (Part p : associatedPartList) {
                    Inventory.getAllProducts().get(Inventory.getAllProducts().size() - 1).addAssociatedPart(p);
                }


                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/ViewController/MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Max/Min Error");
                alert.setContentText("The maximum entered is less than the minimum");

                alert.showAndWait();
            }
        }
        catch (NumberFormatException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Values Missing");
            alert.setContentText("One or more fields was left blank");

            alert.showAndWait();
        }

    }
    @FXML
    void cancelAddProd(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setHeaderText("Changes Will Not Be Saved");
        alert.setContentText("Do you wish to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/ViewController/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        invPartsTable.setItems(Inventory.getAllParts());

        invPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        invPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        invPartInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        invPartPPU.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsTable.setItems(associatedPartList);

        associatedPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPPU.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}
