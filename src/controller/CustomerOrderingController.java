package controller;

import model.MenuItem;
import model.Order;
import model.OrderItem;
import service.DataManager;
import service.MenuService;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for Customer Ordering Page
 * Handles all business logic for the ordering process
 * Uses existing Service layer (MenuService, DataManager)
 */
public class CustomerOrderingController {
    
    // Cart data - local to this controller
    private List<OrderItem> cartItems;
    
    // Constructor
    public CustomerOrderingController() {
        this.cartItems = new ArrayList<>();
        
        // Ensure DataManager is initialized
        if (MenuService.isEmpty()) {
            DataManager.initialize();
        }
    }
    
    
    // ============================================================
    // MENU MANAGEMENT - Delegate to MenuService
    // ============================================================
    
    /**
     * Get all menu items
     */
    public List<MenuItem> getAllMenuItems() {
        return MenuService.getAllMenuItems();
    }
    
    /**
     * Get available menu items only
     */
    public List<MenuItem> getAvailableMenuItems() {
        List<MenuItem> result = new ArrayList<>();
        for (MenuItem item : MenuService.getAllMenuItems()) {
            if (item.isAvailable()) {
                result.add(item);
            }
        }
        return result;
    }
    
    /**
     * Get menu items by category
     */
    public List<MenuItem> getMenuItemsByCategory(String category) {
        return MenuService.getMenuByCategory(category);
    }
    
    /**
     * Get available menu items by category
     */
    public List<MenuItem> getAvailableMenuItemsByCategory(String category) {
        List<MenuItem> result = new ArrayList<>();
        for (MenuItem item : MenuService.getMenuByCategory(category)) {
            if (item.isAvailable()) {
                result.add(item);
            }
        }
        return result;
    }
    
    /**
     * Get all categories
     */
    public List<String> getAllCategories() {
        return MenuService.getAllCategories();
    }
    
    /**
     * Search menu item by ID
     */
    public MenuItem getMenuItemById(String id) {
        return MenuService.getMenuItemById(id);
    }
    
    
    // ============================================================
    // CART MANAGEMENT
    // ============================================================
    
    /**
     * Add item to cart
     * If item already exists, increase quantity
     * @param menuItem - the menu item to add
     * @return true if successfully added
     */
    public boolean addToCart(MenuItem menuItem) {
        if (menuItem == null || !menuItem.isAvailable()) {
            return false;
        }
        
        // Check if item already in cart
        for (OrderItem item : cartItems) {
            if (item.getMenuItem().getId().equals(menuItem.getId())) {
                // Item exists, increase quantity
                item.setQuantity(item.getQuantity() + 1);
                return true;
            }
        }
        
        // Item not in cart, add new OrderItem
        OrderItem newItem = new OrderItem(menuItem, 1);
        cartItems.add(newItem);
        return true;
    }
    
    /**
     * Get all items in cart
     */
    public List<OrderItem> getCartItems() {
        return new ArrayList<>(cartItems); // Return a copy
    }
    
    /**
     * Increase quantity of an item in cart
     */
    public void increaseQuantity(OrderItem item) {
        if (item != null && cartItems.contains(item)) {
            item.setQuantity(item.getQuantity() + 1);
        }
    }
    
    /**
     * Decrease quantity of an item in cart
     * If quantity becomes 0, remove the item
     */
    public void decreaseQuantity(OrderItem item) {
        if (item == null || !cartItems.contains(item)) {
            return;
        }
        
        int currentQty = item.getQuantity();
        
        if (currentQty > 1) {
            item.setQuantity(currentQty - 1);
        } else {
            // Remove item if quantity is 1
            cartItems.remove(item);
        }
    }
    
    /**
     * Remove an item from cart
     */
    public void removeFromCart(OrderItem item) {
        if (item != null) {
            cartItems.remove(item);
        }
    }
    
    /**
     * Clear all items from cart
     */
    public void clearCart() {
        cartItems.clear();
    }
    
    /**
     * Check if cart is empty
     */
    public boolean isCartEmpty() {
        return cartItems.isEmpty();
    }
    
    /**
     * Get number of items in cart (considering quantities)
     */
    public int getCartItemCount() {
        int total = 0;
        for (OrderItem item : cartItems) {
            total += item.getQuantity();
        }
        return total;
    }
    
    /**
     * Calculate total price of cart
     */
    public double calculateTotal() {
        double total = 0;
        for (OrderItem item : cartItems) {
            total += item.getSubtotal();
        }
        return total;
    }
    
