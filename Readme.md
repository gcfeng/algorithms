# 数据结构与算法
> 数据结构与算法的Java实现

## 数据结构
* [线性表](src/main/java/datastructure/lineartable)
    * [双向链表](src/main/java/datastructure/lineartable/DoublyLinkedList.java)
    * [栈](src/main/java/datastructure/lineartable/Stack.java)
    * [队列](src/main/java/datastructure/lineartable/Queue.java)
    * [循环队列](src/main/java/datastructure/lineartable/CircularQueue.java)
* [二叉查找树](src/main/java/datastructure/binarysearchtree/BinarySearchTree.java)
* [AVL树](src/main/java/datastructure/balancedtree/AVLTree.java)
* [红黑树](src/main/java/datastructure/balancedtree/RedBlackTree.java)
* [跳表](src/main/java/datastructure/skiplist/SkipList.java)
* [散列表](src/main/java/datastructure/hashtable/HashTable.java)
* [位图](src/main/java/datastructure/bitmap/BitMap.java)

## 排序算法
* [冒泡排序](src/main/java/sort/BubbleSort.java)  O(n^2)
* [插入排序](src/main/java/sort/InsertionSort.java) O(n^2)
* [选择排序](src/main/java/sort/SelectionSort.java) O(n^2)
* [归并排序](src/main/java/sort/MergeSort.java) O(nlog(n))
* [快速排序](src/main/java/sort/QuickSort.java) O(nlog(n))
* [计数排序](src/main/java/sort/CountingSort.java) O(n)
* [堆排序](src/main/java/sort/HeapSort.java) O(nlog(n))
    
## 字符串算法
* [Brute Force朴素匹配](src/main/java/string/BruteForce.java) O(n * m)
* [Rabin Karp匹配算法](src/main/java/string/RabinKarp.java) O(n)，极端情况下会退化为O(n * m)
* [Boyer Moore匹配算法](src/main/java/string/BoyerMoore.java) 时间复杂度不超过O(3n)
* [KMP匹配算法](src/main/java/string/KMP.java) O(n + m)
* [Trie树，多模字符串匹配](src/main/java/string/Trie.java) 构建Trie树时间复杂度为O(n)，查找时间复杂度为O(k)，k是查找串长度
* [AC自动机，多模字符串匹配](src/main/java/string/AC.java) 匹配过程时间复杂度O(n)

## 图算法
* [拓扑排序](src/main/java/graph/Topology.java)
* [Dijkstra最短路径算法](src/main/java/graph/Dijkstra.java)

## 算法问题
* [二分查找](src/main/java/problems/BinarySearch.java)
    * [求解平方根](src/main/java/problems/BinarySearch.java)
* [二叉树的最小深度](src/main/java/problems/MinDepth.java) BFS算法。[LeetCode 111](https://leetcode-cn.com/problems/minimum-depth-of-binary-tree/)
* [打开转盘锁](src/main/java/problems/OpenLock.java) BFS算法。[LeetCode 752](https://leetcode-cn.com/problems/open-the-lock/) 

### 回溯算法
* [全排列](src/main/java/problems/Permutation.java) 时间复杂度O(n!)。[LeetCode 46](https://leetcode-cn.com/problems/permutations/)
* [N皇后问题](src/main/java/problems/NQueens.java) [LeetCode 51](https://leetcode-cn.com/problems/n-queens/)
* [0-1背包问题](src/main/java/problems/Backpack.java) 回溯算法，参照runBT函数

### 动态规划
* [0-1背包问题](src/main/java/problems/Backpack.java) 参照runDP函数
* [斐波那契数](src/main/java/problems/Fibonacci.java) [LeetCode 509](https://leetcode-cn.com/problems/fibonacci-number/)
* [零钱兑换](src/main/java/problems/CoinChange.java) [LeetCode 322](https://leetcode-cn.com/problems/coin-change/)

### 双指针
* [环形链表](src/main/java/problems/TwoPointers.java) [LeetCode 141](https://leetcode-cn.com/problems/linked-list-cycle/) 参照hasCycle方法
* [环形链表](src/main/java/problems/TwoPointers.java) [LeetCode 142](https://leetcode-cn.com/problems/linked-list-cycle-ii/) 参照detectCycle方法
* [删除链表的倒数第N个结点](src/main/java/problems/TwoPointers.java) [LeetCode 19](https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/) 参照removeNthFromEnd方法
* [两数之和 II - 输入有序数组](src/main/java/problems/TwoPointers.java) [LeetCode 167](https://leetcode-cn.com/problems/two-sum-ii-input-array-is-sorted/) 参照twoSum方法

### 滑动窗口
* [最小覆盖子串](src/main/java/problems/SlideWindow.java) [LeetCode 76](https://leetcode-cn.com/problems/minimum-window-substring/) 参照minWindow方法
* [字符串排列](src/main/java/problems/SlideWindow.java) [LeetCode 567](https://leetcode-cn.com/problems/permutation-in-string/) 参照checkInclusion方法