package test;

//============================================================
//File: TestDataManager.java
//Location: src/TestDataManager.java
//Purpose: Test all DataManager and Model class functionalities
//Usage: Ensure all shared classes and data structures work correctly
//============================================================

import model.*;
import service.*;
import java.util.ArrayList;
import java.util.List;

/**
* DataManager Test Program
* Tests all Model classes and DataManager functionality
*/
public class TestDataManager {
 
 public static void main(String[] args) {
     System.out.println("========================================");
     System.out.println("Starting DataManager Tests");
     System.out.println("========================================\n");
     
     // Initialize
     DataManager.initialize();
     
     // Test 1: Menu Functions
     testMenuFunctions();
     
     // Test 2: Menu Service Operations
     testMenuService();
     
     // Test 3: Menu Search and Query
     testMenuSearch();
     
     // Test 4: BST Functionality
     testBSTFunctionality();
     
     System.out.println("\n========================================");
     System.out.println("All Tests Completed!");
     System.out.println("========================================");
 }
 
 /**
  * Test 1: Basic Menu Functions
  */
 private static void testMenuFunctions() {
     System.out.println("\n【Test 1: Basic Menu Functions】");
     System.out.println("----------------------------------------");
     
     // 1. Display all menu items
     System.out.println("1.1 Display all menu items:");
     System.out.println("Total menu items: " + MenuService.getMenuCount());
     
     // Display first 3 items as sample
     List<MenuItem> allItems = MenuService.getAllMenuItems();
     for (int i = 0; i < Math.min(3, allItems.size()); i++) {
         System.out.println("  " + allItems.get(i).toString());
     }
     System.out.println("  ... (showing first 3 items)");
     
     // 2. Test getting item by ID
     System.out.println("\n1.2 Get item by ID (M001):");
     MenuItem item = MenuService.getMenuItemById("M001");
     if (item != null) {
         System.out.println("  Found: " + item.toString());
     }
     
     // 3. Display categories
     System.out.println("\n1.3 All categories:");
     List<String> categories = MenuService.getAllCategories();
     for (String category : categories) {
         int count = MenuService.getMenuCountByCategory(category);
         System.out.println("  " + category + ": " + count + " items");
     }
 }
 
 /**
  * Test 2: Menu Service Operations (Add, Update, Delete)
  */
 private static void testMenuService() {
     System.out.println("\n【Test 2: Menu Service Operations】");
     System.out.println("----------------------------------------");
     
     // Test adding new item
     System.out.println("2.1 Test adding new menu item:");
     MenuItem newItem = new MenuItem("TEST01", "Test Burger", 99, "Main Dish", "For testing");
     boolean added = MenuService.addMenuItem(newItem);
     System.out.println("  Add result: " + (added ? "SUCCESS" : "FAILED"));
     System.out.println("  New menu count: " + MenuService.getMenuCount());
     
     // Test validation
     System.out.println("\n2.2 Test validation (invalid item):");
     MenuItem invalidItem = new MenuItem("", "", -10, "");
     String validationResult = MenuService.validateMenuItem(invalidItem);
     System.out.println("  Validation result: " + validationResult);
     
     // Test duplicate check
     System.out.println("\n2.3 Test duplicate name check:");
     boolean isDuplicate = MenuService.isNameDuplicate("Test Burger");
     System.out.println("  'Test Burger' is duplicate: " + isDuplicate);
     
     // Test update
     System.out.println("\n2.4 Test updating menu item:");
     MenuItem updatedItem = new MenuItem("TEST01", "Updated Test Burger", 120, "Main Dish", "Updated");
     boolean updated = MenuService.updateMenuItem("TEST01", updatedItem);
     System.out.println("  Update result: " + (updated ? "SUCCESS" : "FAILED"));
     
     // Verify update
     MenuItem verifyItem = MenuService.getMenuItemById("TEST01");
     if (verifyItem != null) {
         System.out.println("  Updated item: " + verifyItem.toString());
     }
     
     // Test delete
     System.out.println("\n2.5 Test deleting menu item:");
     boolean deleted = MenuService.removeMenuItem("TEST01");
     System.out.println("  Delete result: " + (deleted ? "SUCCESS" : "FAILED"));
     System.out.println("  Final menu count: " + MenuService.getMenuCount());
 }
 
