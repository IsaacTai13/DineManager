package adt;

import java.util.List;

/**
 * Generic ADT Interface for Hash Table
 * This interface defines the operations for storing and retrieving items
 * using a hash table data structure.
 * 
 * @param <K> The type of keys maintained by this hash table
 * @param <V> The type of mapped values
 */
public interface HashTableInterface<K, V> {
    
    /**
     * Insert an item into the hash table
     * @param key - unique identifier
     * @param value - the object to store
     */
    void put(K key, V value);
    
    /**
     * Retrieve an item from the hash table
     * @param key - the identifier of the item to retrieve
     * @return the object, or null if not found
     */
    V get(K key);
    
    /**
     * Check if a key exists in the hash table
     * @param key - the identifier to check
     * @return true if the key exists, false otherwise
     */
    boolean containsKey(K key);
    
    /**
     * Remove an item from the hash table
     * @param key - the identifier of the item to remove
     * @return the removed object, or null if not found
     */
    V remove(K key);
    
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
     * Get all values in the hash table
     * @return a List of all values
     */
    List<V> getAllValues();
    
    /**
     * Get all keys in the hash table
     * @return a List of all keys
     */
    List<K> getAllKeys();
}