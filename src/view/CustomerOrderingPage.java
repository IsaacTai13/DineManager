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
import java.util.ArrayList;
import java.util.List;

public class CustomerOrderingPage {
    
    private Stage stage;
    private Scene scene;
    private Scene mainScene;
    
    // Cart data structure - List to store cart items
    private List<CartItem> cartItems;
    
    // UI Components
    private TableView<MenuItem> menuTable;
    private TableView<CartItem> cartTable;
    private TextField searchField;
    private Label totalLabel;
    private Button checkoutButton;
    
    // Constructor with stage and main scene reference
    public CustomerOrderingPage(Stage stage, Scene mainScene) {
        this.stage = stage;
        this.mainScene = mainScene;
        this.cartItems = new ArrayList<>();
        initializeUI();
    }
    
    private void initializeUI() {
        // ============================================================
        // MAIN LAYOUT - BorderPane
        // ============================================================
        BorderPane root = new BorderPane();
        
        
        // ============================================================
        // TOP SECTION - Title and Back Button
        // ============================================================
        Text title = new Text("Customer Ordering System");
        title.setFont(new Font("Arial", 28));
        
        Button backButton = new Button("⬅ Back to Home");
        backButton.setOnAction(e -> stage.setScene(mainScene));
        
        // Create top bar with back button on the left, title in center
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(15));
        
        // Left section - Back button
        HBox leftSection = new HBox();
        leftSection.setAlignment(Pos.CENTER_LEFT);
        leftSection.getChildren().add(backButton);
        
        // Center section - Title
        HBox centerSection = new HBox();
        centerSection.setAlignment(Pos.CENTER);
        centerSection.getChildren().add(title);
        
        // Use HBox.setHgrow to make sections expand properly
        HBox.setHgrow(leftSection, Priority.NEVER);
        HBox.setHgrow(centerSection, Priority.ALWAYS);
        
        topBar.getChildren().addAll(leftSection, centerSection);
        