 /**
  * Test 3: Menu Search and Query Functions
  */
 private static void testMenuSearch() {
     System.out.println("\n【Test 3: Menu Search and Query】");
     System.out.println("----------------------------------------");
     
     // Test search by name
     System.out.println("3.1 Search by name (keyword: 'Burger'):");
     List<MenuItem> searchResult = MenuService.searchMenuByName("Burger");
     System.out.println("  Found " + searchResult.size() + " items:");
     for (MenuItem item : searchResult) {
         System.out.println("    - " + item.toString());
     }
     
     // Test get by category
     System.out.println("\n3.2 Get items by category ('Beverage'):");
     List<MenuItem> beverages = MenuService.getMenuByCategory("Beverage");
     System.out.println("  Found " + beverages.size() + " beverages:");
     for (int i = 0; i < Math.min(3, beverages.size()); i++) {
         System.out.println("    - " + beverages.get(i).toString());
     }
     if (beverages.size() > 3) {
         System.out.println("    ... (showing first 3 items)");
     }
     
     // Test price range query
     System.out.println("\n3.3 Query by price range ($50 - $100):");
     List<MenuItem> priceRange = MenuService.getMenuByPriceRange(50, 100);
     System.out.println("  Found " + priceRange.size() + " items in range:");
     for (MenuItem item : priceRange) {
         System.out.println("    - " + item.toString());
     }
     
     // Test statistics
     System.out.println("\n3.4 Menu statistics:");
     MenuItem cheapest = MenuService.getCheapestItem();
     MenuItem expensive = MenuService.getMostExpensiveItem();
     double average = MenuService.getAveragePrice();
     
     if (cheapest != null) {
         System.out.println("  Cheapest: " + cheapest.toString());
     }
     if (expensive != null) {
         System.out.println("  Most expensive: " + expensive.toString());
     }
     System.out.println("  Average price: $" + String.format("%.2f", average));
 }
 
 /**
  * Test 4: BST Functionality
  */
 private static void testBSTFunctionality() {
     System.out.println("\n【Test 4: BST Functionality】");
     System.out.println("----------------------------------------");
     
     // Test sorted by price (ascending)
     System.out.println("4.1 Get menu sorted by price (ascending):");
     List<MenuItem> sortedAsc = MenuService.getMenuByPrice();
     System.out.println("  First 5 items (cheapest to expensive):");
     for (int i = 0; i < Math.min(5, sortedAsc.size()); i++) {
         System.out.println("    " + (i+1) + ". " + sortedAsc.get(i).toString());
     }
     
     // Test sorted by price (descending)
     System.out.println("\n4.2 Get menu sorted by price (descending):");
     List<MenuItem> sortedDesc = MenuService.getMenuByPriceDescending();
     System.out.println("  First 5 items (expensive to cheapest):");
     for (int i = 0; i < Math.min(5, sortedDesc.size()); i++) {
         System.out.println("    " + (i+1) + ". " + sortedDesc.get(i).toString());
     }
     
     // Verify BST maintains order
     System.out.println("\n4.3 Verify BST maintains sorted order:");
     boolean isOrdered = true;
     for (int i = 0; i < sortedAsc.size() - 1; i++) {
         if (sortedAsc.get(i).getPrice() > sortedAsc.get(i + 1).getPrice()) {
             isOrdered = false;
             break;
         }
     }
     System.out.println("  BST maintains correct order: " + (isOrdered ? "YES" : "NO"));
 }
}
