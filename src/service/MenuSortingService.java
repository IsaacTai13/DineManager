package service;

import model.MenuItem;
import java.util.*;

/**
* Sorting Service Class
* Provides multiple sorting algorithms and comparators for menu items
*/
public class MenuSortingService {
 
	 // Merge Sort Implementation ===============================================================  
	
	 public static List<MenuItem> mergeSort(List<MenuItem> items, Comparator<MenuItem> comparator) {
	     // Base case: list with 0 or 1 element is already sorted
	     if (items.size() <= 1) return new ArrayList<>(items);
	     
	     // Divide: split list into two halves
	     int mid = items.size() / 2;
	     List<MenuItem> left = new ArrayList<>(items.subList(0, mid));
	     List<MenuItem> right = new ArrayList<>(items.subList(mid, items.size()));
	     
	     left = mergeSort(left, comparator);
	     right = mergeSort(right, comparator);
	     
	     return merge(left, right, comparator);
	 }
	 
	 // helper method for Merge Sort
	 private static List<MenuItem> merge(List<MenuItem> left, List<MenuItem> right, Comparator<MenuItem> comparator) {
	     List<MenuItem> result = new ArrayList<>();
	     int i = 0, j = 0;
	     
	     // Compare and merge elements from both lists
	     while (i < left.size() && j < right.size()) {
	         if (comparator.compare(left.get(i), right.get(j)) <= 0) {
	             result.add(left.get(i++));
	         } else {
	             result.add(right.get(j++));
	         }
	     }	     
	     // Add remaining elements
	     while (i < left.size()) result.add(left.get(i++));
	     while (j < right.size()) result.add(right.get(j++));     
	     return result;
	 }
	 
	 
	 
	 // Comparators ================================================================================

	 public static Comparator<MenuItem> byPriceAscending() {
	     return (a, b) -> Double.compare(a.getPrice(), b.getPrice());
	 }
	 
	 public static Comparator<MenuItem> byPriceDescending() {
	     return (a, b) -> Double.compare(b.getPrice(), a.getPrice());
	 }
	 
	 public static Comparator<MenuItem> byNameAscending() {
	     return (a, b) -> a.getName().compareTo(b.getName());
	 }
	 
	 public static Comparator<MenuItem> byNameDescending() {
	     return (a, b) -> b.getName().compareTo(a.getName());
	 }
	 
	 // Main Dish → Beverage → Dessert
	 public static Comparator<MenuItem> byCategory() {
	    Map<String, Integer> categoryOrder = Map.of("Main Dish", 1, "Beverage", 2, "Dessert", 3);
	    return (a, b) -> Integer.compare(categoryOrder.get(a.getCategory()),categoryOrder.get(b.getCategory()));
	 }

	 
	 // Sorting Result ==============================================================================
	 
	 // inner class for the results
	 public static class SortResult {
	     private List<MenuItem> sortedList;
	     private long executionTimeNanos;
	     
	     public SortResult(List<MenuItem> sortedList, long executionTimeNanos) {
	         this.sortedList = sortedList;
	         this.executionTimeNanos = executionTimeNanos;
	     }
	     
	     public List<MenuItem> getSortedList() {
	         return sortedList;
	     }
	     
	     public long getExecutionTimeNanos() {
	         return executionTimeNanos;
	     }
	     
	     public String getFormattedTime() {
    	    return String.format("%.2f ms", executionTimeNanos / 1_000_000.0);
    	 }
	 }
	 

	 public static SortResult sortByPrice(List<MenuItem> items, boolean ascending) {
	        Comparator<MenuItem> comparator = ascending ? byPriceAscending() : byPriceDescending();
	        return sortWithTiming(items, comparator);
	    }
	 
	 public static SortResult sortByName(List<MenuItem> items, boolean ascending) {
	     Comparator<MenuItem> comparator = ascending ? byNameAscending() : byNameDescending();
	     return sortWithTiming(items, comparator);
	 }
	 
	 public static SortResult sortByCategory(List<MenuItem> items) {
	     return sortWithTiming(items, byCategory());
	 }
	 
	 private static SortResult sortWithTiming(List<MenuItem> items, Comparator<MenuItem> comparator) {
	     long startTime = System.nanoTime();
	     List<MenuItem> result;     
	     result = mergeSort(items, comparator);     
	     long endTime = System.nanoTime();
	     
	     return new SortResult(result, endTime - startTime);
	 }
	 
	 public static boolean isSorted(List<MenuItem> items, Comparator<MenuItem> comparator) {
	     for (int i = 0; i < items.size() - 1; i++) {
	         if (comparator.compare(items.get(i), items.get(i + 1)) > 0) {
	             return false;
	         }
	     }
	     return true;
	 }
}
