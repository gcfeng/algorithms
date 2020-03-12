package datastructure.balancedtree;

import datastructure.binarysearchtree.TreeTraversalOrder;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Stack;

/**
 * 红黑树，是一颗不严格的平衡二叉树。其背后的思想是用标准的二叉查找树（完全由2-结点组成）和一些额外的信息（替换3-结点）来表示2-3树。
 * 将树中的链接分为两种类型：红链接将两个2-结点连接起来构成一个3-结点，黑链接则是2-3树的普通链接。确切的说，
 * 我们将3-结点表示为由一条左斜的红色链接相连的两个2-结点。如果将红色链接相连的结点合并，得到的就是一颗2-3树。
 * 红黑树的高度近似logn，插入、删除、查找的时间复杂度都是O(logn)。
 *
 * 构造特征：
 * - 根结点总是黑色的
 * - 每个叶子节点都是黑色的空结点（NULL），也就是说，叶子结点不存储数据
 * - 任何相邻的结点都不能同时为红色，也就是说，红色结点都是被黑色结点隔开的
 * - 该树是完美黑色平衡的，即任意空链接到根结点的路径上的黑链接数量相同
 *
 * 实际上红黑树的定义中并没有规定红结点一定在左边，本程序为了构造上的方便，将红结点统一定义在左边。如果
 * 在右边出现红结点，进行一次左旋就可以将红结点放在左边。另外，本程序的红链接表示的是红链接指向的结点为红色。
 */
public class RedBlackTree<T extends Comparable<T>> {
    public static final boolean RED = true;
    public static final boolean BLACK = false;

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
        // 新键结点默认为红色
        // 因为树是完美黑色平衡的，如果默认为黑色，则会触发一次平衡操作
        boolean color = RED;

        public Node(T data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    public int size() { return size; }

    public boolean isEmpty() { return size() == 0; }

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

    /**
     * 插入结点
     * 1）原树只有一个2-结点。插入有两以下两种情况：
     *
     *          |
     *          50
     *         /  \
     *       nil   nil
     *
     *    如果插入40，会在50的左边插入，40结点是红链接，该树是平衡的，不需调整。
     *
     *          |
     *          50
     *        //  \
     *       40    nil
     *
     *    如果插入60，会在50的右边插入，60结点是红链接，但是在右边，需要进行一次左旋将红链接移动到左边
     *
     *          |       左旋      ｜
     *          50      ->       60
     *         /  \\            //  \
     *       nil   60          50   nil
     *
     * 2）向树底部的2-结点插入新键，处理逻辑同情况1
     *
     * 3）向3-结点插入新键
     *  3.1）新键大于根结点：新键会被连接到3-结点的右链接。此时树是平衡的，但是为了让红链接保持在左边，我们需要做一次颜色转换
     *          |                   |                       ||
     *          50     插入60       50        颜色翻转        50
     *        //  \     ->        //  \\      ->           /   \
     *       40   nil            40    60                 40    60
     *
     *  3.2）新键小于根结点：新键会被连接到3-结点的左链接。
     *          |                   |                |
     *          50     插入30       50     右旋       40
     *        //  \     ->        //       ->      //   \\    -> 接下来逻辑同3.1
     *       40   nil            40               30     50
     *                          //
     *                         30
     *
     *  3.3）新键介于两者之间：新键会被连接到3-结点左子树的右链接。
     *          |                   |                 |                 |
     *          50     插入45       50     左旋        50     右旋        45
     *        //  \     ->        //       ->        //      ->        //  \\    -> 接下来逻辑同3.1
     *       40   nil            40                 45                40    50
     *                             \\              //
     *                              45            40
     */
    public boolean add(T data) {
        if (data == null || contains(data)) return false;
        root = add(root, data);
        // 根结点总是黑色的
        // 换句话说，如果根结点为3-节点，树会向上分解，此时树高加1，那根结点就变为了2-结点。
        root.color = BLACK;
        size++;
        return true;
    }

    /**
     * 删除结点实际上是找到离当前结点相差最小的结点来替代，然后等效的删除这个最小结点。当黑色结点被删除时，会需要
     * 重新对树进行平衡，整个平衡思路是：
     * 1、被删结点能转化为3-结点或者4-结点的，当前子树就能完成删除逻辑。否则进入第2步
     * 2、被删结点是一个2-结点，需要找兄弟结点借。这里需要注意的是兄弟结点需要是黑色的，否则需要通过旋转来找到真正的
     *    兄弟结点。如果兄弟结点也是一个2-结点，进入第3步
     * 3、被删结点和兄弟结点都是2-结点，需要找父亲结点来借。父亲结点被借给孩子结点，此时父亲结点可以当作是被删结点，
     *    重新进入步骤1和步骤2的调整逻辑
     */
    public boolean remove(T data) {
        if (data == null || !contains(data)) return false;
        // 这里是为了后面需要从父结点借结点做准备
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }
        root = remove(root, data);
        size--;
        // 重置根结点为黑色
        if (root != null) root.color = BLACK;
        return true;
    }

