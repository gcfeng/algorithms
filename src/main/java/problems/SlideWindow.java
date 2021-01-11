package problems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 滑动窗口
 *
 * 滑动窗口算法的思路：
 * 1、初始化双指针left = right = 0，把索引左闭右开区间[left, right)称为一个窗口
 * 2、首先不断增加right指针扩大窗口[left, right)，直到窗口中的字符串符合要求（包含了t中的所有字符）
 * 3、停止增加right，转而不断增加left指针缩小窗口[left, right)，直到窗口中的字符串不再符合要求
 * （不包含t中的所有字符了）。同时，每次增加left，我们都更新一轮结果
 * 4、重复第2和第3步，直到right到达字符串s的尽头
 */
public class SlideWindow {
    /**
     * 最小覆盖子串
     * 给你一个字符串s、一个字符串t。返回s中涵盖t所有字符的最小子串。如果s中不存在涵盖t所有字符的子串，则返回空字符串""。
     *
     * https://leetcode-cn.com/problems/minimum-window-substring/
     */
    public String minWindow(String s, String t) {
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

    /**
     * 字符串排列
     * 给定两个字符串 s1 和 s2，写一个函数来判断 s2 是否包含 s1 的排列。
     * 换句话说，第一个字符串的排列之一是第二个字符串的子串。
     *
     * https://leetcode-cn.com/problems/permutation-in-string/
     */
    public boolean checkInclusion(String s1, String s2) {
        // window表示当前窗口字符的出现次数
        HashMap<Character, Integer> window = new HashMap<>();
        // need表示s1中每次字符的出现次数
        HashMap<Character, Integer> need = new HashMap<>();
        for (Character ch : s1.toCharArray()) {
            need.put(ch, need.getOrDefault(ch, 0) + 1);
        }

        int left = 0, right = 0;
        // 表示窗口中满足need条件的字符个数
        int valid = 0;
        while (right < s2.length()) {
            // c表示即将移入窗口的字符
            char c = s2.charAt(right);
            // 右移窗口
            right++;
            // 更新窗口数据
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (need.get(c).equals(window.get(c))) {
                    valid++;
                }
                // 注意这里使用的是need的尺寸大小，因为s1可能会存在重复字符
                if (valid == need.size()) return true;
            }

            while (right - left >= s1.length()) {
                // d是要移除的字符
                char d = s2.charAt(left);
                // 右移窗口
                left++;
                // 更新窗口数据
                if (need.containsKey(d)) {
                    // 之前窗口的该字符刚好满足要求，现在删除了，valid需要减1
                    if (need.get(d).equals(window.getOrDefault(d, 0))) {
                        valid--;
                    }
                    window.put(d, window.getOrDefault(d, 0) - 1);
                }
            }
        }
        return false;
    }

    /**
     * 找到字符串中所有字母异位词
     * 给定一个字符串 s 和一个非空字符串 p，找到 s 中所有是 p 的字母异位词的子串，返回这些子串的起始索引。
     *
     * https://leetcode-cn.com/problems/find-all-anagrams-in-a-string/
     */
    public List<Integer> findAnagrams(String s, String p) {
        // window表示当前窗口字符的出现次数
        HashMap<Character, Integer> window = new HashMap<>();
        // need表示p中每次字符的出现次数
        HashMap<Character, Integer> need = new HashMap<>();
        for (Character ch : p.toCharArray()) {
            need.put(ch, need.getOrDefault(ch, 0) + 1);
        }

        List<Integer> list = new ArrayList<>();
        int left = 0, right = 0;
        // 表示窗口中满足need条件的字符个数
        int valid = 0;
        while (right < s.length()) {
            // c表示即将移入窗口的字符
            char c = s.charAt(right);
            // 右移窗口
            right++;
            // 更新窗口的数据
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (need.get(c).equals(window.get(c))) {
                    valid++;
                }
                if (valid == need.size()) {
                    list.add(left);
                }
            }

            while (right - left >= p.length()) {
                // d表示即将移除窗口的字符
                char d = s.charAt(left);
                // 右移窗口
                left++;
                // 更新窗口
                if (need.containsKey(d)) {
                    if (need.get(d).equals(window.getOrDefault(d, 0))) {
                        valid--;
                    }
                    window.put(d, window.getOrDefault(d, 0) - 1);
                }
            }
        }

        return list;
    }

    /**
     * 无重复字符的最长子串
     * 给定一个字符串，请你找出其中不含有重复字符的最长子串的长度。
     *
     * https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
     */
    public int lengthOfLongestSubstring(String s) {
        if (s.isEmpty()) return 0;
        // 记录当前窗口的字符
        HashMap<Character, Integer> window = new HashMap<>();
        int left = 0, right = 0;
        // 记录最长子串的长度
        int len = 0;
        while (right < s.length()) {
            // 要进入到窗口的字符
            char c = s.charAt(right);
            right++;
            // 更新窗口数据
            window.put(c, window.getOrDefault(c, 0) + 1);
            // 出现了重复字符，收缩左窗口
            while (window.get(c) > 1) {
                char d = s.charAt(left);
                left++;
                window.put(d, Math.max(window.getOrDefault(d, 0) - 1, 0));
            }
            len = Math.max(len, right - left);
        }
        return len;
    }

    public static void main(String[] args) {
        SlideWindow slideWindow = new SlideWindow();

        // 最小覆盖子串
        String str = slideWindow.minWindow("ADOBECODEBANC", "ABC");
        System.out.println(str);    // BANC

        // 字符串排列
        System.out.println(slideWindow.checkInclusion("abcdxabcde", "abcdeabcdx"));   // true
        System.out.println(slideWindow.checkInclusion("ab", "eidbaooo"));   // true
        System.out.println(slideWindow.checkInclusion("ab", "eidboaoo"));   // false

        // 找到字符串中所有字母异位词
        System.out.println(slideWindow.findAnagrams("cbaebabacd", "abc"));  // [0, 6]
        System.out.println(slideWindow.findAnagrams("abab", "ab"));  // [0, 1, 2]

        System.out.println(slideWindow.lengthOfLongestSubstring("aab")); // 2
        System.out.println(slideWindow.lengthOfLongestSubstring("abcabcbb")); // 3
        System.out.println(slideWindow.lengthOfLongestSubstring("bbbbb")); // 1
        System.out.println(slideWindow.lengthOfLongestSubstring("pwwkew")); // 3
    }
}
