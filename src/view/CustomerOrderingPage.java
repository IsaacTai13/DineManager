package view;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.MenuItem;
import model.Order;
import model.OrderItem;
import controller.CustomerOrderingController;

/**
 * Customer Ordering Page - View Layer
 * This class only handles UI display and user interactions
 * Business logic is delegated to CustomerOrderingController
 */
public class CustomerOrderingPage {
    
    private Stage stage;
    private Scene scene;
    private Scene mainScene;
    
    // Controller - handles all business logic
    private CustomerOrderingController controller;
    
    // UI Components
    private TableView<MenuItem> menuTable;
    private TableView<OrderItem> cartTable;
    private TextField searchField;
    private Label totalLabel;
    private Button checkoutButton;
    
    // Constructor
    public CustomerOrderingPage(Stage stage, Scene mainScene) {
        this.stage = stage;
        this.mainScene = mainScene;
        this.controller = new CustomerOrderingController();
        initializeUI();
    }
    
    private void initializeUI() {
        BorderPane root = new BorderPane();
        
        // Top section
        Text title = new Text("Customer Ordering System");
        title.setFont(new Font("Arial", 28));
        
        Button backButton = new Button("⬅ Back to Home");
        backButton.setOnAction(e -> stage.setScene(mainScene));
        
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(15));
        
        HBox leftSection = new HBox();
        leftSection.setAlignment(Pos.CENTER_LEFT);
        leftSection.getChildren().add(backButton);
        
        HBox centerSection = new HBox();
        centerSection.setAlignment(Pos.CENTER);
        centerSection.getChildren().add(title);
        
        HBox.setHgrow(leftSection, Priority.NEVER);
        HBox.setHgrow(centerSection, Priority.ALWAYS);
        
        topBar.getChildren().addAll(leftSection, centerSection);
        root.setTop(topBar);
        
        // Center section - Split pane
        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPositions(0.6);
        
        VBox leftPanel = createMenuPanel();
        VBox rightPanel = createCartPanel();
        
        splitPane.getItems().addAll(leftPanel, rightPanel);
        root.setCenter(splitPane);
        
