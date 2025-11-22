package controller;

import model.Order;
import service.DataManager;

import javax.xml.crypto.Data;

/**
 * Kitchen Management Controller
 * Handles operations related to kitchen order processing
 * decide what order to process next, update order status, etc.
 *
 * @author tisaac
 */
public class KitchenManagementController {

    /**
     * Preview the next order in the order queue without removing it
     * Useful for displaying the next order to kitchen staff
     *
     * @return the next order to process, or null if none
     */
    public Order previewNextOrder() {
        // peek next waiting order from order queue without removing it
        return DataManager.orderQueue.peek();
    }

    /**
     * Start processing the next order in the order queue
     * Update its status to 'cooking'
     * Move it to processed orders
     *
     * @return the order that is started, or null if none
     */
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

    /**
     * Cancel the given order
     * If the order is in 'waiting' status, it would be the top of the order queue
     * So we need to poll it from the order queue first, add it to processed orders
     * Then set its status to 'cancelled'
     *
     * @param order order to cancel
     * @return next order to process, or null if none
     */
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

    /**
     * Find and return the next order to process
     * See if there is any cooking order first
     * If none, start the next waiting order
     *
     * @return next order to process, or null if none
     */
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
