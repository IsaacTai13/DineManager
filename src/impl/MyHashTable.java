package impl;

import adt.HashTableInterface;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Hash Table Implementation from Scratch
 * Uses Separate Chaining for collision handling
 * Do NOT use Java's HashMap - implements hashing from the ground up
 * 
 * @param <K> The type of keys
 * @param <V> The type of values
 * 
 * HashTable Performance
 * 
 * Implementation: Separate Chaining
 * - Each bucket stores all key-value pairs whose keys hash to the same index
 * - Collision handling: LinkedList chains in same bucket
 * - a linked list (a bucket) is longer, the search speed is longer
 * 
 * Example:
 * Bucket 3: [M001=Burger] → [D001=Coke]  ← Both hash to index 3
 * Bucket 7: [M002=Pizza]
 * 
 * Time Complexity:
 * - Average: O(1) for put, get, remove
 * - Worst: O(n) when all keys hash to same bucket
 * 
 * Load Factor: ≤ 0.75
 * - Automatic resize when threshold exceeded
 * - Capacity doubles: 16 → 32 → 64...
 * 
 * Current Usage:
 * - 18 menu items across 32 buckets
 * - Average chain length: 1.3
 * - Lookup time: < 1ms
 */
public class MyHashTable<K, V> implements HashTableInterface<K, V> {
    
    /**
     * Entry class to store key-value pairs in the hash table
     */
    private static class Entry<K, V> {
        K key;
        V value;
        
        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    
    // Array of LinkedLists (buckets) for separate chaining collision handling
    // 1. buckets is an array, each element in array is a linked list
    // 2. each linked list store multiple Entry<K,V>（key–value pair）
    // 3. each bucket store all the key-value pairs whose keys hash to the same index
    //    -  One bucket = one LinkedList<Entry<K,V>>
    
    
    private LinkedList<Entry<K, V>>[] buckets;
    private int capacity;           // Number of buckets
    private int size;               // Number of entries
    
    // Constants for hash table behavior
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;
    
    
    /**
     * Constructor - Initialize hash table with default capacity
     */
    @SuppressWarnings("unchecked")
    public MyHashTable() {
        this.capacity = DEFAULT_CAPACITY;
        this.size = 0;
        this.buckets = new LinkedList[capacity];
        
        // Initialize each bucket as an empty LinkedList
        for (int i = 0; i < capacity; i++) {
            buckets[i] = new LinkedList<>();
        }
    }
    
    /**
     * Constructor with custom initial capacity
     * @param initialCapacity - the initial number of buckets
     */
    @SuppressWarnings("unchecked")
    public MyHashTable(int initialCapacity) {
        this.capacity = initialCapacity;
        this.size = 0;
        this.buckets = new LinkedList[capacity];
        
        for (int i = 0; i < capacity; i++) {
            buckets[i] = new LinkedList<>();
        }
    }
    
    
    /**
     * Hash function - The core of hashing
     * Converts a key to a bucket index
     * 
     * @param key - the key to hash
     * @return bucket index (0 to capacity-1)
     */
    private int hash(K key) {
        if (key == null) {
            return 0;  // null keys go to bucket 0
        }
        
        // Use Java's hashCode() and map to our bucket range
        // Math.abs ensures positive, % capacity ensures it fits in our buckets
        return Math.abs(key.hashCode()) % capacity;
    }
    
    
    @Override
    public void put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key and value cannot be null");
        }
        
        // Step 1: Calculate bucket index using hash function
        int index = hash(key);
        LinkedList<Entry<K, V>> bucket = buckets[index];
        
