package problems;

/**
 * 股票交易的相关问题
 * 使用状态机来解决
 */
public class Stock {
    /**
     * 买卖股票的最佳时机
     * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
     * 如果你最多只允许完成一笔交易（即买入和卖出一支股票一次），设计一个算法来计算你所能获取的最大利润。
     * 注意：你不能在买入股票前卖出股票。
     *
     * 解答：
     * dp[i][1][0] = max(dp[i-1][1][0], dp[i-1][1][1] + prices[i])
     * dp[i][1][1] = max(dp[i-1][1][1], dp[i-1][0][0] - prices[i])
     *             = max(dp[i-1][1][1], -prices[i])
     *
     * k恒定是1，不会改变，即k不会对状态转移产生影响，简化为：
     * dp[i][0] = max(dp[i-1][0], dp[i-1][1] + prices[i])
     * dp[i][1] = max(dp[i-1][1], -prices[i])
     *
     * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/
     */
    public int maxProfit(int[] prices) {
        int n = prices.length;
        // 定义状态机
        // 第一维数组表示第几天
        // 第二维数组表示是否持有，持有的下标是1，没有持有的下标是0
        int[][] dp = new int[n][2];
        for (int i = 0; i < n; i++) {
            // 基础情况，表示交易还没有开始
            if (i == 0) {
                // dp[i][0] = max(dp[-1][0], dp[-1][1] + prices[i])
                //          = max(0, -infinity + prices[i]) = 0
                dp[i][0] = 0;
                // dp[i][1] = max(dp[-1][1], dp[-1][0] - prices[i])
                //          = max(-infinity, -prices[i]) = -prices[i]
                dp[i][1] = -prices[i];
            } else {
                dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
                dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
            }
        }
        // 股票价格为正数，所以最大值肯定是前一天没有持有股票的时候
        return dp[n - 1][0];
    }

    /**
     * 买卖股票的最佳时机。不限制交易次数
     * 解答：
     * dp[i][k][0] = max(dp[i-1][k][0], dp[i-1][k][1] + prices[i])
     * dp[i][k][1] = max(dp[i-1][k][1], dp[i-1][k-1][0] - prices[i])
     *             = max(dp[i-1][k][1], dp[i-1][k][0] - prices[i]) // 这一步推导是因为k无限大，所以可以认为k-1和k是一样的
     *
     * 从上面推导中发现k不会改变，也就不需要记录k这个状态了，简化如下：
     * dp[i][0] = max(dp[i-1][0], dp[i-1][1] + prices[i])
     * dp[i][1] = max(dp[i-1][1], dp[i-1][0] - prices[i])
     *
     * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii/
     */
    public int maxProfit2(int[] prices) {
        int n = prices.length;
        // 定义状态机
        // 第一维数组表示第几天
        // 第二维数组表示是否持有，持有的下标是1，没有持有的下标是0
        int[][] dp = new int[n][2];
        for (int i = 0; i < n; i++) {
            // 基础情况，表示交易还没有开始
            if (i == 0) {
                // dp[i][0] = max(dp[-1][0], dp[-1][1] + prices[i])
                //          = max(0, -infinity + prices[i]) = 0
                dp[i][0] = 0;
                // dp[i][1] = max(dp[-1][1], dp[-1][0] - prices[i])
                //          = max(-infinity, -prices[i]) = -prices[i]
                dp[i][1] = -prices[i];
            } else {
                dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
                dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
            }
        }
        // 股票价格为正数，所以最大值肯定是前一天没有持有股票的时候
        return dp[n - 1][0];
    }

    /**
     * 买卖股票的最佳时机。最多可以完成两笔交易
     * 解答：
     * dp[i][k][0] = max(dp[i-1][k][0], dp[i-1][k][1] + prices[i])
     * dp[i][k][1] = max(dp[i-1][k][1], dp[i-1][k-1][0] - prices[i])
     *
     * base case:
     * dp[0][k][0] = max(dp[-1][k][0], dp[-1][k][1] + prices[i])
     *             = max(0, -infinity) = 0
     * dp[0][k][1] = max(dp[-1][k][1], dp[-1][k-1][0] - prices[i])
     *             = max(-infinity, -prices[i]) = -prices[i]
     *
     * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iii/
     */
    public int maxProfit3(int[] prices) {
        int n = prices.length;
        int[][][] dp = new int[n][3][2];
        for (int i = 0; i < n; i++) {
            for (int k = 2; k >= 1; k--) {
                // 基础情况
                if (i == 0) {
                    dp[i][k][0] = 0;
                    dp[i][k][1] = -prices[i];
                } else {
                    dp[i][k][0] = Math.max(dp[i-1][k][0], dp[i-1][k][1] + prices[i]);
                    dp[i][k][1] = Math.max(dp[i-1][k][1], dp[i-1][k-1][0] - prices[i]);
                }
            }
        }
        return dp[n - 1][2][0];
    }

    /**
     * 买卖股票的最佳时机。最多可以完成k笔交易
     * 解答：
     * dp[i][k][0] = max(dp[i-1][k][0], dp[i-1][k][1] + prices[i])
     * dp[i][k][1] = max(dp[i-1][k][1], dp[i-1][k-1][0] - prices[i])
     *
     * base case:
     * dp[0][k][0] = max(dp[-1][k][0], dp[-1][k][1] + prices[i])
     *             = max(0, -infinity) = 0
     * dp[0][k][1] = max(dp[-1][k][1], dp[-1][k-1][0] - prices[i])
     *             = max(-infinity, -prices[i]) = -prices[i]
     *
     * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iv/
     */
    public int maxProfit4(int k, int[] prices) {
        int n = prices.length;
        if (n == 0) return 0;
        // 一次交易由买入和卖出构成，最少需要2天
        // 所以有效的k应该不能超过n/2，否则k就相当于infinity，失去了约束意义
        int maxK = Math.min(k, n / 2);
        int[][][] dp = new int[n][maxK + 1][2];
        for (int i = 0; i < n; i++) {
            for (k = maxK; k >= 1; k--) {
                // 基础情况
                if (i == 0) {
                    dp[i][k][0] = 0;
                    dp[i][k][1] = -prices[i];
                } else {
                    dp[i][k][0] = Math.max(dp[i-1][k][0], dp[i-1][k][1] + prices[i]);
                    dp[i][k][1] = Math.max(dp[i-1][k][1], dp[i-1][k-1][0] - prices[i]);
                }
            }
        }
        return dp[n - 1][maxK][0];
    }

    public static void main(String[] args) {
        Stock stock = new Stock();

        // 限定只能交易1次
        System.out.println(stock.maxProfit(new int[]{7, 1, 5, 3, 6, 4})); // 5

        // 没有限定交易次数
        System.out.println(stock.maxProfit2(new int[]{7, 1, 5, 3, 6, 4})); // 7

        // 限定最多可以完成两笔交易
        System.out.println(stock.maxProfit3(new int[]{3,3,5,0,0,3,1,4})); // 6

        // 最多可以完成k笔交易
        System.out.println(stock.maxProfit4(2, new int[]{2,4,1})); // 2
    }
}
