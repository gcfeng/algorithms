package problems;

import java.util.ArrayList;
import java.util.List;

/**
 * N皇后问题。有一个n*n的棋盘，往里放n个棋子（皇后），每个棋子所在的行、列、对角线都不能有另一个棋子。
 */
public class NQueens {
    private List<List<String>> res = new ArrayList<>();

    /**
     * 使用回溯算法来求解，求出所有正确的解法
     */
    public List<List<String>> solution(int n) {
        int[] board = new int[n];
        backtrack(0, board);
        return res;
    }

    // 放置皇后
    private void backtrack(int row, int[] board) {
        if (row == board.length) {
            ArrayList<String> b = new ArrayList<>();
            for (int value : board) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < board.length; j++) {
                    if (j == value) sb.append("Q");
                    else sb.append(".");
                }
                b.add(sb.toString());
            }
            res.add(b);
            return;
        }
        int n = board.length;
        for (int col = 0; col < n; col++) {
            // 排除不合法的选项
            if (!isValid(board, row, col)) continue;
            // 做选择
            board[row] = col;
            // 进入到下一层决策树
            backtrack(row + 1, board);
            // 撤销选择
            board[row] = 0;
        }
    }

    // 判断是否可以在board[row][col]位置上放置皇后
    private boolean isValid(int[] board, int row, int col) {
        int n = board.length;
        int left = col - 1, right = col + 1;
        // 逐行往上检查
        for (int i = row - 1; i >= 0; i--) {
            // 第i行的col列是否有棋子
            if (board[i] == col) return false;
            // 检查左上对角线：第i行的col列是否有棋子
            if (left >= 0) {
                if (board[i] == left) return false;
            }
            // 检查右上对角线：第i行的col的列是否有棋子
            if (right < n) {
                if (board[i] == right) return false;
            }
            --left;
            ++right;
        }
        return true;
    }

    public static void main(String[] args) {
        NQueens eq = new NQueens();
        List<List<String>> res = eq.solution(8);
        for (List<String> board : res) {
            System.out.println("Solution: ");
            for (String row : board) {
                System.out.println(row);
            }
            System.out.println();
        }
    }
}