        root.setTop(topBar);
        
        
        // ============================================================
        // CENTER SECTION - Split Pane (Left: Menu, Right: Cart)
        // ============================================================
        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPositions(0.6); // 60% left, 40% right
        
        
        // ------------------------------------------------------------
        // LEFT SIDE - Menu Display
        // ------------------------------------------------------------
        VBox leftPanel = createMenuPanel();
        
        
        // ------------------------------------------------------------
        // RIGHT SIDE - Shopping Cart
        // ------------------------------------------------------------
        VBox rightPanel = createCartPanel();
        
        
        splitPane.getItems().addAll(leftPanel, rightPanel);
        root.setCenter(splitPane);
        
        
        // ============================================================
        // CREATE SCENE
        // ============================================================
        scene = new Scene(root, 1000, 700);
    }
    
    
    // ============================================================
    // CREATE LEFT PANEL - Menu Display
    // ============================================================
    private VBox createMenuPanel() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(15));
        
        // Title
        Label menuTitle = new Label("Menu Items");
        menuTitle.setFont(new Font("Arial", 20));
        
        // Search bar (optional)
        searchField = new TextField();
        searchField.setPromptText("Search for items...");
        searchField.setPrefWidth(300);
        
        // Menu TableView
        menuTable = new TableView<>();
        menuTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        // Column 1: Item Name
        TableColumn<MenuItem, String> nameCol = new TableColumn<>("Item Name");
        nameCol.setPrefWidth(200);
        nameCol.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        
        // Column 2: Price
        TableColumn<MenuItem, String> priceCol = new TableColumn<>("Price");
        priceCol.setPrefWidth(100);
        priceCol.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty("$" + data.getValue().getPrice()));
        
        // Column 3: Category
        TableColumn<MenuItem, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setPrefWidth(120);
        categoryCol.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getCategory()));
        
        menuTable.getColumns().addAll(nameCol, priceCol, categoryCol);
        
        // Load sample menu items (you can replace with real data)
        loadSampleMenuItems();
        
        // Add to Cart button
        Button addToCartBtn = new Button("Add to Cart");
        addToCartBtn.setPrefWidth(150);
        addToCartBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        addToCartBtn.setOnAction(e -> addSelectedItemToCart());
        
        panel.getChildren().addAll(menuTitle, searchField, menuTable, addToCartBtn);
        return panel;
    }
    
    
    // ============================================================
    // CREATE RIGHT PANEL - Shopping Cart
    // ============================================================
    private VBox createCartPanel() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(15));
        
        // Title
        Label cartTitle = new Label("Shopping Cart");
        cartTitle.setFont(new Font("Arial", 20));
        
        // Cart TableView
        cartTable = new TableView<>();
        cartTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        // Column 1: Item Name
        TableColumn<CartItem, String> nameCol = new TableColumn<>("Item");
        nameCol.setPrefWidth(150);
        nameCol.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        
        // Column 2: Quantity
        TableColumn<CartItem, String> qtyCol = new TableColumn<>("Qty");
        qtyCol.setPrefWidth(50);
        qtyCol.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().getQuantity())));
        
        // Column 3: Subtotal
        TableColumn<CartItem, String> subtotalCol = new TableColumn<>("Subtotal");
        subtotalCol.setPrefWidth(80);
        subtotalCol.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty("$" + data.getValue().getSubtotal()));
        
        cartTable.getColumns().addAll(nameCol, qtyCol, subtotalCol);
        
        // Action buttons
        HBox actionButtons = new HBox(10);
        actionButtons.setAlignment(Pos.CENTER);
        
        Button removeBtn = new Button("Remove");
        removeBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        removeBtn.setOnAction(e -> removeSelectedItem());
        
        Button clearBtn = new Button("Clear Cart");
        clearBtn.setOnAction(e -> clearCart());
        
        actionButtons.getChildren().addAll(removeBtn, clearBtn);
        
        // Total price label
        totalLabel = new Label("Total: $0.00");
        totalLabel.setFont(new Font("Arial", 18));
        totalLabel.setStyle("-fx-font-weight: bold;");
        
        // Checkout button
        checkoutButton = new Button("Checkout");
        checkoutButton.setPrefWidth(200);
        checkoutButton.setPrefHeight(40);
        checkoutButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 16px;");
        checkoutButton.setOnAction(e -> checkout());
        
        panel.getChildren().addAll(cartTitle, cartTable, actionButtons, totalLabel, checkoutButton);
        return panel;
    }
    
    
    // ============================================================
    // LOAD SAMPLE MENU ITEMS (Replace with real data later)
    // ============================================================
    private void loadSampleMenuItems() {
        ObservableList<MenuItem> items = FXCollections.observableArrayList(
            new MenuItem("Burger", 8.99, "Main"),
            new MenuItem("Pizza", 12.99, "Main"),
            new MenuItem("Pasta", 10.99, "Main"),
            new MenuItem("Salad", 6.99, "Appetizer"),
            new MenuItem("French Fries", 3.99, "Side"),
            new MenuItem("Coke", 2.50, "Drink"),
            new MenuItem("Ice Cream", 4.99, "Dessert")
        );
        menuTable.setItems(items);
    }
    
    
    // ============================================================
    // ADD SELECTED ITEM TO CART
    // ============================================================
    private void addSelectedItemToCart() {
        MenuItem selected = menuTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No Selection", "Please select an item from the menu.");
            return;
        }
        
        // Check if item already in cart
        boolean found = false;
        for (CartItem item : cartItems) {
            if (item.getName().equals(selected.getName())) {
                item.increaseQuantity();
                found = true;
                break;
            }
        }
        
        // If not found, add new item to cart
        if (!found) {
            cartItems.add(new CartItem(selected.getName(), selected.getPrice(), 1));
        }
        
        updateCartDisplay();
    }
    
    
    // ============================================================
    // REMOVE SELECTED ITEM FROM CART
    // ============================================================
    private void removeSelectedItem() {
        CartItem selected = cartTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No Selection", "Please select an item to remove.");
            return;
        }
        
        cartItems.remove(selected);
        updateCartDisplay();
    }
    
    
    // ============================================================
    // CLEAR ALL ITEMS FROM CART
    // ============================================================
    private void clearCart() {
        cartItems.clear();
        updateCartDisplay();
    }
    
    
    // ============================================================
    // UPDATE CART DISPLAY
    // ============================================================
    private void updateCartDisplay() {
        ObservableList<CartItem> observableCart = FXCollections.observableArrayList(cartItems);
        cartTable.setItems(observableCart);
        
        // Calculate total
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getSubtotal();
        }
        totalLabel.setText(String.format("Total: $%.2f", total));
    }
    
    
    // ============================================================
    // CHECKOUT ACTION
    // ============================================================
    private void checkout() {
        if (cartItems.isEmpty()) {
            showAlert("Empty Cart", "Your cart is empty. Please add items before checkout.");
            return;
        }
        
        // TODO: Implement actual checkout logic (send order to kitchen)
        showAlert("Order Placed", "Order #001 has been sent to the kitchen!");
        clearCart();
    }
    
    
    // ============================================================
    // UTILITY METHOD - Show Alert Dialog
    // ============================================================
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    
    // ============================================================
    // SHOW PAGE
    // ============================================================
    public void show() {
        stage.setScene(scene);
    }
    
    
    // ============================================================
    // INNER CLASSES - Data Models
    // ============================================================
    
    // MenuItem class for menu display
    public static class MenuItem {
        private String name;
        private double price;
        private String category;
        
        public MenuItem(String name, double price, String category) {
            this.name = name;
            this.price = price;
            this.category = category;
        }
        
        public String getName() { return name; }
        public double getPrice() { return price; }
        public String getCategory() { return category; }
    }
    
    
    // CartItem class for shopping cart
    public static class CartItem {
        private String name;
        private double price;
        private int quantity;
        
        public CartItem(String name, double price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }
        
        public String getName() { return name; }
        public double getPrice() { return price; }
        public int getQuantity() { return quantity; }
        public double getSubtotal() { return price * quantity; }
        
        public void increaseQuantity() { quantity++; }
        public void decreaseQuantity() { if (quantity > 1) quantity--; }
    }
}