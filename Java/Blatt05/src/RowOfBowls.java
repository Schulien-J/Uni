import java.util.LinkedList;

/**
 * This class implements a game of Row of Bowls.
 * For the games rules see Blatt05. The goal is to find an optimal strategy.
 */
public class RowOfBowls {

    public RowOfBowls() {
    }
    
    /**
     * Implements an optimal game using dynamic programming
     * @param values array of the number of marbles in each bowl
     * @return number of game points that the first player gets, provided both parties play optimally
     */
    public int maxGain(int[] values) {

        int[][] matrix = new int[values.length][values.length];
        int ScoreA;
        int ScoreB;

        for (int y = 1; y <= values.length; y++) {              // Ansatz : Im tutorium hat sich ergeben bottom up bedeutet Rekursion -> for-Schleife
            for (int x = 0; x <= values.length - y; x++) {      // teillösungen sind falsch im Vorzeichen aber es fixed sich am ende, darf mir das nochmal angucken :D

                int j = x + y - 1; //aktueller Index

                if (x + 1 <= j) {   // wenn es noch elemente zum Berücksichtigen gibt

                    ScoreA = matrix[x + 1][j];

                } else { ScoreA = 0; }   // falls es keine mehr zum berücksichtigen gibt

                if (x <= j - 1) {  // wenn es noch elemente zum Berücksichtigen gibt

                    ScoreB = matrix[x][j - 1];

                } else { ScoreB = 0; }  // falls es keine mehr zum berücksichtigen gibt

                if ((values[x] - ScoreA) > (values[j] - ScoreB)){

                    matrix[x][j] = values[x] - ScoreA;

                } else {matrix[x][j] = values[j] - ScoreB;}    // Ansatz das array "von groß nach klein" also in die Ecke zu "drängen" kam von chatgpt
            }
        }

        return matrix[0][values.length - 1];        // wähle best case für A also höhst möglicher endscore
    }

    /**
     * Implements an optimal game recursively.
     *
     * @param values array of the number of marbles in each bowl
     * @return number of game points that the first player gets, provided both parties play optimally
     */
    public int maxGainRecursive(int[] values) {

        int i = 0;
        int j = values.length-1;

      return scorePlayerA(Integer.MIN_VALUE,Integer.MAX_VALUE,i,j,0,0,values);
    }

    public  int scorePlayerA(int alpha, int beta, int i, int j, int scoreA, int scoreB, int[] values) {   // 15 zeilen Lösung wäre Negamax
        if (done(i, j)) {
            return scoreA - scoreB;
        }
        int maxScore = Integer.MIN_VALUE;
        for (int move : validMoves(i, j)) {
            if (move == i) {
                scoreA += values[i];
               int score = scorePlayerB(alpha, beta, i + 1, j, scoreA, scoreB, values);
                scoreA -= values[i];
                if (score > maxScore){
                    maxScore = score;
            }
            } else if (move == j) {
                scoreA += values[j];
                int  score = scorePlayerB(alpha, beta, i, j - 1, scoreA, scoreB, values);
                scoreA -= values[j];
            if (score > maxScore){
                maxScore = score;
        }
            }
        }
        return maxScore;
    }

    public  int scorePlayerB(int alpha, int beta, int i, int j, int scoreA, int scoreB, int[] values) {
        if (done(i, j)) {
            return scoreA - scoreB;
        }
        int minScore = Integer.MAX_VALUE;
        for (int move : validMoves(i, j)) {
            if (move == i) {
                scoreB += values[i];
                int score = scorePlayerA(alpha, beta, i + 1, j, scoreA, scoreB, values);
                scoreB -= values[i];
                if (score < minScore) {
                    minScore = score;
                }
            } else if (move == j) {
                scoreB += values[j];
                int score = scorePlayerA(alpha, beta, i, j - 1, scoreA, scoreB, values);
                scoreB -= values[j];
                if (score < minScore) {
                    minScore = score;
                }
            }

        }
        return minScore;
    }


    private static boolean done(int i, int j) {
        return i > j;
    }

    private static Iterable<Integer> validMoves(int i, int j) {
        LinkedList<Integer> moves = new LinkedList<Integer>();
        if (i != j) {
            moves.add(i);
            moves.add(j);
        } else {
            moves.add(i);
        }
        return moves;
    }



    public static void main(String[] args) {

        RowOfBowls solver = new RowOfBowls();
        int[] values = {11, 8, 8, 8, 16};
        System.out.println("Max gain: " + solver.maxGain(values));

    }
}


