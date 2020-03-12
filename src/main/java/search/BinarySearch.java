package search;

/**
 * 二分查找
 */
public class BinarySearch {
    public static int search(int[] arr, int val) {
        int low = 0, high = arr.length - 1, mid;

        while (low <= high) {
            // 如果数组很大，low + high可能会发生溢出
            mid = low + (high - low)/2;
            if (arr[mid] == val) return mid;
            if (arr[mid] < val) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 查找第一个值等于给定值的索引
     */
    public static int searchFirst(int[] arr, int val) {
        int low = 0, high = arr.length - 1, mid;

        while (low <= high) {
            // 如果数组很大，low + high可能会发生溢出
            mid = low + (high - low)/2;
            if (arr[mid] < val) {
                low = mid + 1;
            } else if (arr[mid] > val) {
                high = mid - 1;
            } else {
                // 判断是否第一个位置
                if (mid == 0 || (arr[mid - 1] != val)) return mid;
                else high = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 查找最后一个值等于给定值的索引
     */
    public static int searchLast(int[] arr, int val) {
        int low = 0, high = arr.length - 1, mid;

        while (low <= high) {
            // 如果数组很大，low + high可能会发生溢出
            mid = low + (high - low)/2;
            if (arr[mid] < val) {
                low = mid + 1;
            } else if (arr[mid] > val) {
                high = mid - 1;
            } else {
                // 判断是否最后一个位置
                if (mid == arr.length - 1 || (arr[mid + 1] != val)) return mid;
                else low = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 查找第一个大于等于给定值的索引
     */
    public static int searchFirstBigger(int[] arr, int val) {
        int low = 0, high = arr.length - 1, mid;

        while (low <= high) {
            // 如果数组很大，low + high可能会发生溢出
            mid = low + (high - low)/2;
            if (arr[mid] >= val) {
                if (mid == 0 || (arr[mid - 1] != val)) return mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 查找最后一个小于等于给定值的元素
     */
    public static int searchLastSmaller(int[] arr, int val) {
        int low = 0, high = arr.length - 1, mid;

        while (low <= high) {
            // 如果数组很大，low + high可能会发生溢出
            mid = low + (high - low)/2;
            if (arr[mid] > val) {
                high = mid - 1;
            } else {
                if (mid == arr.length - 1 || (arr[mid + 1] > val)) return mid;
                low = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 求解平方根
     */
    public static double sqrt(double val, int precision) {
        if (val <= 0 || precision < 0) return 0.0;
        double p = 1.0 / Math.pow(10, precision);
        double low = 0;
        double high = val;
        double guess = low + (high - low)/2;
        double diff = guess * guess - val;
        while (Math.abs(diff) > p) {
            if (diff > 0) {
                high = guess;
            } else {
                low = guess;
            }
            guess = low + (high - low)/2;
            diff = guess * guess - val;
        }
        return guess;
    }
}
