package adt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generic ADT Implementation for Hash Table
 * This class implements the HashTableInterface using HashMap
 * to provide O(1) average time complexity for lookups.
 * 
 * @param <K> The type of keys maintained by this hash table
 * @param <V> The type of mapped values
 */
public class HashTable<K, V> implements HashTableInterface<K, V> {
    
    // Internal hash table using Java's HashMap
    private HashMap<K, V> table;
    
    /**
     * Constructor - Initialize empty hash table
     */
    public HashTable() {
        this.table = new HashMap<>();
    }
    
    /**
     * Constructor with initial capacity
     * @param initialCapacity - the initial capacity of the hash table
     */
    public HashTable(int initialCapacity) {
        this.table = new HashMap<>(initialCapacity);
    }
    
    
    @Override
    public void put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key and value cannot be null");
        }
        table.put(key, value);
    }
    
    
    @Override
    public V get(K key) {
        if (key == null) {
            return null;
        }
        return table.get(key);
    }
    
    
    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            return false;
        }
        return table.containsKey(key);
    }
    
    
    @Override
    public V remove(K key) {
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
    public List<V> getAllValues() {
        return new ArrayList<>(table.values());
    }
    
    
    @Override
    public List<K> getAllKeys() {
        return new ArrayList<>(table.keySet());
    }
    
    
    /**
     * Additional helper method - Print all items in the hash table
     * Useful for debugging
     */
    public void printAll() {
        System.out.println("=== Hash Table Contents ===");
        System.out.println("Total items: " + size());
        
        for (Map.Entry<K, V> entry : table.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " | Value: " + entry.getValue());
        }
        System.out.println("===========================");
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