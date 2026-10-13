/*
 * Author: Hugo Chavez
 * Date: October 12, 2026
 * Course: CIS171
 * Operating System: Mac
 * IDE: IntelliJ
 * Description: JUnit tests for the BattlegroundLogic methods: calculateSwing, standardizeStateCode, isValidState, and isValidSpend.
 * I have not used unauthorized source code, either modified or unmodified
 * Documentation of Resources Used:
 */

public class BattlegroundLogic {

    public static final String[] STATE_CODES = {"WI", "MI", "PA", "GA"};
    public static final int[] ELECTORAL_VOTES = {10, 15, 19, 16};
    public static final double[] DEFICITS = {1.0, 1.5, 2.0, 2.5};
    public static final double MAX_SWING = 4.0;
    public static final double SWING_FACTOR = 0.001;
    public static final double DOLLARS_PER_MILLION = 1000000.0;
    public static final double EXCELLENT_EFFICIENCY = 2.5;
    public static final double SOLID_EFFICIENCY = 1.5;

    // pipe-delimited so indexOf cannot match across two neighboring codes (e.g. "IM")
    private static final String VALID_CODES = "|WI|MI|PA|GA|";

    // standardizeStateCode will trim and uppercase a state code, keeping only its first two characters
    // String -> String
    public static String standardizeStateCode(String rawCode) {
        String cleaned = rawCode.trim().toUpperCase();
        if (cleaned.length() > 2) {
            cleaned = cleaned.substring(0, 2);
        }
        return cleaned;
    }

    // isValidState will check whether a code is one of the four battleground states
    // String -> boolean
    public static boolean isValidState(String code) {
        return code.length() == 2 && VALID_CODES.indexOf("|" + code + "|") >= 0;
    }

    // getStateIndex will return the position of a valid state code in the state data arrays
    // String -> int
    public static int getStateIndex(String code) {
        return VALID_CODES.indexOf("|" + code + "|") / 3; // each entry in VALID_CODES takes 3 characters
    }

    // isValidSpend will check that spend is not negative and does not exceed the remaining budget
    // double, double -> boolean
    public static boolean isValidSpend(double spend, double remainingBudget) {
        return spend >= 0 && spend <= remainingBudget;
    }

    // calculateSwing will model diminishing returns on spending, capped at MAX_SWING
    // double -> double
    public static double calculateSwing(double spend) {
        if (spend <= 0) {
            return 0.0; // avoids Math.sqrt of a negative number
        }
        return Math.min(MAX_SWING, SWING_FACTOR * Math.sqrt(spend));
    }

    // isStateWon will decide whether the swing overcomes the state's baseline deficit
    // double, double -> boolean
    public static boolean isStateWon(double swing, double deficit) {
        return swing >= deficit;
    }

    // calculateStateVotes will return a state's electoral votes if the spend wins it, otherwise 0
    // double, int -> int
    public static int calculateStateVotes(double spend, int stateIndex) {
        double swing = calculateSwing(spend);
        if (isStateWon(swing, DEFICITS[stateIndex])) {
            return ELECTORAL_VOTES[stateIndex];
        }
        return 0;
    }

    // calculateTotalVotes will add up the electoral votes won across all of the states
    // double[] -> int
    public static int calculateTotalVotes(double[] spends) {
        int totalVotes = 0;
        for (int stateIndex = 0; stateIndex < spends.length; stateIndex++) {
            totalVotes += calculateStateVotes(spends[stateIndex], stateIndex);
        }
        return totalVotes;
    }

    // calculateTotalSpent will add up the spending across all of the states
    // double[] -> double
    public static double calculateTotalSpent(double[] spends) {
        double totalSpent = 0;
        for (double spend : spends) {
            totalSpent += spend;
        }
        return totalSpent;
    }

    // calculateEfficiency will compute electoral votes won per $1 million spent
    // int, double -> double
    public static double calculateEfficiency(int electoralVotes, double amountSpent) {
        if (amountSpent <= 0) {
            return 0.0; // nothing spent, so avoid dividing by zero
        }
        return electoralVotes / (amountSpent / DOLLARS_PER_MILLION);
    }

    // getRating will convert an efficiency value into a rating word
    // double -> String
    public static String getRating(double efficiency) {
        if (efficiency >= EXCELLENT_EFFICIENCY) {
            return "Excellent";
        } else if (efficiency >= SOLID_EFFICIENCY) {
            return "Solid";
        } else {
            return "Needs work";
        }
    }
}
