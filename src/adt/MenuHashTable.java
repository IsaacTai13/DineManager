package adt;

import model.MenuItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ADT Implementation for Menu Hash Table
 * This class implements the MenuHashTableInterface using HashMap
 * to provide O(1) average time complexity for menu item lookups.
 */
public class MenuHashTable implements MenuHashTableInterface {
    
    // Internal hash table using Java's HashMap
    private HashMap<String, MenuItem> table;
    
    /**
     * Constructor - Initialize empty hash table
     */
    public MenuHashTable() {
        this.table = new HashMap<>();
    }
    
    /**
     * Constructor with initial capacity
     * @param initialCapacity - the initial capacity of the hash table
     */
    public MenuHashTable(int initialCapacity) {
        this.table = new HashMap<>(initialCapacity);
    }
    
    
    @Override
    public void put(String key, MenuItem value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key and value cannot be null");
        }
        table.put(key, value);
    }
    
    
    @Override
    public MenuItem get(String key) {
        if (key == null) {
            return null;
        }
        return table.get(key);
    }
    
    
    @Override
    public boolean containsKey(String key) {
        if (key == null) {
            return false;
        }
        return table.containsKey(key);
    }
    
    
    @Override
    public MenuItem remove(String key) {
        if (key == null) {
            return null;
        }
        return table.remove(key);
    }
    
    
    @Override
    public int size() {
        return table.size();
    }
    
    
    @Override
    public boolean isEmpty() {
        return table.isEmpty();
    }
    
    
    @Override
    public void clear() {
        table.clear();
    }
    
    
    @Override
    public List<MenuItem> getAllItems() {
        return new ArrayList<>(table.values());
    }
    
    
    @Override
    public List<MenuItem> getItemsByCategory(String category) {
        List<MenuItem> result = new ArrayList<>();
        
        if (category == null) {
            return result;
        }
        
        // Iterate through all items and filter by category
        for (MenuItem item : table.values()) {
            if (category.equalsIgnoreCase(item.getCategory())) {
                result.add(item);
            }
        }
        
        return result;
    }
    
    
    /**
     * Get only available items
     * @return a List of available MenuItem objects
     */
    public List<MenuItem> getAvailableItems() {
        List<MenuItem> result = new ArrayList<>();
        
        for (MenuItem item : table.values()) {
            if (item.isAvailable()) {
                result.add(item);
            }
        }
        
        return result;
    }
    
    
    /**
     * Get available items by category
     * @param category - the category to filter by
     * @return a List of available MenuItem objects in the specified category
     */
    public List<MenuItem> getAvailableItemsByCategory(String category) {
        List<MenuItem> result = new ArrayList<>();
        
        if (category == null) {
            return result;
        }
        
        for (MenuItem item : table.values()) {
            if (item.isAvailable() && category.equalsIgnoreCase(item.getCategory())) {
                result.add(item);
            }
        }
        
        return result;
    }
    
    
    /**
     * Additional helper method - Get all keys in the hash table
     * @return a List of all keys
     */
    public List<String> getAllKeys() {
        return new ArrayList<>(table.keySet());
    }
    
    
    /**
     * Additional helper method - Print all items in the hash table
     * Useful for debugging
     */
    public void printAll() {
        System.out.println("=== Menu Hash Table Contents ===");
        System.out.println("Total items: " + size());
        
        for (Map.Entry<String, MenuItem> entry : table.entrySet()) {
            MenuItem item = entry.getValue();
            System.out.println(String.format("Key: %s | %s", 
                entry.getKey(), 
                item.toString()));
        }
        System.out.println("================================");
    }
    
    
    /**
     * Get load factor of the hash table (for performance analysis)
     * @return the current load factor
     */
    public double getLoadFactor() {
        // Note: This is a simplified calculation
        // Actual load factor would need access to internal bucket count
        return (double) size() / 16; // Assuming default initial capacity of 16
    }
}