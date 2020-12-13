package problems;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 凑零钱问题：给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。
 * 如果没有任何一种硬币组合能组成总金额，返回 -1。
 *
 * https://leetcode-cn.com/problems/coin-change/
 */
public class CoinChange {
    private Map<Integer, Integer> memo = new HashMap<>();

    /**
     * 1、暴力求解
     * 画出相关递归树，递归算法的时间复杂度是子问题总数*每个子问题的时间。这里的总时间复杂度是O(k*n^k)
     */
    public int solution1(int[] coins, int amount) {
        // 基础情况
        if (amount == 0) return 0;
        if (amount < 0) return -1;
        int res = Integer.MAX_VALUE;
        for (int coin : coins) {
            int subProblem = solution1(coins, amount - coin);
            // 子问题无解，跳过
            if (subProblem == -1) continue;
            res = Math.min(res, 1 + subProblem);
        }
        return res != Integer.MAX_VALUE ? res : -1;
    }

    /**
     * 2、带备忘录的递归，消除已经计算过的子问题，时间复杂度是O(kn)
     */
    public int solution2(int[] coins, int amount) {
        if (memo.get(amount) != null) return memo.get(amount);
        // 基础情况
        if (amount == 0) return 0;
        if (amount < 0) return -1;
        int res = Integer.MAX_VALUE;
        for (int coin : coins) {
            int subProblem = solution2(coins, amount - coin);
            // 子问题无解，跳过
            if (subProblem == -1) continue;
            res = Math.min(res, 1 + subProblem);
        }
        int val = res != Integer.MAX_VALUE ? res : -1;
        memo.put(amount, val);
        return val;
    }

    /**
     * 3、dp数组的迭代解法
     */
    public int solution3(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        // 基础情况
        dp[0] = 0;
        // 外层循环遍历所有状态的所有取值
        for (int i = 0; i < dp.length; i++) {
            // 内层循环求所有选择的最小值
            for (int coin : coins) {
                // 子问题无解，跳过
                if (i - coin < 0) continue;
                dp[i] = Math.min(dp[i], 1 + dp[i - coin]);
            }
        }
        return (dp[amount] == amount + 1) ? -1 : dp[amount];
    }

    public static void main(String[] args) {
        CoinChange cc = new CoinChange();
        int[] coins = {1, 2, 5};
        int amount = 11;
        System.out.println(cc.solution1(coins, amount));
        System.out.println(cc.solution2(coins, amount));
        System.out.println(cc.solution3(coins, amount));
    }
}
