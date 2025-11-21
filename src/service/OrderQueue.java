package service;

import impl.HeapPriorityQueue;
import model.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Specialized Priority Queue for Order objects
 * Wraps the generic HeapPriorityQueue<Order> and provides order-specific operations
 *
 * Maximum number of orders is limited to 20.
 * @author tisaac
 */
public class OrderQueue {
    private HeapPriorityQueue<Order> orderQueue;
    private static final int MAX_ORDERS = 20;

    public OrderQueue() {
        this.orderQueue = new HeapPriorityQueue<>();
    }

    public boolean add(Order order) {
        if (order == null) return false;
        if (orderQueue.size() >= MAX_ORDERS) {
            System.out.println("Order is full for now. Cannot add more orders. Please try again later.");
            return false;
        }

        orderQueue.add(order);
        return true;
    }

    public Order poll() {
        return orderQueue.poll();
    }

    public Order peek() {
        return orderQueue.peek();
    }

    public int size() {
        return orderQueue.size();
    }

    public boolean isEmpty() {
        return orderQueue.isEmpty();
    }

    public void clear() {
        while (!isEmpty()) {
            poll();
        }
    }

    public List<Order> getAllOrders() {
        return orderQueue.getAllElements();
    }

    public Order cancelOrder(int orderNum) {
        Order order = findOrderByNumber(orderNum);

        if (order != null) {
            order.setStatus(Order.STATUS_CANCELLED);
        }
        return order;
    }

    public boolean updateOrderStatus(int orderNum, String newStatus) {
        Order order = findOrderByNumber(orderNum);
        if (order != null) {
            order.setStatus(newStatus);
            return true;
        }
        return false;
    }

    public Order findOrderByNumber(int orderNum) {
        for (Order order : getAllOrders()) {
            if (order.getOrderNumber() == orderNum) {
                return order;
            }
        }
        return null;
    }

    /**
     * @param status Status to filter orders by
     * @return List of orders with the specified status
     */
    public List<Order> getOrdersByStatus(String status) {
        List<Order> result = new ArrayList<>();
        if (status == null) {
            return result;
        }

        for (Order order : getAllOrders()) {
            if (status.equalsIgnoreCase(order.getStatus())) {
                result.add(order);
            }
        }
        return result;
    }

    /**
     * @param priority Priority to filter orders by
     * @return List of orders with the specified priority
     */
    public List<Order> getOrdersByPriority(int priority) {
        List<Order> result = new ArrayList<>();
        if (priority < Order.PRIORITY_NORMAL || priority > Order.PRIORITY_VIP) {
            return result;
        }

        for (Order order : getAllOrders()) {
            if (priority == order.getPriority()) {
                result.add(order);
            }
        }
        return result;
    }

    /**
     * @param status Status to count orders by
     * @return Number of orders with the specified status
     */
    public int countOrdersByStatus(String status) {
        return getOrdersByStatus(status).size();
    }

    /**
     * @param priority Priority to count orders by
     * @return Number of orders with the specified priority
     */
    public int countOrdersByPriority(int priority) {
        return getOrdersByPriority(priority).size();
    }

    public String getQueueStatistics() {
        StringBuilder stats = new StringBuilder();
        stats.append("Queue Statistics:\n");
        stats.append("Total orders: ").append(size()).append("\n");
        stats.append("Waiting: ").append(countOrdersByStatus(Order.STATUS_WAITING)).append("\n");
        stats.append("Cooking: ").append(countOrdersByStatus(Order.STATUS_COOKING)).append("\n");
        stats.append("Done: ").append(countOrdersByStatus(Order.STATUS_DONE)).append("\n");
        stats.append("\n");
        stats.append("Normal priority: ").append(countOrdersByPriority(Order.PRIORITY_NORMAL)).append("\n");
        stats.append("Delivery priority: ").append(countOrdersByPriority(Order.PRIORITY_DELIVERY)).append("\n");
        stats.append("VIP priority: ").append(countOrdersByPriority(Order.PRIORITY_VIP));

        return stats.toString();
    }

    public void printAllOrders() {
        System.out.println("\n=== Order Queue ===");
        System.out.println("Total orders: " + size());

        if (isEmpty()) {
            System.out.println("Queue is empty");
            return;
        }

        List<Order> orders = orderQueue.getAllElements();
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            System.out.println((i + 1) + ". " + order.getSummary() +
                             " [" + order.getStatusText() + "]");
        }
        System.out.println("=======================\n");
    }
}
