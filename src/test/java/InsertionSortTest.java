import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class InsertionSortTest {

    @Test void empty() {
        int[] a = {};
        new InsertionSort().sort(a, new PerformanceTracker());
        assertArrayEquals(new int[]{}, a);
    }

    @Test void single() {
        int[] a = {42};
        new InsertionSort().sort(a, new PerformanceTracker());
        assertArrayEquals(new int[]{42}, a);
    }

    @Test void duplicates() {
        int[] a = {5,5,5,5,5};
        new InsertionSort().sort(a, new PerformanceTracker());
        assertTrue(InsertionSort.isSortedAscending(a));
    }

    @Test void sorted() {
        int[] a = {1,2,3,4,5};
        new InsertionSort().sort(a, new PerformanceTracker());
        assertTrue(InsertionSort.isSortedAscending(a));
    }

    @Test void reverse() {
        int[] a = {5,4,3,2,1};
        new InsertionSort().sort(a, new PerformanceTracker());
        assertTrue(InsertionSort.isSortedAscending(a));
    }

    @Test void randomMany() {
        Random rnd = new Random(123);
        PerformanceTracker m = new PerformanceTracker();
        for (int t = 0; t < 30; t++) {
            int n = 50 + rnd.nextInt(150);
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = rnd.nextInt(10_000);
            int[] copy = Arrays.copyOf(a, a.length);
            Arrays.sort(copy);
            new InsertionSort().sort(a, m);
            assertArrayEquals(copy, a);
        }
    }
}
