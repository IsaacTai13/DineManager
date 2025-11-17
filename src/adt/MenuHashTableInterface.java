package adt;

import model.MenuItem;
import java.util.List;
import java.util.Map;

/**
 * ADT Interface for Menu Hash Table
 * This interface defines the operations for storing and retrieving menu items
 * using a hash table data structure.
 */
public interface MenuHashTableInterface {
    
    /**
     * Insert a menu item into the hash table
     * @param key - unique identifier (e.g., item name or ID)
     * @param value - the MenuItem object to store
     */
    void put(String key, MenuItem value);
    
    /**
     * Retrieve a menu item from the hash table
     * @param key - the identifier of the item to retrieve
     * @return the MenuItem object, or null if not found
     */
    MenuItem get(String key);
    
    /**
     * Check if a key exists in the hash table
     * @param key - the identifier to check
     * @return true if the key exists, false otherwise
     */
    boolean containsKey(String key);
    
    /**
     * Remove a menu item from the hash table
     * @param key - the identifier of the item to remove
     * @return the removed MenuItem object, or null if not found
     */
    MenuItem remove(String key);
    
    /**
     * Get the number of items in the hash table
     * @return the size of the hash table
     */
    int size();
    
    /**
     * Check if the hash table is empty
     * @return true if empty, false otherwise
     */
    boolean isEmpty();
    
    /**
     * Clear all items from the hash table
     */
    void clear();
    
    /**
     * Get all menu items in the hash table
     * @return a List of all MenuItem objects
     */
    List<MenuItem> getAllItems();
    
    /**
     * Get all menu items of a specific category
     * @param category - the category to filter by
     * @return a List of MenuItem objects in the specified category
     */
    List<MenuItem> getItemsByCategory(String category);
    
    /**
     * Get only available menu items
     * @return a List of available MenuItem objects
     */
    List<MenuItem> getAvailableItems();
    
    /**
     * Get available menu items of a specific category
     * @param category - the category to filter by
     * @return a List of available MenuItem objects in the specified category
     */
    List<MenuItem> getAvailableItemsByCategory(String category);
    
    /**
     * Print all items in the hash table
     * Useful for debugging
     */
    void printAll();
}