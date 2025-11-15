package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import view.CustomerOrderingPage;
import view.KitchenManagementPage;
import view.MenuManagementPage;

public class Main extends Application {

    private Scene mainScene;  // Main menu scene

    @Override
    public void start(Stage primaryStage) {
        // ============================================================
        // MAIN MENU PAGE
        // ============================================================
        Text mainTitle = new Text("Dine Manager");
        mainTitle.setFont(new Font("Arial", 32));

        Button btnCustomerOrdering = new Button("Customer Ordering");
        Button btnKitchenManagement = new Button("Kitchen Management");
        Button btnMenuManagement = new Button("Menu Management");

        // Style buttons
        styleButton(btnCustomerOrdering);
        styleButton(btnKitchenManagement);
        styleButton(btnMenuManagement);

        VBox mainLayout = new VBox(20, mainTitle, btnCustomerOrdering, btnKitchenManagement, btnMenuManagement);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(40));

        mainScene = new Scene(mainLayout, 800, 600);


        // ============================================================
        // CREATE PAGE INSTANCES
        // ============================================================
        CustomerOrderingPage customerOrderingPage = new CustomerOrderingPage(primaryStage, mainScene);
        KitchenManagementPage kitchenManagementPage = new KitchenManagementPage(primaryStage, mainScene);
        MenuManagementPage menuManagementPage = new MenuManagementPage(primaryStage, mainScene);


        // ============================================================
        // BUTTON ACTIONS - Navigate to respective pages
        // ============================================================
        btnCustomerOrdering.setOnAction(e -> customerOrderingPage.show());
        btnKitchenManagement.setOnAction(e -> kitchenManagementPage.show());
        btnMenuManagement.setOnAction(e -> menuManagementPage.show());


        // ============================================================
        // SHOW MAIN MENU
        // ============================================================
        primaryStage.setTitle("Dine Manager");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    // Helper method to style buttons
    private void styleButton(Button button) {
        button.setPrefWidth(250);
        button.setPrefHeight(50);
        button.setFont(new Font("Arial", 16));
    }

    public static void main(String[] args) {
        launch(args);
    }
}