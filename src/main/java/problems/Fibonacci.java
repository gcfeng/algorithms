package problems;

/**
 * 斐波那契数，形成的序列称为斐波那契数列。该数列由0和1开始，后面的每一项数字都是前面两项数字的和。
 */
public class Fibonacci {
    /**
     * 1、暴力求解
     * 问题是存在大量重复计算，时间复杂度是O(2^n)
     */
    public int solution1(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return solution1(n - 1) + solution1(n - 2);
    }

    /**
     * 2、备忘录的递归求解
     * 既然暴力求解中存在大量重复计算的子问题，我们使用一个备忘录来记录已经计算过的子问题结果，
     * 已经计算过的子问题无需再次计算，相当于对递归树进行了"剪枝"，时间复杂度是O(n)。
     */
    public int solution2(int n) {
        if (n < 1) return 0;
        // 初始化备忘录
        int[] memo = new int[n + 1];
        return memoHelper(memo, n);
    }

    /**
     * 3、dp数组的迭代求解
     * 备忘录的解法采用自顶向下的方式，dp数组采用自底向上的方式，两者的效率基本相同
     */
    public int solution3(int n) {
        if (n < 1) return 0;
        if (n == 1 || n == 2) return 1;
        int[] dp = new int[n + 1];
        // 基本情况
        dp[1] = dp[2] = 1;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    /**
     * 4、优化空间复杂度
     * 根据斐波那契数列的状态转移方程，当前状态只和之前的两个状态有关，其实就不需要一个DP Table来储存
     * 所有的状态，只要存储之前的两个状态就可以了。
     */
    public int solution4(int n) {
        if (n < 1) return 0;
        if (n == 1 || n == 2) return 1;
        int prev = 1, curr = 1;
        for (int i = 3; i <= n; i++) {
            int sum = prev + curr;
            prev = curr;
            curr = sum;
        }
        return curr;
    }

    private int memoHelper(int[] memo, int n) {
        if (n == 1 || n == 2) return 1;
        // 已经计算过的结果直接返回
        if (memo[n] != 0) return memo[n];
        memo[n] = memoHelper(memo,n - 1) + memoHelper(memo,n - 2);
        return memo[n];
    }

    public static void main(String[] args) {
        Fibonacci fib = new Fibonacci();
        int n = 10;
        System.out.println(fib.solution1(n));
        System.out.println(fib.solution2(n));
        System.out.println(fib.solution3(n));
        System.out.println(fib.solution4(n));
    }
}
