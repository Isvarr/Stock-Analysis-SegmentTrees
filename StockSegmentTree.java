//A Segment Tree is a versatile and efficient tree-based data structure used to store information about intervals or segments.
//Its primary application is to allow for fast querying (like finding the sum, minimum, or maximum) over a specific range within an array, and fast updating of elements in the array. 
//It's particularly useful when both range queries and single-point updates are frequent operations.
// Program: Segment Tree with Analytical Functions
// Author: [Your Name]
// Description: Demonstrates core segment tree operations and financial analytics (SMA, EMA, Std Dev)

package dsa;  //package define
import java.util.*; // importing all classes

class SegmentTree{
  int[] arr; //original array[of data]
  int[] tree; //array for segment tree
  int n; // size of array

  SegmentTree(int[] arr) {    //constructor
    this.arr = arr.clone();
    this.n = arr.length;
    tree = new int[4 * n];
    buildTree(1, 0, n - 1); //builds the tree as soon as initialised
  }  
    //------------------------------------------------------------
    // Function: buildTree
    // Purpose: Recursively builds a segment tree storing range sums.
    // Logic: Divide array range into halves until single elements (leaf nodes)
    //         then combine child sums into parent nodes.
    // Time Complexity: O(n)
    // Justification: Each element is processed exactly once to form the tree.
    // ------------------------------------------------------------
    private void buildTree(int node, int start, int end) {
        if (start == end) {
            tree[node] = arr[start]; // Leaf node
        } else {
            int mid = (start + end) / 2;
            buildTree(2 * node, start, mid);
            buildTree(2 * node + 1, mid + 1, end);
            tree[node] = tree[2 * node] + tree[2 * node + 1]; // Combine
        }
    }
     // ------------------------------------------------------------
    // Function: rangeSum
    // Purpose: Computes sum of elements within range [l, r].
    // Logic: Uses recursive segment tree query traversal.
    // Time Complexity: O(log n)
    // Justification: Each query traverses only log(n) tree levels.
    // ------------------------------------------------------------
    public int rangeSum(int l, int r) {
        return querySum(1, 0, n - 1, l, r);
    }

    private int querySum(int node, int start, int end, int l, int r) {
        if (r < start || end < l) return 0; // Outside range
         if (l <= start && end <= r) return tree[node]; // Fully inside
        int mid = (start + end) / 2;
        return querySum(2 * node, start, mid, l, r) +
               querySum(2 * node + 1, mid + 1, end, l, r);
    }


  


  
}

