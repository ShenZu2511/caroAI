package caro;

public class Move {
    //variables
    private int x; // vertical position
    private int y; // horizontal position

    //getter and setter
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    //check if valid
    public boolean validIndex() {
        if (0 <= x && x < Game.boardHeight && y >= 0 && y < Game.boardWidth)
            return true;
        else return false;
    }

    public boolean valid(int[][] board) {
        if (!validIndex() || board[x][y] != 0) return false;
        return true;
    }

    //constructor
    public Move(int x_, int y_) {
        this.x = x_;
        this.y = y_;
    }

    //check if win move
    public boolean winMove(int[][] board, boolean player1Turn) {
        int count = 1;
        int val = player1Turn? 1:-1;
        int firstX = x;
        int firstY = y;
        // check verti
        while (new Move(x, ++y).validIndex()) {
            if (board[x][y] == val) count ++;
        }
        y = firstY;
        while (new Move(x, --y).validIndex()) {
            if (board[x][y] == val) count ++;
        }
        if (count == 5) return true;
        //reset x,y,count
        x = firstX;
        y = firstY;
        count = 1;

        //check hori
        while (new Move(++x, y).validIndex()) {
            if (board[x][y] == val) count ++;
        }
        x = firstX;
        while (new Move(--x, y).validIndex()) {
            if (board[x][y] == val) count ++;
        }
        if (count == 5) return true;
        //reset x,y,count
        x = firstX;
        y = firstY;
        count = 1;

        //check north east
        while (new Move(++x, ++y).validIndex()) {
            if (board[x][y] == val) count ++;
        }
        x = firstX;
        y = firstY;
        while (new Move(--x, --y).validIndex()) {
            if (board[x][y] == val) count ++;
        }
        if (count == 5) return true;
        //reset x,y,count
        x = firstX;
        y = firstY;
        count = 1;

        //check nort west
        while (new Move(--x, ++y).validIndex()) {
            if (board[x][y] == val) count ++;
        }
        x = firstX;
        y = firstY;
        while (new Move(++x, --y).validIndex()) {
            if (board[x][y] == val) count ++;
        }
        if (count == 5) return true;

        //no case above
        return false;
    }
    public int distanceTo(Move other) {
        return Math.abs(x - other.getX()) + Math.abs(y - other.getY());
    }
}
