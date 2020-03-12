package datastructure.lineartable;

import java.util.Iterator;
import java.util.LinkedList;

public class Stack<T> implements Iterable<T> {
    private LinkedList<T> list = new LinkedList<T>();

    public int size() {
        return list.size();
    }

    public boolean isEmpty() { return list.size() == 0; }

    /**
     * 获取栈顶元素
     */
    public T peek() {
        if (isEmpty()) throw new RuntimeException("Stack empty");
        return list.peekLast();
    }

    /**
     * 入栈
     */
    public void push(T data) {
        list.add(data);
    }

    /**
     * 出栈
     */
    public T pop() {
        if (isEmpty()) throw new RuntimeException("Stack empty");
        return list.removeLast();
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }
}
