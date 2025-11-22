package controller;

import model.Order;
import service.DataManager;

import javax.xml.crypto.Data;

/**
 *
 * @author tisaac
 */
public class KitchenManagementController {

    public Order previewNextOrder() {
        // peek next waiting order from order queue without removing it
        return DataManager.orderQueue.peek();
    }

    public Order startNextOrder() {
        // get next waiting order from order queue
        Order next = DataManager.orderQueue.poll();
        if (next == null) return null;

        // update order status to 'cooking'
        next.setStatusCooking();

        DataManager.processedOrder.add(next);
        return next;
    }

    public Order finishOrder(Order order) {
        // update order status to 'done'
        order.setStatusDone();
        return moveOnToNext();
    }

    public Order cancelOrder(Order order) {
        // update order status to 'cancelled'
        if (order.getStatus().equals(Order.STATUS_WAITING)) {
            // if the order status is waiting, it would be the top of the order queue
            order = DataManager.orderQueue.poll();
            DataManager.processedOrder.add(order); // move to processed orders
        }

        order.setStatusCancelled(); // set status to cancelled
        return moveOnToNext();
    }

    private Order moveOnToNext() {
        // after done/cancel an order, find the next order in the top of the cooking list
        Order next;
        next = DataManager.processedOrder.findNextCookingOrder();

        // if all cooking orders are done/cancelled, start the next waiting order
        if (next == null) {
            // if no cooking order, try to preview the next waiting order
            next = previewNextOrder();
        }
        return next;
    }
}
