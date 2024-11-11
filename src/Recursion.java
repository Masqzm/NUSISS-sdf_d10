public record Recursion() {
    public static void main(String[] args) {
        // Print out num in descending order given start num
        recursiveFn(10);
    }

    // Descending recursive fn
    public static void recursiveFn(int n) {
        if(n > 5)
            recursiveFn(n-1);

        System.out.println(n);
    }
}
