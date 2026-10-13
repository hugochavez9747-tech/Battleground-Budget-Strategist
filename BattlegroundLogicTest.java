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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BattlegroundLogicTest {

    private static final double DELTA = 0.01;

    @Test
    public void calculateSwingZeroSpendReturnsZero() {
        assertEquals(0.0, BattlegroundLogic.calculateSwing(0), DELTA);
    }

    @Test
    public void calculateSwingNegativeSpendReturnsZero() {
        assertEquals(0.0, BattlegroundLogic.calculateSwing(-500), DELTA);
    }

    @Test
    public void calculateSwingOneMillionReturnsOne() {
        assertEquals(1.0, BattlegroundLogic.calculateSwing(1000000), DELTA);
    }

    @Test
    public void calculateSwingFiveMillionReturnsDiminishedValue() {
        assertEquals(2.24, BattlegroundLogic.calculateSwing(5000000), DELTA);
    }

    @Test
    public void calculateSwingDoublingSpendLessThanDoublesSwing() {
        double single = BattlegroundLogic.calculateSwing(2000000);
        double doubled = BattlegroundLogic.calculateSwing(4000000);
        assertTrue(doubled < 2 * single);
    }

    @Test
    public void calculateSwingLargeSpendIsCapped() {
        assertEquals(4.0, BattlegroundLogic.calculateSwing(50000000), DELTA);
    }

    @Test
    public void standardizeStateCodeTrimsAndUppercases() {
        assertEquals("WI", BattlegroundLogic.standardizeStateCode(" wi "));
    }

    @Test
    public void standardizeStateCodeUppercasesLowercase() {
        assertEquals("PA", BattlegroundLogic.standardizeStateCode("pa"));
    }

    @Test
    public void standardizeStateCodeHandlesMixedCaseAndLeadingSpaces() {
        assertEquals("MI", BattlegroundLogic.standardizeStateCode("  Mi"));
    }

    @Test
    public void isValidStateAcceptsBattlegroundCodes() {
        assertTrue(BattlegroundLogic.isValidState("GA"));
        assertTrue(BattlegroundLogic.isValidState("MI"));
    }

    @Test
    public void isValidStateRejectsUnknownAndMalformedCodes() {
        assertFalse(BattlegroundLogic.isValidState("TX"));
        assertFalse(BattlegroundLogic.isValidState("IM"));
        assertFalse(BattlegroundLogic.isValidState("W"));
        assertFalse(BattlegroundLogic.isValidState(""));
    }

    @Test
    public void isValidSpendRejectsNegativeAndOverBudget() {
        assertFalse(BattlegroundLogic.isValidSpend(-1, 1000));
        assertFalse(BattlegroundLogic.isValidSpend(1001, 1000));
        assertTrue(BattlegroundLogic.isValidSpend(1000, 1000));
    }

    @Test
    public void isStateWonTrueWhenSwingEqualsDeficit() {
        assertTrue(BattlegroundLogic.isStateWon(1.0, 1.0));
    }

    @Test
    public void isStateWonFalseWhenSwingBelowDeficit() {
        assertFalse(BattlegroundLogic.isStateWon(0.99, 1.0));
    }

    @Test
    public void calculateTotalVotesEvenSplit() {
        double[] spends = {5000000, 5000000, 5000000, 5000000};
        assertEquals(44, BattlegroundLogic.calculateTotalVotes(spends));
    }

    @Test
    public void calculateTotalVotesUnderspend() {
        double[] spends = {2000000, 2000000, 3000000, 3000000};
        assertEquals(10, BattlegroundLogic.calculateTotalVotes(spends));
    }

    @Test
    public void calculateTotalVotesAllInOnState() {
        double[] spends = {0, 0, 30000000, 0};
        assertEquals(19, BattlegroundLogic.calculateTotalVotes(spends));
    }

    @Test
    public void calculateTotalVotesNoSpendWinsNothing() {
        double[] spends = {0, 0, 0, 0};
        assertEquals(0, BattlegroundLogic.calculateTotalVotes(spends));
    }

    @Test
    public void calculateTotalSpentAddsAllStates() {
        double[] spends = {1000000, 2500000, 0, 500000};
        assertEquals(4000000, BattlegroundLogic.calculateTotalSpent(spends), DELTA);
    }

    @Test
    public void calculateEfficiencyReturnsVotesPerMillion() {
        assertEquals(2.2, BattlegroundLogic.calculateEfficiency(44, 20000000), DELTA);
        assertEquals(1.0, BattlegroundLogic.calculateEfficiency(10, 10000000), DELTA);
    }

    @Test
    public void calculateEfficiencyZeroSpendReturnsZero() {
        assertEquals(0.0, BattlegroundLogic.calculateEfficiency(0, 0), DELTA);
    }

    @Test
    public void getRatingMatchesThresholds() {
        assertEquals("Excellent", BattlegroundLogic.getRating(2.5));
        assertEquals("Solid", BattlegroundLogic.getRating(2.2));
        assertEquals("Solid", BattlegroundLogic.getRating(1.5));
        assertEquals("Needs work", BattlegroundLogic.getRating(1.0));
        assertEquals("Needs work", BattlegroundLogic.getRating(0.0));
    }
}