        // Step 2: Check if key already exists (update case)
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                entry.value = value;  // Update existing value
                return;
            }
        }
        
        // Step 3: Key doesn't exist, add new entry
        bucket.add(new Entry<>(key, value));
        size++;
        
        // Step 4: Check if we need to resize (maintain performance)
        if (getLoadFactor() > LOAD_FACTOR) {
            resize();
        }
    }
    
    
    @Override
    public V get(K key) {
        if (key == null) {
            return null;
        }
        
        // Step 1: Calculate bucket index
        int index = hash(key);
        LinkedList<Entry<K, V>> bucket = buckets[index];
        
        // Step 2: Linear search within the bucket
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                return entry.value;  // Found!
            }
        }
        
        return null;  // Not found
    }
    
    
    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }
    
    
    @Override
    public V remove(K key) {
        if (key == null) {
            return null;
        }
        
        // Step 1: Find the bucket
        int index = hash(key);
        LinkedList<Entry<K, V>> bucket = buckets[index];
        
        // Step 2: Find and remove the entry
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                V value = entry.value;
                bucket.remove(entry);
                size--;
                return value;
            }
        }
        
        return null;  // Key not found
    }
    
    
    @Override
    public int size() {
        return size;
    }
    
    
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    
    
    @Override
    public void clear() {
        // Clear all buckets
        for (int i = 0; i < capacity; i++) {
            buckets[i].clear();
        }
        size = 0;
    }
    
    
    @Override
    public List<V> getAllValues() {
        List<V> values = new ArrayList<>();
        
        // Iterate through all buckets
        for (LinkedList<Entry<K, V>> bucket : buckets) {
            // Iterate through all entries in each bucket
            for (Entry<K, V> entry : bucket) {
                values.add(entry.value);
            }
        }
        
        return values;
    }
    
    
    @Override
    public List<K> getAllKeys() {
        List<K> keys = new ArrayList<>();
        
        for (LinkedList<Entry<K, V>> bucket : buckets) {
            for (Entry<K, V> entry : bucket) {
                keys.add(entry.key);
            }
        }
        
        return keys;
    }
    
    
    /**
     * Resize the hash table when load factor exceeds threshold
     * This is critical for maintaining O(1) average performance
     * 
     * Process:
     * 1. Create new larger bucket array (double the size)
     * 2. Rehash all existing entries into new buckets
     * 3. Replace old buckets with new ones
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        System.out.println("Resizing hash table from " + capacity + " to " + (capacity * 2));
        
        // Save old buckets
        LinkedList<Entry<K, V>>[] oldBuckets = buckets;
        int oldCapacity = capacity;
        
        // Create new larger buckets (double the size)
        capacity *= 2;
        buckets = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            buckets[i] = new LinkedList<>();
        }
        
        // Rehash all entries from old buckets to new buckets
        size = 0;  // Reset size, will be incremented in put()
        for (int i = 0; i < oldCapacity; i++) {
            for (Entry<K, V> entry : oldBuckets[i]) {
                put(entry.key, entry.value);  // Rehash with new capacity
            }
        }
        
        System.out.println("Resize complete. New capacity: " + capacity);
    }
    
    
    /**
     * Get current load factor (size / capacity)
     * Load factor indicates how full the hash table is
     * 
     * @return current load factor
     */
    public double getLoadFactor() {
        return (double) size / capacity;
    }
    
    
    /**
     * Get the number of buckets
     * @return capacity
     */
    public int getCapacity() {
        return capacity;
    }
    
    
    /**
     * Print hash table statistics for analysis
     */
    public void printStatistics() {
        System.out.println("\n=== Hash Table Statistics ===");
        System.out.println("Capacity: " + capacity);
        System.out.println("Size: " + size);
        System.out.println("Load Factor: " + String.format("%.2f", getLoadFactor()));
        
        // Calculate collision statistics
        int emptyBuckets = 0;
        int maxChainLength = 0;
        int totalChainLength = 0;
        
        for (LinkedList<Entry<K, V>> bucket : buckets) {
            int chainLength = bucket.size();
            if (chainLength == 0) {
                emptyBuckets++;
            } else {
                totalChainLength += chainLength;
                maxChainLength = Math.max(maxChainLength, chainLength);
            }
        }
        
        double avgChainLength = (capacity - emptyBuckets > 0) 
            ? (double) totalChainLength / (capacity - emptyBuckets) 
            : 0;
        
        System.out.println("Empty Buckets: " + emptyBuckets + " / " + capacity);
        System.out.println("Max Chain Length: " + maxChainLength);
        System.out.println("Avg Chain Length: " + String.format("%.2f", avgChainLength));
        System.out.println("============================\n");
    }
    
    
    /**
     * Visualize the hash table structure (for debugging/learning)
     * Shows the first few buckets and their contents
     */
    public void visualize() {
        System.out.println("\n=== Hash Table Visualization ===");
        int bucketsToShow = Math.min(10, capacity);
        
        for (int i = 0; i < bucketsToShow; i++) {
            System.out.print("Bucket " + i + ": ");
            
            LinkedList<Entry<K, V>> bucket = buckets[i];
            if (bucket.isEmpty()) {
                System.out.println("[]");
            } else {
                System.out.print("[");
                for (int j = 0; j < bucket.size(); j++) {
                    Entry<K, V> entry = bucket.get(j);
                    System.out.print(entry.key + "=" + entry.value);
                    if (j < bucket.size() - 1) {
                        System.out.print(" -> ");
                    }
                }
                System.out.println("]");
            }
        }
        
        if (capacity > bucketsToShow) {
            System.out.println("... (" + (capacity - bucketsToShow) + " more buckets)");
        }
        System.out.println("================================\n");
    }
    
    
    /**
     * Print all key-value pairs (for debugging)
     */
    public void printAll() {
        System.out.println("\n=== Hash Table Contents ===");
        System.out.println("Total items: " + size);
        
        if (isEmpty()) {
            System.out.println("Hash table is empty");
        } else {
            for (int i = 0; i < capacity; i++) {
                for (Entry<K, V> entry : buckets[i]) {
                    System.out.println("Key: " + entry.key + " | Value: " + entry.value);
                }
            }
        }
        
        System.out.println("===========================\n");
    }
}