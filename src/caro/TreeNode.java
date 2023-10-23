package caro;

import com.sun.source.tree.Tree;

import java.util.ArrayList;

public class TreeNode {
    public int nodeDepth;
    public int[][] board;
    public ArrayList<TreeNode> list;
    public int nodeEvaluation;
    public TreeNode(int[][] board_, int nodeDepth_) {
        board = board_;
        list = new ArrayList<>();
        nodeDepth = nodeDepth_;
    }
    public void generateList() {

        //generate move list
        //only move not played
        ArrayList<Move> moveList = new ArrayList<>();
        for (int i = 0; i < Game.boardHeight; i++) {
            for (int j = 0; j < Game.boardWidth; j++) {
                if (board[i][j] != 0) moveList.add(new Move(i, j));
            }
        }

        //check if possible to add to move list, not to far
        boolean flag;
        boolean distFlag;
        for (int i = 0; i < Game.boardHeight; i++) {
            for (int j = 0; j < Game.boardWidth; j++) {
                flag = true;
                //check if move used or not used
                for (Move move : moveList) {
                    if (move.getX() == i && move.getY() == j) {
                        flag = false;
                        break;
                    }
                }

                //check distance
                distFlag = false;
                for (Move move : moveList) {
                    if (move.distanceTo(new Move(i, j)) < 6) {
                        distFlag = true;
                        break;
                    }
                }

                if (flag && distFlag) {
                    //copy and make a new board
                    int[][] temp = new int[Game.boardHeight][Game.boardWidth];
                    for (int m = 0; m < Game.boardHeight; m++) {
                        for (int n = 0; n < Game.boardWidth; n++) {
                            temp[m][n] = board[m][n];
                        }
                    }
                    temp[i][j] = nodeDepth%2==0?-1:1;

                    //System.out.println("depth: " + (nodeDepth+1));
                    //caroEngine.printBoard(temp);

                    list.add(new TreeNode(temp, nodeDepth + 1));
                }
            }
        }
    }

    public forEvaluation findVal() {
        if (nodeDepth % 2 == 0) { // find min
            int min = Integer.MAX_VALUE;
            int[][] minBoard = new int[Game.boardHeight][Game.boardWidth];
            if (list.isEmpty()) {
                min = nodeEvaluation;
                minBoard = board;
            }else {
                for (TreeNode node : list) {
                    node.nodeEvaluation = node.findVal().val;
                    if ( node.nodeEvaluation < min) {
                        min = node.nodeEvaluation;
                        minBoard = node.board;
                    }
                }
                //caroEngine.printBoard(minBoard);
            }

            //System.out.println("min value: " + min + " \n");
            return new forEvaluation(minBoard, min);
        } else { // find max
            int max = Integer.MIN_VALUE;
            int[][] maxBoard = new int[Game.boardHeight][Game.boardWidth];
            if (list.isEmpty()) {
                max = nodeEvaluation;
                maxBoard = board;
            } else {
                for (TreeNode node : list) {
                    node.nodeEvaluation = node.findVal().val;
                    if (node.nodeEvaluation > max) {
                        max = node.nodeEvaluation;
                        maxBoard = node.board;
                    }
                }
                //caroEngine.printBoard(maxBoard);
            }

            //System.out.println("max value: " + max + " \n");
            return new forEvaluation(maxBoard, max);
        }
    }
}
class forEvaluation {
    public int[][] board;
    public int val;

    public forEvaluation(int[][] board, int val) {
        this.board = board;
        this.val = val;
    }
}