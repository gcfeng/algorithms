package sort;

/**
 * 插入排序：将数据分为左边已排序区和右边未排序区，取未排序区间中的元素，
 * 在已排序区间中找到合适的插入位置将其插入，并保证已排序区间一直有序。
 *
 * 1、稳定的排序算法
 * 2、时间复杂度为O(n^2)
 */
public class InsertionSort {
    public void run(int[] arr) {
        if (arr.length <= 1) return;
        for (int i = 1, n = arr.length; i < n; i++) {
            int val = arr[i];
            // 查找插入位置
            int j = i - 1;
            for (; j >= 0; j--) {
                if (arr[j] > val) {
                    arr[j + 1] = arr[j]; // 移动数据
                } else {
                    break;
                }
            }
            // 插入数据
            arr[j + 1] = val;
        }
    }
}
