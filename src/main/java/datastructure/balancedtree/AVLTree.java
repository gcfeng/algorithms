package datastructure.balancedtree;

import datastructure.binarysearchtree.TreeTraversalOrder;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Stack;

/**
 * AVL树，是一颗自平衡的二叉查找树
 *
 * 构造特征：
 * - 是一颗自平衡二叉查找树，拥有二叉查找树的性质
 * - 平衡条件是任何结点的左子树和右子树的高度差不能大于1
 * - 插入、删除、查找的效率都是O(log(n))
 *
 * 程序中会涉及到以下几个概念：
 * - 高度：结点到叶子结点的最大路径长，叶子结点的高度为0
 * - 深度：根结点到指定结点的最大路径长
 *
 * 为了保持平衡，AVL树可能会做以下四种不同类型的旋转：
 * 1、左单旋（RR）
 *      A                   B
 *       \                 / \
 *        B       ->      A   C
 *         \
 *          C
 *
 * 2、右单旋（LL）
 *      C                   B
 *     /                   / \
 *    B         ->        A   C
 *   /
 *  A
 *
 * 3、左右双旋（LR）
 *       C                  C                   B
 *      /                  /                   / \
 *     A        ->        B        ->         A   C
 *      \                /
 *       B              A
 *
 * 先对A进行左单旋，B成为A新的父亲节点，如果B有左子树，将该左子树变为A的右子树。然后对C进行右单旋。
 *
 * 4、右左双旋（RL）
 *      A                 A                     B
 *       \                 \                   / \
 *        C     ->          B       ->        A   C
 *       /                   \
 *      B                     C
 *
 * 先对C进行右单旋，B成为C新的父亲节点，如果B有右子树，将该右子树变为C的左子树。然后对A进行左单旋。
 *
 *
 * 可视化：https://www.cs.usfca.edu/~galles/visualization/AVLtree.html
 */
public class AVLTree<T extends Comparable<T>> {
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
        // 树高
        int height;
        // 平衡因子，当factor绝对值大于1时，需要重新对树进行平衡
        int factor;

        public Node(T data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.height = 0;
        }
    }

    public int size() { return size; }

    public boolean isEmpty() { return size() == 0; }

    public int height() {
        if (root == null) return 0;
        return root.height;
    }

    public boolean contains(T data) {
        Node node = root;
        if (node == null || data == null) return false;
        while (node != null) {
            int cmp = data.compareTo(node.data);
            if (cmp < 0) node = node.left;
            else if (cmp > 0) node = node.right;
            else return true;
        }
        return false;
    }

    public boolean add(T data) {
        if (data == null) return false;
        if (contains(data)) return false;
        root = add(root, data);
        size++;
        return true;
    }

    public boolean remove(T data) {
        if (data == null) return false;
        if (!contains(data)) return false;
        root = remove(root, data);
        size--;
        return true;
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

    // 判断树是否平衡的
    public boolean isBalanced() {
        return isBalanced(root);
    }

    // 更新结点高度和平衡因子
    private void update(Node node) {
        int leftHeight = node.left == null ? -1 : node.left.height;
        int rightHeight = node.right == null ? -1 : node.right.height;
        node.height = 1 + Math.max(leftHeight, rightHeight);
        node.factor = rightHeight - leftHeight;
    }

    // 左单旋
    private Node rotateLeft(Node node) {
        Node newParent = node.right;
        node.right = newParent.left;
        newParent.left = node;
        update(node);
        update(newParent);
        return newParent;
    }

    // 右单旋
    private Node rotateRight(Node node) {
        Node newParent = node.left;
        node.left = newParent.right;
        newParent.right = node;
        update(node);
        update(newParent);
        return newParent;
    }

    // LL情况
    private Node ll(Node node) {
        return rotateRight(node);
    }

    // RR情况
    private Node rr(Node node) {
        return rotateLeft(node);
    }

    // LR情况
    private Node lr(Node node) {
        node.left = rotateLeft(node.left);
        return ll(node);
    }

    // RL情况
    private Node rl(Node node) {
        node.right = rotateRight(node.right);
        return rr(node);
    }

    // 如果结点平衡因子绝对值大于1，重新平衡树
    private Node balance(Node node) {
        // 左子树高
        if (node.factor == -2) {
            if (node.left.factor <= 0) { // LL
                return ll(node);
            } else { // LR
                return lr(node);
            }
        } else if (node.factor == 2) { // 右子树高
            if (node.right.factor >= 0) { // RR
                return rr(node);
            } else { // RL
                return rl(node);
            }
        }
        return node;
    }

    // 添加结点
    private Node add(Node node, T data) {
        if (node == null) return new Node(data, null, null);
        int cmp = data.compareTo(node.data);
        if (cmp < 0) { // 左子树
            node.left = add(node.left, data);
        } else { // 在右子树
            node.right = add(node.right, data);
        }
        // 更新结点树高和平衡因子
        update(node);
        // 重新平衡子树
        return balance(node);
    }

    // 删除结点
    private Node remove(Node node, T data) {
        if (node == null) return null;
        int cmp = data.compareTo(node.data);
        if (cmp < 0) { // 在左子树
            node.left = remove(node.left, data);
        } else if (cmp > 0) { // 在右子树
            node.right = remove(node.right, data);
        } else { // 找到结点
            if (node.left == null) { // 没有子树或者只有右子结点，用右子结点来替代
                return node.right;
            } else if (node.right == null) { // 没有子树或者只有左子结点，用左子结点来替代
                return node.left;
            } else { // 找到右子树最小结点，用该结点的值来替换被删除结点值，然后实际删除该最小结点
                Node min = node.right;
                while (min.left != null) {
                    min = min.left;
                }
                node.data = min.data;
                node.right = remove(node.right, min.data);
            }
        }
        update(node);
        return balance(node);
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

    // 判断树是否自平衡的
    private boolean isBalanced(Node node) {
        if (node == null) return true;
        if (Math.abs(node.factor) > 1) return false;
        return isBalanced(node.left) && isBalanced(node.right);
    }
}
