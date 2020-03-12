package string;

/**
 * 暴力匹配算法(Brute Force算法)，也叫朴素匹配算法。
 *
 * 算法逻辑：假设主串A，长度为n，模式串B，长度为m。在主串A中，检查起始位置分别是0、1、2...n-m且长度为m
 * 的n-m+1个子串，看有没有与模式串匹配的。
 *
 * 时间复杂度为O(n * m)
 */
public class BruteForce {
    public static int search(String str, String pattern) {
        int n = str.length();
        int m = pattern.length();
        if (str.isEmpty() || pattern.isEmpty() || n < m) return -1;
        for (int i = 0; i < n - m + 1; i++) {
            boolean matched = true;
            for (int j = i, k = 0; j < i + m && k < m;) {
                if (str.charAt(j) != pattern.charAt(k)) {
                    matched = false;
                    break;
                }
                j++;
                k++;
            }
            if (matched) return i;
        }
        return -1;
    }
}
