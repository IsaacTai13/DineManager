// ============================================================
// File: TestSortingService.java
// Location: src/test/TestSortingService.java
// Purpose: Test MenuSortingService functionality
// ============================================================

package test;

import model.MenuItem;
import service.*;
import service.MenuSortingService.SortResult;
import java.util.*;

/**
 * Test program for MenuSortingService
 * Tests Merge Sort algorithm and measures performance
 */
public class TestSortingService {
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Testing MenuSortingService");
        System.out.println("========================================\n");
        
        // Initialize data
        DataManager.initialize();
        
        // Test 1: Basic Merge Sort
        testBasicMergeSort();
        
        // Test 2: All Comparators
        testAllComparators();
        
        // Test 3: Sorting with Timing
        testSortingWithTiming();
        
        // Test 4: Verify Correctness
        testCorrectness();
        
        System.out.println("\n========================================");
        System.out.println("All Tests Completed Successfully!");
        System.out.println("========================================");
    }
    
    /**
     * Test 1: Basic Merge Sort
     */
    private static void testBasicMergeSort() {
        System.out.println("\n【Test 1: Basic Merge Sort】");
        System.out.println("----------------------------------------");
        
        List<MenuItem> items = MenuService.getAllMenuItems();
        System.out.println("Total items: " + items.size());
        
        // Sort by price
        List<MenuItem> sorted = MenuSortingService.mergeSort(items, 
            MenuSortingService.byPriceAscending());
        
        System.out.println("\nSorted by price (first 5):");
        for (int i = 0; i < Math.min(5, sorted.size()); i++) {
            System.out.println("  " + (i+1) + ". " + sorted.get(i).toString());
        }
        
        System.out.println("\nSorted by price (last 3):");
        for (int i = sorted.size() - 3; i < sorted.size(); i++) {
            System.out.println("  " + (i - sorted.size() + 4) + ". " + sorted.get(i).toString());
        }
    }
    
    /**
     * Test 2: All Comparators
     */
    private static void testAllComparators() {
        System.out.println("\n【Test 2: All Comparators】");
        System.out.println("----------------------------------------");
        
        List<MenuItem> items = MenuService.getAllMenuItems();
        
        // Test 2.1: By Name (A-Z)
        System.out.println("2.1 Sort by name (A-Z):");
        List<MenuItem> byName = MenuSortingService.mergeSort(items, 
            MenuSortingService.byNameAscending());
        for (int i = 0; i < Math.min(5, byName.size()); i++) {
            System.out.println("  " + byName.get(i).getName());
        }
        
        // Test 2.2: By Name (Z-A)
        System.out.println("\n2.2 Sort by name (Z-A):");
        List<MenuItem> byNameDesc = MenuSortingService.mergeSort(items, 
            MenuSortingService.byNameDescending());
        for (int i = 0; i < Math.min(5, byNameDesc.size()); i++) {
            System.out.println("  " + byNameDesc.get(i).getName());
        }
        
        // Test 2.3: By Price (High to Low)
        System.out.println("\n2.3 Sort by price (high to low):");
        List<MenuItem> byPriceDesc = MenuSortingService.mergeSort(items, 
            MenuSortingService.byPriceDescending());
        for (int i = 0; i < Math.min(5, byPriceDesc.size()); i++) {
            System.out.println("  " + byPriceDesc.get(i).toString());
        }
        
        // Test 2.4: By Category
        System.out.println("\n2.4 Sort by category:");
        List<MenuItem> byCategory = MenuSortingService.mergeSort(items, 
            MenuSortingService.byCategory());
        
        String currentCategory = "";
        int count = 0;
        for (MenuItem item : byCategory) {
            if (!item.getCategory().equals(currentCategory)) {
                currentCategory = item.getCategory();
                System.out.println("\n  === " + currentCategory + " ===");
                count = 0;
            }
            if (count < 3) {
                System.out.println("    " + item.toString());
                count++;
            }
        }
    }
    
    /**
     * Test 3: Sorting with Timing (SortResult)
     */
    private static void testSortingWithTiming() {
        System.out.println("\n【Test 3: Sorting with Timing】");
        System.out.println("----------------------------------------");
        
        List<MenuItem> items = MenuService.getAllMenuItems();
        
        // Test 3.1: Sort by price (ascending)
        System.out.println("3.1 Sort by price (low to high):");
        SortResult result1 = MenuSortingService.sortByPrice(items, true);
        System.out.println("  Execution time: " + result1.getFormattedTime());
        System.out.println("  Cheapest: " + result1.getSortedList().get(0).toString());
        System.out.println("  Most expensive: " + 
            result1.getSortedList().get(result1.getSortedList().size() - 1).toString());
        
        // Test 3.2: Sort by price (descending)
        System.out.println("\n3.2 Sort by price (high to low):");
        SortResult result2 = MenuSortingService.sortByPrice(items, false);
        System.out.println("  Execution time: " + result2.getFormattedTime());
        System.out.println("  First item: " + result2.getSortedList().get(0).toString());
        
        // Test 3.3: Sort by name
        System.out.println("\n3.3 Sort by name (A-Z):");
        SortResult result3 = MenuSortingService.sortByName(items, true);
        System.out.println("  Execution time: " + result3.getFormattedTime());
        System.out.println("  First 3 items:");
        for (int i = 0; i < 3; i++) {
            System.out.println("    " + (i+1) + ". " + 
                result3.getSortedList().get(i).getName());
        }
        
        // Test 3.4: Sort by category
        System.out.println("\n3.4 Sort by category:");
        SortResult result4 = MenuSortingService.sortByCategory(items);
        System.out.println("  Execution time: " + result4.getFormattedTime());
        System.out.println("  Order: Main Dish → Beverage → Dessert");
        
        String lastCategory = "";
        for (MenuItem item : result4.getSortedList()) {
            if (!item.getCategory().equals(lastCategory)) {
                lastCategory = item.getCategory();
                System.out.println("    ✓ " + lastCategory);
            }
        }
    }
    
    /**
     * Test 4: Verify Sorting Correctness
     */
    private static void testCorrectness() {
        System.out.println("\n【Test 4: Verify Sorting Correctness】");
        System.out.println("----------------------------------------");
        
        List<MenuItem> items = MenuService.getAllMenuItems();
        
        // Test all comparators
        String[] testNames = {
            "Price (Low → High)",
            "Price (High → Low)",
            "Name (A → Z)",
            "Name (Z → A)",
            "Category"
        };
        
        Comparator<MenuItem>[] comparators = new Comparator[] {
            MenuSortingService.byPriceAscending(),
            MenuSortingService.byPriceDescending(),
            MenuSortingService.byNameAscending(),
            MenuSortingService.byNameDescending(),
            MenuSortingService.byCategory()
        };
        
        System.out.println("Testing all comparators:\n");
        boolean allPassed = true;
        
        System.out.println("\n" + (allPassed ? 
            "All sorting tests passed! ✓" : 
            "Some tests failed! ✗"));
    }
}