    /**
     * Get formatted total price
     */
    public String getFormattedTotal() {
        return String.format("$%.0f", calculateTotal());
    }
    
    
    // ============================================================
    // ORDER MANAGEMENT - Uses OrderQueue ADT
    // ============================================================
    
    /**
     * Create order from current cart
     * @param priority - order priority (PRIORITY_NORMAL, PRIORITY_DELIVERY, PRIORITY_VIP)
     * @return the created Order object, or null if cart is empty
     */
    public Order checkout(int priority) {
        if (cartItems.isEmpty()) {
            return null;
        }
        
        // Create a copy of cart items for the order
        List<OrderItem> orderItems = new ArrayList<>(cartItems);
        
        // Create new order with auto-increment order number
        Order order = new Order(DataManager.orderCounter, orderItems, priority);
        DataManager.orderCounter++;
        
        // Add order to queue using ADT (enqueue operation)
        boolean success = DataManager.orderQueue.enqueue(order);
        
        if (success) {
            System.out.println("✓ Order #" + order.getOrderNumber() + " added to queue");
            System.out.println("  Priority: " + order.getPriorityText());
            System.out.println("  Total: " + order.getFormattedTotalPrice());
            System.out.println("  Current queue size: " + DataManager.orderQueue.size());
        }
        
        // Clear cart after checkout
        clearCart();
        
        return order;
    }
    
    /**
     * Create order with normal priority
     */
    public Order checkout() {
        return checkout(Order.PRIORITY_NORMAL);
    }
    
    /**
     * Get current queue size
     */
    public int getQueueSize() {
        return DataManager.orderQueue.size();
    }
    
    /**
     * Get all orders in queue
     */
    public List<Order> getAllOrdersInQueue() {
        return DataManager.orderQueue.getAllOrders();
    }
    
    
    // ============================================================
    // SEARCH AND FILTER
    // ============================================================
    
    /**
     * Search menu items by name (case-insensitive)
     * Uses MenuService for searching
     */
    public List<MenuItem> searchMenuItems(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAvailableMenuItems();
        }
        
        // Use MenuService search function
        List<MenuItem> results = MenuService.searchMenuByName(keyword);
        
        // Filter only available items
        List<MenuItem> availableResults = new ArrayList<>();
        for (MenuItem item : results) {
            if (item.isAvailable()) {
                availableResults.add(item);
            }
        }
        
        return availableResults;
    }
    
    /**
     * Get menu items by price range
     */
    public List<MenuItem> getMenuByPriceRange(double minPrice, double maxPrice) {
        List<MenuItem> results = MenuService.getMenuByPriceRange(minPrice, maxPrice);
        
        // Filter only available items
        List<MenuItem> availableResults = new ArrayList<>();
        for (MenuItem item : results) {
            if (item.isAvailable()) {
                availableResults.add(item);
            }
        }
        
        return availableResults;
    }
    
    
    // ============================================================
    // VALIDATION
    // ============================================================
    
    /**
     * Validate if checkout is possible
     * @return error message, or null if valid
     */
    public String validateCheckout() {
        if (cartItems.isEmpty()) {
            return "Your cart is empty. Please add items before checkout.";
        }
        
        // Check if all items are still available
        for (OrderItem item : cartItems) {
            if (!item.getMenuItem().isAvailable()) {
                return "Item '" + item.getMenuItem().getName() + "' is no longer available.";
            }
        }
        
        return null; // No errors
    }
    
    
    // ============================================================
    // STATISTICS - Using MenuService
    // ============================================================
    
    /**
     * Get menu statistics
     */
    public String getMenuStatistics() {
        StringBuilder stats = new StringBuilder();
        stats.append("Total menu items: ").append(MenuService.getMenuCount()).append("\n");
        stats.append("Average price: $").append(String.format("%.0f", MenuService.getAveragePrice())).append("\n");
        
        MenuItem cheapest = MenuService.getCheapestItem();
        if (cheapest != null) {
            stats.append("Cheapest item: ").append(cheapest.getName())
                 .append(" (").append(cheapest.getFormattedPrice()).append(")\n");
        }
        
        MenuItem expensive = MenuService.getMostExpensiveItem();
        if (expensive != null) {
            stats.append("Most expensive: ").append(expensive.getName())
                 .append(" (").append(expensive.getFormattedPrice()).append(")");
        }
        
        return stats.toString();
    }
}