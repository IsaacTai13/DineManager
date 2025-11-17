package test;

import adt.MenuHashTable;
import adt.MenuHashTableInterface;
import model.MenuItem;
import java.util.List;

/**
 * Test class to demonstrate MenuHashTable usage
 * Updated to use your MenuItem class structure
 */
public class MenuHashTableTest {
    
    public static void main(String[] args) {
        // Create a new hash table instance
        MenuHashTableInterface menuTable = new MenuHashTable();
        
        System.out.println("=== Testing MenuHashTable ADT ===\n");
        
        
        // ============================================================
        // Test 1: Adding items to hash table
        // ============================================================
        System.out.println("Test 1: Adding menu items");
        
        // Create menu items using your MenuItem constructor
        MenuItem burger = new MenuItem("M001", "Burger", 8.99, "Main");
        MenuItem pizza = new MenuItem("M002", "Pizza", 12.99, "Main");
        MenuItem pasta = new MenuItem("M003", "Pasta", 10.99, "Main");
        MenuItem salad = new MenuItem("A001", "Salad", 6.99, "Appetizer");
        MenuItem fries = new MenuItem("S001", "French Fries", 3.99, "Side");
        MenuItem coke = new MenuItem("D001", "Coke", 2.50, "Drink");
        MenuItem iceCream = new MenuItem("DS001", "Ice Cream", 4.99, "Dessert");
        
        // Put items into hash table using ID as key
        menuTable.put(burger.getId(), burger);
        menuTable.put(pizza.getId(), pizza);
        menuTable.put(pasta.getId(), pasta);
        menuTable.put(salad.getId(), salad);
        menuTable.put(fries.getId(), fries);
        menuTable.put(coke.getId(), coke);
        menuTable.put(iceCream.getId(), iceCream);
        
        System.out.println("Added 7 items to the hash table");
        System.out.println("Current size: " + menuTable.size());
        System.out.println();
        
        
        // ============================================================
        // Test 2: Retrieving items by ID (fast O(1) lookup)
        // ============================================================
        System.out.println("Test 2: Retrieving items by ID");
        
        MenuItem retrievedItem = menuTable.get("M001");
        if (retrievedItem != null) {
            System.out.println("Retrieved: " + retrievedItem.toString());
            System.out.println("  Name: " + retrievedItem.getName());
            System.out.println("  Price: " + retrievedItem.getFormattedPrice());
            System.out.println("  Category: " + retrievedItem.getCategory());
            System.out.println("  Available: " + retrievedItem.isAvailable());
        }
        
        MenuItem notFound = menuTable.get("X999");
        System.out.println("\nLooking for non-existent item 'X999': " + 
                         (notFound == null ? "Not found (expected)" : "Found"));
        System.out.println();
        
        
        // ============================================================
        // Test 3: Checking if keys exist
        // ============================================================
        System.out.println("Test 3: Checking key existence");
        
        System.out.println("Contains 'M001': " + menuTable.containsKey("M001"));
        System.out.println("Contains 'M002': " + menuTable.containsKey("M002"));
        System.out.println("Contains 'X999': " + menuTable.containsKey("X999"));
        System.out.println();
        
        
        // ============================================================
        // Test 4: Get all items
        // ============================================================
        System.out.println("Test 4: Getting all items");
        
        List<MenuItem> allItems = menuTable.getAllItems();
        System.out.println("Total items: " + allItems.size());
        for (MenuItem item : allItems) {
            System.out.println("  - " + item.toString());
        }
        System.out.println();
        
        
        // ============================================================
        // Test 5: Get items by category
        // ============================================================
        System.out.println("Test 5: Getting items by category");
        
        List<MenuItem> mainDishes = menuTable.getItemsByCategory("Main");
        System.out.println("Main dishes: " + mainDishes.size());
        for (MenuItem item : mainDishes) {
            System.out.println("  - " + item.toString());
        }
        
        List<MenuItem> drinks = menuTable.getItemsByCategory("Drink");
        System.out.println("\nDrinks: " + drinks.size());
        for (MenuItem item : drinks) {
            System.out.println("  - " + item.toString());
        }
        System.out.println();
        
        
        // ============================================================
        // Test 6: Test availability filtering
        // ============================================================
        System.out.println("Test 6: Testing availability");
        
        // Mark one item as unavailable
        salad.setAvailable(false);
        System.out.println("Marked Salad as unavailable");
        
        // Get only available items
        List<MenuItem> availableItems = menuTable.getAvailableItems();
        System.out.println("Available items: " + availableItems.size());
        for (MenuItem item : availableItems) {
            System.out.println("  - " + item.toString());
        }
        System.out.println();
        
        
        // ============================================================
        // Test 7: Remove item
        // ============================================================
        System.out.println("Test 7: Removing an item");
        
        MenuItem removed = menuTable.remove("D001");
        System.out.println("Removed: " + (removed != null ? removed.toString() : "null"));
        System.out.println("Size after removal: " + menuTable.size());
        System.out.println("Contains 'D001' after removal: " + menuTable.containsKey("D001"));
        System.out.println();
        
        
        // ============================================================
        // Test 8: Print all (using helper method)
        // ============================================================
        System.out.println("Test 8: Print all items");
        // For printAll(), we still need casting because it's a helper method
        // not part of the core ADT interface
      
        menuTable.printAll();
        
        System.out.println();
        
        
        // ============================================================
        // Test 9: Check if empty and clear
        // ============================================================
        System.out.println("Test 9: Empty check and clear");
        
        System.out.println("Is empty: " + menuTable.isEmpty());
        System.out.println("Current size: " + menuTable.size());
        
        menuTable.clear();
        System.out.println("\nAfter clear:");
        System.out.println("Is empty: " + menuTable.isEmpty());
        System.out.println("Size after clear: " + menuTable.size());
        
        
        System.out.println("\n=== All tests completed successfully! ===");
    }
}