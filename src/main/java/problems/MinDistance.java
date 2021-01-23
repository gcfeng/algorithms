package problems;

/**
 * 最小编辑距离
 */
public class MinDistance {
    /**
     * 计算出将word1转换成word2所需要的最少操作数。
     * 解答：
     * 思路与求最长公共子序列一致。假设索引从1开始，定义dp[i][j]为word1[1..i]转换为word2[1..j]所需要的最少操作数。
     *
     * 基础情况是dp[0][j]=j，dp[i][0]=i。解释是dp[0][j]表示将""转换成j长度字符串所需的最少操作数，这里只需要连续
     * 执行插入操作即可，共执行j次。同理dp[i][0]连续执行删除操作即可。
     *
     * 接下来进行状态转移，
     * - 如果两个对比字符相等，无需操作，dp[i+1][j+1]=dp[i][j]
     * - 如果两个对比字符不想等，需要执行删除、插入或者替换操作，dp[i+1][j+1]=min(dp[i][j+1]+1, dp[i+1][j]+1, dp[i][j]+1)
     *
     * https://leetcode-cn.com/problems/edit-distance/
     */
    public int solution(String word1, String word2) {
        char[] s1 = word1.toCharArray();
        char[] s2 = word2.toCharArray();
        int[][] dp = new int[s1.length + 1][s2.length + 1];
        // 初始化基础情况
        for (int i = 0; i < s1.length + 1; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j < s2.length + 1; j++) {
            dp[0][j] = j;
        }

        for (int i = 0; i < s1.length; i++) {
            for (int j = 0; j < s2.length; j++) {
                if (s1[i] == s2[j]) {
                    dp[i + 1][j + 1] = dp[i][j];
                } else {
                    dp[i + 1][j + 1] = Math.min(
                            dp[i][j] + 1,   // 替换
                            Math.min(
                                    dp[i + 1][j] + 1, // 插入
                                    dp[i][j + 1] + 1  // 删除
                            )
                    );
                }
            }
        }

        return dp[s1.length][s2.length];
    }

    public static void main(String[] args) {
        MinDistance md = new MinDistance();
        System.out.println(md.solution("intention", "execution"));  // 5
    }
}
