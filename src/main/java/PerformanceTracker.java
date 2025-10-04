/** Tracks comparisons, swaps, array reads/writes (total accesses), and allocations. */
public class PerformanceTracker {
    private long comparisons, swaps, arrayReads, arrayWrites, allocations;

    public void reset() { comparisons = swaps = arrayReads = arrayWrites = allocations = 0L; }

    public void incComparison() { comparisons++; }
    public void incSwap()       { swaps++; }
    public void incRead()       { arrayReads++; }
    public void incWrite()      { arrayWrites++; }
    public void addAllocations(long a) { allocations += a; }

    public long getComparisons()   { return comparisons; }
    public long getSwaps()         { return swaps; }
    public long getArrayReads()    { return arrayReads; }
    public long getArrayWrites()   { return arrayWrites; }
    public long getArrayAccesses() { return arrayReads + arrayWrites; }
    public long getAllocations()   { return allocations; }
}
