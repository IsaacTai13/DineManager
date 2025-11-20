package service;

import model.MenuItem;
import java.util.*;

// Menu Service Class: Provides all menu-related operations
public class MenuService {
    
	// ============================================================
    // Basic CRUD Operations
    // ============================================================
    
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
        DataManager.menuHashTable.put(item.getId(), item);
        DataManager.menuBST.insert(item);
        
        System.out.println("Successfully added menu item: " + item.getName());
        return true;
    }
    
    
    public static boolean removeMenuItem(String id) {
        // Check if the item exists
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
    
    
    public static boolean updateMenuItem(String id, MenuItem newItem) {
    	// Check if the item exists
    	if (!DataManager.menuHashTable.containsKey(id)) {
            System.err.println("Error: Menu item ID not found: " + id);
            return false;
        }
        
        // Remove old item and add a new one
        removeMenuItem(id);
        return addMenuItem(newItem);
    }
    
    
    // ============================================================
    // Query Methods
    // ============================================================

    public static MenuItem getMenuItemById(String id) {
        return DataManager.menuHashTable.get(id);
    }    
    
    public static List<MenuItem> getAllMenuItems() {
        return DataManager.menuHashTable.getAllItems();
    }    

    public static List<MenuItem> getMenuByPrice() {
    	return DataManager.menuBST.getAllByPrice();
    }
    
    public static List<MenuItem> getMenuByPriceDescending() {
    	return DataManager.menuBST.getAllByPriceDescending();
    }
    
    public static List<MenuItem> getMenuByCategory(String category) {
        return DataManager.menuHashTable.getItemsByCategory(category);
    }
    
    public static List<MenuItem> getMenuByPriceRange(double minPrice, double maxPrice) {
    	return DataManager.menuBST.rangeSearch(minPrice, maxPrice);
    }
    
    public static List<MenuItem> searchMenuByName(String keyword) {
        List<MenuItem> result = new ArrayList<>();
        
        if (keyword == null || keyword.trim().isEmpty()) return result;
        
        String lowerKeyword = keyword.toLowerCase().trim();
        for (MenuItem item : DataManager.menuHashTable.getAllItems()) {
            if (item.getName().toLowerCase().contains(lowerKeyword)) result.add(item);
        }     
        return result;
    }
    
     
    // ============================================================
    // Statistics Methods
    // ============================================================
    
    public static int getMenuCount() {
        return DataManager.menuHashTable.size();
    }
    
    public static MenuItem getCheapestItem() {
    	return DataManager.menuBST.findMin();
    }
    
    public static MenuItem getMostExpensiveItem() {
    	return DataManager.menuBST.findMax();
    }
    
    public static double getAveragePrice() {
        if (DataManager.menuHashTable.isEmpty()) return 0;
        
        double total = 0;
        for (MenuItem item : DataManager.menuHashTable.getAllItems()) {
            total += item.getPrice();
        }
        return total / DataManager.menuHashTable.size();
    }
    
    
    // ============================================================
    // Helper Methods
    // ============================================================
    
    public static List<String> getAllCategories() {
        Set<String> categories = new HashSet<>();    
        for (MenuItem item : DataManager.menuHashTable.getAllItems()) {
            categories.add(item.getCategory());
        }       
        return new ArrayList<>(categories);
    }
    
    public static boolean isEmpty() {
        return DataManager.menuHashTable.isEmpty();
    }
}