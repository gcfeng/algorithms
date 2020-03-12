package datastructure.heap;

/**
 * 大顶堆
 */
public class MaxHeap {
    private int[] arr;
    private int capacity;   // 堆中可以存储的最大数据个数
    private int size = 0;   // 堆中已经存储的数据个数

    public MaxHeap(int capacity) {
        arr = new int[capacity];
        this.capacity = capacity;
    }

    public int size() { return size; }

    public boolean isEmpty() { return size() == 0; }

    /**
     * 插入数据
     */
    public void push(int data) {
        if (size >= capacity) throw new RuntimeException("Heap full");
        // 在数组末尾添加数据
        arr[size] = data;
        size++;
        // 自下往上堆化
        int i = size;
        int parent = getParent(i);
        while (parent >= 0 && arr[i] > arr[parent]) {
            swap(arr, i, parent);
            i = parent;
            parent = getParent(i);
        }
    }

    /**
     * 删除堆顶元素
     */
    public void removeMax() {
        if (size == 0) throw new RuntimeException("Heap empty");
        arr[0] = arr[size - 1];
        size--;
        heapify(arr, size - 1, 0);
    }

    /**
     * 建堆
     * i: 父结点
     * 2i+1: 左子结点
     * 2i+2: 右子结点
     */
    public static void build(int[] arr) {
        // 叶子结点跟自己比较没有意义，所以这里从非叶子结点开始
        // 给定下标i，(n-1)/2取得相对应的父结点
        for (int n = arr.length, i = (n - 1) / 2; i >= 0; i--) {
            heapify(arr, n - 1, i);
        }
    }

    /**
     * 堆化。采取从上往下的堆化逻辑
     * @param arr 数组
     * @param high 需要堆化的数组最大索引，可能只需要局部堆化
     * @param i 当前数据索引
     */
    public static void heapify(int[] arr, int high, int i) {
        while (true) {
            int k = i;
            // 结点小于左子结点
            if (getLeftChild(i) <= high && arr[i] < arr[getLeftChild(i)]) k = getLeftChild(i);
            // 结点小于右子结点
            if (getRightChild(i) <= high && arr[k] < arr[getRightChild(i)]) k = getRightChild(i);
            // 结点位置没有变，认为堆化完成
            if (k == i) break;
            swap(arr, i, k);
            i = k;
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int data = arr[i];
        arr[i] = arr[j];
        arr[j] = data;
    }

    private static int getParent(int i) {
        return (i - 1) / 2;
    }

    private static int getLeftChild(int i) {
        return 2 * i + 1;
    }

    private static int getRightChild(int i) {
        return 2 * i + 2;
    }
}
