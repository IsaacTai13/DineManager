package adt;

import model.MenuItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Specialized Hash Table for MenuItem objects
 * Wraps the generic HashTable<String, MenuItem> and provides
 * menu-specific operations
 */
public class MenuHashTable {
    
    // Internal generic hash table
    private HashTable<String, MenuItem> table;
    
    /**
     * Constructor - Initialize empty menu hash table
     */
    public MenuHashTable() {
        this.table = new HashTable<>();
    }
    
    /**
     * Constructor with initial capacity
     */
    public MenuHashTable(int initialCapacity) {
        this.table = new HashTable<>(initialCapacity);
    }
    
    
    // ============================================================
    // Basic Operations - Delegate to generic HashTable
    // ============================================================
    
    public void put(String key, MenuItem value) {
        table.put(key, value);
    }
    
    public MenuItem get(String key) {
        return table.get(key);
    }
    
    public boolean containsKey(String key) {
        return table.containsKey(key);
    }
    
    public MenuItem remove(String key) {
        return table.remove(key);
    }
    
    public int size() {
        return table.size();
    }
    
    public boolean isEmpty() {
        return table.isEmpty();
    }
    
    public void clear() {
        table.clear();
    }
    
    public List<MenuItem> getAllItems() {
        return table.getAllValues();
    }
    
    public List<String> getAllKeys() {
        return table.getAllKeys();
    }
    
    
    // ============================================================
    // Menu-Specific Operations
    // ============================================================
    
    /**
     * Get only available menu items
     */
    public List<MenuItem> getAvailableItems() {
        List<MenuItem> result = new ArrayList<>();
        
        for (MenuItem item : table.getAllValues()) {
            if (item.isAvailable()) {
                result.add(item);
            }
        }
        
        return result;
    }
    
    /**
     * Get menu items by category
     */
    public List<MenuItem> getItemsByCategory(String category) {
        List<MenuItem> result = new ArrayList<>();
        
        if (category == null) {
            return result;
        }
        
        for (MenuItem item : table.getAllValues()) {
            if (category.equalsIgnoreCase(item.getCategory())) {
                result.add(item);
            }
        }
        
        return result;
    }
    
    /**
     * Get available items by category
     */
    public List<MenuItem> getAvailableItemsByCategory(String category) {
        List<MenuItem> result = new ArrayList<>();
        
        if (category == null) {
            return result;
        }
        
        for (MenuItem item : table.getAllValues()) {
            if (item.isAvailable() && category.equalsIgnoreCase(item.getCategory())) {
                result.add(item);
            }
        }
        
        return result;
    }
    
    /**
     * Print all menu items
     */
    public void printAll() {
        System.out.println("=== Menu Hash Table Contents ===");
        System.out.println("Total items: " + size());
        
        for (MenuItem item : table.getAllValues()) {
            System.out.println(item.toString());
        }
        System.out.println("================================");
    }
}