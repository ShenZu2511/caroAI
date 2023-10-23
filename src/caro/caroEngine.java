package caro;
// i would using a simple minimax and alpha-beta pruning to make AI
//if a move has distance from all move on the board more than 4 then it wont be generate

import com.sun.source.tree.Tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


public class caroEngine {
    private static int depth = 4;
    public Move generateAIMove(int[][] board) {
        TreeNode root = new TreeNode(board, 0);

        // create game tree
        ArrayList<TreeNode> listNode1 = new ArrayList<>();
        listNode1.add(root);
        ArrayList<TreeNode> listNode2 = new ArrayList<>();

        for (int i = 0; i < depth; i++) {
            for (TreeNode node : listNode1) {
                node.generateList();
                for (TreeNode nodeInList : node.list) {
                    listNode2.add(nodeInList);
                }
            }
            //update list
            listNode1.clear();
            listNode1 = listNode2;
            listNode2 = new ArrayList<TreeNode>();
        }

        System.out.println("number of leaf nodes: " + listNode1.size());
        for (TreeNode node : listNode1) {
            node.nodeEvaluation = evaluate(node);
            //System.out.println("value: " + node.nodeEvaluation);
            //printBoard(node.board);
        }

        //iterate game tree to apply minimax
        forEvaluation eval = root.findVal();
        root.nodeEvaluation = eval.val;
        root.board = eval.board;
        printBoard(eval.board);
        System.out.println(eval.val);

        //make the tree to test
        System.out.println("tree: ");
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        for (int i = 0; i < depth; i++) {
            int size = queue.size();
            for (int j = 0; j < size; j++) {
                TreeNode node = queue.poll();
                System.out.print(node.nodeEvaluation + " ");
                for (TreeNode childNode : node.list)
                    queue.add(childNode);
            }
            System.out.println();
        }

        //return difference in board
        for (int i = 0; i < Game.boardHeight; i++) {
            for (int j = 0; j < Game.boardWidth; j++) {
                if (board[i][j] != eval.board[i][j])
                    return new Move(i, j);
            }
        }
        return null;
    }
    public static void printBoard(int[][] board) {
        System.out.print("  ");
        for (int m = 0; m < Game.boardWidth; m++) {
            System.out.printf("%4d", m);
            System.out.print(" ");
        }

        System.out.println();
        for (int i = 0; i < Game.boardHeight; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < Game.boardWidth; j++) {
                System.out.printf("%4d",board[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }
    public static int getRandomNumber() {
        // Tạo một đối tượng Random
        Random random = new Random();

        // Sử dụng nextInt để random một số từ 1 đến 10 (bao gồm cả 1 và 10)
        int min = 1;
        int max = 70;
        int randomNumber = random.nextInt(max - min + 1) + min;

        return randomNumber;
    }

    public int evaluate(TreeNode node) {
        int[][] board = node.board;
        //evalute for X
        int evaluationX = 0;
        for (int i = 0; i < Game.boardHeight; i++) {
            for (int j = 0; j < Game.boardWidth; j++) {
                if (board[i][j] == 1) { // evaluate for every point x
                    int i1 = i;
                    int j1 = j;
                    int firstObstacle = -1;
                    int secondObstacle = -1;
                    int length = 1;
                    // search hori
                    while ((new Move(i1, j1+1)).validIndex() && board[i1][++j1] == 1)
                        length++;
                    if (board[i1][j1] != 1)
                        firstObstacle = board[i1][j1];
                    j1 = j;
//                    System.out.print("i1, j1: " + i1 + " " + j1 + "\n");
                    while ((new Move(i1, j1-1)).validIndex() && board[i1][--j1] == 1)
                        length++;
//                    System.out.print("i1, j1: " + i1 + " " + j1 + "\n");
                    if (board[i1][j1] != 1)
                        secondObstacle = board[i1][j1];

                        //evalute this hori ray
                    evaluationX += evaluateRay(length, firstObstacle, secondObstacle);

//                    System.out.println("evaluationX: " + evaluationX + "\nlength: " + length +
//                            " firstOb: " + firstObstacle + " secondOb: " + secondObstacle);
                    //reset length, obstacles
                    length = 1;
                    firstObstacle = -1;
                    secondObstacle = -1;
                    i1 = i;
                    j1 = j;

                    //search verti
                    while ((new Move(i1+1, j1)).validIndex() && board[++i1][j1] == 1)
                        length++;
                    if (board[i1][j1] != 1)
                        firstObstacle = board[i1][j1];
                    i1 = i;
                    while ((new Move(i1-1, j1)).validIndex() && board[--i1][j1] == 1)
                        length++;
                    if (board[i1][j1] != 1)
                        secondObstacle = board[i1][j1];

                    //evalute this verti ray
                    evaluationX += evaluateRay(length, firstObstacle, secondObstacle);

                    //reset length, obstacles
                    length = 1;
                    firstObstacle = -1;
                    secondObstacle = -1;
                    i1 = i;
                    j1 = j;

                    //search north east
                    while ((new Move(i1+1, j1+1)).validIndex() && board[++i1][++j1] == 1)
                        length++;
                    if (board[i1][j1] != 1)
                        firstObstacle = board[i1][j1];
                    j1 = j;
                    i1 = i;
                    while ((new Move(i1-1, j1-1)).validIndex() && board[--i1][--j1] == 1)
                        length++;
                    if (board[i1][j1] != 1)
                        secondObstacle = board[i1][j1];

                    //evalute this north east ray
                    evaluationX += evaluateRay(length, firstObstacle, secondObstacle);

                    //reset length, obstacles
                    length = 1;
                    firstObstacle = -1;
                    secondObstacle = -1;
                    i1 = i;
                    j1 = j;

                    //search north west
                    while ((new Move(i1+1, j1-1)).validIndex() && board[++i1][--j1] == 1)
                        length++;
                    if (board[i1][j1] != 1)
                        firstObstacle = board[i1][j1];
                    j1 = j;
                    i1 = i;
                    while ((new Move(i1-1, j1+1)).validIndex() && board[--i1][++j1] == 1)
                        length++;
                    if (board[i1][j1] != 1)
                        secondObstacle = board[i1][j1];

                    //evalute this north west ray
                    evaluationX += evaluateRay(length, firstObstacle, secondObstacle);
                }
            }
        }

        //evalute for O
        int evaluationO = 0;
        for (int i = 0; i < Game.boardHeight; i++) {
            for (int j = 0; j < Game.boardWidth; j++) {
                if (board[i][j] == -1) { // evaluate for every point O
                    int i1 = i;
                    int j1 = j;
                    int firstObstacle = 1;
                    int secondObstacle = 1;
                    int length = 1;
                    // search hori
                    while ((new Move(i1, j1+1)).validIndex() && board[i1][++j1] == -1)
                        length++;
                    if (board[i1][j1] != -1)
                        firstObstacle = board[i1][j1];
                    j1 = j;
                    while ((new Move(i1, j1-1)).validIndex() && board[i1][--j1] == -1)
                        length++;
                    if (board[i1][j1] != -1)
                        secondObstacle = board[i1][j1];

                    //evalute this hori ray
                    evaluationO += evaluateRay(length, firstObstacle, secondObstacle);

                    //reset length, obstacles
                   length = 1;
                    firstObstacle = 1;
                    secondObstacle = 1;
                    i1 = i;
                    j1 = j;

                    //search verti
                    while ((new Move(i1+1, j1)).validIndex() && board[++i1][j1] == -1)
                        length++;
                    if (board[i1][j1] != -1)
                        firstObstacle = board[i1][j1];
                    i1 = i;
                    while ((new Move(i1-1, j1)).validIndex() && board[--i1][j1] == -1)
                        length++;
                    if (board[i1][j1] != -1)
                        secondObstacle = board[i1][j1];

                    //evalute this hori ray
                    evaluationO += evaluateRay(length, firstObstacle, secondObstacle);

                    //reset length, obstacles
                   length = 1;
                    firstObstacle = 1;
                    secondObstacle = 1;
                    i1 = i;
                    j1 = j;

                    //search north east
                    while ((new Move(i1+1, j1+1)).validIndex() && board[++i1][++j1] == -1)
                        length++;
                    if (board[i1][j1] != -1)
                        firstObstacle = board[i1][j1];
                    j1 = j;
                    i1 = i;
                    while ((new Move(i1-1, j1-1)).validIndex() && board[--i1][--j1] == -1)
                        length++;
                    if (board[i1][j1] != -1)
                        secondObstacle = board[i1][j1];

                    //evalute this hori ray
                    evaluationO += evaluateRay(length, firstObstacle, secondObstacle);

                    //reset length, obstacles
                    length = 1;
                    firstObstacle = 1;
                    secondObstacle = 1;
                    i1 = i;
                    j1 = j;

                    //search north west
                    while ((new Move(i1+1, j1-1)).validIndex() && board[++i1][--j1] == -1)
                        length++;
                    if (board[i1][j1] != -1)
                        firstObstacle = board[i1][j1];
                    j1 = j;
                    i1 = i;
                    while ((new Move(i1-1, j1+1)).validIndex() && board[--i1][++j1] == -1)
                        length++;
                    if (board[i1][j1] != -1)
                        secondObstacle = board[i1][j1];

                    //evalute this hori ray
                    evaluationO += evaluateRay(length, firstObstacle, secondObstacle);

                }
            }
        }
//        System.out.println("evaluationX: " + evaluationX);
//        System.out.println("evaluationO: " + evaluationO);
//        System.out.println("evaluationX-evaluationO: " + (evaluationX-evaluationO));
        //board evaluation
        return evaluationX - evaluationO;
    }

    private int evaluateRay(int length, int firstObstacle, int secondObstacle) {
        if (length == 5)
            return 10000;
        if (length == 4 && firstObstacle == 0 && secondObstacle == 0)
            return 1000;
        if (length == 3 && firstObstacle == 0 && secondObstacle == 0)
            return 10;
        if (length == 2 && firstObstacle == 0 && secondObstacle == 0)
            return 2;
        return 0;
    }
}
