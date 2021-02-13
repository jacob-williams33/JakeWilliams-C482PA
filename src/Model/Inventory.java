package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

    public static void  addPart(Part newPart) {
        allParts.add(newPart);
    }
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
    public static Part lookupPart (int partId) {
        for (Part p : allParts) {
            if (p.getId() == partId) {
                return p;
            }
        } return null;
    }

    public static Product lookupProduct (int productId) {
        for (Product prod : allProducts) {
            if (prod.getId() == productId) {
                return prod;
            }
        } return null;
    }
    public static ObservableList<Product> lookupProduct (String name) {
        if (!(Inventory.getAllFilteredProducts()).isEmpty())
            Inventory.getAllFilteredProducts().clear();
        for (Product prod : Inventory.getAllProducts()) {
            if (prod.getName().contains(name))
                Inventory.getAllFilteredProducts().add(prod);
        }
        if (Inventory.getAllFilteredProducts().isEmpty()) {
            return Inventory.getAllProducts();
        }else {
            return Inventory.getAllFilteredProducts();
        }
    }
    public static ObservableList<Part> lookupPart (String name) {
        if (!(Inventory.getAllFilteredParts()).isEmpty())
            Inventory.getAllFilteredParts().clear();
        for (Part p : Inventory.getAllParts()) {
            if (p.getName().contains(name))
                Inventory.getAllFilteredParts().add(p);
        }
        if (Inventory.getAllFilteredParts().isEmpty()) {
            return Inventory.getAllParts();
        }else {
            return Inventory.getAllFilteredParts();
        }
    }



    public static void updatePart(int index, Part part) {
        allParts.set(index, part);
    }

    public static void updateProduct(int index, Product product) {
        allProducts.set(index, product);
    }

    public static void deletePart(Part part) {
        allParts.remove(part);
    }

    public static void deleteProduct(Product product) {
        allProducts.remove(product);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Part> getAllFilteredParts() {
        return filteredParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
    public static ObservableList<Product> getAllFilteredProducts() {
        return filteredProducts;
    }
}
