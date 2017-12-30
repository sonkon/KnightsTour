
/**
 * KnightsTour solves the KnightsTour riddle.
 * 
 * @author Sonali Kondapalli
 * @version January 2016
 */

import java.util.*;

public class KnightsTour {
    
    // Initialize the board, the moves, and the total number.
    private final static int num = 12;
    private final static int[][] moves = {{1,-2},{2,-1},{2,1},{1,2},{-1,2},
            {-2,1},{-2,-1},{-1,-2}};
    private static int[][] board;
    private static int total;

    public static void main(String[] args) {
        // Create board.
        board = new int[num][num];
        total = (num - 4) * (num - 4);
        
        System.out.println("Knights Tour Result");
        
        for (int r = 0; r < num; r++) {
            for (int c = 0; c < num; c++){
                if (r < 2 || r > num - 3 || c < 2 || c > num - 3) {
                    board[r][c] = -1;
                }
            }
        }

        // Randomize the numbers.
        int row = 2 + (int) (Math.random() * (num - 4));
        int col = 2 + (int) (Math.random() * (num - 4));

        board[row][col] = 1;

        // If can't be done, then print error message.
        if (tour(row, col, 2)) {
            outputBoard();
        } else {
            System.out.println("Result Unavailable.");
        }

    }

    private static boolean tour(int r, int c, int count) {
        if (count > total) {
            return true;
        }

        List<int[]> next = nextto(r, c);

        // If space is empty, return it.
        if (next.isEmpty() && count != total) {
            return false;
        }
        
        Collections.sort(next, new Comparator<int[]>() {
                public int compare(int[] a, int[] b) {
                    return a[2] - b[2];
                }
            });

        // Detect open and not open spaces.
        for (int[] nb : next) {
            r = nb[0];
            c = nb[1];
            board[r][c] = count;
            if (!spaceDetect(count, r, c) && tour(r, c, count + 1)) {
                return true;
            }
            board[r][c] = 0;
        }

        return false;
    }

    private static List<int[]> nextto(int r, int c) {
        List<int[]> next = new ArrayList<>();

        for (int[] m : moves) {
            int x = m[0];
            int y = m[1];
            if (board[r + y][c + x] == 0) {
                int num = countNext(r + y, c + x);
                next.add(new int[]{r + y, c + x, num});
            }
        }
        return next;
    }

    private static int countNext(int r, int c) {
        // See how many open spaces are next to current spot.
        int num = 0;
        for (int[] m : moves) {
            if (board[r + m[1]][c + m[0]] == 0) {
                num++;
            }
        }
        return num;
    }

    private static boolean spaceDetect(int cnt, int r, int c) {
        // Find empty slots.
        if (cnt < total - 1) {
            List<int[]> next = nextto(r, c);
            for (int[] nb : next) {
                if (countNext(nb[0], nb[1]) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void outputBoard() {
        // Print out the board.
        for (int[] row : board) {
            for (int i : row) {
                if (i == -1) {
                    continue;
                }
                System.out.printf("%2d ", i);
            }
            System.out.println();
        }
        System.out.println("64 Complete Moves");
    }
}