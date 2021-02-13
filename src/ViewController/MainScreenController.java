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


public class MainScreenController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField mainPartSearch;

    @FXML
    private TextField mainProdSearch;

    @FXML
    private TableView<Part> mainPartTable;

    @FXML
    private TableColumn<Part, Integer> mainPartID;

    @FXML
    private TableColumn<Part, String> mainPartName;

    @FXML
    private TableColumn<Part, Integer> mainPartInv;

    @FXML
    private TableColumn<Part, Double> mainPartPPU;

    @FXML
    private TableView<Product> mainProdTable;

    @FXML
    private TableColumn<Product, Integer> mainProdID;

    @FXML
    private TableColumn<Product, String> mainProdName;

    @FXML
    private TableColumn<Product, Integer> mainProdInv;

    @FXML
    private TableColumn<Product, Double> mainProdPPU;

    @FXML
    void deletePart(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setHeaderText("This will permanently delete the selected part");
        alert.setContentText("Do you wish to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Inventory.deletePart(mainPartTable.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    void modifyPart(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ViewController/ModifyPart.fxml"));
            loader.load();

            ModifyPartController modpartcont = loader.getController();
            modpartcont.sendPart(mainPartTable.getSelectionModel().getSelectedItem());
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Selection Error");
            alert.setContentText("No Product Selected");

            alert.showAndWait();
        }
    }

    @FXML
    void addPart(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/ViewController/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void modifyProduct(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ViewController/ModifyProduct.fxml"));
            loader.load();

            ModifyProductController modprodcont = loader.getController();
            modprodcont.sendProd(mainProdTable.getSelectionModel().getSelectedItem());
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Selection Error");
            alert.setContentText("No Product Selected");

            alert.showAndWait();
        }
    }

    @FXML
    void addProduct(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/ViewController/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void deleteProduct(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setHeaderText("This will permanently delete the selected product");
        alert.setContentText("Do you wish to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Inventory.deleteProduct(mainProdTable.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    void exitMainScreen(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setHeaderText("Exiting The Program");
        alert.setContentText("Are you sure you wish to exit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @FXML
    void searchParts(ActionEvent event){
        String searchTerm = mainPartSearch.getText();
        try {
            int searchId = Integer.valueOf(searchTerm);
            ObservableList<Part> searchResult = FXCollections.observableArrayList();
            searchResult.add(Inventory.lookupPart(searchId));

            if (searchResult.get(0) == null) {
                mainPartTable.setItems(Inventory.getAllParts());
            } else {
                mainPartTable.setItems(searchResult);
            }
        } catch (NumberFormatException e) {
            mainPartTable.setItems(Inventory.lookupPart(searchTerm));
        }
    }


    @FXML
    void searchProducts(ActionEvent event) {
        String searchTerm = mainProdSearch.getText();
        try {
            int searchId = Integer.valueOf(searchTerm);
            ObservableList<Product> searchResult = FXCollections.observableArrayList();
            searchResult.add(Inventory.lookupProduct(searchId));

            if (searchResult.get(0) == null) {
                mainProdTable.setItems(Inventory.getAllProducts());
            } else {
                mainProdTable.setItems(searchResult);
            }
        } catch (NumberFormatException e) {
            mainProdTable.setItems(Inventory.lookupProduct(searchTerm));
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        mainPartTable.setItems(Inventory.getAllParts());

        mainPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mainPartPPU.setCellValueFactory(new PropertyValueFactory<>("price"));

        mainProdTable.setItems(Inventory.getAllProducts());

        mainProdID.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainProdName.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainProdInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mainProdPPU.setCellValueFactory(new PropertyValueFactory<>("price"));


    }
}
