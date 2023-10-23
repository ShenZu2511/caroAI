package caro;

public class Testing {
    public static caroEngine engine = new caroEngine();
    public static void main(String[] args) {
        int[][] testBoard = new int[Game.boardHeight][Game.boardWidth];
        testBoard[1][0] = -1;
        testBoard[1][1] = -1;
        testBoard[1][2] = -1;
        testBoard[1][3] = -1;
        testBoard[1][4] = -1;

        caroEngine.printBoard(testBoard);
        TreeNode node = new TreeNode(testBoard, 0);
        node.generateList();
        System.out.println("size" + node.list.size());
        for (TreeNode n : node.list) {
            caroEngine.printBoard(n.board);
        }
        System.out.println(engine.evaluate(new TreeNode(testBoard,0)));
    }
    public static void main2(String[] args) {
        int[][] testBoard = new int[Game.boardHeight][Game.boardWidth];
        testBoard[4][4] = 1;
        caroEngine engine = new caroEngine();

    }
}
