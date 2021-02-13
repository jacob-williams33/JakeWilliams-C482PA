package Main;

import Model.InhousePart;
import Model.Inventory;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../ViewController/MainScreen.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 1000, 775));
        primaryStage.show();
    }


    public static void main(String[] args) {

        InhousePart alpha = new InhousePart(1234, "alpha", 9.99, 5, 0, 10, 1111);
        InhousePart bravo = new InhousePart(2345, "bravo", 10.99, 5, 0, 10, 2222);
        InhousePart charlie = new InhousePart(3456, "charlie", 11.99, 5, 0, 10, 3333);

        Inventory.addPart(alpha);
        Inventory.addPart(bravo);
        Inventory.addPart(charlie);

        Product prod1 = new Product(1234, "prod1", 9.99, 1, 4, 14);
        Product prod2 = new Product(2345, "prod2", 9.99, 1, 4, 14);
        Product prod3 = new Product(3456, "prod3", 9.99, 1, 4, 14);

        Inventory.addProduct(prod1);
        Inventory.addProduct(prod2);
        Inventory.addProduct(prod3);

        prod1.addAssociatedPart(alpha);
        prod2.addAssociatedPart(bravo);
        prod3.addAssociatedPart(charlie);

        launch(args);
    }
}
