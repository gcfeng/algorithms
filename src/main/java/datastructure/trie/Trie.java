package datastructure.trie;

import java.util.HashMap;
import java.util.Map;

/**
 * Trie树，也称为字典树、前缀树等，用来解决在一组字符串集合中快速查找某个字符串的问题，
 * 本质是利用字符串之间的公共前缀，将重复的前缀合并在一起。通常的应用场景是搜索提示等。
 */
public class Trie {
    // 根结点不存储值
    private final char rootChar = '\0';
    // 根结点
    private Node root = new Node(rootChar);

    /**
     * 结点
     */
    private class Node {
        char ch;
        // 结点分叉数
        int count = 0;
        // 表示结点是否是某个字符串的结束字符
        boolean isWordEnding = false;
        Map<Character, Node> children = new HashMap<>();

        public Node(char ch) { this.ch = ch; }

        public void addChild(Node node, char ch) { children.put(ch, node); }
    }

    /**
     * 查找字符串
     */
    public boolean contains(String str) {
        if (str == null || str.isEmpty()) return false;
        Node node = root;
        for (int i = 0, n = str.length(); i < n; i++) {
            char ch = str.charAt(i);
            node = node.children.get(ch);
            if (node == null) return false;
        }
        // 如果node.isWordEnding=false，表示只是前缀匹配了
        return node.isWordEnding;
    }

    /**
     * 插入字符串
     */
    public void add(String str) {
        if (str == null || str.isEmpty()) throw new RuntimeException("str empty");

        Node node = root;

        // 逐个处理字符串的字符
        for (int i = 0, n = str.length(); i < n; i++) {
            char ch = str.charAt(i);
            Node nextNode = node.children.get(ch);

            // 该字符不存在Trie树中
            if (nextNode == null) {
                nextNode = new Node(ch);
                node.addChild(nextNode, ch);
            }

            node = nextNode;
            node.count += 1;
        }

        if (node != root) node.isWordEnding = true;
    }

    /**
     * 删除字符串
     */
    public void remove(String str) {
        if (!contains(str)) return;
        Node node = root;
        for (int i = 0, n = str.length(); i < n; i++) {
            char ch = str.charAt(i);
            Node curNode = node.children.get(ch);
            curNode.count--;

            // 删除结点
            if (curNode.count <= 0) {
                node.children.remove(ch);
                curNode.children = null;
                curNode = null;
                return;
            }

            node = curNode;
        }
    }
}
