package ViewController;

import Model.InhousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;


public class AddPartController {

    Stage stage;
    Parent scene;

    @FXML
    private Label sourceVariable;

    @FXML
    private RadioButton addPartInHouse;

    @FXML
    private RadioButton addPartOutsourced;

    @FXML
    private TextField addPartID;

    @FXML
    private TextField addPartName;

    @FXML
    private TextField addPartInv;

    @FXML
    private TextField addPartPC;

    @FXML
    private TextField addPartMax;

    @FXML
    private TextField addPartMin;

    @FXML
    private TextField addPartSourceVariable;

    @FXML
    void addPartInHouse(ActionEvent event) {

        sourceVariable.setText("Machine ID");
    }

    @FXML
    void addPartOutsourced(ActionEvent event) {

        sourceVariable.setText("Company Name");
    }

    @FXML
    void savePart(ActionEvent event) throws IOException {
        try {
            int id = (int) Math.random() * 1000 + 9999;
            String name = addPartName.getText();
            int partInv = Integer.parseInt(addPartInv.getText());
            double priceCost = Double.parseDouble(addPartPC.getText());
            int max = Integer.parseInt(addPartMax.getText());
            int min = Integer.parseInt(addPartMin.getText());

            if (max >= min) {
                if (addPartInHouse.isSelected()) {

                    int machineId = Integer.parseInt(addPartSourceVariable.getText());
                    Inventory.addPart(new InhousePart(id, name, priceCost, partInv, min, max, machineId));

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/ViewController/MainScreen.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();

                } else if (addPartOutsourced.isSelected()){

                    String companyName = addPartSourceVariable.getText();
                    Inventory.addPart(new OutsourcedPart(id, name, priceCost, partInv, min, max, companyName));

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/ViewController/MainScreen.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Select Source Type");
                    alert.setContentText("You must select In-House or Outsourced");

                    alert.showAndWait();
                }


            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Max/Min Error");
                alert.setContentText("The maximum entered is less than the minimum");

                alert.showAndWait();
            }
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Values Missing");
            alert.setContentText("One or more fields was left blank");

            alert.showAndWait();

        }

    }

    @FXML
    void cancelAddPart(ActionEvent event) throws IOException {
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
}
