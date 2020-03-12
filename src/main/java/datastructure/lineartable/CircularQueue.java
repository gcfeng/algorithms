package datastructure.lineartable;

/**
 * 循环队列
 */
public class CircularQueue {
    private int[] items;
    private int front, tail, size;

    public CircularQueue(int maxSize) {
        front = tail = 0;
        size = maxSize + 1;
        items = new int[size];
    }

    public boolean isEmpty() { return front == tail; }

    public int size() {
        if (front > tail) return (tail + size - front);
        return tail - front;
    }

    public void enqueue(int data) {
        if ((tail + 1) % size == front) throw new RuntimeException("Queue full");
        items[tail] = data;
        tail = (tail + 1) % size;
    }

    public int dequeue() {
        if (isEmpty()) throw new RuntimeException("Queue empty");
        int data = items[front];
        front = (front + 1) % size;
        return data;
    }
}
