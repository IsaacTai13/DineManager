package view;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class KitchenManagementPage {
    
    private Stage stage;
    private Scene scene;
    private Scene mainScene;
    
    // Constructor with stage and main scene reference
    public KitchenManagementPage(Stage stage, Scene mainScene) {
        this.stage = stage;
        this.mainScene = mainScene;
        initializeUI();
    }
    
    private void initializeUI() {
        // ============================================================
        // KITCHEN MANAGEMENT PAGE
        // ============================================================
        BorderPane kitchenManagementLayout = new BorderPane();
        
        Text kitchenTitle = new Text("Kitchen Management");
        kitchenTitle.setFont(new Font("Arial", 28));
        
        Button backToHome2 = new Button("⬅ Back to Home");
        backToHome2.setOnAction(e -> stage.setScene(mainScene));
        
        Text kitchenContent = new Text("Kitchen management functionality will be implemented here TEST");
        kitchenContent.setFont(new Font("Arial", 14));
        
        VBox kitchenTopSection = new VBox(10);
        kitchenTopSection.setAlignment(Pos.CENTER);
        kitchenTopSection.setPadding(new Insets(20));
        kitchenTopSection.getChildren().addAll(kitchenTitle, backToHome2);
        
        VBox kitchenCenterSection = new VBox(20);
        kitchenCenterSection.setAlignment(Pos.CENTER);
        kitchenCenterSection.setPadding(new Insets(40));
        kitchenCenterSection.getChildren().add(kitchenContent);
        
        kitchenManagementLayout.setTop(kitchenTopSection);
        kitchenManagementLayout.setCenter(kitchenCenterSection);
        
        scene = new Scene(kitchenManagementLayout, 800, 600);
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