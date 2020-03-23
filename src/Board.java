import java.util.LinkedList;
import java.util.Queue;

public class Board {
    private Block[][] board;
    private int mines;
    int clearedBlocks;
    private int rows;
    private int cols;
    private final static int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
    private final static int[] dy = {0, -1, -1, -1, 0, 1, 1, 1};

    public Board(int r, int c) {
        board = new Block[r][c];
        this.rows = r;
        this.cols = c;
        this.clearedBlocks = 0;
        this.mines = 0;

        populate(board);
    }

    public void populate(Block[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Block();
            }
        }
    }


    public int getCols() {
        return this.cols;
    }

    public int getRows() {
        return this.rows;
    }

    public int area() {
        return this.rows * this.cols;
    }

    public boolean isCleared() {
        return (area() - this.mines) == this.clearedBlocks;
    }

    public boolean toggleFlag(int x, int y){
        if(notInRange(x,y)) return false;
        board[x][y].toggleMark();
        return true;
    }

    public boolean setMine(int x, int y) {

        if (notInRange(x, y)) return false;
        if (board[x][y].isMine()) return false;

        addMine(x, y);

        //turn this into a lambda function
        for (int i = 0, j = 0; i < dx.length; i++, j++) {
            int xc = x + dx[i];
            int yr = y + dy[j];
            if (xc >= 0 && yr >= 0 && xc < board[x].length && yr < board[y].length) {
                this.board[xc][yr].increment();
            }
        }
        return true;
    }

    private void addMine(int x, int y) {
        this.board[x][y].toggleMine();
        this.mines++;
    }

    private boolean notInRange(int x, int y) {
        return x < 0 || y < 0 || x >= this.board.length || y >= this.board[x].length;
    }

    public boolean reveal(int x, int y) {
        if (notInRange(x, y)) return true;

        boolean safeReveal = clearBlock(x, y);

        if (safeReveal) {
            sweep(x, y);
        }
        return safeReveal;
    }

    private void sweep(int x, int y) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{x, y});

        while (!queue.isEmpty()) {
            int[] coord = queue.poll();
            if (board[coord[0]][coord[1]].getSum() == 0) {
                for (int i = 0, j = 0; i < dx.length; i++, j++) {
                    int xc = coord[0] + dx[i];
                    int yr = coord[1] + dy[j];
                    if (xc >= 0 && yr >= 0 && xc < board[x].length && yr < board[y].length) {
                        if (!board[xc][yr].isMine() && !board[xc][yr].isRevealed()) {
                            clearBlock(xc, yr);
                            if (board[xc][yr].getSum() <= 0) {
                                queue.add(new int[]{xc, yr});
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean clearBlock(int x, int y) {
        if (!board[x][y].isRevealed()) {
            this.clearedBlocks += 1;
        }
        return board[x][y].reveal();
    }

    public void draw() {
        for (Block[] blocks : board) {
            for (Block b : blocks) {
                b.draw();
            }
            System.out.println();
        }
    }
}
