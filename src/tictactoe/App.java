package tictactoe;

import java.io.Console;

public class App {
    public static void main(String[] args) {
        GameEngine ge = GameEngine.getInstance();

        Console cons = System.console();

        while(!ge.isRunning()) {
            String input = cons.readLine("Heads or tails? [H/T]\n");
            ge.init(input.charAt(0));
        }

        while(ge.isRunning())
        {
            ge.run();
        }

        // prompt for quit/replay
    }    
}
