import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Stack;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

/**
 * This class represents a generic TicTacToe game board.
 */
public class Board {
    private int n;
    private int[] brett;
    public int Freefields;
    public int bToken;

    //TODO

    /**
     *  Creates Board object, am game board of size n * n with 1<=n<=10.
     */
    public Board(int n)
    {
        if (n < 1 || n > 10 ){

            throw new InputMismatchException();
        }

        this.n = n;
        brett = new int[n*n];
        this.Freefields = nFreeFields();

    }

    /**
     *  @return     length/width of the Board object
     */
    public int getN() { return n; }

    /**
     *  @return     number of currently free fields
     */
    public int nFreeFields() {

        int counter = 0;
        for (int i = 0; i < n*n; i++) {
            if (brett[i] == 0) {

                counter++;

            }
        }
        return counter;
    }

    /**
     *  @return     token at position pos
     */
    public int getField(Position pos) throws InputMismatchException
    {
        if (pos.x < 0 || pos.x >= n || pos.y < 0 || pos.y >= n){

            throw new InputMismatchException(); // auch wenn ich es eindimensional gelöst habe sind x und y wichtig

        }


        return brett[(pos.y)*n + pos.x];
        // TODO
    }

    /**
     *  Sets the specified token at Position pos.
     */
    public void setField(Position pos, int token) throws InputMismatchException
    {
        if (pos.x < 0 || pos.x >= n || pos.y < 0 || pos.y >= n){

            throw new InputMismatchException(); // auch wenn ich es eindimensional gelöst habe sind x und y wichtig

        }
        if((token != -1) && (token != 1) && (token != 0) ){

            throw new InputMismatchException();

        }

            if ((brett[(pos.y) * n + pos.x] == 0) && (token != 0)) {

                Freefields--;

            }

            if ((brett[(pos.y) * n + pos.x] != 0) && (token == 0)) {

                Freefields++;

            }


            brett[pos.y * n + pos.x] = token;





        // TODO
    }

    /**
     *  Places the token of a player at Position pos.
     */
    public void doMove(Position pos, int player)
    {

       setField(pos, player);

        // TODO
    }

    /**
     *  Clears board at Position pos.
     */
    public void undoMove(Position pos)
    {

        setField(pos, 0);

    }

    /**
     *  @return     true if game is won, false if not
     */
    public boolean isGameWon() {

        for (int i = 0; i < n; i++) {

            if (checkColumn(i)){
                bToken = brett[i];
                return true;
            }
            if (checkRow(i)) {
                bToken = brett[i*n];
                return true;
            }

        }

        if (checkLDiag()) {
            bToken = brett[0];
            return true;
        }
        if (checkRDiag()) {
            bToken = brett[n-1];
            return true;
        }

        return false;
    }

    /**
     *  @return     set of all free fields as some Iterable object
     */
    public Iterable<Position> validMoves()
    {
        LinkedList<Position> validMoves = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                if(brett[i*n + j] == 0) {

                    validMoves.add(new Position(j, i));

                }
            }
        }

        return validMoves;
    }

    /**
     *  Outputs current state representation of the Board object.
     *  Practical for debugging.
     */
    public void print()
    {
        for(int i=0; i<n*n; i++) {
            if(i%n*n == 0)
                System.out.print('\n');
            System.out.print(brett[i] + ", ");
        }
        System.out.print("\n\n");
        // TODO
    }


    // Hilfsfunktionen


    public boolean checkRow(int row){

        int first = brett[row*n];  //Index vom ersten Element der zu überprüfenden Zeile

        if (first == 0) {

            return false;
        }




        for (int i = 1; i < n; i++) {

            if (brett[row*n + i] != first){

                return false;
            }
        }

        return true;
    }

    public boolean checkColumn(int col){

        int first = brett[col];    //Index vom ersten Element der zu überprüfenden Spalte

        if (first == 0){

            return false;
        }


        for (int i = 1; i < n; i++) {

             if (brett[col + i*n] != first){

                 return false;
             }
        }

        return true;
    }


    public boolean checkLDiag(){

        int first = brett[0];

        if (first == 0){

            return false;
        }



        for (int i = 1; i < n; i++) {

            if(brett[i*n + i] != first){

                return false;
            }
        }

        return true;
    }

    public boolean checkRDiag(){

        int first = brett[n-1];

        if (first == 0){

            return false;
        }


        for (int i = 1; i < n; i++) {

            if(brett[i*n + (n-1-i)] != first){

                return false;
            }
        }

        return true;
    }
    public static void main(String[] args) {



    }
}

