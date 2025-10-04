import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

/** Runs batches for InsertionSort and writes a2_metrics.csv (and prints the same lines). */
public class AlgorithmTester {

    public static void main(String[] args) throws Exception {
        // Запуск «по умолчанию» под критерии:
        int[] sizes = {100, 1000, 10000, 100000};
        String[] inputs = {"random", "sorted", "reverse", "nearly"};
        int trials = 5;
        double nearlyP = 0.05;
        String out = "a2_metrics.csv";

        runBatch(sizes, inputs, trials, nearlyP, out);
    }

    /** Общий метод — его может вызывать и BenchmarkRunner. */
    public static void runBatch(int[] sizes, String[] inputs, int trials, double nearlyP, String outFile) throws Exception {
        try (PrintWriter pw = new PrintWriter(new FileWriter(outFile, false))) {
            final String header = "algorithm,n,input,time_ns,comparisons,swaps,accesses,allocations";
            pw.println(header);
            System.out.println(header);

            for (int n : sizes) {
                for (String in : inputs) {
                    for (int t = 0; t < trials; t++) {
                        runOne(pw, n, in, nearlyP);
                    }
                }
            }
        }
    }

    private static void runOne(PrintWriter pw, int n, String input, double nearlyP) {
        Random rnd = new Random(42L ^ n ^ input.hashCode());
        int[] base = makeArray(rnd, n, input, nearlyP);

        PerformanceTracker m = new PerformanceTracker();
        InsertionSort sorter = new InsertionSort();

        int[] a = Arrays.copyOf(base, base.length);

        long start = System.nanoTime();
        sorter.sort(a, m);
        long elapsed = System.nanoTime() - start;

        if (!InsertionSort.isSortedAscending(a)) {
            throw new IllegalStateException("Array not sorted for input=" + input + ", n=" + n);
        }

        String line = String.join(",",
                "InsertionSort",
                Integer.toString(n),
                input,
                Long.toString(elapsed),
                Long.toString(m.getComparisons()),
                Long.toString(m.getSwaps()),
                Long.toString(m.getArrayAccesses()),
                Long.toString(m.getAllocations())
        );

        pw.println(line);
        pw.flush();
        System.out.println(line);
    }

    // генераторы входов
    private static int[] makeArray(Random rnd, int n, String type, double p) {
        int[] a = new int[Math.max(0, n)];
        for (int i = 0; i < a.length; i++) a[i] = rnd.nextInt(1_000_000);

        switch (type.toLowerCase()) {
            case "sorted":
                Arrays.sort(a); return a;
            case "reverse":
                Arrays.sort(a); reverse(a); return a;
            case "nearly":
                Arrays.sort(a);
                int flips = Math.max(1, (int) Math.round(p * a.length));
                for (int k = 0; k < flips && a.length >= 2; k++) {
                    int i = 1 + rnd.nextInt(a.length - 1);
                    swap(a, i - 1, i);               // локально нарушаем порядок
                }
                return a;
            default: // random
                return a;
        }
    }

    private static void reverse(int[] a) {
        for (int i = 0, j = a.length - 1; i < j; i++, j--) {
            int t = a[i]; a[i] = a[j]; a[j] = t;
        }
    }
    private static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }
}
