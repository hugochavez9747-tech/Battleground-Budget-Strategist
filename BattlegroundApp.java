/*
 * Author: Hugo Chavez
 * Date: October 12, 2026
 * Course: CIS171
 * Operating System: Mac
 * IDE: IntelliJ
 * Description: Console user interface for the Battleground Budget Strategist. Gets the campaign budget from the user and validates it.
 * I have not used unauthorized source code, either modified or unmodified
 * Documentation of Resources Used:
 */

import java.util.Scanner;

public class BattlegroundApp {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("========== BATTLEGROUND BUDGET STRATEGIST ==========");
        instructions();

        boolean runAgain = true;
        while (runAgain) {
            System.out.println();
            runStrategy(input);
            runAgain = askRunAgain(input);
        }

        System.out.println("Thanks for using the Battleground Budget Strategist.");

        input.close();
    }

    // instructions will explain how to use the program
    // none -> void
    public static void instructions() {
        System.out.println("Split your campaign budget across four battleground states.");
        System.out.println("Each state can be funded once, and you can enter 0 to skip one.");
        System.out.println("Extra spending in a state helps less and less, so spread it wisely.");
        System.out.println("A state is won when its swing is at least its deficit.");
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

    // getStateCode will prompt until the user enters a valid state that has not been funded yet
    // Scanner, boolean[] -> String
    public static String getStateCode(Scanner input, boolean[] funded) {
        String code = "";
        boolean valid = false;

        while (!valid) {
            System.out.print("Enter a state to fund (WI, MI, PA, GA): ");
            code = BattlegroundLogic.standardizeStateCode(input.nextLine());
            if (!BattlegroundLogic.isValidState(code)) {
                System.out.println("Invalid state. Choose WI, MI, PA, or GA.");
            } else if (funded[BattlegroundLogic.getStateIndex(code)]) {
                System.out.println(code + " already has a spend entered.");
            } else {
                valid = true;
            }
        }

        return code;
    }

    // getSpend will prompt until the user enters a spend that fits the remaining budget
    // Scanner, String, double -> double
    public static double getSpend(Scanner input, String code, double remaining) {
        double spend = -1;

        while (!BattlegroundLogic.isValidSpend(spend, remaining)) {
            System.out.printf("Enter spend for %s (0 to skip): $", code);
            if (input.hasNextDouble()) {
                spend = input.nextDouble();
                if (!BattlegroundLogic.isValidSpend(spend, remaining)) {
                    System.out.printf("Spend must be between $0 and $%,.2f.%n", remaining);
                }
            } else {
                System.out.println("Please enter a number.");
                input.next();
            }
        }
        input.nextLine(); // clear the leftover newline so later nextLine() calls work

        return spend;
    }

    // getSpends will collect one spend for each state and return them in state data order
    // Scanner, double -> double[]
    public static double[] getSpends(Scanner input, double budget) {
        double[] spends = new double[BattlegroundLogic.STATE_CODES.length];
        boolean[] funded = new boolean[spends.length];
        double remaining = budget;

        for (int entry = 1; entry <= spends.length; entry++) {
            System.out.printf("%nEntry %d of %d | Remaining budget: $%,.2f%n", entry, spends.length, remaining);
            String code = getStateCode(input, funded);
            int stateIndex = BattlegroundLogic.getStateIndex(code);
            spends[stateIndex] = getSpend(input, code, remaining);
            funded[stateIndex] = true;
            remaining -= spends[stateIndex];
        }

        return spends;
    }

    // runStrategy will get one set of spending from the user and show the full report
    // Scanner -> void
    public static void runStrategy(Scanner input) {
        double budget = getBudget(input);
        double[] spends = getSpends(input, budget);

        int totalVotes = BattlegroundLogic.calculateTotalVotes(spends);
        double totalSpent = BattlegroundLogic.calculateTotalSpent(spends);
        double efficiency = BattlegroundLogic.calculateEfficiency(totalVotes, totalSpent);
        String rating = BattlegroundLogic.getRating(efficiency);

        printReport(budget, spends, totalVotes, budget - totalSpent, efficiency, rating);
    }

    // printReport will display the state-by-state results and campaign totals
    // double, double[], int, double, double, String -> void
    public static void printReport(double budget, double[] spends, int totalVotes,
            double remaining, double efficiency, String rating) {
        System.out.printf("%n%-8s%-18s%-12s%-12s%-8s%-5s%n", "State", "Spend", "Swing", "Deficit", "Result", "EVs");

        for (int stateIndex = 0; stateIndex < spends.length; stateIndex++) {
            double swing = BattlegroundLogic.calculateSwing(spends[stateIndex]);
            int votes = BattlegroundLogic.calculateStateVotes(spends[stateIndex], stateIndex);
            String result = "LOST";
            if (votes > 0) {
                result = "WON";
            }

            System.out.printf("%-8s%-18s%-12s%-12s%-8s%-5d%n",
                    BattlegroundLogic.STATE_CODES[stateIndex],
                    String.format("$%,.2f", spends[stateIndex]),
                    String.format("%.2f pts", swing),
                    String.format("%.2f pts", BattlegroundLogic.DEFICITS[stateIndex]),
                    result,
                    votes);
        }

        System.out.printf("%n%-24s$%,.2f%n", "Total Budget:", budget);
        System.out.printf("%-24s%d%n", "Electoral Votes Won:", totalVotes);
        System.out.printf("%-24s$%,.2f%n", "Budget Remaining:", remaining);
        System.out.printf("%-24s%.2f EVs per $1M%n", "Efficiency:", efficiency);
        System.out.printf("%-24s%s%n", "Rating:", rating);
    }

    // askRunAgain will prompt until the user answers yes or no
    // Scanner -> boolean
    public static boolean askRunAgain(Scanner input) {
        String answer = "";

        while (!answer.equals("Y") && !answer.equals("N")) {
            System.out.print("\nRun another strategy? (Y/N): ");
            String reply = input.nextLine().trim().toUpperCase();
            answer = "";
            if (!reply.isEmpty()) {
                answer = reply.substring(0, 1);
            }
            if (!answer.equals("Y") && !answer.equals("N")) {
                System.out.println("Please enter Y or N.");
            }
        }

        return answer.equals("Y");
    }
}
