// ============================================================
// File: MenuManagementPage.java
// Location: src/view/MenuManagementPage.java
// Purpose: Menu management GUI (View layer only)
// Responsible: Member C
// ============================================================

package view;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import model.MenuItem;
import controller.MenuManagementController;
import java.util.List;

/**
 * Menu Management Page (View Layer)
 * Only handles UI components and delegates logic to controller
 */
public class MenuManagementPage {
    
    private Stage stage;
    private Scene scene;
    private Scene mainScene;
    private MenuManagementController controller;
    
    // UI Components
    private TableView<MenuItem> tableView;
    private TextField idField, nameField, priceField, descField;
    private ComboBox<String> categoryBox, sortBox;
    private Label timeLabel;
    
    public MenuManagementPage(Stage stage, Scene mainScene) {
        this.stage = stage;
        this.mainScene = mainScene;
        this.controller = new MenuManagementController(this);
        initializeUI();
    }
    
    private void initializeUI() {
        BorderPane mainLayout = new BorderPane();
        
        // ============================================================
        // TOP SECTION - Title and Back Button
        // ============================================================
        Text title = new Text("Menu Management");
        title.setFont(new Font("Arial", 28));
        
        Button backButton = new Button("⬅ Back to Home");
        backButton.setOnAction(e -> stage.setScene(mainScene));
        
        HBox topBar = new HBox(20);
        topBar.setAlignment(Pos.CENTER);
        topBar.setPadding(new Insets(20));
        topBar.getChildren().addAll(title, backButton);
        
        // ============================================================
        // INPUT FORM SECTION
        // ============================================================
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(20));
        
        // Row 1: ID and Name
        formGrid.add(new Label("ID:"), 0, 0);
        idField = new TextField();
        idField.setPromptText("e.g. M001");
        idField.setPrefWidth(100);
        formGrid.add(idField, 1, 0);
        
        formGrid.add(new Label("Name:"), 2, 0);
        nameField = new TextField();
        nameField.setPromptText("e.g. Burger");
        nameField.setPrefWidth(200);
        formGrid.add(nameField, 3, 0);
        
        // Row 2: Price and Category
        formGrid.add(new Label("Price:"), 0, 1);
        priceField = new TextField();
        priceField.setPromptText("e.g. 150");
        priceField.setPrefWidth(100);
        formGrid.add(priceField, 1, 1);
        
        formGrid.add(new Label("Category:"), 2, 1);
        categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll("Main Dish", "Beverage", "Dessert");
        categoryBox.setValue("Main Dish");
        categoryBox.setPrefWidth(150);
        formGrid.add(categoryBox, 3, 1);
        
        // Row 3: Description
        formGrid.add(new Label("Description:"), 0, 2);
        descField = new TextField();
        descField.setPromptText("Optional");
        descField.setPrefWidth(400);
        formGrid.add(descField, 1, 2, 3, 1);
        
        // Buttons
        Button addButton = new Button("Add");
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");
        Button clearButton = new Button("Clear");
        
