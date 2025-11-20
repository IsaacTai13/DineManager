package test;

import service.OrderQueue;
import model.MenuItem;
import model.Order;
import model.OrderItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Test class for OrderQueue ADT
 * Demonstrates queue operations and priority handling
 */
public class OrderQueueTest {
    
    public static void main(String[] args) {
        System.out.println("=== Testing OrderQueue ADT ===\n");
        
        // Create queue instance
        OrderQueue queue = new OrderQueue();
        
        
        // ============================================================
        // Test 1: Basic add/Dequeue
        // ============================================================
        System.out.println("Test 1: Basic add/Dequeue Operations");
        
        // Create sample orders
        Order order1 = createSampleOrder(1, Order.PRIORITY_NORMAL);
        Order order2 = createSampleOrder(2, Order.PRIORITY_NORMAL);
        Order order3 = createSampleOrder(3, Order.PRIORITY_NORMAL);
        
        queue.add(order1);
        queue.add(order2);
        queue.add(order3);
        
        System.out.println("Queue size: " + queue.size());
        System.out.println();
        
        
        // ============================================================
        // Test 2: Priority Handling
        // ============================================================
        System.out.println("Test 2: Priority Handling");
        
        Order normalOrder = createSampleOrder(4, Order.PRIORITY_NORMAL);
        Order deliveryOrder = createSampleOrder(5, Order.PRIORITY_DELIVERY);
        Order vipOrder = createSampleOrder(6, Order.PRIORITY_VIP);
        
        queue.add(normalOrder);
        queue.add(deliveryOrder);
        queue.add(vipOrder);
        
        System.out.println("Added orders with different priorities");
        System.out.println("Queue size: " + queue.size());
        System.out.println();
        
        
        // ============================================================
        // Test 3: Peek Operation
        // ============================================================
        System.out.println("Test 3: Peek Operation");
        
        Order nextOrder = queue.peek();
        System.out.println("Next order (peek): " + 
            (nextOrder != null ? nextOrder.getSummary() : "null"));
        System.out.println("Queue size after peek: " + queue.size());
        System.out.println();
        
        
        // ============================================================
        // Test 4: Dequeue by Priority
        // ============================================================
        System.out.println("Test 4: Dequeue Operations (Priority Order)");
        
        System.out.println("Processing orders in priority order:");
        int count = 1;
        while (!queue.isEmpty()) {
            Order order = queue.poll();
            System.out.println(count + ". " + order.getSummary() + 
                             " [Priority: " + order.getPriorityText() + "]");
            count++;
        }
        System.out.println("Queue is now empty: " + queue.isEmpty());
        System.out.println();
        
        
        // ============================================================
        // Test 5: Search and Filter Operations
        // ============================================================
        System.out.println("Test 5: Search and Filter Operations");
        
        // Add orders with different statuses
        Order waiting1 = createSampleOrder(10, Order.PRIORITY_NORMAL);
        Order waiting2 = createSampleOrder(11, Order.PRIORITY_VIP);
        Order cooking = createSampleOrder(12, Order.PRIORITY_DELIVERY);
        cooking.setStatus(Order.STATUS_COOKING);
        
        queue.add(waiting1);
        queue.add(waiting2);
        queue.add(cooking);
        
        System.out.println("Total orders: " + queue.size());
        
        List<Order> waitingOrders = queue.getOrdersByStatus(Order.STATUS_WAITING);
        System.out.println("Waiting orders: " + waitingOrders.size());
        
        List<Order> vipOrders = queue.getOrdersByPriority(Order.PRIORITY_VIP);
        System.out.println("VIP orders: " + vipOrders.size());
        
        Order found = queue.findOrderByNumber(11);
        System.out.println("Found order #11: " + 
            (found != null ? found.getSummary() : "not found"));
        System.out.println();
        
        
        // ============================================================
        // Test 6: Update and Remove Operations
        // ============================================================
        System.out.println("Test 6: Update and Remove Operations");
        
        // Update status
        boolean updated = queue.updateOrderStatus(10, Order.STATUS_COOKING);
        System.out.println("Update order #10 status: " + updated);
        
        // Cancel order
        Order canceledOrder = queue.cancelOrder(11);
        System.out.println("canceledOrder order #11: " + canceledOrder.getStatus());
        System.out.println();
        
        
        // ============================================================
        // Test 7: Clear Operation
        // ============================================================
        System.out.println("Test 7: Clear Operation");
        
        System.out.println("Queue size before clear: " + queue.size());
        queue.clear();
        System.out.println("Queue size after clear: " + queue.size());
        System.out.println("Is empty: " + queue.isEmpty());
        System.out.println();
        
        
        // ============================================================
        // Test 8: Statistics
        // ============================================================
        System.out.println("Test 8: Queue Statistics");
        
        // Add variety of orders
        queue.add(createSampleOrder(20, Order.PRIORITY_NORMAL));
        queue.add(createSampleOrder(21, Order.PRIORITY_VIP));
        queue.add(createSampleOrder(22, Order.PRIORITY_DELIVERY));
        queue.add(createSampleOrder(23, Order.PRIORITY_NORMAL));
        
        // Update some statuses
        queue.updateOrderStatus(20, Order.STATUS_COOKING);
        queue.updateOrderStatus(21, Order.STATUS_DONE);
        
        // Print statistics (if using OrderQueue implementation)
        if (queue instanceof OrderQueue) {
            System.out.println(((OrderQueue) queue).getQueueStatistics());
        }
        
        
        System.out.println("\n=== All Tests Completed! ===");
    }
    
    
    /**
     * Helper method to create a sample order
     */
    private static Order createSampleOrder(int orderNum, int priority) {
        // Create sample menu items
        MenuItem burger = new MenuItem("M001", "Burger", 150, "Main Dish");
        MenuItem coke = new MenuItem("D001", "Coke", 30, "Beverage");
        
        // Create order items
        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem(burger, 1));
        items.add(new OrderItem(coke, 1));
        
        // Create and return order
        return new Order(orderNum, items, priority);
    }
}