        scene = new Scene(root, 1000, 700);
    }
    
    
    // ============================================================
    // CREATE LEFT PANEL - Menu Display
    // ============================================================
    private VBox createMenuPanel() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(15));
        
        Label menuTitle = new Label("Menu Items");
        menuTitle.setFont(new Font("Arial", 20));
        
        // Search bar
        searchField = new TextField();
        searchField.setPromptText("Search for items...");
        searchField.setPrefWidth(300);
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            searchMenuItems(newVal);
        });
        
        // Menu TableView
        menuTable = new TableView<>();
        menuTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn<MenuItem, String> nameCol = new TableColumn<>("Item Name");
        nameCol.setPrefWidth(200);
        nameCol.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        
        TableColumn<MenuItem, String> priceCol = new TableColumn<>("Price");
        priceCol.setPrefWidth(100);
        priceCol.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getFormattedPrice()));
        
        TableColumn<MenuItem, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setPrefWidth(120);
        categoryCol.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getCategory()));
        
        menuTable.getColumns().addAll(nameCol, priceCol, categoryCol);
        
        // Load menu items from controller
        loadMenuItems();
        
        // Add to Cart button
        Button addToCartBtn = new Button("Add to Cart");
        addToCartBtn.setPrefWidth(150);
        addToCartBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        addToCartBtn.setOnAction(e -> handleAddToCart());
        
        panel.getChildren().addAll(menuTitle, searchField, menuTable, addToCartBtn);
        return panel;
    }
    
    
    // ============================================================
    // CREATE RIGHT PANEL - Shopping Cart
    // ============================================================
    private VBox createCartPanel() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(15));
        
        Label cartTitle = new Label("Shopping Cart");
        cartTitle.setFont(new Font("Arial", 20));
        
        // Cart TableView
        cartTable = new TableView<>();
        cartTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn<OrderItem, String> nameCol = new TableColumn<>("Item");
        nameCol.setPrefWidth(120);
        nameCol.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getMenuItem().getName()));
        
        // Quantity column with +/- buttons
        TableColumn<OrderItem, OrderItem> qtyCol = new TableColumn<>("Quantity");
        qtyCol.setPrefWidth(120);
        qtyCol.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue()));
        qtyCol.setCellFactory(col -> new TableCell<OrderItem, OrderItem>() {
            private final Button minusBtn = new Button("-");
            private final Label qtyLabel = new Label();
            private final Button plusBtn = new Button("+");
            private final HBox container = new HBox(5);
            
            {
                minusBtn.setStyle("-fx-background-color: #ff6b6b; -fx-text-fill: white; -fx-font-weight: bold;");
                plusBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
                minusBtn.setPrefWidth(30);
                plusBtn.setPrefWidth(30);
                qtyLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
                qtyLabel.setMinWidth(30);
                qtyLabel.setAlignment(Pos.CENTER);
                
                container.setAlignment(Pos.CENTER);
                container.getChildren().addAll(minusBtn, qtyLabel, plusBtn);
                
                minusBtn.setOnAction(e -> {
                    OrderItem item = getTableView().getItems().get(getIndex());
                    handleDecreaseQuantity(item);
                });
                
                plusBtn.setOnAction(e -> {
                    OrderItem item = getTableView().getItems().get(getIndex());
                    handleIncreaseQuantity(item);
                });
            }
            
            @Override
            protected void updateItem(OrderItem item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    qtyLabel.setText(String.valueOf(item.getQuantity()));
                    setGraphic(container);
                }
            }
        });
        
        TableColumn<OrderItem, String> subtotalCol = new TableColumn<>("Subtotal");
        subtotalCol.setPrefWidth(80);
        subtotalCol.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getFormattedSubtotal()));
        
        cartTable.getColumns().addAll(nameCol, qtyCol, subtotalCol);
        
        // Action buttons
        HBox actionButtons = new HBox(10);
        actionButtons.setAlignment(Pos.CENTER);
        
        Button removeBtn = new Button("Remove");
        removeBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        removeBtn.setOnAction(e -> handleRemoveItem());
        
        Button clearBtn = new Button("Clear Cart");
        clearBtn.setOnAction(e -> handleClearCart());
        
        actionButtons.getChildren().addAll(removeBtn, clearBtn);
        
        // Total price label
        totalLabel = new Label("Total: $0");
        totalLabel.setFont(new Font("Arial", 18));
        totalLabel.setStyle("-fx-font-weight: bold;");
        
        // Checkout button
        checkoutButton = new Button("Checkout");
        checkoutButton.setPrefWidth(200);
        checkoutButton.setPrefHeight(40);
        checkoutButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 16px;");
        checkoutButton.setOnAction(e -> handleCheckout());
        
        panel.getChildren().addAll(cartTitle, cartTable, actionButtons, totalLabel, checkoutButton);
        return panel;
    }
    
    
    // ============================================================
    // EVENT HANDLERS - Delegate to Controller
    // ============================================================
    
    /**
     * Load menu items from controller
     */
    private void loadMenuItems() {
        ObservableList<MenuItem> items = FXCollections.observableArrayList(
            controller.getAvailableMenuItems()
        );
        menuTable.setItems(items);
    }
    
    /**
     * Search menu items
     */
    private void searchMenuItems(String keyword) {
        ObservableList<MenuItem> items = FXCollections.observableArrayList(
            controller.searchMenuItems(keyword)
        );
        menuTable.setItems(items);
    }
    
    /**
     * Handle add to cart button click
     */
    private void handleAddToCart() {
        MenuItem selected = menuTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No Selection", "Please select an item from the menu.");
            return;
        }
        
        boolean success = controller.addToCart(selected);
        if (success) {
            updateCartDisplay();
        } else {
            showAlert("Unavailable", "This item is currently unavailable.");
        }
    }
    
    /**
     * Handle increase quantity
     */
    private void handleIncreaseQuantity(OrderItem item) {
        controller.increaseQuantity(item);
        updateCartDisplay();
    }
    
    /**
     * Handle decrease quantity
     */
    private void handleDecreaseQuantity(OrderItem item) {
        controller.decreaseQuantity(item);
        updateCartDisplay();
    }
    
    /**
     * Handle remove item from cart
     */
    private void handleRemoveItem() {
        OrderItem selected = cartTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No Selection", "Please select an item to remove.");
            return;
        }
        
        controller.removeFromCart(selected);
        updateCartDisplay();
    }
    
    /**
     * Handle clear cart
     */
    private void handleClearCart() {
        controller.clearCart();
        updateCartDisplay();
    }
    
    /**
     * Handle checkout
     */
    private void handleCheckout() {
        // Validate checkout
        String errorMessage = controller.validateCheckout();
        if (errorMessage != null) {
            showAlert("Checkout Error", errorMessage);
            return;
        }
        
        // Create order
        Order order = controller.checkout();
        
        if (order != null) {
            showAlert("Order Placed", 
                String.format("Order #%03d has been placed successfully!\nTotal: %s", 
                    order.getOrderNumber(), 
                    order.getFormattedTotalPrice()));
            updateCartDisplay();
        } else {
            showAlert("Checkout Failed", "Unable to process your order.");
        }
    }
    
    
    // ============================================================
    // UI UPDATE
    // ============================================================
    
    /**
     * Update cart display
     */
    private void updateCartDisplay() {
        ObservableList<OrderItem> observableCart = FXCollections.observableArrayList(
            controller.getCartItems()
        );
        cartTable.setItems(observableCart);
        cartTable.refresh();
        
        // Update total
        totalLabel.setText("Total: " + controller.getFormattedTotal());
    }
    
    
    // ============================================================
    // UTILITY
    // ============================================================
    
    /**
     * Show alert dialog
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Show this page
     */
    public void show() {
        stage.setScene(scene);
    }
}