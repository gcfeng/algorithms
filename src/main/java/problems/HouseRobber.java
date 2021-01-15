package problems;

import java.util.Arrays;

/**
 * 打家劫舍问题
 */
public class HouseRobber {
    /**
     * 打家劫舍
     * 解答：
     * 从左到右走过这一排房子，在每间房子前都有两种选择：抢或者不抢。如果抢了这间房子，那么肯定
     * 就不能抢相邻的下一间房子了，只能从下下间房子开始选择。如果不抢这间房子，那么可以走到下一
     * 间房子继续做选择。当走过了最后一间房子后，就没得抢了，能抢到的钱就是0（base case）。
     *
     * 所以我们定义当前房子的索引就是状态，抢和不抢就是选择。
     *
     * https://leetcode-cn.com/problems/house-robber/
     */
    public int rob1(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int n = nums.length;
        if (n == 1) return nums[0];
        int[] dp = new int[n];
        // 基础条件
        // 1、只有一间房子时，只能抢它了
        // 2、只有两间房子，因为不能抢相邻的，就选择最大的那个来抢
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(
                    // 抢了i - 2
                    dp[i - 1],
                    // 没有抢i - 2
                    dp[i - 2] + nums[i]);
        }
        return dp[n - 1];
    }

    /**
     * 打家劫舍。房子围成一圈，即第一个房屋和最后一个房屋相连
     * 解答：
     * 在第一题的基础上进行扩展，对于该环形问题，分以下情况：
     * - 首尾房子都不抢
     * - 抢第一间，不抢最后一间
     * - 不抢第一间，抢最后一间
     * 后两种情况一定比第一种情况大，所以只需要取后两者最大值即可。从这里我们可以将该问题分成
     * 两个单排列房子的问题：
     * - 抢第一间，不抢最后一间，则求nums[:n-1]的最大值
     * - 不抢第一间，抢最后一间，则求nums[1:]的最大值
     *
     * https://leetcode-cn.com/problems/house-robber-ii/
     */
    public int rob2(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        if (n == 1) return nums[0];
        return Math.max(
                rob1(Arrays.copyOfRange(nums, 0, n - 1)),
                rob1(Arrays.copyOfRange(nums, 1, n))
        );
    }

    public static void main(String[] args) {
        HouseRobber robber = new HouseRobber();
        System.out.println(robber.rob1(new int[]{1,2,3,1}));    // 4
        System.out.println(robber.rob2(new int[]{2,3,2}));    // 3
    }
}
