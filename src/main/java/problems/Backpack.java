package problems;

import java.util.ArrayList;
import java.util.List;

/**
 * 0-1背包问题。有一个背包，背包总的承载重量是w。现在有n个物品，每个物品重量不等，并且不能分割，
 * 期望选择几种物品装载到背包中，在不超过背包总承载重量的前提下，让背包中物品的总重量最大。
 */
public class Backpack {
    // 背包总容量
    private int weight;
    // 物品列表，值为每个物品的重量
    private int[] items;
    // 存储已经放置的物品列表
    private int[] added;
    // 当前背包存储物品的总重量
    private int addedWeight;
    // 存储最优解的物品总重量
    private int bestWeight;
    // 存储最优解的物品列表
    private List<Integer> bestAdded;

    public Backpack(int weight, int[] items) {
        this.weight = weight;
        this.items = items;
        this.added = new int[items.length];
        this.bestAdded = new ArrayList<>();
    }

    /**
     * 获取放置的物品列表
     */
    public List<Integer> getBestAdded() {
        return this.bestAdded;
    }

    /**
     * 计算放置物品的重量
     */
    public int getBestWeight() {
        return this.bestWeight;
    }

    /**
     * 回溯算法。整个思路是将物品依次排列，整个问题就分解为了n个阶段，每个阶段对应一个物品怎么选择。
     * 先对第一个物品进行处理，选择装进去或者不装进去，然后再递归处理剩下的物品。
     *
     * 回溯法本质上是一种穷举法，解决这个问题的时间复杂度是O(2^n)
     *
     * @param i 当前检查到的物品下标
     */
    public void runBT(int i) {
        // 已经检查完所有物品了
        if (i >= items.length || addedWeight == weight) {
            if (addedWeight > bestWeight) {
                bestWeight = addedWeight;
                bestAdded.clear();
                for (int j = 0, n = added.length; j < n; j++) {
                    if (added[j] == 1) bestAdded.add(j);
                }
            }
            return;
        }

        // 背包可以继续装载
        if (addedWeight + items[i] <= weight) {
            // 装载物品到背包，更新存储物品列表和总重量
            added[i] = 1;
            addedWeight += items[i];
            // 检查下一个物品
            runBT(i + 1);
            // 走不下去了，进行回溯
            addedWeight -= items[i];
            added[i] = 0;
        }
        // 继续检查下一个物品
        runBT(i + 1);
    }

    /**
     * 动态规划算法。下面是算法步骤：
     * 物品列表为{87, 10, 34, 65, 2}，背包可承载100的重力。定义一个states[n][w+1]二维数组来记录每层可以达到的不同状态。
     *
     * 步骤1：第0个物品的重量是87，要么装入背包，要么不装入背包，决策完之后，会对应背包的两种状态，背包中物品的总重量是87，
     * 或者是0。我们用states[0][0]=true和states[0][87]=true来表示这两种状态。
     *
     * 步骤2：第1个物品的重量是10，基于之前背包的状态，这个物品决策之后，背包会增加4种不同的状态，背包中物品总重量分别是
     * w(0+0)、w(0+10)、w(87+0)、w(87+10)，用states[1][0]=true、states[1][10]=true、states[1][87]=true和
     * states[1][97]=true来表示这些状态。
     *
     * ...
     *
     * 步骤5：考察完所有物品后，整个states数组就计算好了。我们只需要在最后一层，找一个值为true的最接近weight的值，就是
     * 背包中物品总重量的最大值了。
     *
     * 动态规划算法将问题分解为多个阶段，每个阶段对应一个决策。我们记录每一个阶段的状态集合（去掉重复的），然后通过当前阶段
     * 的状态集合，来推导下一个阶段的状态集合，动态的往前推进。
     *
     * 时间复杂度为O(n*w)，n是物品个数，w是背包承载重量
     */
    public void runDP() {
        boolean[][] states = new boolean[items.length][weight + 1];
        // 第一个物品的两种状态
        states[0][0] = true;
        states[0][items[0]] = true;
        for (int i = 1, n = items.length; i < n; i++) {
            // 不把第i个物品放入背包，当前放置物品总重量不变
            for (int j = 0; j <= weight; j++) {
                if (states[i - 1][j]) states[i][j] = states[i - 1][j];
            }
            // 将第i个物品放入背包
            for (int j = 0, k = weight - items[i]; j <= k; j++) {
                if (states[i - 1][j]) states[i][j + items[i]] = true;
            }
        }
        // 取得最接近weight的结果
        int i = weight;
        for (; i >= 0; i--) {
            if (states[items.length - 1][i]) bestWeight = i;
        }
        // 没有解
        if (bestWeight == 0) return;
        // 倒推出选择的物品列表
        bestAdded.clear();
        for (int j = items.length - 1; j >= 1; j--) {
            if (i - items[j] >= 0 && states[j - 1][i - items[j]]) {
                bestAdded.add(j);
                i = i - items[j];
            }
        }
    }

    /**
     * 动态规划优化版
     */
    public void runDP2() {
        boolean[] states = new boolean[weight + 1];
        // 第一个物品的两种状态
        states[0] = true;
        states[items[0]] = true;
        for (int i = 1, n = items.length; i < n; i++) {
            // 注意这里是从后往前算，如果从0开始，在遍历过程中会影响后面的计算结果
            for (int j = weight - items[i]; j >= 0; j--) {
                if (states[j]) states[j + items[i]] = true;
            }
        }
        for (int i = weight; i >= 0; i--) {
            if (states[i]) {
                bestWeight = i;
                break;
            }
        }
    }

    public static void main(String[] args) {
        int[] items = {2, 2, 4, 5, 3};
        Backpack backpack = new Backpack(10, items);
        backpack.runBT(0);
        System.out.println("回溯算法------------------");
        System.out.println("总重量: " + backpack.getBestWeight());
        System.out.print("装进物品：");
        for (Integer item : backpack.getBestAdded()) {
            System.out.print(item + " ");
        }
        System.out.println("\n");

        System.out.println("动态规划------------------");
        System.out.println("总重量: " + backpack.getBestWeight());
        System.out.print("装进物品：");
        for (Integer item : backpack.getBestAdded()) {
            System.out.print(item + " ");
        }
        System.out.println("\n");

        System.out.println("动态规划2------------------");
        backpack.runDP2();
        System.out.println("总重量: " + backpack.getBestWeight());
    }
}
