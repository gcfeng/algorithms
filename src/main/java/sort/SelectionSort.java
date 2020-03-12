package sort;

/**
 * 选择排序：将数据分为左边已排序区和右边未排序区，每次从未排序区中找到最小的元素，将它插入到已排序区间的末尾。
 *
 * 1、不是稳定的排序算法
 * 2、时间复杂度为O(n^2)
 */
public class SelectionSort {
    public void run(int[] arr) {
        if (arr.length <= 1) return;
        for (int i = 0, n = arr.length; i < n; i++) {
            // 寻找第i小的数所在的位置
            int minIndex = i;
            for (int j = i; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            // 交换
            if (i != minIndex) {
                swap(arr, i, minIndex);
            }
        }
    }

    public void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
