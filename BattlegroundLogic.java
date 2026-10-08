/*
 * Author: Hugo Chavez
 * Date: October 7, 2026
 * Course: CIS171
 * IDE: VS Code
 * File: BattlegroundLogic.java
 * Description: Business logic for the Battleground Budget Strategist: state data, input
 *              cleanup and validation, and the diminishing-returns swing model.
 */

public class BattlegroundLogic {

    public static final String[] STATE_CODES = {"WI", "MI", "PA", "GA"};
    public static final int[] ELECTORAL_VOTES = {10, 15, 19, 16};
    public static final double[] DEFICITS = {1.0, 1.5, 2.0, 2.5};
    public static final double MAX_SWING = 4.0;
    public static final double SWING_FACTOR = 0.001;

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
}
