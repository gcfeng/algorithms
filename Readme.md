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

## 排序算法
* [冒泡排序](src/main/java/sort/BubbleSort.java)  O(n^2)
* [插入排序](src/main/java/sort/InsertionSort.java) O(n^2)
* [选择排序](src/main/java/sort/SelectionSort.java) O(n^2)
* [归并排序](src/main/java/sort/MergeSort.java) O(nlog(n))
* [快速排序](src/main/java/sort/QuickSort.java) O(nlog(n))
* [计数排序](src/main/java/sort/CountingSort.java) O(n)
* [堆排序](src/main/java/sort/HeapSort.java) O(nlog(n))

## 查找算法
* [二分查找](src/main/java/search/BinarySearch.java)
    * [求解平方根](src/main/java/search/BinarySearch.java)
    
## 字符串算法
* [Brute Force朴素匹配](src/main/java/string/BruteForce.java) O(n * m)
* [Rabin Karp算法](src/main/java/string/RabinKarp.java) O(n)，极端情况下会退化为O(n * m)