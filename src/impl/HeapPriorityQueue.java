package impl;

import adt.PriorityQueueADT;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a Priority Queue using a Min Heap
 * This class accepts generic type T to allow flexibility in the type of elements stored.
 * But T must implement Comparable to ensure proper ordering in the priority queue.
 *
 * <p>For example, you can store Orders in the priority queue, and they will be ordered</p>
 * @author tisaac
 */
public class HeapPriorityQueue<T extends Comparable<T>> implements PriorityQueueADT<T> {
    private MinHeap<T> heap;

    /**
     * Constructor - Initialize empty priority queue
     */
    public HeapPriorityQueue() {
        heap = new MinHeap<>();
    }

    @Override
    public void add(T item) {
        heap.insert(item);
    }

    @Override
    public T poll() {
        return heap.removeMin();
    }

    @Override
    public T peek() {
        return heap.peek();
    }

    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public boolean isEmpty() {
        return heap.size() == 0;
    }

    public List<T> getAllElements() {
        return heap.toList();
    }
}
