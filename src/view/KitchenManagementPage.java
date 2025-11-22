package view;

import controller.KitchenManagementController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import model.Order;
import service.DataManager;
import service.ProcessedOrder;

import javax.xml.crypto.Data;
import java.awt.color.ProfileDataException;
import java.util.List;

public class KitchenManagementPage {

    private KitchenManagementController controller;
    private Order currentFocusedOrder;
    private static final int MAX_COOKING_ORDERS = 10;
    
    private Stage stage;
    private Scene scene;
    private Scene mainScene;
    private TableView<Order> cookingTable;      // Right side table
    private ScrollPane detailPane;               // Left side detail pane
    private VBox kitchenTop;
    private HBox buttonBar;
    private ObservableList<Order> tableData = FXCollections.observableArrayList();
    ListView<String> itemListView;

    // ===== UI components that need to be updated dynamically =====
    private Label waitingLabel;
    private Label valOrderNum;
    private Label valPriority;
    private Label valStatus;
    private Label valTime;
    private Label valTotal;

    private Button btnBack;
    private Button btnStart;
    private Button btnFinish;
    private Button btnCancel;


    // Constructor with stage and main scene reference
    public KitchenManagementPage(Stage stage, Scene mainScene) {
        controller = new KitchenManagementController();
        this.stage = stage;
        this.mainScene = mainScene;
        initializeUI();
    }
    
    private void initializeUI() {
        // Layout setup
        setTopPane();
        setLeftDetailPane();
        setRightTable();
        setBottomBar();

        BorderPane root = new BorderPane();
        root.setTop(kitchenTop);
        root.setLeft(detailPane);
        root.setCenter(cookingTable);
        root.setBottom(buttonBar);

        scene = new Scene(root, 800, 600);
        setupButtonActions();
    }

    /**
     * Loads the initial order to display when entering the Kitchen Management page.
     *
     * <p>Priority:</P>
     * <ol>
     *     <li>If there is any COOKING order → show the first cooking order.</li>
     *     <li>Otherwise, if there is a WAITING order → preview the next waiting order.</li>
     *     <li>If both lists are empty → clear the detail panel.</li>
     * </ol>
     *
     * This ensures the user always sees the most relevant order when opening the page.
     */
    public void loadInitialOrder() {
        Order cooking = DataManager.processedOrder.findNextCookingOrder();
        if (cooking != null) {
            loadOrderDetails(cooking);
            return;
        }

        // no cooking → preview first waiting
        Order next = controller.previewNextOrder();

        if (next != null) {
            loadOrderDetails(next);
            return;
        }

        clearDetailPanel();
    }

    private void loadOrderDetails(Order order) {
        loadOrderDetails(order, "");
    }

    /**
     * Updates the enabled/disabled state and color style of the action buttons
     * (Start, Finish, Cancel) based on the current order being displayed and the
     * overall kitchen state.
     *
     * <p>This method evaluates three scenarios:</p>
     *
     * <ul>
     *   <li><b>No orders at all</b>
     *       <br>If both waiting and cooking lists are empty, all buttons are disabled
     *       and displayed in grey. This means the kitchen has nothing to process.</li>
     *
     *   <li><b>Viewing a WAITING order</b>
     *       <br>• “Start Next Order” is enabled only if:
     *       (a) there is at least one waiting order, and
     *       (b) cooking orders have not reached the maximum limit.
     *       <br>• “Finish” is disabled because waiting orders cannot be finished.
     *       <br>• “Cancel” is enabled for waiting orders.</li>
     *
     *   <li><b>Viewing a COOKING order</b>
     *       <br>• “Start Next Order” follows the same rule as above (depends on waiting count + cooking limit).
     *       <br>• “Finish” is enabled because cooking orders can be completed.
     *       <br>• “Cancel” is still enabled because cooking orders may be cancelled.</li>
     * </ul>
     *
     * @param order the currently focused order, or {@code null} if no order is selected.
     */
    private void loadOrderDetails(Order order, String msg) {
        currentFocusedOrder = order;

        if (order == null) {
            showAlert(msg, Alert.AlertType.INFORMATION);
            clearDetailPanel();
            updateButtonStates(null);
            updateWaitingLabel();
            refreshTable();
            return;
        }

        valOrderNum.setText(order.getOrderNumber() + "");
        valPriority.setText(order.getPriorityText());
        valStatus.setText(order.getStatus());
        valTime.setText(order.getTimestamp().toString());
        valTotal.setText("$" + order.getTotalPrice());

        itemListView.getItems().clear();
        order.getItems().forEach(item -> {
            itemListView.getItems().add(
                String.format(
                    "%s   $%.2f   x%d",
                    item.getMenuItem().getName(),
                    item.getMenuItem().getPrice(),
                    item.getQuantity()
                ));
        });

        updateButtonStates(currentFocusedOrder);
        updateWaitingLabel();
        refreshTable();
    }

