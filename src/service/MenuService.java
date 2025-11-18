// ============================================================
// File: MenuService.java
// Location: src/service/MenuService.java
// Purpose: Handle all menu-related business logic
// Updated: Now uses generic MenuHashTable ADT
// ============================================================

package service;

import model.MenuItem;
import java.util.*;

/**
 * Menu Service Class
 * Provides all menu-related operations
 * Includes: add, delete, update, query, search, categorize functions
 * 
 * Updated to use MenuHashTable ADT (generic hash table wrapper)
 */
public class MenuService {
    
    // ==================== Basic CRUD Operations ====================
    
    /**
     * Add menu item
     * Updates both MenuHashTable ADT and BST
     * 
     * @param item Menu item to add
     * @return Whether addition was successful
     */
    public static boolean addMenuItem(MenuItem item) {
        if (item == null) {
            System.err.println("Error: Menu item cannot be null");
            return false;
        }
        
        // Check for duplicate ID using MenuHashTable ADT
        if (DataManager.menuHashTable.containsKey(item.getId())) {
            System.err.println("Error: Menu item ID already exists: " + item.getId());
            return false;
        }
        
        // Add to both MenuHashTable ADT and BST
        DataManager.menuHashTable.put(item.getId(), item);  // Hash Table ADT
        DataManager.menuBST.insert(item);     // BST for price sorting
        
        System.out.println("Successfully added menu item: " + item.getName());
        return true;
    }
    
    /**
     * Remove menu item
     * Removes from both MenuHashTable ADT and BST
     * 
     * @param id Menu item ID to remove
     * @return Whether removal was successful
     */
    public static boolean removeMenuItem(String id) {
        // Use MenuHashTable ADT to get the item
        MenuItem item = DataManager.menuHashTable.get(id);
        
        if (item == null) {
            System.err.println("Error: Menu item ID not found: " + id);
            return false;
        }
        
        // Remove from both data structures
        DataManager.menuHashTable.remove(id);
        DataManager.menuBST.delete(item);
        
        System.out.println("Successfully removed menu item: " + item.getName());
        return true;
    }
    
    /**
     * Update menu item
     * Removes old item and adds new one
     * 
     * @param id Menu item ID to update
     * @param newItem New menu item data
     * @return Whether update was successful
     */
    public static boolean updateMenuItem(String id, MenuItem newItem) {
        if (!DataManager.menuHashTable.containsKey(id)) {
            System.err.println("Error: Menu item ID not found: " + id);
            return false;
        }
        
        // Remove old item
        removeMenuItem(id);
        
        // Add new item (with new ID)
        return addMenuItem(newItem);
    }
    
    // ==================== Query Methods ====================
    
    /**
     * Get menu item by ID
     * 
     * @param id Menu item ID
     * @return Found menu item, null if not found
     */
    public static MenuItem getMenuItemById(String id) {
        return DataManager.menuHashTable.get(id);
    }
    
    /**
     * Get all menu items (no specific order)
     * 
     * @return List of all menu items
     */
    public static List<MenuItem> getAllMenuItems() {
        return DataManager.menuHashTable.getAllItems();
    }
    
    /**
     * Get menu sorted by price (using BST)
     * 
     * @return List of menu items sorted by price
     */
    public static List<MenuItem> getMenuByPrice() {
    	return DataManager.menuBST.getAllByPrice();
    }
    
    /**
     * Get menu sorted by price (descending)
     * 
     * @return List of menu items sorted by price in descending order
     */
    public static List<MenuItem> getMenuByPriceDescending() {
    	return DataManager.menuBST.getAllByPriceDescending();
    }
    
    // ==================== Search Methods ====================
    
    /**
     * Get menu items by category
     * Uses MenuHashTable's built-in category filtering
     * 
     * @param category Category name (Main Dish/Beverage/Dessert)
     * @return List of all menu items in the specified category
     */
    public static List<MenuItem> getMenuByCategory(String category) {
        // Use MenuHashTable's built-in method
        return DataManager.menuHashTable.getItemsByCategory(category);
    }
    
    /**
     * Search menu items by name (supports partial matching)
     * 
     * @param keyword Search keyword
     * @return List of matching menu items
     */
    public static List<MenuItem> searchMenuByName(String keyword) {
        List<MenuItem> result = new ArrayList<>();
        
        if (keyword == null || keyword.trim().isEmpty()) {
            return result;
        }
        
        String lowerKeyword = keyword.toLowerCase().trim();
        
        for (MenuItem item : DataManager.menuHashTable.getAllItems()) {
            if (item.getName().toLowerCase().contains(lowerKeyword)) {
                result.add(item);
            }
        }
        
        return result;
    }
    
