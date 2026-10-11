/*
 * Author: Hugo Chavez
 * Date: October 10, 2026
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
}
