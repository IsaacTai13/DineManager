package test;

import model.MenuItem;
import adt.MenuBST;
import java.util.List;

/**
 * Test program for MenuBST
 * Tests all BST operations and MenuItem-specific features
 */
public class TestMenuBST {
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Testing MenuBST Implementation");
        System.out.println("========================================\n");
        
        // Create MenuBST
        MenuBST bst = new MenuBST();
        
        // Test 1: Basic Insert and Traversal
        testBasicOperations(bst);
        
        // Test 2: Ordering (Price + ID)
        testOrdering(bst);
        
        // Test 3: Find Min/Max
        testMinMax(bst);
        
        // Test 4: Search
        testSearch(bst);
        
        // Test 5: Range Search
        testRangeSearch(bst);
        
        // Test 6: Delete
        testDelete(bst);
        
        // Test 7: Edge Cases
        testEdgeCases();
        
        System.out.println("\n========================================");
        System.out.println("All MenuBST Tests Completed!");
        System.out.println("========================================");
    }
    
    /**
     * Test 1: Basic Insert and Inorder Traversal
     */
    private static void testBasicOperations(MenuBST bst) {
        System.out.println("\n【Test 1: Basic Insert and Traversal】");
        System.out.println("----------------------------------------");
        
        // Insert items (intentionally out of order)
        MenuItem m1 = new MenuItem("M003", "Pasta", 180, "Main Dish");
        MenuItem m2 = new MenuItem("M001", "Burger", 150, "Main Dish");
        MenuItem m3 = new MenuItem("D001", "Coke", 30, "Beverage");
        MenuItem m4 = new MenuItem("S001", "Ice Cream", 60, "Dessert");
        MenuItem m5 = new MenuItem("M002", "Chicken", 120, "Main Dish");
        
        System.out.println("Inserting items (out of order):");
        bst.insert(m1);
        System.out.println("  Inserted: " + m1.getName() + " ($" + m1.getPrice() + ")");
        bst.insert(m2);
        System.out.println("  Inserted: " + m2.getName() + " ($" + m2.getPrice() + ")");
        bst.insert(m3);
        System.out.println("  Inserted: " + m3.getName() + " ($" + m3.getPrice() + ")");
        bst.insert(m4);
        System.out.println("  Inserted: " + m4.getName() + " ($" + m4.getPrice() + ")");
        bst.insert(m5);
        System.out.println("  Inserted: " + m5.getName() + " ($" + m5.getPrice() + ")");
        
        System.out.println("\nBST size: " + bst.size());
        System.out.println("Is empty: " + bst.isEmpty());
        
        System.out.println("\nInorder Traversal (should be sorted by price):");
        List<MenuItem> sorted = bst.inorderTraversal();
        for (MenuItem item : sorted) {
            System.out.println("  " + item.getName() + " - $" + item.getPrice() + " (" + item.getId() + ")");
        }
        
        // Verify ordering
        boolean correctOrder = true;
        for (int i = 0; i < sorted.size() - 1; i++) {
            if (sorted.get(i).getPrice() > sorted.get(i + 1).getPrice()) {
                correctOrder = false;
                break;
            }
        }
        System.out.println("\nCorrect ordering: " + (correctOrder ? "✓ YES" : "✗ NO"));
    }
    
    /**
     * Test 2: Ordering with same price (should use ID as tiebreaker)
     */
    private static void testOrdering(MenuBST bst) {
        System.out.println("\n【Test 2: Ordering (Same Price)】");
        System.out.println("----------------------------------------");
        
        // Insert items with same price but different IDs
        MenuItem s2 = new MenuItem("S002", "Cake", 60, "Dessert");  // Same price as S001
        MenuItem s3 = new MenuItem("S003", "Pudding", 60, "Dessert"); // Same price
        
        System.out.println("Inserting items with same price ($60):");
        bst.insert(s2);
        System.out.println("  Inserted: " + s2.getName() + " (ID: " + s2.getId() + ")");
        bst.insert(s3);
        System.out.println("  Inserted: " + s3.getName() + " (ID: " + s3.getId() + ")");
        
        System.out.println("\nItems with price $60 (should be sorted by ID):");
        List<MenuItem> items = bst.inorderTraversal();
        for (MenuItem item : items) {
            if (item.getPrice() == 60) {
                System.out.println("  " + item.getId() + " - " + item.getName());
            }
        }
        
        System.out.println("\nCurrent BST size: " + bst.size());
    }
    
    /**
     * Test 3: Find Min and Max
     */
    private static void testMinMax(MenuBST bst) {
        System.out.println("\n【Test 3: Find Min/Max】");
        System.out.println("----------------------------------------");
        
        MenuItem min = bst.findMin();
        MenuItem max = bst.findMax();
        
        System.out.println("Cheapest item: " + min.getName() + " - $" + min.getPrice());
        System.out.println("Most expensive item: " + max.getName() + " - $" + max.getPrice());
        
        // Verify
        boolean minCorrect = (min.getPrice() == 30);  // Coke
        boolean maxCorrect = (max.getPrice() == 180); // Pasta
        
        System.out.println("\nMin correct: " + (minCorrect ? "✓ YES" : "✗ NO"));
        System.out.println("Max correct: " + (maxCorrect ? "✓ YES" : "✗ NO"));
    }
    
    /**
     * Test 4: Search
     */
    private static void testSearch(MenuBST bst) {
        System.out.println("\n【Test 4: Search】");
        System.out.println("----------------------------------------");
        
        // Search for existing item
        MenuItem searchItem1 = new MenuItem("M001", "Burger", 150, "Main Dish");
        boolean found1 = bst.search(searchItem1);
        System.out.println("Search for Burger ($150, M001): " + (found1 ? "✓ FOUND" : "✗ NOT FOUND"));
        
        // Search for non-existing item
        MenuItem searchItem2 = new MenuItem("M999", "Pizza", 200, "Main Dish");
        boolean found2 = bst.search(searchItem2);
        System.out.println("Search for Pizza ($200, M999): " + (found2 ? "✗ FOUND" : "✓ NOT FOUND"));
        
        // Search with same price but different ID
        MenuItem searchItem3 = new MenuItem("S999", "Unknown", 60, "Dessert");
        boolean found3 = bst.search(searchItem3);
        System.out.println("Search for Unknown ($60, S999): " + (found3 ? "✗ FOUND" : "✓ NOT FOUND"));
    }
    
    /**
     * Test 5: Range Search
     */
    private static void testRangeSearch(MenuBST bst) {
        System.out.println("\n【Test 5: Range Search】");
        System.out.println("----------------------------------------");
        
        double minPrice = 50;
        double maxPrice = 150;
        
        System.out.println("Searching for items between $" + minPrice + " and $" + maxPrice + ":");
        List<MenuItem> rangeResults = bst.rangeSearch(minPrice, maxPrice);
        
        System.out.println("Found " + rangeResults.size() + " items:");
        for (MenuItem item : rangeResults) {
            System.out.println("  " + item.getName() + " - $" + item.getPrice());
        }
        
        // Verify all items are within range
        boolean allInRange = true;
        for (MenuItem item : rangeResults) {
            if (item.getPrice() < minPrice || item.getPrice() > maxPrice) {
                allInRange = false;
                break;
            }
        }
        System.out.println("\nAll items in range: " + (allInRange ? "✓ YES" : "✗ NO"));
    }
    
    /**
     * Test 6: Delete
     */
    private static void testDelete(MenuBST bst) {
        System.out.println("\n【Test 6: Delete】");
        System.out.println("----------------------------------------");
        
        int sizeBefore = bst.size();
        System.out.println("Size before deletion: " + sizeBefore);
        
        // Delete an item
        MenuItem toDelete = new MenuItem("M001", "Burger", 150, "Main Dish");
        System.out.println("\nDeleting: " + toDelete.getName() + " ($" + toDelete.getPrice() + ")");
        bst.delete(toDelete);
        
        int sizeAfter = bst.size();
        System.out.println("Size after deletion: " + sizeAfter);
        
        // Verify deletion
        boolean deleted = !bst.search(toDelete);
        System.out.println("Item deleted: " + (deleted ? "✓ YES" : "✗ NO"));
        System.out.println("Size decreased by 1: " + ((sizeBefore - sizeAfter == 1) ? "✓ YES" : "✗ NO"));
        
        // Show remaining items
        System.out.println("\nRemaining items:");
        List<MenuItem> remaining = bst.inorderTraversal();
        for (MenuItem item : remaining) {
            System.out.println("  " + item.getName() + " - $" + item.getPrice());
        }
    }
    
    /**
     * Test 7: Edge Cases
     */
    private static void testEdgeCases() {
        System.out.println("\n【Test 7: Edge Cases】");
        System.out.println("----------------------------------------");
        
        // Test with empty BST
        MenuBST emptyBST = new MenuBST();
        
        System.out.println("Testing empty BST:");
        System.out.println("  Is empty: " + emptyBST.isEmpty());
        System.out.println("  Size: " + emptyBST.size());
        System.out.println("  Find min: " + emptyBST.findMin());
        System.out.println("  Find max: " + emptyBST.findMax());
        System.out.println("  Inorder traversal size: " + emptyBST.inorderTraversal().size());
        
        // Test insert and delete same item
        System.out.println("\nTesting insert and delete same item:");
        MenuItem testItem = new MenuItem("T001", "Test", 100, "Test");
        emptyBST.insert(testItem);
        System.out.println("  After insert - size: " + emptyBST.size());
        emptyBST.delete(testItem);
        System.out.println("  After delete - size: " + emptyBST.size());
        System.out.println("  Is empty again: " + emptyBST.isEmpty());
        
        // Test getAllByPriceDescending
        System.out.println("\nTesting descending order:");
        MenuBST testBST = new MenuBST();
        testBST.insert(new MenuItem("A", "A", 10, "Test"));
        testBST.insert(new MenuItem("B", "B", 20, "Test"));
        testBST.insert(new MenuItem("C", "C", 30, "Test"));
        
        List<MenuItem> desc = testBST.getAllByPriceDescending();
        System.out.print("  Descending order: ");
        for (MenuItem item : desc) {
            System.out.print("$" + item.getPrice() + " ");
        }
        System.out.println();
        
        boolean correctDesc = (desc.get(0).getPrice() == 30 && 
                              desc.get(1).getPrice() == 20 && 
                              desc.get(2).getPrice() == 10);
        System.out.println("  Correct descending: " + (correctDesc ? "✓ YES" : "✗ NO"));
    }
}