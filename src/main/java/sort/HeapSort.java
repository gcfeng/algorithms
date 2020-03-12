package sort;

import datastructure.heap.MaxHeap;

/**
 * 堆排序
 *
 * 堆排序步骤：
 * - 建堆，时间复杂度为O(n)
 * - 排序，时间复杂度为O(nlog(n))
 *
 * 与快排的比较：
 * - 堆排序数据访问的方式没有快速排序友好。快速排序是顺序访问，堆排序是跳着访问的，所以堆排序对CPU缓存是不友好的
 * - 对于同样的数据，堆排序的数据交换次数要比快速排序多。
 */
public class HeapSort {
    public void run(int[] arr) {
        if (arr.length <= 1) return;
        // 建立大顶堆
        MaxHeap.build(arr);

        // 排序。在大顶堆中，堆顶的元素就是最大的，当要进行排序时，我们可以删除堆顶元素，然后重新堆化，
        // 一直进行这个过程，直到堆中没有元素，排序也就完成了。
        int index = arr.length - 1;
        while (index >= 0) {
            // 将最后一个元素替换到堆顶，然后对子数组进行重新堆化
            int tmp = arr[index];
            arr[index] = arr[0];
            arr[0] = tmp;
            index--;
            MaxHeap.heapify(arr, index, 0);
        }
    }
}
