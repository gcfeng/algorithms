package datastructure.hashtable;

import util.Prime;

/**
 * 散列表：存储键值对。
 *
 * 构造特征：
 * - 散列函数：给定键值key，经过散列函数计算得到对应的散列值
 * - 散列冲突
 *   - 开放寻址法
 *   - 链表法
 *
 * 当散列表空闲位置不多的时候，散列冲突的概率就会大大提高。为了尽可能保证散列表的操作效率，
 * 一般情况下我们需要保证散列表有一定的空闲槽位，使用装载因子来表示空位的多少。装载因子越大，
 * 说明空闲位置越少，冲突越多，散列表的性能就会下降。
 * ```
 * 装载因子 = 填入表中的元素个数 / 散列表的长度
 * ```
 */
public class HashTable {
    // 定义一个最小容量值
    private final static int DEFAULT_CAPACITY = 57;
    // 装载因子
    private final static double DEFAULT_FACTOR = 0.7;
    // 散列表容量，最好是一个素数
    private int capacity;
    // 已经填入的结点数
    private int size = 0;
    // 存放结点的数组
    private Node[] nodes;

    /**
     * 结点
     */
    public class Node {
        String key;
        int data;

        public Node(String key, int data) {
            this.key = key;
            this.data = data;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[").append(key)
                    .append(":").append(data)
                    .append("]");
            return sb.toString();
        }
    }

    public HashTable() {
        this.capacity = Prime.nextPrime(50);
        this.nodes = new Node[this.capacity];
    }

    public HashTable(int capacity) {
        if (capacity < DEFAULT_CAPACITY) capacity = DEFAULT_CAPACITY;
        this.capacity = Prime.nextPrime(capacity);
        this.nodes = new Node[this.capacity];
    }

    public int size() { return size; }

    public boolean isEmpty() { return size() == 0; }

    /**
     * 查找指定key的结点
     */
    public Node get(String key) {
        for (int i = 0, n = nodes.length; i < n; i++) {
            Node node = nodes[i];
            if (node != null && node.key.equals(key)) {
                return node;
            }
        }
        return null;
    }

    /**
     * 插入结点
     */
    public boolean add(String key, int data) {
        if (key == null) return false;
        int factor = size / capacity;
        if (factor > DEFAULT_FACTOR) resize(capacity * 2);

        int times = 0;
        int index = doubleHash(key, times);
        Node rover = nodes[index];
        // 检查到空闲位置就执行插入，否则重新计算下一个散列位置
        while (rover != null) {
            // 如果两个键相同，更新值
            if (key.equals(rover.key)) {
                rover.data = data;
                return true;
            }
            index = doubleHash(key, ++times);
            rover = nodes[index];
        }
        nodes[index] = new Node(key, data);
        size++;
        return true;
    }

    /**
     * 删除结点
     */
    public boolean remove(String key) {
        for (int i = 0, n = nodes.length; i < n; i++) {
            Node node = nodes[i];
            if (node != null && node.key.equals(key)) {
                nodes[i] = null;
                size--;
                return true;
            }
        }
        return false;
    }

    /**
     * 打印散列表
     */
    public void print() {
        for (int i = 0, n = nodes.length; i < n; i++) {
            Node node = nodes[i];
            if (node != null) {
                System.out.print(node);
                System.out.print("----");
            }
        }
    }

    // 重新调整散列表大小
    private void resize(int capacity) {
        if (capacity < DEFAULT_CAPACITY) return;
        HashTable newTable = new HashTable(capacity);
        // 复制原来散列表的数据
        // 当散列表很大时，会导致某次插入很慢。为了均摊这个时间，我们可以在每次插入时复制一个节点数据，
        // 直到复制完毕为止
        for (int i = 0; i < size; i++) {
            Node node = nodes[i];
            if (node != null) {
                newTable.add(node.key, node.data);
            }
        }

        this.size = newTable.size;
        this.capacity = newTable.capacity;
        Node[] tmp = newTable.nodes;
        newTable.nodes = this.nodes;
        this.nodes = tmp;
        newTable = null;
    }

    // 散列函数
    private int hash(String key, int prime) {
        long h = 0;
        for (int i = 0, n = key.length(); i < n; i++) {
            h += Math.pow(prime, n - (i + 1)) * key.charAt(i);
        }
        h = h % capacity;
        return (int)h;
    }

    // 双重散列
    private int doubleHash(String key, int attemptTimes) {
        int prime1 = Prime.nextPrime(128);
        int prime2 = Prime.nextPrime(prime1);
        int h1 = hash(key, prime1);
        int h2 = hash(key, prime2);
        return (h1 + attemptTimes * (h2 + 1)) % capacity;
    }
}
