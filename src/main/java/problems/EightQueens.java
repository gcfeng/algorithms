package problems;

/**
 * 八皇后问题。有一个8*8的棋盘，往里放8个棋子（皇后），每个棋子所在的行、列、对角线都不能有另一个棋子。
 *
 * 解决思路：回溯算法
 */
public class EightQueens {
    // 棋盘，下标表示行，值表示棋子存储在哪一列
    private int[] chessboard = new int[8];

    public void run() {
        run(0);
    }

    /**
     * 放置皇后
     */
    private void run(int row) {
        // 8个棋子都已经放置好了
        if (row == 8) {
            print();
            return;
        }
        // 每一行都有8种放法
        for (int col = 0; col < 8; ++col) {
            if (isValid(row, col)) {
                // 第row行的棋子放置到了col列
                chessboard[row] = col;
                // 考察下一行
                run(row + 1);
            }
        }
    }

    /**
     * 判断棋子位置是否符合要求
     */
    private boolean isValid(int row, int col) {
        int left = col - 1, right = col + 1;
        // 逐行往上检查
        for (int i = row - 1; i >= 0; i--) {
            // 第i行的col列是否有棋子
            if (chessboard[i] == col) return false;
            // 检查左上对角线：第i行的col列是否有棋子
            if (left >= 0) {
                if (chessboard[i] == left) return false;
            }
            // 检查右上对角线：第i行的col的列是否有棋子
            if (right < 8) {
                if (chessboard[i] == right) return false;
            }
            --left;
            ++right;
        }
        return true;
    }

    /**
     * 打印棋盘
     */
    private void print() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (chessboard[row] == col) System.out.print("Q ");
                else System.out.print("* ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        EightQueens eq = new EightQueens();
        eq.run();
    }
}
