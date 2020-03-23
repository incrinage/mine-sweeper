import java.util.Random;
import java.util.Scanner;

public class Game {
    private int rows;
    private int cols;
    private int mines;

    public static void main(String[] args) throws InterruptedException {
        Game game = new Game();
        while (game.gameLoop()) ;
    }

    private Board board;

    public Game() {
        this(10, 10);
    }

    public Game(int rows, int cols) {
        newGame(rows, cols, 15);
    }

    public void newGame(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        board = new Board(rows, cols);
        addMines(mines);
    }

    public void newGame() {
        board = new Board(this.rows, this.cols);
        addMines(this.mines);
    }

    public boolean gameLoop() {
        Scanner in = new Scanner((System.in));
        boolean gameOver = false;
        boolean cont = true;
        while (!gameOver) {
            board.draw();

            System.out.println("Reveal a block ! (x,y)");
            System.out.println("-1 -1 to exit");
            System.out.println("F x y to mark");
            System.out.println("C x y to clear");
            String c = in.next();
            int x = in.nextInt();
            int y = in.nextInt();
            if (x == -1 && y == -1) {
                System.out.println("Quitter");
                gameOver = true;
                cont = false;
            }

            if (c.equals("C")) {
                boolean safeReveal = this.board.reveal(x, y);
                if (!safeReveal) {
                    board.draw();
                    System.out.println("You got booty popped!");
                    gameOver = true;
                }
            } else {
                board.toggleFlag(x, y);
            }

            if (this.board.isCleared()) {
                board.draw();
                System.out.println("You did it !");
                gameOver = true;
            }
        }

        if (cont) {
            System.out.println("Play again? (y,n)");
            String c = in.next();

            if (c.equals("y")) {
                newGame();
                return true;
            } else {
                System.out.println("sike thats the wrong number");
                return false;
            }
        }
        return false;
    }

    public void addMines(int numMines) {
        Random random = new Random();
        for (int i = 0; i < numMines; i++) {
            int x = random.nextInt(this.board.getRows());
            int y = random.nextInt(this.board.getCols());
            this.board.setMine(x, y);
        }
    }

    //TODO add various mine strategies to beat player

}