    public void setTopPane() {
        // Top bar container
        BorderPane topBar = new BorderPane();
        topBar.setPadding(new Insets(15, 20, 15, 20));

        /* --- Back Button at Top Left --- */
        btnBack = new Button("← Back");
        btnBack.setStyle("-fx-font-size: 14;");
        topBar.setLeft(btnBack);

        /* --- Title --- */
        Text kitchenTitle = new Text("Kitchen Management");
        kitchenTitle.setFont(new Font("Arial", 26));
        topBar.setCenter(kitchenTitle);
        BorderPane.setAlignment(kitchenTitle, Pos.CENTER);

        /* --- Waiting Orders label (right side) --- */
        waitingLabel = new Label();
        waitingLabel.setStyle("-fx-background-color: #ffcc00; -fx-padding: 6 12; -fx-background-radius: 8;");
        waitingLabel.setFont(new Font(16));
        updateWaitingLabel();
        topBar.setRight(waitingLabel);

        kitchenTop = new VBox(topBar);
    }

    public void setLeftDetailPane() {
        VBox contentBox = new VBox(15);
        contentBox.setPadding(new Insets(15));
        contentBox.setPrefWidth(350);
        contentBox.setStyle("-fx-border-color: black; -fx-padding: 10;");

        // Title
        Label detailTitle = new Label("Order Detail");
        detailTitle.setFont(new Font("Arial", 20));

        // GridPane for basic info
        GridPane detailGrid = new GridPane();
        detailGrid.setVgap(8);
        detailGrid.setHgap(10);

        Label lblOrderNum = new Label("Order Number:");
        Label lblPriority = new Label("Priority:");
        Label lblStatus = new Label("Status:");
        Label lblTime = new Label("Time:");
        Label lblTotal = new Label("Total Price:");

        valOrderNum = new Label("-");
        valPriority = new Label("-");
        valStatus = new Label("-");
        valTime = new Label("-");
        valTotal = new Label("-");

        // Add to grid
        detailGrid.add(lblOrderNum, 0, 0);
        detailGrid.add(valOrderNum, 1, 0);

        detailGrid.add(lblPriority, 0, 1);
        detailGrid.add(valPriority, 1, 1);

        detailGrid.add(lblStatus, 0, 2);
        detailGrid.add(valStatus, 1, 2);

        detailGrid.add(lblTime, 0, 3);
        detailGrid.add(valTime, 1, 3);

        detailGrid.add(lblTotal, 0, 4);
        detailGrid.add(valTotal, 1, 4);

        // ListView for items
        Label lblItems = new Label("Items:");
        itemListView = new ListView<>();
        itemListView.setPrefHeight(200);

        // Add everything into detailPane
        contentBox.getChildren().addAll(detailTitle, detailGrid, lblItems, itemListView);

        detailPane = new ScrollPane(contentBox);
        detailPane.setFitToWidth(true);  // Make scroll pane fit the width of content
        detailPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // No horizontal scroll
        detailPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Vertical scroll as needed
        detailPane.setFitToHeight(true); // Make scroll pane fit the height of content
    }

