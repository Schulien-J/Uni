import java.util.*;

public class AStar15Puzzle {

    /**
     * Finds a shortest solution to a given sliding puzzle using the A* algorithm with the
     * Manhattan distance (aka taxicab metric) between the current and the goal configuration
     * as heuristic.
     *
     * @param board The initial puzzle configuration that should be solved
     * @return a {@link PartialSolution} object which holds a shortest solution in its <em>moveSequence</em>
     */
    public static PartialSolution solveByAStar(Board board) {

        PriorityQueue<PartialSolution> pq = new PriorityQueue<>();
        pq.add(new PartialSolution(board));

        while (!pq.isEmpty()) {

            PartialSolution currentBoard = pq.poll();
            if (currentBoard.isSolution()) return currentBoard;

            for (Move move : currentBoard.validMoves()) {

                PartialSolution solution = new PartialSolution(currentBoard);
                solution.doMove(move);
                pq.add(solution);

            }
        }

        return null;
    }


    public static void printBoardSequence(Board board, Iterable<Move> moveSequence) {
        int moveno = 0;
        for (Move move : moveSequence) {
            System.out.println("Manhattan metric: " + board.manhattan() + " -> cost = " + (moveno + board.manhattan()));
            System.out.println(board);
            System.out.println((++moveno) + ". Move: " + move);
            board.doMove(move);
        }
        System.out.println("Solved board:");
        System.out.println(board);
    }

    public static void main(String[] args) {
      //  String filename = "samples/board-3x3-twosteps.txt";
//        String filename = "samples/board-3x3-moresteps.txt";
       String filename = "samples/board-3x3-moststeps.txt";
        Board board = new Board(filename);
        long start = System.nanoTime();
        PartialSolution sol = solveByAStar(board);
        long duration1 = (System.nanoTime() - start) / 1000;
        System.out.println("Time: " + duration1 / 1000 + " ms");
        if (sol == null) {
            System.out.println("No solution found.");
        } else {
            printBoardSequence(board, sol.moveSequence());
        }
    }

}

