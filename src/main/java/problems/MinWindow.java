package problems;

import java.util.HashMap;

/**
 * 最小覆盖子串
 * 给你一个字符串s、一个字符串t。返回s中涵盖t所有字符的最小子串。如果s中不存在涵盖t所有字符的子串，则返回空字符串""。
 *
 * https://leetcode-cn.com/problems/minimum-window-substring/
 */
public class MinWindow {
    /**
     * 滑动窗口算法的思路：
     * 1、初始化双指针left = right = 0，把索引左闭右开区间[left, right)称为一个窗口
     * 2、首先不断增加right指针扩大窗口[left, right)，直到窗口中的字符串符合要求（包含了t中的所有字符）
     * 3、停止增加right，转而不断增加left指针缩小窗口[left, right)，直到窗口中的字符串不再符合要求
     * （不包含t中的所有字符了）。同时，每次增加left，我们都更新一轮结果
     * 4、重复第2和第3步，直到right到达字符串s的尽头
     */
    public String solution(String s, String t) {
        // window表示当前窗口字符的出现次数
        HashMap<Character, Integer> window = new HashMap<>();
        // need表示t中每次字符的出现次数
        HashMap<Character, Integer> need = new HashMap<>();
        for (Character ch : t.toCharArray()) {
            need.put(ch, need.getOrDefault(ch, 0) + 1);
        }

        int left = 0, right = 0;
        // 表示窗口中满足need条件的字符个数
        int valid = 0;
        // 记录最小覆盖子串的起始索引和长度
        int start = 0, len = Integer.MAX_VALUE;
        while (right < s.length()) {
            // c表示即将移入窗口的字符
            char c = s.charAt(right);
            // 右移窗口
            right++;
            // 更新窗口数据
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                // 窗口字符数量满足了要求
                if (window.get(c).equals(need.get(c))) {
                    valid++;
                }
            }

            // 窗口已经包含了子串，开始判断是否要收缩左指针
            while (valid == need.size()) {
                // 更新最小覆盖子串信息
                if (right - left < len) {
                    start = left;
                    len = right - left;
                }
                // d是将要移除窗口的字符
                char d = s.charAt(left);
                // 右移窗口
                left++;
                if (need.containsKey(d)) {
                    // 之前窗口刚好满足这个要求，现在删除了，那么valid就要减少
                    if (window.getOrDefault(d, 0).equals(need.get(d))) {
                        valid--;
                    }
                    window.put(d, window.getOrDefault(d, 0) - 1);
                }
            }
        }
        // 返回最小覆盖子串
        return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
    }

    public static void main(String[] args) {
        MinWindow minWindow = new MinWindow();
        String str = minWindow.solution("ADOBECODEBANC", "ABC");
        System.out.println(str);    // BANC
    }
}
