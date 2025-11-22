package service;

import model.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * ProcessedOrder class to manage a list of processed orders.
 * After an order status is not 'waiting', it will be moved to this list.
 * @author tisaac
 */
public class ProcessedOrder {
    List<Order> processedOrders;

    public ProcessedOrder() {
        this.processedOrders = new ArrayList<>();
    }

    public void add(Order order) {
        if (order != null) {
            processedOrders.add(order);
        }
    }

    public int getTotal() {
        return processedOrders.size();
    }

    public int getCountByStatus(String status) {
        int count = 0;
        for (Order order : processedOrders) {
            if (order.getStatus().equals(status)) {
                count++;
            }
        }
        return count;
    }

    public Order findNextCookingOrder() {
        for (Order order : processedOrders) {
            if (order.getStatus().equals(Order.STATUS_COOKING)) {
                return order;
            }
        }
        return null;
    }

    public int getCountFinished() {
        return getCountByStatus(Order.STATUS_DONE);
    }

    public int getCountCancelled() {
        return getCountByStatus(Order.STATUS_CANCELLED);
    }

    public int getCountCooking() {
        return getCountByStatus(Order.STATUS_COOKING);
    }

    public Order getOrderByOrderNum(int orderNum) {
        for (Order order : processedOrders) {
            if (order.getOrderNumber() == orderNum) {
                System.out.println("Order with ID " + orderNum + " not found.");
                return order;
            }
        }
        return null;
    }

    public List<Order> getProcessedOrders() {
        return processedOrders;
    }
}
