package problems;

import java.util.LinkedList;

/**
 * 求二叉树的最小深度
 *
 * https://leetcode-cn.com/problems/minimum-depth-of-binary-tree/
 */
public class MinDepth {
    /**
     * BFS算法
     */
    public int solution(TreeNode root) {
        if (root == null) return 0;
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        // root本身是一层
        int depth = 1;

        while (!queue.isEmpty()) {
            int size = queue.size();
            // 将当前队列中的结点向四周扩散
            for (int i = 0; i < size; i++) {
                TreeNode curr = queue.removeFirst();
                // 判断是否到达结束条件
                if (curr.left == null && curr.right == null) return depth;
                // 将结点的下一层孩子结点加入到队列
                if (curr.left != null) {
                    queue.add(curr.left);
                }
                if (curr.right != null) {
                    queue.add(curr.right);
                }
            }
            // 增加多一层
            depth++;
        }

        return depth;
    }

    // 树结点
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // 给定值序列构造一棵树
    private TreeNode buildTree(Integer[] values) {
        TreeNode[] nodes = new TreeNode[values.length];
        for (int i = 0; i < values.length; i++) {
            if (i == 0 && values[i] != null) {
                nodes[0] = new TreeNode(values[i]);
            } else if (values[i] != null) {
                nodes[i] = new TreeNode(values[i]);
                int parentIndex = (i - 1) / 2;
                if ((i - 1) % 2 == 0) { // left
                    nodes[parentIndex].left = nodes[i];
                } else { // right
                    nodes[parentIndex].right = nodes[i];
                }
            }
        }
        return nodes[0];
    }

    public static void main(String[] args) {
        MinDepth minDepth = new MinDepth();
        TreeNode node = minDepth.buildTree(new Integer[]{3, 9, 20, null, null, 15, 7});
        System.out.println(minDepth.solution(node)); // 2
    }
}
