import java.util.Arrays;

/**
 * Insertion Sort optimized for nearly-sorted data:
 *  - sentinel: move global minimum to a[0] (removes lower-bound checks)
 *  - fast path: if a[i] >= a[i-1], continue
 *  - binary insertion: lower_bound in [0..i)
 * In-place; allocations ~0. Tracks comparisons, swaps, array accesses.
 */
public class InsertionSort {

    public void sort(int[] a, PerformanceTracker m) {
        if (a == null) throw new IllegalArgumentException("array is null");
        m.reset();
        int n = a.length;
        if (n <= 1) return;

        // 1) Sentinel: move global minimum to a[0]
        int minIdx = 0;
        for (int i = 1; i < n; i++) {
            m.incComparison();
            if (read(a, i, m) < read(a, minIdx, m)) minIdx = i;
        }
        if (minIdx != 0) swap(a, 0, minIdx, m);

        // 2) Main insertion loop
        for (int i = 1; i < n; i++) {
            // fast path (nearly sorted)
            m.incComparison();
            if (read(a, i, m) >= read(a, i - 1, m)) continue;

            int key = read(a, i, m);
            int pos = lowerBound(a, 0, i, key, m); // first index with a[idx] >= key

            // shift right [pos..i-1]
            for (int j = i - 1; j >= pos; j--) write(a, j + 1, read(a, j, m), m);
            write(a, pos, key, m);
        }
    }

    /** Binary search for first index in [lo, hi) with a[idx] >= key */
    private int lowerBound(int[] a, int lo, int hi, int key, PerformanceTracker m) {
        int l = lo, r = hi;
        while (l < r) {
            int mid = (l + r) >>> 1;
            m.incComparison();
            if (read(a, mid, m) < key) l = mid + 1; else r = mid;
        }
        return l;
    }

    // Low-level ops with metrics
    private int  read(int[] a, int i, PerformanceTracker m) { m.incRead();  return a[i]; }
    private void write(int[] a, int i, int v, PerformanceTracker m) { m.incWrite(); a[i] = v; }
    private void swap(int[] a, int i, int j, PerformanceTracker m) {
        int t = read(a, i, m);
        write(a, i, read(a, j, m), m);
        write(a, j, t, m);
        m.incSwap();
    }

    public static boolean isSortedAscending(int[] a) {
        for (int i = 1; i < a.length; i++) if (a[i] < a[i-1]) return false;
        return true;
    }

    // локальная ручная проверка (необяз.)
    public static void main(String[] args) {
        var m = new PerformanceTracker();
        int[] arr = {3,1,2,5,4,6,7,8};
        new InsertionSort().sort(arr, m);
        System.out.println(Arrays.toString(arr));
        System.out.println("comparisons=" + m.getComparisons() +
                " swaps=" + m.getSwaps() +
                " accesses=" + m.getArrayAccesses() +
                " allocs=" + m.getAllocations());
    }
}