    /**
     * 判断树是否完美黑色平衡的
     */
    public boolean isBalanced() {
        // 计算从根到叶子路径上的黑结点数量
        int black = 0;
        Node node = root;
        while (node != null) {
            if (!isRed(node)) black++;
            node = node.left;
        }
        return isBalanced(root, black);
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

    private Node add(Node node, T data) {
        if (node == null) return new Node(data, null, null);
        int cmp = data.compareTo(node.data);
        if (cmp < 0) node.left = add(node.left, data);
        else node.right = add(node.right, data);
        return balance(node);
    }

    private Node remove(Node node, T data) {
        if (node == null) return null;
        int cmp = data.compareTo(node.data);
        if (cmp < 0) { // 在左子树
            // 自己搞不定，需要找父结点或者兄弟结点借结点
            if (!isRed(node) && !isRed(node.left.left)) {
                node = moveRedLeft(node);
            }
            node.left = remove(node.left, data);
        } else { // 在右子树
            // 这里相当于将红结点往上拉平
            if (isRed(node.left)) node = rotateRight(node);
            // 找到结点，并且是叶子结点，直接删除
            // 在往下查找的过程中，已经保证了结点不会是2-结点。这里只需要判断右孩子是否为空，因为如果左孩子非空，
            // 右孩子为空只可能是3-结点，而这个红结点已经在上一步右旋了
            if (cmp == 0 && node.right == null) return null;
            // 右结点遍历时保证结点非2-结点，相当于需要找父亲或者兄弟借结点
            if (!isRed(node.right) && !isRed(node.right.left)) node = moveRedRight(node);
            // 找到结点，将结点替换为后继结点
            if (cmp == 0) {
                // 找右子树最小结点
                Node min = node.right;
                while (min.left != null) min = min.left;
                node.data = min.data;
                // 将问题转化为删除右子树的最小结点
                node.right = removeMin(node.right);
            } else { // 没有找到，继续往右子树寻找
                node.right = remove(node.right, data);
            }
        }
        // 重新进行平衡
        return balance(node);
    }

    /**
     * 删除最小结点
     */
    private Node removeMin(Node node) {
        // 已经找到了最小结点
        if (node.left == null) return null;
        // 如果当前结点不是3-结点并且它的左孩子不是一个3-结点，需要找兄弟结点和父结点来借
        if (!isRed(node.left) && !isRed(node.left.left)) {
            node = moveRedLeft(node);
        }
        node.left = removeMin(node.left);
        return balance(node);
    }

    /**
     * 判断结点是否红结点
     */
    private boolean isRed(Node node) {
        if (node == null) return false;
        return node.color == RED;
    }

    /**
     * 左旋
     *
     *          E
     *        /   \\
     *      B       S
     *            /   \
     *          F       T
     *
     * 指向E结点的链接可能是左链接，也可能是右链接，颜色可以是红色，也可能是黑色。双斜杠表示是
     * 红链接。当前处理结点是S，也就是说对S子树进行左旋，得到的结果如下图：
     *
     *          S
     *       //   \
     *      E       T
     *    /   \
     *   B     F
     */
    private Node rotateLeft(Node node) {
        Node newParent = node.right;
        node.right = newParent.left;
        newParent.left = node;
        // 保持原来子树的颜色不变
        newParent.color = node.color;
        // 设置左结点为红色
        node.color = RED;
        return newParent;
    }

    /**
     * 右旋
     *            S
     *         //   \
     *       E       T
     *     /   \
     *   B      F
     *
     * 指向S结点的链接可能是左链接，也可能是右链接，颜色可以是红色，也可能是黑色。双斜杠表示是
     * 红链接。当前处理结点是E，也就是说对E子树进行右旋，得到的结果如下图：
     *
     *            E
     *          /   \\
     *        B       S
     *              /   \
     *             F     T
     */
    private Node rotateRight(Node node) {
        Node newParent = node.left;
        node.left = newParent.right;
        newParent.right = node;
        // 保持原来子树的颜色不变
        newParent.color = node.color;
        // 设置右结点为红色
        node.color = RED;
        return newParent;
    }

    /**
     * 翻转颜色
     */
    private void flipColor(Node node) {
        node.color = !node.color;
        node.left.color = !node.left.color;
        node.right.color = !node.right.color;
    }

    /**
     * 插入或者删除结点，需要对树重新进行平衡，在到根结点的往上移动过程中，
     * 我们可能需要顺序完成下列操作：
     * 1、如果右子结点是红色的而左子结点是黑色的，进行左旋转
     * 2、如果左子结点是红色的且它的左子结点也是红色的，进行右旋转
     * 3、如果左右子结点均为红色，进行颜色转换
     */
    private Node balance(Node node) {
        if (isRed(node.right) && !isRed(node.left)) node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right)) flipColor(node);
        return node;
    }

    /**
     * 如果node是红结点，并且node->left和node->left->left都是黑节点，将node->left或者node->left的其中一个孩子设置为红色
     *          ||                          |
     *          100                         100
     *        /    \                     //   \\
     *       90     120        ->       90     120
     *     /  \    /  \                / \     /  \
     *    70  80  110  130            70  80  110  130
     *
     */
    private Node moveRedLeft(Node node) {
        if (node == null) return null;
        if (!isRed(node) || isRed(node.left) || isRed(node.left.left)) return node;
        // 融合当前结点、左孩子、右孩子为新的4-结点，这里相当于找父亲借结点
        flipColor(node);
        // 如果右孩子是3-结点，借键给左孩子，这里相当于找兄弟借结点
        if (node.right != null && isRed(node.right.left)) {
            node.right = rotateRight(node.right);
            node = rotateLeft(node);
            // 当从兄弟结点借到结点之后，需要将从父结点借的结点还回去
            flipColor(node);
        }
        return node;
    }

    /**
     * 如果node是红结点，并且node->right和node->right->left都是黑节点，将node->right或者node->right的其中一个孩子设置为红色
     *          ||                          |
     *          100                         100
     *        /    \                     //   \\
     *       90     120        ->       90     120
     *     /  \    /  \                / \     /  \
     *    70  80  110  130            70  80  110  130
     *
     */
    private Node moveRedRight(Node node) {
        if (node == null) return null;
        if (!isRed(node) || isRed(node.right) || isRed(node.right.left)) return node;
        // 融合当前结点、左孩子、右孩子为新的4-结点，这里相当于找父亲借结点
        flipColor(node);
        // 如果左孩子是3-结点，借键给右孩子，这里相当于找兄弟借结点
        if (node.left != null && isRed(node.left.left)) {
            node = rotateRight(node);
            // 当从兄弟结点借到结点之后，需要将从父结点借的结点还回去
            flipColor(node);
        }
        return node;
    }

    private boolean isBalanced(Node node, int black) {
        if (node == null) return black == 0;
        if (!isRed(node)) black--;
        return isBalanced(node.left, black) && isBalanced(node.right, black);
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
