/**
 * This class implements and evaluates game situations of a TicTacToe game.
 */
public class TicTacToe {

    /**
     * Returns an evaluation for player at the current board state.
     * Arbeitet nach dem Prinzip der Alphabeta-Suche. Works with the principle of Alpha-Beta-Pruning.
     *
     * @param board     current Board object for game situation
     * @param player    player who has a turn
     * @return          rating of game situation from player's point of view
     **/
    public static int alphaBeta(Board board, int player)
    {
        if (board.isGameWon()){

            return board.bToken * (board.nFreeFields()+1);
        }

        if (player == 1)

            return player * alphaBetaX(board, player, Integer.MIN_VALUE, Integer.MAX_VALUE);
            return player * alphaBetaO(board, player, Integer.MIN_VALUE, Integer.MAX_VALUE);
        // TODO
    }

    public static int alphaBetaX(Board board, int player, int alpha, int beta) {


        if(board.isGameWon()) {

            return -1 * (board.nFreeFields() + 1);
        }

        if(board.nFreeFields() == 0) {
            return 0;
        }

        for(Position pos : board.validMoves()) {
            board.doMove(pos, 1);
            int score = alphaBetaO(board, -1, alpha, beta);
            board.undoMove(pos);

            if(score > alpha) {
                alpha = score;
                if(alpha >= beta) {
                    break;
                }
            }
        }

        return alpha;
    }

    public static int alphaBetaO(Board board, int player, int alpha, int beta) {


        if(board.isGameWon()) {
            return 1 * (board.nFreeFields() + 1);
        }

        if(board.nFreeFields() == 0) {
            return 0;
        }

        for(Position pos : board.validMoves()) {
            board.doMove(pos, -1);
            int score = alphaBetaX(board, 1, alpha, beta);
            board.undoMove(pos);

            if(score < beta) {
                beta = score;
                if(beta <= alpha) {
                    break;
                }
            }
        }

        return beta;
    }




    /**
     * Vividly prints a rating for each currently possible move out at System.out.
     * (from player's point of view)
     * Uses Alpha-Beta-Pruning to rate the possible moves.
     * formatting: See "Beispiel 1: Bewertung aller ZugmÃ¶glichkeiten" (Aufgabenblatt 4).
     *
     * @param board     current Board object for game situation
     * @param player    player who has a turn
     **/
    public static void evaluatePossibleMoves(Board board, int player) {
        int n = board.getN();
        int[][] scores = new int[n][n];

        for (Position pos : board.validMoves()) {
            board.doMove(pos, player);
            scores[pos.y][pos.x] = alphaBeta(board, -player);
            board.undoMove(pos);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int field = board.getField(new Position(j, i));
                if (field == 0) {
                    System.out.print(scores[i][j]);
                } else if (field == 1) {
                    System.out.print('x');
                } else if (field == -1) {
                    System.out.print('o');
                }

                if (j < n - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args)
    {
Board board = new Board(3);
        board.setField(new Position(0, 0), -1);












        board.print();
        System.out.println(alphaBeta(board, 1));
       //System.out.println(alphaBeta(board, 1));

    }
}


