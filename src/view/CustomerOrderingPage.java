package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CustomerOrderingPage {
    
    private Stage stage;
    private Scene scene;
    private Scene mainScene;  // Reference to main menu scene for back button
    
    // Constructor with stage and main scene reference
    public CustomerOrderingPage(Stage stage, Scene mainScene) {
        this.stage = stage;
        this.mainScene = mainScene;
        initializeUI();
    }
    
    private void initializeUI() {
        // ============================================================
        // CUSTOMER ORDERING PAGE
        // ============================================================
        BorderPane customerOrderingLayout = new BorderPane();
        
        Text customerTitle = new Text("Customer Ordering");
        customerTitle.setFont(new Font("Arial", 28));
        
        Button backToHome1 = new Button("⬅ Back to Home");
        backToHome1.setOnAction(e -> stage.setScene(mainScene));
        
        Text customerContent = new Text("Customer ordering functionality will be implemented here TEST");
        customerContent.setFont(new Font("Arial", 14));
        
        VBox customerTopSection = new VBox(10);
        customerTopSection.setAlignment(Pos.CENTER);
        customerTopSection.setPadding(new Insets(20));
        customerTopSection.getChildren().addAll(customerTitle, backToHome1);
        
        VBox customerCenterSection = new VBox(20);
        customerCenterSection.setAlignment(Pos.CENTER);
        customerCenterSection.setPadding(new Insets(40));
        customerCenterSection.getChildren().add(customerContent);
        
        customerOrderingLayout.setTop(customerTopSection);
        customerOrderingLayout.setCenter(customerCenterSection);
        
        scene = new Scene(customerOrderingLayout, 800, 600);
    }
    
    // Method to get the scene
    public Scene getScene() {
        return scene;
    }
    
    // Method to show this page
    public void show() {
        stage.setScene(scene);
    }
}