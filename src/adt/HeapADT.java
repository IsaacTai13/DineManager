package adt;

/**
 * ADT Interface for Menu Heap
 * This interface accepts generic type T to allow flexibility in the type of elements stored.
 * But T must implement Comparable to ensure proper ordering in the heap.
 * This interface is for priority queue operations using a heap data structure.
 * @author tisaac
 */
public interface HeapADT<T extends Comparable<T>> {
    public void insert(T item);
    public T removeMin();
    public T peek();
    public int size();
    public boolean isEmpty();
}
