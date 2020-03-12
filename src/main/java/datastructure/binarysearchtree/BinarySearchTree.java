package datastructure.binarysearchtree;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Stack;

/**
 * 二叉查找树
 */
public class BinarySearchTree<T extends Comparable<T>> {
    // 结点数量
    private int size = 0;
    // 树的根结点
    private Node root = null;

    /**
     * 树结点
     */
    private class Node {
        T data;
        Node left, right;

        public Node(T data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    public int size() { return size; }

    public boolean isEmpty() { return size() == 0; }

    public boolean contains(T data) { return contains(root, data); }

    public boolean add(T data) {
        // 不插入相同值的结点
        if (contains(data)) return false;
        root = add(root, data);
        size++;
        return true;
    }

    public boolean remove(T data) {
        if (!contains(data)) return false;
        root = remove(root, data);
        size--;
        return true;
    }

    public int height() {
        return height(root);
    }

    public Iterator<T> traverse(TreeTraversalOrder order) {
        switch (order) {
            case PRE_ORDER:
                return preOrder();
            case IN_ORDER:
                return inOrder();
            default:
                return null;
        }
    }

    // 添加结点
    private Node add(Node node, T data) {
        if (node == null) {
            node = new Node(data, null, null);
        } else {
            if (data.compareTo(node.data) < 0) { // 在左子树
                node.left = add(node.left, data);
            } else { // 在右子树
                node.right = add(node.right, data);
            }
        }
        return node;
    }

    // 判断结点是否在树中
    private boolean contains(Node node, T data) {
        if (node == null) return false;
        int cmp = data.compareTo(node.data);
        if (cmp < 0) return contains(node.left, data);
        else if (cmp > 0) return contains(node.right, data);
        return true;
    }

    // 删除结点
    private Node remove(Node node, T data) {
        if (node == null) return null;
        int cmp = data.compareTo(node.data);
        if (cmp < 0) {
            node.left = remove(node.left, data);
        } else if (cmp > 0) {
            node.right = remove(node.right, data);
        } else { // 找到结点
            if (node.left == null && node.right == null) { // 没有孩子，直接删除该结点
                return null;
            } else if (node.right == null) { // 右子树为空
                Node left = node.left;
                node = node.left = null;
                return left;
            } else { // 左子树为空或者左右子树都不为空
                // 寻找右子树最小结点
                Node min = node.right;
                while (min.left != null) {
                    min = min.left;
                }
                // 用右子树最小结点的值替代被删结点的值
                node.data = min.data;
                // 删除右子树最小结点
                node.right = remove(node.right, min.data);
            }
        }
        return node;
    }

    // 获取某个子树的树高
    private int height(Node node) {
        if (node == null) return 0;
        return Math.max(height(node.left), height(node.right)) + 1;
    }

    private Iterator<T> preOrder() {
        final int expectedSize = size;
        final Stack<Node> stack = new Stack<>();
        stack.push(root);

        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                if (expectedSize != size) throw new ConcurrentModificationException();
                return root != null && !stack.isEmpty();
            }

            @Override
            public T next() {
                if (expectedSize != size) throw new ConcurrentModificationException();
                Node node = stack.pop();
                if (node.right != null) stack.push(node.right);
                if (node.left != null) stack.push(node.left);
                return node.data;
            }
        };
    }

    private Iterator<T> inOrder() {
        final int expectedSize = size;
        final Stack<Node> stack = new Stack<>();
        stack.push(root);

        return new Iterator<T>() {
            Node rover = root;

            @Override
            public boolean hasNext() {
                if (expectedSize != size) throw new ConcurrentModificationException();
                return root != null && !stack.isEmpty();
            }

            @Override
            public T next() {
                if (expectedSize != size) throw new ConcurrentModificationException();

                // 将左子树入栈
                while (rover != null && rover.left != null) {
                    stack.push(rover.left);
                    rover = rover.left;
                }

                Node node = stack.pop();

                // 将右子树入栈
                if (node.right != null) {
                    stack.push(node.right);
                    rover = node.right;
                }

                return node.data;
            }
        };
    }
}
