package sort;

import java.util.Arrays;

/**
 * 计数排序：算是桶排序的一种特殊情况。假设需要排序的数据所处的范围不大，比如最大值是k，
 * 我们就可以将数据划分为k个桶，每个桶内的数据值都是相同的，这样其实就省掉了桶内排序的时间。
 *
 * 1、只能应用在数据值范围不大的场景
 * 2、时间复杂度是O(n)
 *
 * 注意：
 * 1、排序的数值不能为负数，否则在初始化桶的数量时会发生问题
 */
public class CountingSort {
    public void run(int[] arr) {
        if (arr.length <= 1) return;
        int[] newArr = new int[arr.length];

        // 1、找到最大整数，以确定需要划分成多少个桶
        int max = 0;
        for (int i = 0, n = arr.length; i < n; i++) {
            if (max < arr[i]) {
                max = arr[i];
            }
        }

        // 2、初始化每个桶的计数为0
        int[] count = new int[max + 1];
        Arrays.fill(count, 0);

        // 3、统计每个桶内数据值的数量
        for (int i = 0, n = arr.length; i < n; i++) {
            count[arr[i]]++;
        }

        // 4、计算count每项的累加值，这里是为了能确定排序后的数组下标
        // 完成后count数组的最后一项就是所有数据的总数
        for (int i = 1; i < max + 1; i++) {
            count[i] = count[i-1] + count[i];
        }

        // 5、遍历原来数组，找到每个数据应该落在的区间索引
        for (int i = 0, n = arr.length; i < n; i++) {
            int index = count[arr[i]] - 1;
            newArr[index] = arr[i];
            // 将原来的计数减去1
            count[arr[i]]--;
        }

        // 6、将排好序的数组复制回去
        for (int i = 0, n = arr.length; i < n; i++) {
            arr[i] = newArr[i];
        }
    }
}
