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

