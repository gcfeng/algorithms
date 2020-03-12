package sort;

/**
 * 快速排序：需要排序从low到high的数组，选择low到high之间的任意一个数据作为pivot(分区点)，
 * 遍历low到high的数据，将小于pivot的放到左边，将大于pivot的放到右边，将pivot放到中间。
 * 以此方式来进行分治和递归，直到区间缩小为1。
 *
 * 快速排序和归并排序都应用了分治思想。归并排序是从下到上的，它先处理子问题，再进行合并。快速
 * 排序是从上到下的，它先分区，然后再处理子问题。归并排序是稳定的排序算法，但是在合并过程中需要
 * 额外的内存空间，快速排序虽然不是稳定的排序算法，但是解决了内存占用的问题。
 *
 * 递推公式：
 * quick_sort(low, high) = quick_sort(low, pivot - 1) + quick_sort(pivot + 1, high)
 *
 * 终止条件：
 * low >= high
 *
 * 1、不是稳定的排序算法。原因是在分区操作中会涉及到交换操作
 * 2、时间复杂度是O(nlog(n))
 */
public class QuickSort {
    public void run(int[] arr) {
        if (arr.length <= 1) return;
        sort(arr, 0, arr.length - 1);
    }

    private void sort(int[] arr, int low, int high) {
        if (low >= high) return;
        int q = partition(arr, low, high);
        // 对左边进行排序
        sort(arr, low, q - 1);
        // 对右边进行排序
        sort(arr, q + 1, high);
    }

    // 分区
    // 这里的分区点只是简单的选取区间的最后一个元素，如果区间是已经排序的情况下，算法的时间复杂度会退化为O(n^2)。
    // 有几种方法可以优化此分区算法：
    // 1、三数取中法：每次从区间的首、尾、中间取出一个数，取这三个数的中间数。
    // 2、随机法：每次从要排序的区间中随机选择一个元素点作为分区点。
    private int partition(int[] arr, int low, int high) {
        int i, j;
        i = j = low;
        for (; j < high; j++) {
            if (arr[j] < arr[high]) {
                if (i != j) {
                    swap(arr, i, j);
                }
                i++;
            }
        }
        swap(arr, i, high);
        return i;
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
