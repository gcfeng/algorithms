package datastructure.heap;

import java.util.ArrayList;
import java.util.List;

/**
 * 小顶堆
 */
public class MinHeap<T extends Comparable<T>> {
    private List<T> arr;
    private int capacity;   // 堆中可以存储的最大数据个数
    private int size = 0;   // 堆中已经存储的数据个数

    public MinHeap(int capacity) {
        this.arr = new ArrayList<>();
        // 将剩余元素初始化为空
        for (int i = arr.size(); i < capacity; i++) {
            arr.add(null);
        }
        this.capacity = capacity;
    }

    public int size() { return size; }

    public boolean isEmpty() { return size() == 0; }

    /**
     * 插入数据
     */
    public void add(T data) {
        if (size >= capacity) throw new RuntimeException("Heap full");
        // 在数组末尾添加数据
        arr.set(size, data);
        size++;
        // 自下往上堆化
        int i = size;
        int parent = getParent(i);
        while (parent >= 0 && arr.get(i) != null && arr.get(i).compareTo(arr.get(parent)) < 0) {
            swap(arr, i, parent);
            i = parent;
            parent = getParent(i);
        }
    }

    /**
     * 删除堆顶元素，相当于删除最小元素
     */
    public T pop() {
        if (size == 0) throw new RuntimeException("Heap empty");
        T data = arr.get(0);
        arr.set(0, arr.get(size - 1));
        size--;
        heapify(size - 1, 0);
        return data;
    }

    /**
     * 更新数据
     */
    public void update(T data) {
        boolean isUpdate = false;
        int i = 0;
        for (; i < this.size; i++) {
            if (data.equals(this.arr.get(i))) {
                this.arr.set(i, data);
                isUpdate = true;
                break;
            }
        }
        if (isUpdate) {
            // 重新堆化
            this.heapify(this.size - 1, i);
        }
    }

    /**
     * 建堆
     * i: 父结点
     * 2i+1: 左子结点
     * 2i+2: 右子结点
     */
    public void build() {
        // 叶子结点跟自己比较没有意义，所以这里从非叶子结点开始
        // 给定下标i，(n-1)/2取得相对应的父结点
        for (int n = this.size, i = (n - 1) / 2; i >= 0; i--) {
            heapify(n - 1, i);
        }
    }

    /**
     * 堆化。采取从上往下的堆化逻辑
     * @param high 需要堆化的数组最大索引，可能只需要局部堆化
     * @param i 当前数据索引
     */
    public void heapify(int high, int i) {
        while (true) {
            int k = i;
            // 结点小于左子结点
            if (getLeftChild(i) <= high && arr.get(i).compareTo(arr.get(getLeftChild(i))) > 0) k = getLeftChild(i);
            // 结点小于右子结点
            if (getRightChild(i) <= high && arr.get(k).compareTo(arr.get(getRightChild(i))) > 0) k = getRightChild(i);
            // 结点位置没有变，认为堆化完成
            if (k == i) break;
            swap(arr, i, k);
            i = k;
        }
    }

    private void swap(List<T> arr, int i, int j) {
        T data = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, data);
    }

    private int getParent(int i) {
        return (i - 1) / 2;
    }

    private int getLeftChild(int i) {
        return 2 * i + 1;
    }

    private int getRightChild(int i) {
        return 2 * i + 2;
    }
}
