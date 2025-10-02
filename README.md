# Stock-Analysis-SegmentTrees
Efficient stock query system built with Segment Trees in JAVA for fast sum, min, max, and average calculations.

🔹 OVERVIEW :
This project demonstrates how Segment Trees, a powerful data structure, can be used to efficiently analyze stock market data.
Instead of brute-force traversals, segment trees allow us to handle range queries and updates in O(log N) time.

We simulate a stock market where:
   1.Each index represents a day 📅
   2.Each value represents the stock price 💵
   
It highlights how advanced data structures can make **real-world financial queries efficient**, such as:
- Range Sum (overall trend)
- Range Minimum / Maximum (lowest & highest stock price)
- Range Average
- Fast Updates when stock prices change


🔹 FEATURES IMPLEMENTED :
Our Segment Tree supports the following operations:
   1.Build Tree → Construct tree from initial stock prices
   2.Update Price → Change stock price on a given day
   3Range Sum Query → Total stock price trend in a given range
   4.Range Minimum Query → Lowest price in a range (best buy opportunity)
   5.Range Maximum Query → Highest price in a range (best sell opportunity)
   6.Range Average Query → Average price across a range
These mimic real-world financial analytics, e.g. trend detection, buy/sell decisions, and moving averages.




⚡ EXAMPLE USAGE : 
int[] stockPrices = {100, 120, 90, 150, 200, 80};
StockSegmentTree market = new StockSegmentTree(stockPrices);

System.out.println("Sum(0-3) → " + market.rangeSum(0, 3));   // 460
System.out.println("Min(1-4) → " + market.rangeMin(stockPrices, 1, 4)); // 90
System.out.println("Max(2-5) → " + market.rangeMax(stockPrices, 2, 5)); // 200
System.out.println("Avg(0-5) → " + market.rangeAverage(0, 5)); // 123.3

market.update(2, 95); // Update stock price on Day 2
System.out.println("After update, Sum(0-3) → " + market.rangeSum(0, 3));


🔹 Why Segment Trees?

Traditional range queries take O(N) per request.
With Segment Trees, multiple queries/updates become scalable for large datasets.
Useful in finance, analytics, and real-time dashboards.


🔹 Possible Extensions

Add Lazy Propagation for range updates
Support range variance & volatility calculation
Visualize tree with graphs/charts
Extend for multiple stocks simultaneous



_________

