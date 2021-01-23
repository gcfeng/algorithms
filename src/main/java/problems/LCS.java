package problems;

/**
 * 最长公共子序列（Longest Common Subsequence）
 */
public class LCS {
    /**
     * 最长公共子序列
     * 解答：
     * 假设text1="abcde", text2="ace"。设置索引从1开始，定义dp[i][j]为对于text1[1..i]和text2[1..j]，
     * 它们的LCS长度是dp[i][j]。比如dp[4][2]的含义就是，对于"abcd"和"ac"，它们的LCS长度是2。最终想得到的
     * 答案就是dp[5][3]。
     *
     * 基础情况就是dp[0][j]和dp[i][0]，它们的值都是0。
     *
     * 接下来就是状态转移，其实就是如何根据已有的状态来推导下一个状态dp[i][j]。
     * - 第一次遍历i=1和j=1，两个a相同，所以dp[1][1]=1
     * - 第二次遍历i=1和j=2，a与c不等，这里求的是a与ac的最长子序列，需要将之前的状态传递过来，所以dp[1][2]=dp[1][1]=1
     * - 第三次遍历i=1和j=3，a与e不等，处理同第二步
     * 接下来text1走第二轮，即到了b字符
     * - 第四次遍历i=2和j=1，b与a不等，需要传递之前的状态，所以dp[2][1]=dp[1][1]=1
     * ...
     * 总结下来：
     * - 如果对比的两个字符相同，找到各自后退一个的值加1即可，dp[i+1][j+1]=dp[i][j]+1
     * - 如果对比的两个字符不同，要么是i退一格，要么是j退一格，dp[i+1][j+1]=max(dp[i][j+1], dp[i+1][j])
     *
     * https://leetcode-cn.com/problems/longest-common-subsequence/
     */
    public int solution(String text1, String text2) {
        char[] s1 = text1.toCharArray();
        char[] s2 = text2.toCharArray();

        int[][] dp = new int[s1.length + 1][s2.length + 1];
        for (int i = 0; i < s1.length; i++) {
            for (int j = 0; j < s2.length; j++) {
                if (s1[i] == s2[j]) {
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                } else {
                    dp[i + 1][j + 1] = Math.max(dp[i][j + 1], dp[i + 1][j]);
                }
            }
        }

        return dp[s1.length][s2.length];
    }

    public static void main(String[] args) {
        LCS lcs = new LCS();
        System.out.println(lcs.solution("abc", "abc"));   // 3
    }
}
