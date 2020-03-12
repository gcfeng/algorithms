package datastructure.skiplist;

/**
 * 跳表：跳表是一种可以替代平衡树的动态数据结构。它的实现原理是在单链表上再构建多层链表作为索引，其中第i+1层链表中的
 * 节点是第i层节点的子集。跳表可以做到查找、插入和删除的时间复杂度是O(log(n))。
 *
 * 构造特征：
 * 1、一个跳表由若干层链表组成，最底层包含所有数据，并且每一层链表的数据都是有序的
 * 2、如果节点x出现在第i层，对于j < i，节点x也必须出现在第j层
 * 3、第i层的节点通过一个指针指向下一层拥有相同值的节点
 *
 * 实例：
 *  1 -------------------------------------> 13
 *  |                                        |
 *  1 ----------------> 7 -----------------> 13
 *  |                   |                    |
 *  1 ------> 4 ------> 7 ------> 9 -------> 13 -------> 17
 *  |         |         |         |          |           |
 *  1 -> 3 -> 4 -> 5 -> 7 -> 8 -> 9 -> 10 -> 13 -> 16 -> 17 -> 18
 */
public class SkipList {
    // 跳表的最大层级，下标为0的链表存储原始数据，其余层才是索引层
    private static final int MAX_LEVEL = 16;
    // 带头链表
    private Node head = new Node(MAX_LEVEL);
    // 当前已经建立索引的层级数，可以简单理解为有多少条链表
    private int levelCount = 1;
    // 记录跳表的元素个数
    private int size = 0;

    /**
     * 结点
     */
    private class Node {
        private int data = Integer.MIN_VALUE;
        // 表示当前结点位置在某个层级的下一个结点，从上层切换到下层，就是数组下标-1
        // 例如forwards[1]表示结点在第一层索引的下一个结点
        private Node[] forwards;

        public Node(int level) {
            forwards = new Node[level];
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[").append(data);
            sb.append(":").append(forwards.length);
            sb.append("]");
            return sb.toString();
        }
    }

    public int size() { return size; }

    public boolean isEmpty() { return size() == 0; }

    public int level() { return levelCount; }

    /**
     * 判断是否包含结点
     */
    public boolean contains(int data) {
        Node node = head;
        // 从最大层开始查找，找到前一结点，然后移动到下一层接着查找
        for (int i = levelCount - 1; i >= 0; i--) {
            Node next = node.forwards[i];
            while (next != null && next.data < data) {
                node = next;
                next = node.forwards[i];
            }
        }
        Node next = node.forwards[0];
        return next != null && next.data == data;
    }

    /**
     * 添加结点
     */
    public boolean add(int data) {
        if (contains(data)) return false;

        int levelIndex = head.forwards[0] == null ? 0 : randomLevel();
        // 每次只增加一层索引
        if (levelIndex >= levelCount) {
            levelIndex = levelCount++;
        }

        Node newNode = new Node(levelIndex + 1);
        newNode.data = data;
        // update数组保存了从顶层到底层的结点插入路径
        Node[] update = new Node[levelIndex + 1];
        for (int i = 0; i <= levelIndex; i++) {
            update[i] = head;
        }

        // 从最大层开始查找，找到每一层的前一个结点
        Node rover = head;
        for (int i = levelIndex; i >= 0; i--) {
            Node next = rover.forwards[i];
            while (next != null && next.data < data) {
                rover = next;
                next = rover.forwards[i];
            }
            update[i] = rover;
        }
        // 在跳表的各个层级中添加结点
        for (int i = 0; i <= levelIndex; i++) {
            newNode.forwards[i] = update[i].forwards[i];
            update[i].forwards[i] = newNode;
        }

        size++;
        return true;
    }

    /**
     * 删除结点
     */
    public boolean remove(int data) {
        if (!contains(data)) return false;

        // 找到每一层被删结点的前一个结点，将其记录在update中
        Node[] update = new Node[this.levelCount];
        Node rover = head;
        for (int i = levelCount - 1; i >= 0; i--) {
            Node next = rover.forwards[i];
            while (next != null && next.data < data) {
                rover = next;
                next = next.forwards[i];
            }
            update[i] = rover;
        }

        for (int i = levelCount - 1; i >= 0; i--) {
            Node node = update[i].forwards[i];
            if (node != null && node.data == data) {
                update[i].forwards[i] = node.forwards[i];
            }
        }
        return true;
    }

    /**
     * 打印跳表
     */
    public void print() {
        Node rover = head;
        System.out.println("levelCount: " + level());
        for (int i = levelCount - 1; i >= 0; i--) {
            Node next = rover.forwards[i];
            while (next != null) {
                System.out.print(next);
                System.out.print("------");
                next = next.forwards[i];
            }
            System.out.println();
        }
    }

    /**
     * 随机层级，决定新节点插入到哪几级索引层中。比如随机函数生成了值k，
     * 那需要将新节点插入到第一级到第k级这k级索引中。
     *
     * 理论上说，一级索引元素个数应该占原始数据的50%，二级索引占25%，三级索引占12.5%，一直到最顶层。
     * randomLevel函数随机生成1~max_level-1的值，每一层的晋升概率是50%：
     * - 50%的概率返回1
     * - 25%的概率返回2
     * - 12.5%的概率返回3
     * ...
     */
    private int randomLevel() {
        int level = 0;
        while (Math.random() < 0.5 && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }
}

