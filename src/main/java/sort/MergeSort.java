package sort;

/**
 * 归并排序：将数组从中间分成前后两部分，然后对前后两部分分别排序，再将排好序的两部分合并在一起。
 *
 * 1、稳定的排序算法
 * 2、时间复杂度为O(nlog(n))
 */
public class MergeSort {
    public void run(int[] arr) {
        if (arr.length <= 1) return;
        sort(arr, 0, arr.length - 1);
    }

    private void sort(int[] arr, int low, int high) {
        if (low >= high) return;
        int mid = (low + high) / 2;
        // 对左边进行排序
        sort(arr, low, mid);
        // 对右边进行排序
        sort(arr, mid + 1, high);
        // 合并两边的排序结果
        merge(arr, low, mid, high);
    }

    private void merge(int[] arr, int low, int mid, int high) {
        // 创建一个新的数组
        int[] newArr = new int[high - low + 1];

        int i, j, k;
        for (i = low, j = mid + 1, k = 0; i <= mid && j <= high;) {
            if (arr[i] > arr[j]) {
                newArr[k++] = arr[j++];
            } else {
                newArr[k++] = arr[i++];
            }
        }

        if (i == mid + 1) { // 左边处理完成，处理右边剩余
            while (j <= high) {
                newArr[k++] = arr[j++];
            }
        } else { // 右边处理完成，处理左边剩余
            while (i <= mid) {
                newArr[k++] = arr[i++];
            }
        }

        // 复制排序好的数组
        int n;
        for (i = 0, n = high - low + 1; i < n; i++) {
            arr[i + low] = newArr[i];
        }
    }
}
