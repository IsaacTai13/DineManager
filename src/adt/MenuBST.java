package adt;

import model.MenuItem;
import java.util.ArrayList;
import java.util.List;

/**
* MenuBST - Binary Search Tree for MenuItem
* Wrapper around generic BST<MenuItem>
* Sorted by: Price (primary), then ID (secondary)
*/
public class MenuBST {
 
 private BST<MenuItem> bst;
 
 /**
  * Constructor
  * Initializes BST with MenuItem comparator
  */
 public MenuBST() {
     this.bst = new BST<>(this::compareMenuItems);
 }
 
 /**
  * Comparator for MenuItem
  * Compares by price first, then by ID if prices are equal
  */
 private int compareMenuItems(MenuItem a, MenuItem b) {
     // First compare by price
     int priceCompare = Double.compare(a.getPrice(), b.getPrice());
     if (priceCompare != 0) {
         return priceCompare;
     }
     
     // If prices are equal, compare by ID
     return a.getId().compareTo(b.getId());
 }
 
 // ============================================================
 // Basic BST Operations (Wrapper methods)
 // ============================================================
 
 /**
  * Insert a menu item into the BST
  */
 public void insert(MenuItem item) {
     bst.insert(item);
 }
 
 /**
  * Delete a menu item from the BST
  */
 public void delete(MenuItem item) {
     bst.delete(item);
 }
 
 /**
  * Search for a menu item in the BST
  */
 public boolean search(MenuItem item) {
     return bst.search(item);
 }
 
 /**
  * Get all menu items in sorted order (by price, then ID)
  * Uses inorder traversal
  */
 public List<MenuItem> inorderTraversal() {
     return bst.inorderTraversal();
 }
 
 /**
  * Find the cheapest menu item (minimum price)
  */
 public MenuItem findMin() {
     return bst.findMin();
 }
 
 /**
  * Find the most expensive menu item (maximum price)
  */
 public MenuItem findMax() {
     return bst.findMax();
 }
 
 /**
  * Check if the BST is empty
  */
 public boolean isEmpty() {
     return bst.isEmpty();
 }
 
 /**
  * Get the number of menu items in the BST
  */
 public int size() {
     return bst.size();
 }
 
 // ============================================================
 // MenuItem-Specific Operations
 // ============================================================
 
 /**
  * Range search for menu items within a price range
  * Returns all items where minPrice <= item.price <= maxPrice
  * 
  * @param minPrice Minimum price (inclusive)
  * @param maxPrice Maximum price (inclusive)
  * @return List of menu items within the price range
  */
 public List<MenuItem> rangeSearch(double minPrice, double maxPrice) {
     List<MenuItem> result = new ArrayList<>();
     rangeSearchHelper(minPrice, maxPrice, result);
     return result;
 }
 
 /**
  * Helper method for range search
  * Uses inorder traversal and filters by price range
  */
 private void rangeSearchHelper(double minPrice, double maxPrice, List<MenuItem> result) {
     // Get all items in sorted order
     List<MenuItem> allItems = bst.inorderTraversal();
     
     // Filter items within the price range
     for (MenuItem item : allItems) {
         double price = item.getPrice();
         if (price >= minPrice && price <= maxPrice) {
             result.add(item);
         }
         // Since items are sorted by price, we can stop early
         if (price > maxPrice) {
             break;
         }
     }
 }
 
 /**
  * Get all menu items sorted by price (ascending)
  * This is an alias for inorderTraversal for clarity
  */
 public List<MenuItem> getAllByPrice() {
     return inorderTraversal();
 }
 
 /**
  * Get all menu items sorted by price (descending)
  */
 public List<MenuItem> getAllByPriceDescending() {
     List<MenuItem> items = inorderTraversal();
     // Reverse the list
     List<MenuItem> reversed = new ArrayList<>();
     for (int i = items.size() - 1; i >= 0; i--) {
         reversed.add(items.get(i));
     }
     return reversed;
 }
}
