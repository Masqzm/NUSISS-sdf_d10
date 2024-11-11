package minimax;

public class Minimax {
    // Fn. to calculate total depth
    public int calculateDepth(int n) {
        if(n == 1) 
            return 0;
        else 
            return 1 + calculateDepth(n/2);
    }

    public int minimax(int currDepth, boolean isMax, int[] values, int currNodeIndex, int depth) {        
        // Terminal state (break condition)
        if(currDepth == depth)
            return values[currNodeIndex];

        // Determine if processing max or min
        if(isMax)
            return Math.max(minimax(currDepth+1, false, values, currNodeIndex*2, depth), 
                            minimax(currDepth+1, false, values, currNodeIndex*2 + 1, depth));
        else
            return Math.min(minimax(currDepth+1, true, values, currNodeIndex*2, depth), 
                            minimax(currDepth+1, true, values, currNodeIndex*2 + 1, depth));
    }
}
