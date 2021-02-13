package ViewController;

import Model.Inventory;
import Model.Part;
import Model.Product;
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

public class ModifyProductController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TextField modProdSearch;

    @FXML
    private TextField modProdID;

    @FXML
    private TextField modProdName;

    @FXML
    private TextField modProdInv;

    @FXML
    private TextField modProdPrice;

    @FXML
    private TextField modProdMax;

    @FXML
    private TextField modProdMin;

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
        String searchTerm = modProdSearch.getText();
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
    void addProdPart(ActionEvent event) {
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
            associatedPartList.remove((Part) associatedPartsTable.getSelectionModel().getSelectedItem());
        }

    }
    @FXML
    void saveProd(ActionEvent event) throws IOException {
            int id = Integer.parseInt(modProdID.getText());
            String name = modProdName.getText();
            int stock = Integer.parseInt(modProdInv.getText());
            double price = Double.parseDouble(modProdPrice.getText());
            int min = Integer.parseInt(modProdMin.getText());
            int max = Integer.parseInt(modProdMax.getText());

            if (max > min) {

                for (Product product : Inventory.getAllProducts()) {

                    if (product.getId() == id) {

                        int productIndex = Inventory.getAllProducts().indexOf(product);

                        Product modifiedProduct = new Product(id, name, price, stock, min, max);

                        modifiedProduct.getAllAssociatedParts().setAll(associatedPartList);

                        Inventory.updateProduct(productIndex, modifiedProduct);
                        break;
                    }
                }

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/ViewController/MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Max/Min Error");
                alert.setContentText("The maximum entered is less than the minimum");

                alert.showAndWait();
            }
    }


    @FXML
    void cancelModProd(ActionEvent event) throws IOException {
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
    public void sendProd (Product product) {
        modProdID.setText(String.valueOf(product.getId()));
        modProdName.setText(product.getName());
        modProdInv.setText(String.valueOf(product.getStock()));
        modProdPrice.setText(String.valueOf(product.getPrice()));
        modProdMax.setText(String.valueOf(product.getMax()));
        modProdMin.setText(String.valueOf(product.getMin()));

        associatedPartList.setAll(product.getAllAssociatedParts());
        associatedPartsTable.setItems(associatedPartList);

        associatedPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPPU.setCellValueFactory(new PropertyValueFactory<>("price"));
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
