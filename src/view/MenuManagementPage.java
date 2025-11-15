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

public class MenuManagementPage {
    
    private Stage stage;
    private Scene scene;
    private Scene mainScene;
    
    // Constructor with stage and main scene reference
    public MenuManagementPage(Stage stage, Scene mainScene) {
        this.stage = stage;
        this.mainScene = mainScene;
        initializeUI();
    }
    
    private void initializeUI() {
        // ============================================================
        // MENU MANAGEMENT PAGE
        // ============================================================
        BorderPane menuManagementLayout = new BorderPane();
        
        Text menuTitle = new Text("Menu Management");
        menuTitle.setFont(new Font("Arial", 28));
        
        Button backToHome3 = new Button("⬅ Back to Home");
        backToHome3.setOnAction(e -> stage.setScene(mainScene));
        
        Text menuContent = new Text("Menu management functionality will be implemented here TEST");
        menuContent.setFont(new Font("Arial", 14));
        
        VBox menuTopSection = new VBox(10);
        menuTopSection.setAlignment(Pos.CENTER);
        menuTopSection.setPadding(new Insets(20));
        menuTopSection.getChildren().addAll(menuTitle, backToHome3);
        
        VBox menuCenterSection = new VBox(20);
        menuCenterSection.setAlignment(Pos.CENTER);
        menuCenterSection.setPadding(new Insets(40));
        menuCenterSection.getChildren().add(menuContent);
        
        menuManagementLayout.setTop(menuTopSection);
        menuManagementLayout.setCenter(menuCenterSection);
        
        scene = new Scene(menuManagementLayout, 800, 600);
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