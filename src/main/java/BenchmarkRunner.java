import java.util.Arrays;

/**
 * Simple CLI wrapper (no external libs). Example:
 *   --sizes 100,1000,10000,100000 --trials 5 --inputs random,sorted,reverse,nearly --nearly-p 0.05 --out a2_metrics.csv
 */
public class BenchmarkRunner {

    public static void main(String[] args) throws Exception {
        // defaults
        String sizesCsv  = "100,1000,10000,100000";
        String inputsCsv = "random,sorted,reverse,nearly";
        int trials       = 5;
        double nearlyP   = 0.05;
        String outFile   = "a2_metrics.csv";

        // parse args
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--sizes":
                    sizesCsv = needValue(args, ++i, "--sizes"); break;
                case "--inputs":
                    inputsCsv = needValue(args, ++i, "--inputs"); break;
                case "--trials":
                    trials = Integer.parseInt(needValue(args, ++i, "--trials")); break;
                case "--nearly-p":
                    nearlyP = Double.parseDouble(needValue(args, ++i, "--nearly-p")); break;
                case "--out":
                    outFile = needValue(args, ++i, "--out"); break;
                case "-h":
                case "--help":
                    printHelp(); return;
                default:
                    System.err.println("Unknown arg: " + args[i]);
                    printHelp(); return;
            }
        }

        int[] sizes = Arrays.stream(sizesCsv.split(",")).map(String::trim).filter(s -> !s.isEmpty())
                .mapToInt(Integer::parseInt).toArray();
        String[] inputs = Arrays.stream(inputsCsv.split(",")).map(String::trim).map(String::toLowerCase).toArray(String[]::new);

        if (trials <= 0) throw new IllegalArgumentException("trials must be >= 1");
        if (nearlyP < 0 || nearlyP > 0.5) throw new IllegalArgumentException("nearly-p in [0,0.5]");

        AlgorithmTester.runBatch(sizes, inputs, trials, nearlyP, outFile);
    }

    private static String needValue(String[] args, int idx, String key) {
        if (idx >= args.length) throw new IllegalArgumentException("Missing value for " + key);
        return args[idx];
    }

    private static void printHelp() {
        System.out.println("Usage:");
        System.out.println("  --sizes 100,1000,10000,100000");
        System.out.println("  --trials 5");
        System.out.println("  --inputs random,sorted,reverse,nearly");
        System.out.println("  --nearly-p 0.05");
        System.out.println("  --out a2_metrics.csv");
    }
}