    private void setRightTable() {
        cookingTable = new TableView<>();

        // Define Columns
        TableColumn<Order, Integer> colOrderNum = new TableColumn<>("OrderNumber");
        TableColumn<Order, String> colPriority = new TableColumn<>("Priority");
        TableColumn<Order, String>  colStatus = new TableColumn<>("Status");
        TableColumn<Order, Integer> colItemCount = new TableColumn<>("Items");

        // Set Cell Value Factories (Tell columns which property to use)
        colOrderNum.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
        colPriority.setCellValueFactory(new PropertyValueFactory<>("priorityText"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colItemCount.setCellValueFactory(new PropertyValueFactory<>("itemCount"));

        // Set Table Data (This give the ability to show dynamic data)
        tableData.setAll(DataManager.processedOrder.getProcessedOrders());
        cookingTable.setItems(tableData);

        // Add Columns to Table
        cookingTable.getColumns().addAll(colOrderNum, colPriority, colStatus, colItemCount);

        // Make columns auto-resize / auto-spread evenly
        cookingTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colOrderNum.setMinWidth(100);
        colPriority.setMinWidth(150);
        colStatus.setMinWidth(100);
        colItemCount.setMinWidth(50);

        setupTableSelectionHandler();
        // prevent default selection on the first row
        cookingTable.getSelectionModel().clearSelection();
    }

    private void setBottomBar() {
        btnStart  = new Button("Start Next Order");
        btnFinish = new Button("Finish");
        btnCancel = new Button("Cancel");

        buttonBar = new HBox(30, btnStart, btnFinish, btnCancel);
        buttonBar.setPadding(new Insets(15));
        buttonBar.setAlignment(Pos.CENTER);
    }

    /**
     * Update the waiting orders label with the current count.
     */
    private void updateWaitingLabel() {
        int waitingCount = DataManager.orderQueue.size();
        waitingLabel.setText("Waiting Orders: " + waitingCount);
    }

    /**
     * Safely refreshes the TableView's data without causing index mapping errors.
     *
     * <p>JavaFX TableView maintains internal row-to-index mappings for selection and sorting.
     * Replacing the entire ObservableList at once (e.g., using setAll()) may cause the
     * TableView to reference stale indexes, leading to ArrayIndexOutOfBoundsException.</p>
     *
     * <p>This method avoids the issue by:</p>
     * <ul>
     *   <li>Clearing the current selection to prevent the TableView from resolving old indexes</li>
     *   <li>Clearing the list incrementally instead of replacing it</li>
     *   <li>Adding new items in a controlled manner so TableView updates mappings safely</li>
     * </ul>
     */
    private void refreshTable() {
        List<Order> updated = DataManager.processedOrder.getProcessedOrders();

        tableData.clear(); // safe reset
        tableData.addAll(updated); // rebuild with new data
    }

    private void setupButtonActions() {
        btnBack.setOnAction(e -> stage.setScene(mainScene));
        btnStart.setOnAction(e -> handleStart());
        btnFinish.setOnAction(e -> handleFinish());
        btnCancel.setOnAction(e -> handleCancel());
    }

    /**
     * Handles the "Start Next Order" button action.
     * Starts the next waiting order if there is if there are any remaining orders to process
     * If yes, change their status to COOKING and store in processedOrder
     */
    private void handleStart() {
        Order next = controller.startNextOrder();
        updateWaitingLabel();
        refreshTable();

        loadOrderDetails(next, "No pending orders.");
    }

    /**
     * Handles the "Finish" button action.
     * Marks the currently focused order as DONE and updates the UI accordingly.
     * Leads to the first cooking order in the list if available.
     * if no more cooking orders, preview the next waiting order.
     */
    private void handleFinish() {
        Order next = controller.finishOrder(currentFocusedOrder);
        updateWaitingLabel();
        refreshTable();

        // After finishing current order, lead the next cooking order if available
        loadOrderDetails(next, "All orders finished!");
    }

    /**
     * Handles the "Cancel" button action.
     * Marks the currently focused order as CANCELLED and updates the UI accordingly.
     * Leads to the first cooking order in the list if available.
     * if no more cooking orders, preview the next waiting order.
     */
    private void handleCancel() {
        Order next = controller.cancelOrder(currentFocusedOrder);
        refreshTable();
        updateWaitingLabel();

        // After cancelling current order, lead the next cooking order if available
        loadOrderDetails(next, "All orders finished!");
    }

    private void clearDetailPanel() {
        valOrderNum.setText("-");
        valPriority.setText("-");
        valStatus.setText("-");
        valTime.setText("-");
        valTotal.setText("-");
        itemListView.getItems().clear();
        currentFocusedOrder = null;
    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(type.toString());
        alert.setHeaderText(null); // No header
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Updates the enabled/disabled state and visual style of the action buttons
     * (Start, Finish, Cancel) based on the currently focused order and global
     * queue status.
     *
     * <p>This method considers three major scenarios:</p>
     *
     * <h3>1. No orders at all (no waiting, no cooking)</h3>
     * <ul>
     *   <li>There is no focused order (order == null)</li>
     *   <li>No order in waiting queue</li>
     *   <li>No order currently cooking</li>
     * </ul>
     * → All buttons are disabled because there is nothing to operate on.
     *
     * <h3>2. The focused order is a WAITING order</h3>
     * <ul>
     *   <li>"Start" is enabled only if there are waiting orders AND cooking slots are not full</li>
     *   <li>"Finish" is always disabled (waiting orders cannot be finished)</li>
     *   <li>"Cancel" is enabled because waiting orders can be cancelled</li>
     * </ul>
     *
     * <h3>3. The focused order is a COOKING order</h3>
     * <ul>
     *   <li>"Start" is enabled if new orders are waiting AND cooking slots are not full</li>
     *   <li>"Finish" is enabled for cooking orders</li>
     *   <li>"Cancel" is enabled to allow cancelling a cooking order</li>
     * </ul>
     *
     * <p>Button colors are also updated to reflect their enabled/disabled state.</p>
     *
     * @param order The currently focused order in the detail panel; may be null when no orders exist.
     */
    private void updateButtonStates(Order order) {



        // Case 1: No waiting or cooking orders
        if (order == null) {
            setStartBtnState();
            btnFinish.setDisable(true);
            btnCancel.setDisable(true);

            setStyle(btnFinish, BtnStyle.GREY);
            setStyle(btnCancel, BtnStyle.GREY);
            return;
        }

        // Case 2: There are waiting order but no cooking order
        if (order != null && Order.STATUS_WAITING.equals(order.getStatus())) {
            setStartBtnState();

            // Finish disabled
            btnFinish.setDisable(true);
            setStyle(btnFinish, BtnStyle.GREY);

            // Waiting order can be cancelled
            btnCancel.setDisable(false);
            setStyle(btnCancel, BtnStyle.RED);
            return;
        }

        // Case 3: There is a cooking order
        if (order != null && Order.STATUS_COOKING.equals(order.getStatus())) {
            setStartBtnState();

            // Finish disabled
            btnFinish.setDisable(false);
            setStyle(btnFinish, BtnStyle.GREEN);

            // Waiting order can be cancelled
            btnCancel.setDisable(false);
            setStyle(btnCancel, BtnStyle.RED);
        }

        // Case 4: Other statuses (DONE, CANCELLED) - disable all buttons
        if (order != null &&
            (Order.STATUS_DONE.equals(order.getStatus()) ||
             Order.STATUS_CANCELLED.equals(order.getStatus()))) {

            setStartBtnState();
            btnFinish.setDisable(true);
            btnCancel.setDisable(true);
            setStyle(btnFinish, BtnStyle.GREY);
            setStyle(btnCancel, BtnStyle.GREY);
        }
    }

    private void setStartBtnState() {
        int waitingCount = DataManager.orderQueue.size();
        int cookingCount = DataManager.processedOrder.getCountCooking();

        if (waitingCount > 0 && cookingCount < MAX_COOKING_ORDERS) {
            btnStart.setDisable(false);
            setStyle(btnStart, BtnStyle.BLUE);
        } else {
            btnStart.setDisable(true);
            setStyle(btnStart, BtnStyle.GREY);
        }
    }

    /**
     * Sets up a listener for selection changes in the cooking orders table.
     * When a new order is selected, it updates the detail panel to show
     */
    private void setupTableSelectionHandler() {
        cookingTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                currentFocusedOrder = newSel;
                loadOrderDetails(currentFocusedOrder);
            }
        });
    }

    /* ===== Button Color Helpers ===== */
    private enum BtnStyle {
        BLUE, GREEN, RED, GREY
    }

    private void setStyle(Button btn, BtnStyle style) {
        switch (style) {
            case BLUE  -> btn.setStyle("-fx-background-color: #4ea3ff; -fx-text-fill: white;");
            case GREEN -> btn.setStyle("-fx-background-color: #3cb371; -fx-text-fill: white;");
            case RED   -> btn.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white;");
            case GREY  -> btn.setStyle("-fx-background-color: #cccccc; -fx-text-fill: #666;");
        }
    }

    // Method to get the scene
    public Scene getScene() {
        return scene;
    }
    
    // Method to show this page
    public void show() {
        stage.setScene(scene);

        loadInitialOrder();
    }
}