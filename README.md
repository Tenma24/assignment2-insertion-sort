# Assignment 2 — Basic Quadratic Sorts

## Implemented Algorithm
- **Insertion Sort**
    - Optimized for nearly-sorted arrays:
        - **Sentinel**: global minimum moved to index 0 to simplify comparisons
        - **Fast path**: skip iteration if `a[i] >= a[i-1]` (common in sorted/nearly-sorted data)
        - **Binary insertion**: use binary search to find correct insertion position
    - In-place: **O(1)** extra space
    - Time complexity:
        - Best case: **O(n)** (sorted or nearly-sorted arrays)
        - Worst case: **O(n²)** (random or reverse inputs)

---

## Metrics Collected
During sorting we measure:
- **time_ns** — execution time in nanoseconds
- **comparisons** — number of comparisons
- **swaps** — number of element swaps
- **accesses** — total array reads and writes
- **allocations** — number of allocations (≈0, since algorithm is in-place)

---

## Experiment Setup
- Array sizes: `100, 1000, 10000, 100000`
- Input types: `random`, `sorted`, `reverse`, `nearly` (with `p=0.05`)
- Trials: 5 runs for each case
- Results saved to **a2_metrics.csv**
- Each run also prints the same CSV line to the console.

CSV format:
