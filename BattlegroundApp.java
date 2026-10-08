/*
 * Author: Hugo Chavez
 * Date: October 7, 2026
 * Course: CIS171
 * Operating System: Mac
 * IDE: IntelliJ
 * Description: Console user interface for the Battleground Budget Strategist. Gets the campaign budget from the user and validates it.
 * I have not used unauthorized source code, either modified or unmodified
 * Documentation of Resources Used:
 */

import java.util.Scanner;

/*
 * Incremental design: I built this in three small steps and tested each before moving on.
 * Step 1 was the state data and input cleanup, Step 2 added the rules and math, Step 3 added the budget input with validation and the JUnit tests for the core methods.
 * Next I will add the loop over the four states, then efficiency and rating, then the report and the run-again loop. printReport is a stub until the rest of the data exists.
 * Question: should the loop that reads each state code and spend stay in main with its own validation loops inside it, or should it go into a separate method?
 * Struggle: I am not sure yet how to line up the report columns with printf when the dollar amounts have commas and different lengths.
 */
public class BattlegroundApp {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("========== BATTLEGROUND BUDGET STRATEGIST ==========");
        double budget = getBudget(input);
        System.out.printf("Total Budget: $%,.2f%n", budget);

        input.close();
    }

    // getBudget will prompt until the user enters a number greater than 0
    // Scanner -> double
    public static double getBudget(Scanner input) {
        double budget = 0;

        while (budget <= 0) {
            System.out.print("Enter your total campaign budget in dollars: $");
            if (input.hasNextDouble()) {
                budget = input.nextDouble();
                if (budget <= 0) {
                    System.out.println("Budget must be greater than 0.");
                }
            } else {
                System.out.println("Please enter a number.");
                input.next();
            }
        }
        input.nextLine(); // clear the leftover newline so later nextLine() calls work

        return budget;
    }

    // printReport will display the state results, total electoral votes, efficiency, and rating
    // String[], double[], int, double, double -> void
    // STUB: not implemented yet; it will print the formatted table from my proposal using printf
    public static void printReport(String[] codes, double[] spends, int totalVotes, double remaining, double efficiency) {
    }
}