        addButton.setOnAction(e -> handleAddClick());
        updateButton.setOnAction(e -> handleUpdateClick());
        deleteButton.setOnAction(e -> handleDeleteClick());
        clearButton.setOnAction(e -> clearFields());
        
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));
        buttonBox.getChildren().addAll(addButton, updateButton, deleteButton, clearButton);
        
        VBox formSection = new VBox(10);
        formSection.getChildren().addAll(formGrid, buttonBox);
        
        // ============================================================
        // TABLE VIEW SECTION
        // ============================================================
        tableView = new TableView<>();
        
        TableColumn<MenuItem, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(80);
        
        TableColumn<MenuItem, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colName.setPrefWidth(200);
        
        TableColumn<MenuItem, Double> colPrice = new TableColumn<>("Price");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colPrice.setPrefWidth(100);
        
        TableColumn<MenuItem, String> colCategory = new TableColumn<>("Category");
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colCategory.setPrefWidth(120);
        
        TableColumn<MenuItem, String> colDescription = new TableColumn<>("Description");
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDescription.setPrefWidth(250);
        
        tableView.getColumns().addAll(colId, colName, colPrice, colCategory, colDescription);
        
        
        // Click event - fill form with selected item
        tableView.setOnMouseClicked(e -> {
            MenuItem selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                fillFormWithItem(selected);
            }
        });
        
        // ============================================================
        // SORTING SECTION
        // ============================================================
        Label sortLabel = new Label("Sort by:");
        
        sortBox = new ComboBox<>();
        sortBox.getItems().addAll(
            "Price (Low → High)",
            "Price (High → Low)",
            "Name (A → Z)",
            "Name (Z → A)",
            "Category"
        );
        sortBox.setValue("Price (Low → High)");
        sortBox.setPrefWidth(200);
        
        Button sortButton = new Button("Sort");
        sortButton.setOnAction(e -> handleSortClick());
        
        timeLabel = new Label("Time: -");
        timeLabel.setFont(new Font("Arial", 12));
        
        HBox sortBoxContainer = new HBox(10);
        sortBoxContainer.setAlignment(Pos.CENTER);
        sortBoxContainer.setPadding(new Insets(10));
        sortBoxContainer.getChildren().addAll(sortLabel, sortBox, sortButton, timeLabel);
        
        // ============================================================
        // ASSEMBLE LAYOUT
        // ============================================================
        VBox centerSection = new VBox(10);
        centerSection.getChildren().addAll(formSection, tableView, sortBoxContainer);
        
        mainLayout.setTop(topBar);
        mainLayout.setCenter(centerSection);
        
        scene = new Scene(mainLayout, 800, 600);
        
        // Load initial data
        refreshTable(controller.getAllMenuItems());
    }
    
    // ============================================================
    // EVENT HANDLERS (Delegate to Controller)
    // ============================================================
    
    private void handleAddClick() {
        controller.handleAdd(
            idField.getText().trim(),
            nameField.getText().trim(),
            priceField.getText().trim(),
            categoryBox.getValue(),
            descField.getText().trim()
        );
    }
    
    private void handleUpdateClick() {
        MenuItem selected = tableView.getSelectionModel().getSelectedItem();
        controller.handleUpdate(
            selected,
            idField.getText().trim(),
            nameField.getText().trim(),
            priceField.getText().trim(),
            categoryBox.getValue(),
            descField.getText().trim()
        );
    }
    
    private void handleDeleteClick() {
        MenuItem selected = tableView.getSelectionModel().getSelectedItem();
        controller.handleDelete(selected);
    }
    
    private void handleSortClick() {
        controller.handleSort(sortBox.getValue());
    }
    
    // ============================================================
    // PUBLIC METHODS (Called by Controller)
    // ============================================================
    
    /**
     * Refresh table with given data
     */
    public void refreshTable(List<MenuItem> items) {
        tableView.getItems().setAll(items);
        timeLabel.setText("Time: -");
    }
    
    /**
     * Update table with sorted data and execution time
     */
    public void updateTableWithSortedData(List<MenuItem> sortedItems, String executionTime) {
        tableView.getItems().setAll(sortedItems);
        timeLabel.setText("Time: " + executionTime);
    }
    
    /**
     * Clear all input fields
     */
    public void clearFields() {
        idField.clear();
        nameField.clear();
        priceField.clear();
        descField.clear();
        categoryBox.setValue("Main Dish");
        tableView.getSelectionModel().clearSelection();
    }
    
    /**
     * Show alert dialog
     */
    public void showAlert(String title, String content, String alertType) {
        Alert.AlertType type;
        switch (alertType) {
            case "ERROR":
                type = Alert.AlertType.ERROR;
                break;
            case "WARNING":
                type = Alert.AlertType.WARNING;
                break;
            case "INFORMATION":
            default:
                type = Alert.AlertType.INFORMATION;
        }
        
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    /**
     * Show confirmation dialog
     */
    public boolean showConfirmation(String title, String content) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle(title);
        confirm.setHeaderText(null);
        confirm.setContentText(content);
        return confirm.showAndWait().get() == ButtonType.OK;
    }
    
    // ============================================================
    // PRIVATE HELPER METHODS
    // ============================================================
    
    private void fillFormWithItem(MenuItem item) {
        idField.setText(item.getId());
        nameField.setText(item.getName());
        priceField.setText(String.valueOf(item.getPrice()));
        categoryBox.setValue(item.getCategory());
        descField.setText(item.getDescription());
    }
    
    // ============================================================
    // PUBLIC ACCESSOR METHODS
    // ============================================================
    
    public Scene getScene() {
        return scene;
    }
    
    public void show() {
        refreshTable(controller.getAllMenuItems());
        stage.setScene(scene);
    }
}