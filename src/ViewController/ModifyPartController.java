package ViewController;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ModifyPartController {
    Stage stage;
    Parent scene;

    @FXML
    private RadioButton modPartInHouse;

    @FXML
    private RadioButton modPartOutsourced;

    @FXML
    private TextField modPartID;

    @FXML
    private TextField modPartName;

    @FXML
    private TextField modPartInventory;

    @FXML
    private TextField modPartPC;

    @FXML
    private TextField modPartMax;

    @FXML
    private TextField modPartVariable;

    @FXML
    private Label modPartVariableType;

    @FXML
    private TextField modPartMin;

    @FXML
    void savePart(ActionEvent event) throws IOException {
        int id = Integer.parseInt(modPartID.getText());
        String name = modPartName.getText();
        int partInv = Integer.parseInt(modPartInventory.getText());
        double priceCost = Double.parseDouble(modPartPC.getText());
        int max = Integer.parseInt(modPartMax.getText());
        int min = Integer.parseInt(modPartMin.getText());

        if (max > min) {

            for (Part p : Inventory.getAllParts()) {
                if (p.getId() == id) {

                    int index = Inventory.getAllParts().indexOf(p);

                    if (modPartInHouse.isSelected()) {
                        if (p instanceof InhousePart) {
                            p.setId(id);
                            p.setName(name);
                            p.setStock(partInv);
                            p.setPrice(priceCost);
                            p.setMax(max);
                            p.setMin(min);
                            ((InhousePart) p).setMachineID(Integer.parseInt(modPartVariable.getText()));
                        } else {
                            Part updatedInHousePart = new InhousePart(id, name, priceCost, partInv, max, min, Integer.parseInt(modPartVariable.getText()));
                            Inventory.updatePart(index, updatedInHousePart);
                        }
                    } else if (modPartOutsourced.isSelected()) {
                        if (p instanceof OutsourcedPart) {
                            p.setId(id);
                            p.setName(name);
                            p.setStock(partInv);
                            p.setPrice(priceCost);
                            p.setMax(max);
                            p.setMin(min);
                            ((OutsourcedPart) p).setCompanyName(modPartVariable.getText());
                        } else {
                            Part updatedOutsourcedPart = new OutsourcedPart(id, name, priceCost, partInv, max, min, modPartVariable.getText());
                            Inventory.updatePart(index, updatedOutsourcedPart);
                        }
                    }
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
    void cancelModPart(ActionEvent event) throws IOException {
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

    @FXML
    void modPartIHSelect(ActionEvent event) {
        modPartVariableType.setText("Machine ID");
    }
    @FXML
    void modPartOUTSelect(ActionEvent event) {
        modPartVariableType.setText("Company Name");
    }

    public void sendPart(Part part) {
        modPartID.setText(String.valueOf(part.getId()));
        modPartName.setText(part.getName());
        modPartInventory.setText(String.valueOf(part.getStock()));
        modPartPC.setText(String.valueOf(part.getPrice()));
        modPartMax.setText(String.valueOf(part.getMax()));
        modPartMin.setText(String.valueOf(part.getMin()));

        if (part instanceof InhousePart) {
            modPartInHouse.setSelected(true);
            modPartVariable.setText(String.valueOf(((InhousePart) part).getMachineID()));
        } else {
            modPartOutsourced.setSelected(true);
            modPartVariable.setText(((OutsourcedPart) part).getCompanyName());
        }
    }
}
