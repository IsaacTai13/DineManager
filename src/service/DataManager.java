// ============================================================
// File: DataManager.java
// Location: src/service/DataManager.java
// Purpose: Centralized storage for all shared data structures
// Responsibility: Only handles data storage, not business logic
// ============================================================

package service;

import model.*;
import java.util.*;

/**
 * Data Manager Class (Simplified Version)
 * Only responsible for declaring and storing all shared data structures
 * Specific business logic is handled by respective Service classes
 */
public class DataManager {
    
    // ==================== Menu-Related Data Structures ====================
    
    /**
     * Menu HashMap
     * Key: Menu item ID, Value: MenuItem
     * Purpose: Fast item lookup (O(1))
     * Users: MenuService, CustomerOrderPage
     */
    public static HashMap<String, MenuItem> menuHashMap = new HashMap<>();
    
    /**
     * Menu BST (implemented using TreeMap)
     * Key: Price, Value: MenuItem
     * Purpose: Automatic sorting by price
     * Users: MenuService, MenuManagementPage
     */
    public static TreeMap<Double, MenuItem> menuBST = new TreeMap<>();
    
    // ==================== Order-Related Data Structures ====================
    
//    /**
//     * Order Queue (regular Queue)
//     * Purpose: FIFO order storage
//     * Users: OrderService, CustomerOrderPage → KitchenPage
//     */
//    public static Queue<Order> orderQueue = new LinkedList<>();
    
    /**
     * Order Priority Queue
     * Purpose: Sort orders by priority and time
     * Users: OrderService, KitchenPage
     */
    public static PriorityQueue<Order> orderPriorityQueue = new PriorityQueue<>();
    
    
    /**
     * Order Number Counter
     * Automatically increments when creating new orders
     */
    public static int orderCounter = 1;
    
    // ==================== Initialization Method ====================
    
    /**
     * Initialize system
     * Load initial menu data
     */
    public static void initialize() {
        System.out.println("Initializing DataManager...");
        
        // Load initial menu
        InitialDataLoader.loadInitialMenu();
        
        System.out.println("DataManager initialization complete!");
        System.out.println("Current menu count: " + menuHashMap.size());
    }
}