// ============================================================
// File: DataManager.java
// Location: src/service/DataManager.java
// Purpose: Centralized storage for all shared data structures
// Responsibility: Only handles data storage, not business logic
// ============================================================

package service;

import model.*;
import adt.MenuHashTable;
import adt.OrderQueue;
import adt.MenuBST;
import java.util.*;

/**
 * Data Manager Class
 * Only responsible for declaring and storing all shared data structures
 * Specific business logic is handled by respective Service classes
 * 
 * Now uses generic ADT implementations:
 * - MenuHashTable wraps HashTable<String, MenuItem>
 * - OrderQueue wraps PriorityQueueADT<Order>
 */
public class DataManager {
    
    // ==================== Menu-Related Data Structures ====================
    
    /**
     * Menu Hash Table (Generic ADT)
     * Uses: HashTable<String, MenuItem>
     * Purpose: Fast item lookup (O(1))
     * Users: MenuService, CustomerOrderPage
     */
    public static MenuHashTable menuHashTable = new MenuHashTable();
    
    /**
     * Menu BST (implemented using BST)
     * Key: Price, Value: MenuItem
     * Purpose: Automatic sorting by price
     * Users: MenuService, MenuManagementPage
     */
    public static MenuBST menuBST = new MenuBST();
    
    // ==================== Order-Related Data Structures ====================
    
    /**
     * Order Queue (Generic ADT)
     * Uses: PriorityQueueADT<Order>
     * Purpose: Store and manage orders with priority
     * Features:
     * - FIFO for same priority orders
     * - Higher priority orders processed first
     * - VIP (3) > Delivery (2) > Normal (1)
     */
    public static OrderQueue orderQueue = new OrderQueue();
    
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
        System.out.println("=== Initializing DataManager ===");
        
        // Load initial menu
        InitialDataLoader.loadInitialMenu();
        
        System.out.println("DataManager initialization complete!");
        System.out.println("MenuHashTable size: " + menuHashTable.size());
        System.out.println("Order queue initialized: " + orderQueue.isEmpty());
        System.out.println("================================\n");
    }
    
    // ==================== Utility Methods ====================
    
    /**
     * Get order queue statistics
     */
    public static String getOrderQueueStatistics() {
        return orderQueue.getQueueStatistics();
    }
    
    /**
     * Print all orders in queue (for debugging)
     */
    public static void printOrderQueue() {
        orderQueue.printAllOrders();
    }
    
    /**
     * Print menu hash table (for debugging)
     */
    public static void printMenuHashTable() {
        menuHashTable.printAll();
    }
}