package adt;
import model.MenuItem;
import java.util.ArrayList;
import java.util.List;

// Based on BST data structure
public class MenuBST {
    
    private BST<MenuItem> bst;
    
    public MenuBST() {
        this.bst = new BST<>(this::compareMenuItems);
    }
    
    // Comparator for MenuItem, compares by price first, then by ID if prices are equal
    private int compareMenuItems(MenuItem a, MenuItem b) {
        int priceCompare = Double.compare(a.getPrice(), b.getPrice());
        if (priceCompare != 0) return priceCompare;
        
        return a.getId().compareTo(b.getId());
    }
    
    // ============================================================
    // Basic Operations
    // ============================================================
    
    public void insert(MenuItem item) {
        bst.insert(item);
    }
    
    public void delete(MenuItem item) {
        bst.delete(item);
    }
    
    public boolean search(MenuItem item) {
        return bst.search(item);
    }
    
    public List<MenuItem> inorderTraversal() {
        return bst.inorderTraversal();
    }
    
    public MenuItem findMin() {
        return bst.findMin();
    }
    
    public MenuItem findMax() {
        return bst.findMax();
    }
    
    public boolean isEmpty() {
        return bst.isEmpty();
    }
    
    public int size() {
        return bst.size();
    }
    
    // ============================================================
    // MenuItem: Specific Operations
    // ============================================================
    
    // Range search for menu items within a price range and return all items
    public List<MenuItem> rangeSearch(double minPrice, double maxPrice) {
        List<MenuItem> result = new ArrayList<>();
        List<MenuItem> allItems = bst.inorderTraversal();
        
        for (MenuItem item : allItems) {
            double price = item.getPrice();
            if (price >= minPrice && price <= maxPrice) result.add(item);          
            if (price > maxPrice) break; // Since items are sorted by price, we can stop early
        }
        
        return result;
    }
    
    // Same logic as inorderTraversal()
    public List<MenuItem> getAllByPrice() {
        return inorderTraversal();
    }
    
    // Get all menu items sorted by price (descending)
    public List<MenuItem> getAllByPriceDescending() {
        List<MenuItem> items = inorderTraversal();

        List<MenuItem> reversed = new ArrayList<>();
        for (int i = items.size() - 1; i >= 0; i--) {
            reversed.add(items.get(i));
        }
        return reversed;
    }
}