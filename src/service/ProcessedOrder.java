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
    ArrayList<Order> processedOrders;

    public ProcessedOrder() {
        this.processedOrders = new ArrayList<>();
    }

    public void add(Order order) {
        if (order != null) {
            processedOrders.add(order);
        }
    }

    public Order getOrderById(int orderNum) {
        for (Order order : processedOrders) {
            if (order.getOrderNumber() == orderNum) {
                System.out.println("Order with ID " + orderNum + " not found.");
                return order;
            }
        }
        return null;
    }

    /**
     * Set Order to finished based on order number
     */
    public void setOrderFinished(int orderNum) {
        Order order = getOrderById(orderNum);
        order.setStatus(Order.STATUS_DONE);
    }

    /**
     * Set Order to cancelled based on order number
     */
    public void setOrderCancelled(int orderNum) {
        Order order = getOrderById(orderNum);
        order.setStatus(Order.STATUS_CANCELLED);
    }

    /**
     * Set Order to cooking based on order number
     */
    public void setOrderCooking(int orderNum) {
        Order order = getOrderById(orderNum);
        order.setStatus(Order.STATUS_COOKING);
    }

    public List<Order> getProcessedOrders() {
        return processedOrders;
    }
}
