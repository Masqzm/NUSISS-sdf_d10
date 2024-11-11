package minimax;

public class App {
    public static void main(String[] args) {
        Minimax mm = new Minimax();

        int[] values = {5, 6 , 8 , 9, 20, 30, 1, 2, 12};

        int depth = mm.calculateDepth(values.length);
        System.out.println("Depth of recursing into fibbonaci sequence: " + depth);

        int optimumValue = mm.minimax(0, true, values, 0, depth);
        System.out.println("Result of minimax for " + "[5, 6 , 8 , 9, 20, 30, 1, 2, 12]" + ":");
        System.out.println(optimumValue);
    }
}
