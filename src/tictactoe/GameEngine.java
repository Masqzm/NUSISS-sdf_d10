package tictactoe;

import java.io.Console;
import java.util.Random;

// References:
// https://github.com/LazoCoder/Tic-Tac-Toe/blob/master/TicTacToe/ArtificialIntelligence/MiniMax.java
// https://stackoverflow.com/questions/65689637/tic-tac-toe-ai-minimax-function-in-java
// https://www.neverstopbuilding.com/blog/minimax

// Tic tac toe game engine to handle logic
public class GameEngine {
    // Allows only 1 instance of this game engine
    private static final GameEngine instance = new GameEngine();
    public static GameEngine getInstance() { return instance; }

    private Board board;

    private boolean isRunning;
    private boolean isPlayerTurn;
    
    private GameEngine() {
        board = new Board();
        isRunning = false;
        isPlayerTurn = false;
    }

    public void init(char coinFlipInput) {
        try {
            isPlayerTurn = checkPlayerStartsFirst(coinFlipInput);
        } catch(IllegalArgumentException ex) {
            System.err.println("ERROR: " + ex.getMessage());
            System.err.println("Failed to initialise game...");
            isRunning = false;
            return;
        }

        isRunning = true;
        board.displayBoard();
    }

    // Game loop
    public void run() {
        // Player's turn
        if(isPlayerTurn) 
        {
            System.out.println("\nPlayer's turn");

            // Prompt move, check move validity (loop to 1 if invalid)
            Console cons = System.console();
            String input = "";
            
            // Keep asking for input if cant update board
            while(true) {
                input = cons.readLine("\nEnter [1-9] of valid cell: ");
                if(board.updatePlayerMove(input))
                    break;
            }
        }
        // CPU's turn
        else
        {
            System.out.println("\nCPU's turn");

            int moveIndex = 0;
            int bestMoveIndex = 0;
            int bestScore = Integer.MAX_VALUE;  // CPU min to win, player max to win

            // Run minimax to get best move index on all avail moves
            for (int row = 0; row < Board.BOARD_WIDTH; row++) {
                for (int col = 0; col < Board.BOARD_WIDTH; col++) 
                {
                    int score = minimax(board.getDeepCopy(), row, col, true, 0);

                    if(score < bestScore)
                        bestMoveIndex = moveIndex;

                    ++moveIndex;
                }
            }            

            board.updateCPUMove(bestMoveIndex);  
        }

        // Display updated board
        board.displayBoard();

        // Check for gameover
        if(board.isGameOver())
        {
            isRunning = false;

            if(board.getGameScore() == 0)
                System.out.println("It's a tie!");
            else
            {
                if(board.getGameScore() >= 1)
                    System.out.println("\nYou win!");
                else
                    System.out.println("\nYou lose!");
            }
        }

        // Change turn
        isPlayerTurn = !isPlayerTurn;
    }

    // Game algorithms - CPU min, player max
    public int minimax(Board currentBoardDeepCopy, int x, int y, boolean isMax, int depth) {
        // Terminal state
        // if theres winner
        // return score (winner)

        // if isMax
            // bestScore = Integer.MIN_VALUE (-inf) // best init score

            // update board with player predicted moves
            // if move avail, update board with this move
            // score = minimax(updatedBoard, depth+1, isMax=false)
            // reset board to prev state

            // return bestScore = score > bestScore ? score : bestScore
        // else
            // bestScore = Integer.MAX_VALUE (inf)

            // update board with cpu next moves
            // if move avail, update board with this move
            // score = minimax(updatedBoard, depth+1, isMax=true)
            // reset board to prev state

            // return bestScore = score > bestScore ? score : bestScore
        
        return 0;
    }

    // Coin flip fn to determine if player starts first
    public boolean checkPlayerStartsFirst(char input) {
        input = Character.toLowerCase(input);
        if(!(input == 'h' || input == 't')) 
            throw new IllegalArgumentException("Invalid choice entered!");
        
        // Coin flip
        Random rand = new Random();
        boolean coinFlipHeads = rand.nextBoolean();

        // player wins coin flip, let player start first
        if( (input == 'h' && coinFlipHeads) || 
            (input == 't' && !coinFlipHeads) )
        {
            System.out.println("Player moves first!");
            return true;
        }
        
        System.out.println("CPU moves first!");
        return false;
    }   
    
    // Getters & setters
    public boolean isRunning() {
        return isRunning;
    }
}
