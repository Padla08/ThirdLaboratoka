import java.util.*;
import java.lang.Math;

public class Laba3 {
    // Function to calculate the value of y1
    public static double y1(double x) {
        return -x - 2;
    }

    // Function to calculate the value of y2
    public static double y2(double x) {
        return Math.pow(x + 1, 2) + Math.pow(y1(x), 2) - 1;
    }

    // Function to calculate the value of y3
    public static double y3(double x) {
        return 1;
    }

    // Function to calculate the value of y4
    public static double y4(double x) {
        return -2 * x + 3;
    }

    // Function to calculate the value of y5
    public static double y5(double x) {
        return -1;
    }

    public static void main(String[] args) {
        // Header of the table
        System.out.printf("%11s %10s %10s %10s %10s %10s %10s\n",
                "Values of X", "y=-x-2", "y=(x+1)^2+y^2=1", "y=1", "y=-2x+3", "y=-1");

        // Separator of the table
        System.out.printf("%11s %10s %10s %10s %10s %10s %10s\n",
                "---------", "---------", "---------", "---------", "---------", "---------");

        // Starting and ending values of x
        double xStart = -3;
        double xEnd = 5;

        // Step to change x
        double xStep = 0.1;

        // Calculation and output of values of functions for each x in the given range
        for (double x = xStart; x <= xEnd; x += xStep) {
            System.out.printf("%10.2f %10.2f %10.2f %10.2f %10.2f %10.2f\n",
                    x, y1(x), y2(x), y3(x), y4(x), y5(x));
        }
    }
}