    /**
     * Query menu items by price range
     * 
     * @param minPrice Minimum price
     * @param maxPrice Maximum price
     * @return List of menu items within the price range
     */
    public static List<MenuItem> getMenuByPriceRange(double minPrice, double maxPrice) {
    	return DataManager.menuBST.rangeSearch(minPrice, maxPrice);
    }
    
    // ==================== Statistics Methods ====================
    
    /**
     * Get total menu item count
     * 
     * @return Total number of menu items
     */
    public static int getMenuCount() {
        return DataManager.menuHashTable.size();
    }
    
    /**
     * Get menu item count by category
     * 
     * @param category Category name
     * @return Number of menu items in the specified category
     */
    public static int getMenuCountByCategory(String category) {
        return getMenuByCategory(category).size();
    }
    
    /**
     * Get the cheapest menu item
     * 
     * @return The cheapest menu item, null if no items exist
     */
    public static MenuItem getCheapestItem() {
    	return DataManager.menuBST.findMin();
    }
    
    /**
     * Get the most expensive menu item
     * 
     * @return The most expensive menu item, null if no items exist
     */
    public static MenuItem getMostExpensiveItem() {
    	return DataManager.menuBST.findMax();
    }
    
    /**
     * Calculate average price
     * 
     * @return Average price of all menu items
     */
    public static double getAveragePrice() {
        if (DataManager.menuHashTable.isEmpty()) {
            return 0;
        }
        
        double total = 0;
        for (MenuItem item : DataManager.menuHashTable.getAllItems()) {
            total += item.getPrice();
        }
        
        return total / DataManager.menuHashTable.size();
    }
    
    // ==================== Validation Methods ====================
    
    /**
     * Check if menu item name is duplicate
     * 
     * @param name Name to check
     * @return true if name already exists
     */
    public static boolean isNameDuplicate(String name) {
        for (MenuItem item : DataManager.menuHashTable.getAllItems()) {
            if (item.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Check if menu item name is duplicate (excluding specific ID)
     * Used for validation during updates
     * 
     * @param name Name to check
     * @param excludeId Menu item ID to exclude
     * @return true if name already exists (after excluding specified ID)
     */
    public static boolean isNameDuplicate(String name, String excludeId) {
        for (MenuItem item : DataManager.menuHashTable.getAllItems()) {
            if (!item.getId().equals(excludeId) && item.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Validate if menu item data is valid
     * 
     * @param item Menu item to validate
     * @return Validation result message (empty string indicates validation passed)
     */
    public static String validateMenuItem(MenuItem item) {
        if (item == null) {
            return "Menu item cannot be null";
        }
        
        if (item.getId() == null || item.getId().trim().isEmpty()) {
            return "Menu item ID cannot be empty";
        }
        
        if (item.getName() == null || item.getName().trim().isEmpty()) {
            return "Menu item name cannot be empty";
        }
        
        if (item.getPrice() <= 0) {
            return "Price must be greater than 0";
        }
        
        if (item.getCategory() == null || item.getCategory().trim().isEmpty()) {
            return "Category cannot be empty";
        }
        
        return "";  // Validation passed
    }
    
    // ==================== Helper Methods ====================
    
    /**
     * Get all categories
     * 
     * @return List of all unique categories
     */
    public static List<String> getAllCategories() {
        Set<String> categories = new HashSet<>();
        
        for (MenuItem item : DataManager.menuHashTable.getAllItems()) {
            categories.add(item.getCategory());
        }
        
        return new ArrayList<>(categories);
    }
    
    /**
     * Check if menu is empty
     * 
     * @return true if no menu items exist
     */
    public static boolean isEmpty() {
        return DataManager.menuHashTable.isEmpty();
    }
    
    // ==================== Testing/Debugging Methods ====================
    
    /**
     * Display all menu items (for testing)
     */
    public static void printAllMenu() {
        System.out.println("\n=== All Menu Items ===");
        if (DataManager.menuHashTable.isEmpty()) {
            System.out.println("No menu items currently");
            return;
        }
        
        for (MenuItem item : DataManager.menuHashTable.getAllItems()) {
            System.out.println(item.toString());
        }
        System.out.println("Total: " + getMenuCount() + " items");
    }
    
    /**
     * Display menu items sorted by price (for testing)
     */
    public static void printMenuByPrice() {
        System.out.println("\n=== Menu Items (Sorted by Price) ===");
        if (DataManager.menuBST.isEmpty()) {
            System.out.println("No menu items currently");
            return;
        }
        
        for (MenuItem item : DataManager.menuBST.inorderTraversal()) {
            System.out.println(item.toString());
        }
    }
    
    /**
     * Print menu using MenuHashTable's built-in method
     */
    public static void printMenuHashTable() {
        DataManager.menuHashTable.printAll();
    }
}