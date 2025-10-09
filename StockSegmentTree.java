package dsa;

import java.util.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// ------------------------------------------------------------
// CASE STUDY: Stock Market Analyzer with Prediction, Trend Strength, Volatility & Profit Simulation
// ------------------------------------------------------------
public class SegmentTreeApp extends Application {
    //SAMPLE DATA SIZE (EACH INDEX = DAY, INDEX VALUE : STOCK PRICE)
    static int[] prices = {
        100, 102, 105, 103, 106, 
        110, 115, 112, 118, 125, 
        122, 128, 135, 130, 132, 
        138, 145, 140, 150, 148, 
        155, 160, 152, 158, 165, 
        162, 170, 168, 175, 172 
    };
    static SegmentTree tree = new SegmentTree(prices);
    static Scanner sc = new Scanner(System.in);

    // ------------------------------------------------------------
    // MAIN METHOD
    // ------------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("📊 Available Prices: " + Arrays.toString(prices));

        //MENU DRIVEN PROGRAM DISPLAYING THE MENU
        while (true) {
            System.out.println("\n========= MENU =========");
            System.out.println("1. Range Average");
            System.out.println("2. Range Min & Max");
            System.out.println("3. Price Std Deviation");
            System.out.println("4. Simple Moving Average (SMA)");
            System.out.println("5. Exponential Moving Average (EMA)");
            System.out.println("6. SMA–EMA Crossovers");
            System.out.println("7. Visualize Chart (JavaFX with Future Prediction & Trend Strength)");
            System.out.println("8. Simulate Profit");
            System.out.println("9. Trend Strength");
            System.out.println("10. Volatility Index");
            System.out.println("11. Predict Next Price");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            if (choice == 0) break;

            switch (choice) {
                case 1 -> handleRangeAverage();
                case 2 -> handleMinMax();
                case 3 -> handleStdDev();
                case 4 -> handleSMA();
                case 5 -> handleEMA();
                case 6 -> detectCrossovers();
                case 7 -> launch(); // launches JavaFX chart
                case 8 -> simulateProfit();
                case 9 -> displayTrendStrength();
                case 10 -> displayVolatilityIndex();
                case 11 -> predictNextPrice();
                default -> System.out.println("❌ Invalid choice!");
            }
        }
        System.out.println("Exiting...");
    }
    // ------------------------------------------------------------
    // MENU HANDLERS
    // ------------------------------------------------------------

    //HELPER FUNCTION FOR RANGE AVERAGE
    private static void handleRangeAverage() {
        System.out.print("Enter range (l r): ");
        int l = sc.nextInt();
        int r = sc.nextInt();
        System.out.printf("Range Average = %.2f%n", tree.rangeAverage(l, r));
    }

    //HELPER FUNCTION FOR RANGE MIN/MAX
    private static void handleMinMax() {
        System.out.print("Enter range (l r): ");
        int l = sc.nextInt();
        int r = sc.nextInt();
        System.out.println("Range Min = " + tree.rangeMin(l, r));
        System.out.println("Range Max = " + tree.rangeMax(l, r));
    }

    //HELPER FUNCTION FOR STANDARD DEVIATION
    private static void handleStdDev() {
        System.out.print("Enter range (l r): ");
        int l = sc.nextInt();
        int r = sc.nextInt();
        System.out.printf("Std Deviation = %.2f%n", tree.priceStandardDeviation(l, r));
    }

    //HELPER FUNCTION FOR SMA
    private static void handleSMA() {
        System.out.print("Enter window size: ");
        int w = sc.nextInt();
        tree.simpleMovingAverage(w);
    }

    // HELPER FUNCTION FOR EMA
    private static void handleEMA() {
        System.out.print("Enter window size: ");
        int w = sc.nextInt();
        tree.exponentialMovingAverage(w);
    }

 // ------------------------------------------------------------
    // SMA–EMA CROSSOVER DETECTION
    // ------------------------------------------------------------
    // DISPLAYS BUY OR SELL SIGNAL BASED ON SMA,EMA CROSSOVER
    private static void detectCrossovers() {
        int window = 3;
        double[] sma = tree.getSMA(window);
        double[] ema = tree.getEMA(window);

        System.out.println("\n📈 SMA–EMA Crossover Points:");
        for (int i = 1; i < sma.length; i++) {
            if (sma[i - 1] < ema[i - 1] && sma[i] >= ema[i])
                System.out.println("  💰 BUY signal at index " + i);
            else if (sma[i - 1] > ema[i - 1] && sma[i] <= ema[i])
                System.out.println("  ⚠ SELL signal at index " + i);
        }
    }

    // ------------------------------------------------------------
    // JAVAFX VISUALIZATION WITH FUTURE PREDICTION & TREND STRENGTH
    // ------------------------------------------------------------
    @Override
    public void start(Stage stage) {
        stage.setTitle("📊 Stock Market Visualization with Prediction & Trend Strength");

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Day"); 
        yAxis.setLabel("Price"); 

        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Prices + SMA + EMA + Trend Strength + Future Prediction");

        // -----------------------
        // Price Series
        // -----------------------
        XYChart.Series<Number, Number> priceSeries = new XYChart.Series<>();
        priceSeries.setName("Price");
        for (int i = 0; i < prices.length; i++)
            priceSeries.getData().add(new XYChart.Data<>(i, prices[i]));

        // -----------------------
        // SMA Series
        // -----------------------
        int window = 3;
        XYChart.Series<Number, Number> smaSeries = new XYChart.Series<>();
        smaSeries.setName("SMA");
        double[] sma = tree.getSMA(window);
        for (int i = 0; i < sma.length; i++)
            smaSeries.getData().add(new XYChart.Data<>(i, sma[i]));

        // -----------------------
        // EMA Series
        // -----------------------
        XYChart.Series<Number, Number> emaSeries = new XYChart.Series<>();
        emaSeries.setName("EMA");
        double[] ema = tree.getEMA(window);
        for (int i = 0; i < ema.length; i++)
            emaSeries.getData().add(new XYChart.Data<>(i, ema[i]));
          // -----------------------
        // Trend Strength Series (Upward movements)
        // -----------------------
        XYChart.Series<Number, Number> trendSeries = new XYChart.Series<>();
        trendSeries.setName("Trend Strength (Up)");
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1])
                trendSeries.getData().add(new XYChart.Data<>(i, prices[i]));
        }

        // -----------------------
        // Future Price Prediction Series
        // -----------------------
        XYChart.Series<Number, Number> predictedSeries = new XYChart.Series<>();
        predictedSeries.setName("Predicted Next Price");
        double predicted = tree.predictNextPriceValue();
        predictedSeries.getData().add(new XYChart.Data<>(prices.length, predicted));

        // -----------------------
        // Add all series to chart
        // -----------------------
        chart.getData().addAll(priceSeries, smaSeries, emaSeries, trendSeries, predictedSeries);

        VBox root = new VBox(chart);
        Scene scene = new Scene(root, 900, 550);
        stage.setScene(scene);
        stage.show();
    }






