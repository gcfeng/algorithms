package sort;

/**
 * 冒泡排序：只操作相邻的两个元素，一次冒泡过程至少会让一个元素移动到它应该在的位置。
 *
 * 1、稳定的排序算法
 * 2、时间复杂度为O(n^2)
 */
public class BubbleSort {
    public void run(int[] arr) {
        if (arr.length <= 1) return;
        for (int i = 0, n = arr.length; i < n; i++) { // 共执行n次冒泡
            // 是否可以退出冒泡排序的标识
            boolean exit = true;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j+1]) {
                    swap(arr, j, j+1);
                    exit = false;
                }
            }
            // 没有数据交换，提前退出
            if (exit) break;
        }

    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
