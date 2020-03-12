package datastructure.lineartable;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * 队列
 */
public class Queue<T> implements Iterable<T> {
    private LinkedList<T> list = new LinkedList<T>();

    public int size() {
        return list.size();
    }

    public boolean isEmpty() { return list.size() == 0; }

    /**
     * 获取队首元素
     */
    public T peek() {
        if (isEmpty()) throw new RuntimeException("Queue empty");
        return list.peekFirst();
    }

    /**
     * 入队
     */
    public void enqueue(T data) {
        list.add(data);
    }

    /**
     * 出队
     */
    public T dequeue() {
        if (isEmpty()) throw new RuntimeException("Queue empty");
        return list.removeFirst();
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }
}
