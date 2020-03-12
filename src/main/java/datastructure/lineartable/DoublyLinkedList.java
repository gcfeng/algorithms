package datastructure.lineartable;

import java.util.Iterator;

/**
 * 双向链表
 */
public class DoublyLinkedList<T> implements Iterable<T> {
    private int size = 0;
    private Node<T> head = null;
    private Node<T> tail = null;

    /**
     * 结点
     */
    private static class Node<T> {
        private T data;
        private Node<T> prev, next;

        public Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() { return data.toString(); }
    }

    /**
     * 获取链表结点数量
     */
    public int size() {
        return size;
    }

    /**
     * 判断链表是否为空
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 在尾部添加结点
     * O(1)
     */
    public void addLast(T data) {
        if (isEmpty()) {
            head = tail = new Node<T>(data, null, null);
        } else {
            tail.next = new Node<T>(data, tail, null);
            tail = tail.next;
        }
        size++;
    }

    /**
     * 在头部添加结点
     * O(1)
     */
    public void addFirst(T data) {
        if (isEmpty()) {
            head = tail = new Node<T>(data, null, null);
        } else {
            head.prev = new Node<T>(data, null, head);
            head = head.prev;
        }
        size++;
    }

    /**
     * 在指定索引处添加结点
     */
    public void addAt(int index, T data) {
        if (index < 0) throw new RuntimeException("Illegal Index");
        if (index == 0) {
            addFirst(data);
            return;
        }
        if (index == size) {
            addLast(data);
            return;
        }
        Node<T> node = head;
        for (int i = 0; i < index - 1; i++) {
            node = head.next;
        }
        Node<T> newNode = new Node<T>(data, node, node.next);
        node.next.prev = newNode;
        node.next = newNode;
        size++;
    }

    /**
     * 获取头部结点
     */
    public T peekFirst() {
        if (isEmpty()) throw new RuntimeException("Empty list");
        return head.data;
    }

    /**
     * 获取尾部结点
     */
    public T peekLast() {
        if (isEmpty()) throw new RuntimeException("Empty list");
        return tail.data;
    }

    /**
     * 获取指定位置的结点
     * O(n)
     */
    public T peekAt(int index) {
        if (index < 0 || index >= size) throw new RuntimeException("Illegal index");
        if (index == 0) return peekFirst();
        if (index == size - 1) return peekLast();

        if (index > size / 2) {
            Node<T> node = tail;
            for (int i = size - 1; i >= 0; i--) {
                if (i == index) return node.data;
                node = node.prev;
            }
        } else {
            Node<T> node = head;
            for (int i = 0; i < size; i++) {
                if (i == index) return node.data;
                node = node.next;
            }
        }
        return null;
    }

    /**
     * 删除头部结点
     * O(1)
     */
    public T removeFirst() {
        if (isEmpty()) throw new RuntimeException("Empty list");
        T data = head.data;
        head = head.next;
        size--;

        if (isEmpty()) tail = null;
        else head.prev = null;

        return data;
    }

    /**
     * 删除尾部结点
     */
    public T removeLast() {
        if (isEmpty()) throw new RuntimeException("Empty list");
        T data = tail.data;
        tail = tail.prev;
        size--;

        if (isEmpty()) head = null;
        else tail.next = null;

        return data;
    }

    /**
     * 删除指定索引的结点
     */
    public T removeAt(int index) {
        if (index < 0 || index >= size) throw new RuntimeException("Illegal index");
        if (index == 0) return removeFirst();
        if (index == size - 1) return removeLast();

        Node<T> node;
        if (index > size / 2) {
            node = tail;
            for (int i = size - 1; i >= 0; i--) {
                if (i == index) break;
                node = node.prev;
            }
        } else {
            node = head;
            for (int i = 0; i < size; i++) {
                if (i == index) break;
                node = node.next;
            }
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;

        T data = node.data;

        // 清除数据
        node.data = null;
        node = node.prev = node.next = null;

        size--;

        return data;
    }

    /**
     * 删除指定数据
     * O(n)
     */
    public boolean remove(Object obj) {
        Node<T> node = head;
        if (obj == null) {
            for (int i = 0; node != null; node = node.next, i++) {
                if (node.data == null) {
                    removeAt(i);
                    return true;
                }
            }
        } else {
            for (int i = 0; node != null; node = node.next, i++) {
                if (obj.equals(node.data)) {
                    removeAt(i);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 清空链表数据
     */
    public void clear() {
        Node<T> node = head;
        while (node != null) {
            Node<T> next = node.next;
            node.prev = node.next = null;
            node.data = null;
            node = next;
        }
        head = tail = node = null;
        size = 0;
    }

    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> node = head;

            public boolean hasNext() {
                return node != null;
            }

            public T next() {
                T data = node.data;
                node = node.next;
                return data;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        Node<T> node = head;
        while (node != null) {
            sb.append(node.data);
            if (node.next != null) {
                sb.append(", ");
            }
            node = node.next;
        }
        sb.append(" ]");
        return sb.toString();
    }
}
