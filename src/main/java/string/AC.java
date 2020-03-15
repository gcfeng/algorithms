package string;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * AC自动机(Aho-Corasick)。AC自动机的基础是Trie树，与Trie树不同的是，AC自动机中每个结点
 * 都有一个fail指针，它表示输入的字符与当前结点的所有孩子结点都不匹配时，自动机的状态应转移
 * 到的状态。fail指针的功能很像KMP算法中的next数组。
 *
 * AC自动机的典型应用场景是敏感词过滤。将敏感词集合构造成一个AC自动机，对用户输入内容通过AC
 * 自动机进行匹配，从而对敏感词进行过滤。在这里，敏感词集合称为模式串，用户输入内容称为主串。
 */
public class AC {
    // 根结点
    private Node root = new Node('\0');

    /**
     * 结点
     */
    private class Node {
        char ch;
        // 如果结点为字符串的结束字符，length记录了该字符串的长度
        int length = 0;
        // 表示结点是否是某个字符串的结束字符
        boolean isWordEnding = false;
        Map<Character, Node> children = new HashMap<>();
        // fail指针，作用是当结点匹配时，应该将状态转移到何处
        // 按照KMP算法的理解，fail指针指向那个最长匹配后缀子串对应的模式串的前缀的最后一个结点
        Node fail = null;

        public Node(char ch) { this.ch = ch; }

        public void addChild(Node node, char ch) { children.put(ch, node); }
    }

    /**
     * 执行匹配
     */
    public static Map<Integer, String> match(String text, String[] patterns) {
        // 构建AC自动机
        AC ac = new AC();
        for (String pattern : patterns) {
            ac.add(pattern);
        }
        ac.build();
        // 执行匹配
        return ac.match(text);
    }

    /**
     * 插入模式串
     */
    private void add(String str) {
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
        }
        if (node != root) {
            node.isWordEnding = true;
            node.length = str.length();
        }
    }

    /**
     * 构建失败指针，是一个按层往上遍历树的过程。
     */
    private void build() {
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node cur = queue.pop();
            for (Node curChild : cur.children.values()) {
                // 根结点的所有孩子结点的失败指针都指向根结点
                if (cur == root) {
                    curChild.fail = root;
                } else {
                    Node prev = cur.fail;
                    while (prev != null) {
                        Node prevChild = prev.children.get(curChild.ch);
                        if (prevChild != null) {
                            curChild.fail = prevChild;
                            break;
                        }
                        prev = prev.fail;
                    }
                    if (prev == null) {
                        curChild.fail = root;
                    }
                }
                queue.add(curChild);
            }
        }
    }

    /**
     * 执行匹配过程
     */
    private Map<Integer, String> match(String text) {
        Map<Integer, String> map = new HashMap<>();
        Node p = root;

        int n = text.length();
        for (int i = 0; i < n; i++) {
            char ch = text.charAt(i);
            while (p.children.get(ch) == null && p != root) {
                p = p.fail;
            }
            p = p.children.get(ch);
            if (p == null) p = root;

            Node tmp = p;
            while (tmp != root) {
                // 匹配成功
                if (tmp.isWordEnding) {
                    int start = i - tmp.length + 1;
                    map.put(start, text.substring(start, start + tmp.length));
                }
                tmp = tmp.fail;
            }
        }
        return map;
    }
}
