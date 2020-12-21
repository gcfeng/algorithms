package problems;

import java.util.LinkedList;
import java.util.List;

/**
 * 全排列
 * 问题描述：给定一个 没有重复 数字的序列，返回其所有可能的全排列。
 *
 * https://leetcode-cn.com/problems/permutations/
 */
public class Permutation {
    List<List<Integer>> res = new LinkedList<>();

    /**
     * 使用回溯算法来求解，只能全部遍历一次，时间复杂度是O(n!)
     */
    public List<List<Integer>> solution(int[] nums) {
        LinkedList<Integer> track = new LinkedList<>();
        backtrack(nums, track);
        return res;
    }

    // 路径：记录在track中
    // 选择列表：nums中不存在于track中的元素
    // 结束条件：nums中的元素全都在track中
    private void backtrack(int[] nums, LinkedList<Integer> track) {
        if (nums.length == track.size()) {
            res.add(new LinkedList<>(track));
            return;
        }
        for (int num : nums) {
            // 元素已经存在track中，跳过处理
            if (track.contains(num)) continue;
            // 做选择
            track.add(num);
            // 进入到下一层决策树
            backtrack(nums, track);
            // 撤销选择
            track.removeLast();
        }
    }

    public static void main(String[] args) {
        Permutation permutation = new Permutation();
        List<List<Integer>> res = permutation.solution(new int[]{1, 2, 3});
        for (List<Integer> track : res) {
            System.out.println(track);
        }
    }
}
