package caro;

import java.util.Objects;
import java.util.Scanner;

public class Game {
    public static caroEngine engine = new caroEngine();
    public static final int boardHeight = 6;
    public static final int boardWidth = 6;
    public static int[][] board = new int[boardHeight][boardWidth];
    //initial 0; x mean 1; o mean -1
    public static boolean vsHuman = true;
    public static boolean player1Turn = true;
    private static Move player1Move;
    private static Move player2Move;
    public static Scanner s = new Scanner(System.in);
    public static boolean notEnd() {
        return true;
    }
    public static void takePlayer1move() {
        System.out.println("give me player 1's move: ");
        System.out.print("x = ");
        int x = s.nextInt();
        System.out.print("y = ");
        int y = s.nextInt();
        if (!(new Move(x,y)).valid(board)) {
            System.out.println("Invalid move, go again!");
            takePlayer1move();
        }
        else {
            player1Move = new Move(x,y);
            updateAndContinue(player1Move);
        }
    }

    public static void reset() {
        board = new int[boardHeight][boardWidth];
        player1Turn = false;
        // because after set player turn there's a change in turn
        // problem if reset cannot change type, but i dont really care
    }
    public static void updateAndContinue(Move move) {
        update(move);
        //render board
        caroEngine.printBoard(board);
        // check if win move
        if (move.winMove(board, player1Turn)) {
            // congrate winner
            if (player1Turn)
                System.out.println("player 1 win!!! Game will reset.");
            else System.out.println("player 2 win!!! Game will reset.");

            //reset
            reset();
        }



        //change turn and continue
        player1Turn = !player1Turn;
        takePlayerMove();
    }

    public static void takePlayerMove() {
        if (player1Turn)
            takePlayer1move();
        else takePlayer2move(vsHuman);
    }

    public static void update(Move move) {
        if (player1Turn)
            board[move.getX()][move.getY()] = 1;
        else board[move.getX()][move.getY()] = -1;
    }
    public static void takePlayer2move(boolean vsHuman) {
        int x,y;
        if (vsHuman) {
            System.out.println("give me player 2's move: ");
            System.out.print("x = ");
            x = s.nextInt();
            System.out.print("y = ");
            y = s.nextInt();
            if (!(new Move(x,y)).valid(board)) {
                System.out.println("Invalid move, go again!");
                takePlayer2move(vsHuman);
            }
            else {
                player2Move = new Move(x,y);
                updateAndContinue(player2Move);
            }
        } else {
            System.out.println("bot move: ");
            player2Move = takeAImove();
            updateAndContinue(player2Move);
        }
    }
    public static Move takeAImove() {
        return engine.generateAIMove(board);
    }
    public static void main(String[] argc) {
        //initial
        System.out.println("play with AI(a) or play 2 player(b)");
        String type = s.next();
        if (type.equals("a")) {
            vsHuman = false;
            System.out.println("play with bot mode");
        } else
            System.out.println("play 2 players mode");
        takePlayerMove();
    }
}
// too much memory
// dont know to pick win move when both have win move, because of minimax in future