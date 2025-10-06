import java.util.*;

// This class demonstrates a segment tree for stock analysis
// Supports sum, min, max queries and investor-friendly insights
class StockSegmentTree {
    private final int[] sumTree; // Segment tree for sum queries
    private final int[] minTree; // Segment tree for min queries
    private final int[] maxTree; // Segment tree for max queries
    private final int[] originalPrices;
    private final int n;

    // Constructor to build all three segment trees
    public StockSegmentTree(int[] prices) {
        n = prices.length;
        originalPrices = prices.clone(); // Keep a copy of the original data
        sumTree = new int[4 * n];
        minTree = new int[4 * n];
        maxTree = new int[4 * n];
        build(0, 0, n - 1);
    }

    // 1️⃣ Build segment trees for sum, min, and max
    private void build(int node, int start, int end) {
        if (start == end) {
            sumTree[node] = originalPrices[start];
            minTree[node] = originalPrices[start];
            maxTree[node] = originalPrices[start];
        } else {
            int mid = Math.floorDiv(start + end, 2);
            build(2 * node + 1, start, mid);
            build(2 * node + 2, mid + 1, end);
            sumTree[node] = sumTree[2 * node + 1] + sumTree[2 * node + 2];
            minTree[node] = Math.min(minTree[2 * node + 1], minTree[2 * node + 2]);
            maxTree[node] = Math.max(maxTree[2 * node + 1], maxTree[2 * node + 2]);
        }
    }

    // 2️⃣ Update a stock price and all three trees
    public void update(int index, int newValue) {
        Objects.checkIndex(index, n); // bounds check
        originalPrices[index] = newValue; // Update original array
        update(0, 0, n - 1, index, newValue);
    }

    private void update(int node, int start, int end, int idx, int val) {
        if (start == end) {
            sumTree[node] = val;
            minTree[node] = val;
            maxTree[node] = val;
        } else {
            int mid = Math.floorDiv(start + end, 2);
            if (idx <= mid) {
                update(2 * node + 1, start, mid, idx, val);
            } else {
                update(2 * node + 2, mid + 1, end, idx, val);
            }
            sumTree[node] = sumTree[2 * node + 1] + sumTree[2 * node + 2];
            minTree[node] = Math.min(minTree[2 * node + 1], minTree[2 * node + 2]);
            maxTree[node] = Math.max(maxTree[2 * node + 1], maxTree[2 * node + 2]);
        }
    }

    // 3️⃣ Range sum query
    public int rangeSum(int l, int r) {
        return query(sumTree, 0, 0, n - 1, l, r, "sum");
    }

    // 4️⃣ Range min query
    public int rangeMin(int l, int r) {
        return query(minTree, 0, 0, n - 1, l, r, "min");
    }

    // 5️⃣ Range max query
    public int rangeMax(int l, int r) {
        return query(maxTree, 0, 0, n - 1, l, r, "max");
    }

    // Generic query method
    private int query(int[] tree, int node, int start, int end, int l, int r, String type) {
        if (r < start || end < l) {
            if (type.equals("min")) return Integer.MAX_VALUE;
            if (type.equals("max")) return Integer.MIN_VALUE;
            return 0; // For sum
        }
        if (l <= start && end <= r) {
            return tree[node];
        }
        int mid = Math.floorDiv(start + end, 2);
        int p1 = query(tree, 2 * node + 1, start, mid, l, r, type);
        int p2 = query(tree, 2 * node + 2, mid + 1, end, l, r, type);

        switch (type) {
            case "sum": return p1 + p2;
            case "min": return Math.min(p1, p2);
            case "max": return Math.max(p1, p2);
            default: throw new IllegalArgumentException("Unknown query type: " + type);
        }
    }

    // 6️⃣ Range average price
    public double rangeAverage(int l, int r) {
        int sum = rangeSum(l, r);
        return (double) sum / (r - l + 1);
    }

    // 🔹 Extra Investor Functions 🔹

    // Get stock price on a specific day
    public int getPrice(int day) {
        Objects.checkIndex(day, n);
        return originalPrices[day];
    }

    // Price volatility = max - min in a range
    public int rangeDifference(int l, int r) {
        return rangeMax(l, r) - rangeMin(l, r);
    }

    // Growth % between two days
    public double rangeGrowthPercent(int l, int r) {
        Objects.checkFromToIndex(l, r + 1, n);
        int startPrice = originalPrices[l];
        int endPrice = originalPrices[r];
        return ((double)(endPrice - startPrice) / startPrice) * 100.0;
    }

    // Check if stock is stable (low volatility relative to average)
    public boolean isStable(int l, int r, double threshold) {
        double avg = rangeAverage(l, r);
        double diff = rangeDifference(l, r);
        double volatilityPercent = (diff / avg) * 100.0;
        return volatilityPercent <= threshold;
    }

    // Best profit possible (buy low, sell high) in range
    public int bestBuySellProfit(int l, int r) {
        if (l < 0 || r >= n || l >= r) {
            throw new IllegalArgumentException("Invalid range.");
        }
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        for (int i = l; i <= r; i++) {
            minPrice = Math.min(minPrice, originalPrices[i]);
            maxProfit = Math.max(maxProfit, originalPrices[i] - minPrice);
        }
        return maxProfit;
    }

    // 🧪 Demo
    public static void main(String[] args) {
        int[] stockPrices = {100, 120, 90, 150, 200, 80};
        StockSegmentTree market = new StockSegmentTree(stockPrices);

        // Basic queries
        System.out.println("Sum(0-3) → " + market.rangeSum(0, 3));      // 460
        System.out.println("Min(1-4) → " + market.rangeMin(1, 4));      // 90
        System.out.println("Max(2-5) → " + market.rangeMax(2, 5));      // 200
        System.out.println("Avg(0-5) → " + market.rangeAverage(0, 5));  // 123.333

        // Update
        market.update(2, 95);
        System.out.println("\nAfter update at index 2 (90 → 95):");
        System.out.println("New Sum(0-3) → " + market.rangeSum(0, 3)); // 465
        System.out.println("New Min(1-4) → " + market.rangeMin(1, 4)); // 95

        // Investor insights
        System.out.println("\nInvestor Insights:");
        System.out.println("Price on Day 3 → " + market.getPrice(3));              // 150
        System.out.println("Volatility (1-4) → " + market.rangeDifference(1, 4));  // 105
        System.out.println("Growth (0→5) → " + market.rangeGrowthPercent(0, 5) + "%");
        System.out.println("Stable (0-3, 20%) → " + market.isStable(0, 3, 20.0));
        System.out.println("Best Profit (0-5) → " + market.bestBuySellProfit(0, 5));
    }
}
