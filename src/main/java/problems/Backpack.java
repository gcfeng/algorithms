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
     * @param i 当前检查到的物品下标
     */
    public void runBT(int i) {
        // 已经检查完所有物品了
        if (i >= items.length) {
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

    public static void main(String[] args) {
        int[] items = {87, 10, 34, 65, 2};
        Backpack backpack = new Backpack(100, items);
        backpack.runBT(0);
        System.out.println("回溯算法: " + backpack.getBestWeight());
        System.out.print("装进物品：");
        for (Integer item : backpack.getBestAdded()) {
            System.out.print(item + " ");
        }
        System.out.println();
    }
}
