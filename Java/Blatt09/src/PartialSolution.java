import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;

/**
 * PartialSolution is a class which represents a state of the game
 * from its initial state to its solution. It includes the current
 * state of the board and the move sequence from the initial state
 * to the current state.</br>
 * For the use in the A*-algorithm, the class implements Comparable
 * wrt the cost of the number of moves + heuristic.</br>
 * For the heuristic and game functionality, the respective methods
 * of class {@link Board} are used.
 */
public class PartialSolution implements Comparable<PartialSolution> {
    private Board board;
    private Move[] moveSequence;
    private Iterable<Move> moves;
    private int cost;
    public Move lastMove = null;
    private int movecount = 0;
    /**
     * Constructor, generates an empty solution based on the provided
     * <em>board</em> with an empty move sequence.
     *
     * @param board initial state of the board
     */
    public PartialSolution(Board board) {
        // TODO 1.2 PartialSolution(board)
        this.board = new Board(board);
        this.moveSequence = new Move[0];
        this.cost = movecount + board.manhattan();
        this.lastMove = null;
        this.movecount = 0;


    }

    /**
     * Copy constructor, generates a deep copy of the input
     *
     * @param that The partial solution that is to be copied
     */
    public PartialSolution(PartialSolution that) {
        // TODO 1.2 PartialSolution(PartialSolution)
        this.board = new Board(that.board);
        this.moveSequence = Arrays.copyOf(that.moveSequence, that.moveSequence.length);
        this.cost = that.cost;
        this.lastMove = that.lastMove;
        this.movecount = that.movecount;

    }

    /**
     * Performs a move on the board of the partial solution and updates
     * the cost.
     * 
     * @param move The move that is to be performed
     */
    public void doMove(Move move) {
        board.doMove(move);
        lastMove = move;
        moveSequence = Arrays.copyOf(moveSequence, moveSequence.length + 1);
        moveSequence[moveSequence.length - 1] = move;
        movecount++;
        isSolution();
        cost = movecount + board.manhattan();

    }

    /**
     * Tests whether the solution has been reached, i.e. whether
     * current board is in the goal state.
     *
     * @return {@code true}, if the board is in goal state
     */
    public boolean isSolution() {
        return this.board.isSolved();
    }

    /**
     * Return the sequence of moves which leads from the initial board
     * to the current state.
     *
     * @return move sequence leading to this state of solution
     */
    public Iterable<Move> moveSequence() {
        // TODO 1.2 moveSequence
       return Arrays.asList(moveSequence);
    }

    /**
     * Generates all possible moves on the current board, <em>except</em>
     * the move which would undo the previous move (if there is one).
     * 
     * @return moves to be considered in the current situation
     */
    public Iterable<Move> validMoves() {
        // TODO 1.2 validMoves
           moves = board.validMoves(lastMove);
           return moves;
    }


    /**
     * Compares partial solutions based on their cost.
     * (For performance reasons, the costs should be pre-calculated
     * and stored for each partial solution, rather than computed
     * here each time anew.)
     *
     * @param that the other partial solution
     * @return result of cost comparistion between this and that
     */
    public int compareTo(PartialSolution that) {
        // TODO 1.2 compareTo
        return Integer.compare(this.cost, that.cost);

    }

    @Override
    public String toString() {
        StringBuilder msg = new StringBuilder("Partial solution with moves: \n");
        for (Move move : moveSequence()) {
            msg.append(move).append(", ");
        }
        return msg.substring(0, msg.length() - 2);
    }


    public static void main(String[] args) {
        String filename = "samples/board-3x3-moststeps.txt";
        Board board = new Board(filename);

        PartialSolution psol = new PartialSolution(board);
        PartialSolution psol2 = new PartialSolution(psol);


        psol.doMove(new Move(new Position(2, 2), 0));
        psol.doMove(new Move(new Position(1, 2), 1));

        System.out.println(psol2.compareTo(psol));
        AStar15Puzzle.printBoardSequence(board, psol.moveSequence());
    }
}

