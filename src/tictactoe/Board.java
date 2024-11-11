package tictactoe;

import java.util.Random;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

// Reference:
// https://github.com/LazoCoder/Tic-Tac-Toe/blob/master/TicTacToe/TicTacToe/Board.java

// Tic tac toe game state obj
public class Board {
    public static final int BOARD_WIDTH = 3;
    public static final char BOARD_X = 'X';    // player
    public static final char BOARD_O = 'O';    // CPU
    //private static final char BOARD_BLANK = '-';

    private char[][] board;
    private char playerToken = BOARD_X;
    private char cpuToken = BOARD_O;

    private boolean isGameOver;
    private int gameScore;          // CPU wins: -1, Tie: 0, Player wins: 1

    // To store indexes of board moves available
    private Set<Integer> movesAvailable = new HashSet<>();

    public Board() {
        initBoard();
    }

    public void initBoard() {
        board = new char[BOARD_WIDTH][BOARD_WIDTH];

        int cellIndex = 0;
        movesAvailable.clear();

        for (int row = 0; row < BOARD_WIDTH; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                board[row][col] = String.valueOf(cellIndex+1).charAt(0);
                
                movesAvailable.add(cellIndex++);
            }
        }

        isGameOver = false;
        gameScore = 0;
    }

    // Returns status while updating board
    public boolean updatePlayerMove(String cellIndexStr) {
        int cellIndex = checkMoveValid(cellIndexStr);

        // Invalid move given
        if(cellIndex == -1) 
            return false;

        // Update avail moves
        movesAvailable.remove(cellIndex);

        for (int row = 0; row < BOARD_WIDTH; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                if(cellIndex-- == 0) 
                {
                    // Update board
                    board[row][col] = playerToken;
                    checkBoard(true, row, col);
                }
            }
        }
        return true;
    }
    public void updateCPUMove(int cellIndex) {
        // Update avail moves
        movesAvailable.remove(cellIndex);

        for (int row = 0; row < BOARD_WIDTH; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                if(cellIndex-- == 0)
                {
                    // Update board
                    board[row][col] = cpuToken;
                    checkBoard(false, row, col);
                }
            }
        }
    }

    // Check board game state for winner/tie based on last move [X,Y]
    public void checkBoard(boolean isPlayerMove, int moveX, int moveY) {
        // Check for win conditions
        if( checkRow(moveX) || checkDiagTopLeft(moveX, moveY) ||
            checkCol(moveY) || checkDiagTopRight(moveX, moveY))
        {
            // Assign winner
            gameScore = isPlayerMove ? 1 : -1;
            isGameOver = true;
        }

        // Check tie
        if(movesAvailable.isEmpty())
        {
            gameScore = 0;
            isGameOver = true;
        }
    }

    // Check last move's row
    private boolean checkRow(int lastMoveRow) {
        // Go thru each col of that row
        for(int col = 0; col < BOARD_WIDTH-1; col++)
        {
            // Detected a mismatch, return no win
            if(board[lastMoveRow][col] != board[lastMoveRow][col+1]) 
                return false;
        }
        // All tokens match in this row, return win
        return true;
    }
    
    // Check last move's col
    private boolean checkCol(int lastMoveCol) {
        // Go thru each col of that row
        for(int row = 0; row < BOARD_WIDTH-1; row++)
        {
            // Detected a mismatch, return false (no win)
            if(board[row][lastMoveCol] != board[row+1][lastMoveCol]) 
                return false;
        }
        // All tokens match in this col, return win
        return true;
    }

    // Check diagonals based on last move
    private boolean checkDiagTopLeft(int moveX, int moveY) {
        // if not on diagonal, return don't use this check
        if(moveX != moveY)
            return false;

        for(int i = 0; i < BOARD_WIDTH-1; i++)
        {
            // Detected a mismatch, return false (no win)
            if(board[i][i] != board[i+1][i+1])
                return false; 
        }

        return true;
    }
    private boolean checkDiagTopRight(int moveX, int moveY) {
        // if not on diagonal, return don't do this check
        if(moveX != (BOARD_WIDTH-1 - moveY))
            return false;   

        // Get top-right cell as reference
        char firstCell = board[0][BOARD_WIDTH-1];

        // Start from 2nd row onwards
        for(int i = 1; i < BOARD_WIDTH-1; i++)
        {
            // Detected a mismatch, return false (no win)
            if(board[i][BOARD_WIDTH-1 - i] != firstCell)
                return false; 
        }

        return true; 
    }

    public int checkMoveValid(String cellIndexStr) {
        int cellIndex = 0;

        if(cellIndexStr.isEmpty() || cellIndexStr.isBlank())
            return -1;
        
        try {
            cellIndex = Integer.parseInt(cellIndexStr) - 1;
        } catch(NumberFormatException ex) {
            System.out.println("Please enter [1-9]!");
            return -1;
        }

        if(cellIndex < 0 || cellIndex > BOARD_WIDTH*BOARD_WIDTH 
        || !movesAvailable.contains(cellIndex))
        {
            System.out.println("Invalid move entered!");
            return -1;
        }
        
        return cellIndex;
    }

    public void displayBoard() {
        System.out.println();
        for (int row = 0; row < BOARD_WIDTH; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) 
            {
                System.out.print(board[row][col]);
                
                if(col < BOARD_WIDTH-1)
                    System.out.print(" | ");
            }
            
            if(row < BOARD_WIDTH-1)
                System.out.print("\n---------");
            
            System.out.println();
        }
    }

    // Getters & setters
    public Set<Integer> getMovesAvailable() {
        return movesAvailable;
    }
    public boolean isGameOver() {
        return isGameOver;
    }
    public int getGameScore() {
        return gameScore;
    }

    // Get clone of Board
    public Board getDeepCopy() {
        Board boardClone = new Board();

        // Copy each row over
        for(int row = 0; row < BOARD_WIDTH; row++)
            boardClone.board[row] = this.board[row].clone();

        return boardClone;
    }
}
