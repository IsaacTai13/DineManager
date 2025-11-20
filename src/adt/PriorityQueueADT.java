package adt;

/**
 * ADT Interface for Menu Priority Queue
 * This interface accepts generic type T to allow flexibility in the type of elements stored.
 * But T must implement Comparable to ensure proper ordering in the priority queue.
 * @author tisaac
 */
public interface PriorityQueueADT<T extends Comparable<T>> {
    public void add(T item);
    public T poll();
    public T peek();
    public int size();
    public boolean isEmpty();
}
