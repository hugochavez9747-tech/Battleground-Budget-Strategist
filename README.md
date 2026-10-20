# Battleground Budget Strategist

A Java console program that puts you in the seat of a campaign manager. You split a fixed budget across four battleground states, and the program models how far each dollar goes, tells you which states you win, and rates how efficiently you spent.

Midterm project for **CIS171** (Java programming) by Hugo Chavez.

## How it works

1. Enter your total campaign budget.
2. Make four spending entries. Each entry is a state code (`WI`, `MI`, `PA`, or `GA`) and a dollar amount. Each state can be funded once, and you can enter `0` to skip a state.
3. The program calculates the swing (in points) each state gains from your spending and compares it to that state's baseline deficit. If the swing is at least the deficit, you win the state and its electoral votes.
4. A report shows the state-by-state results, your total electoral votes, the budget you have left, an efficiency score, and a rating.
5. Choose whether to run another strategy.

### The spending model

Spending has diminishing returns, so doubling a state's budget does not double its swing:

```
swing = min(4.0, 0.001 * sqrt(spend))
```

Efficiency is electoral votes won per $1 million spent. The rating is **Excellent** at 2.5 or higher, **Solid** at 1.5 or higher, and **Needs work** below that.

### State data

The electoral votes are real. The baseline deficits are simplified, fictional values chosen to keep the model easy to test.

| State | Electoral votes | Baseline deficit (points) |
|-------|-----------------|---------------------------|
| WI    | 10              | 1.0                       |
| MI    | 15              | 1.5                       |
| PA    | 19              | 2.0                       |
| GA    | 16              | 2.5                       |

## Example run

```
========== BATTLEGROUND BUDGET STRATEGIST ==========
Split your campaign budget across four battleground states.
Each state can be funded once, and you can enter 0 to skip one.
Extra spending in a state helps less and less, so spread it wisely.
A state is won when its swing is at least its deficit.

Enter your total campaign budget in dollars: $20000000

Entry 1 of 4 | Remaining budget: $20,000,000.00
Enter a state to fund (WI, MI, PA, GA): wi
Enter spend for WI (0 to skip): $5000000

Entry 2 of 4 | Remaining budget: $15,000,000.00
Enter a state to fund (WI, MI, PA, GA): MI
Enter spend for MI (0 to skip): $5000000

Entry 3 of 4 | Remaining budget: $10,000,000.00
Enter a state to fund (WI, MI, PA, GA): pa
Enter spend for PA (0 to skip): $5000000

Entry 4 of 4 | Remaining budget: $5,000,000.00
Enter a state to fund (WI, MI, PA, GA): ga
Enter spend for GA (0 to skip): $5000000

State   Spend             Swing       Deficit     Result  EVs
WI      $5,000,000.00     2.24 pts    1.00 pts    WON     10
MI      $5,000,000.00     2.24 pts    1.50 pts    WON     15
PA      $5,000,000.00     2.24 pts    2.00 pts    WON     19
GA      $5,000,000.00     2.24 pts    2.50 pts    LOST    0

Total Budget:           $20,000,000.00
Electoral Votes Won:    44
Budget Remaining:       $0.00
Efficiency:             2.20 EVs per $1M
Rating:                 Solid

Run another strategy? (Y/N): n
Thanks for using the Battleground Budget Strategist.
```

## Input validation

- The budget must be a number greater than 0.
- State codes are trimmed and uppercased, so ` wi ` works. Unknown states and states that already have a spend are rejected.
- A spend must be a number that is not negative and does not exceed the remaining budget.
- The run-again question accepts anything starting with `Y` or `N`.

Bad entries show a message and ask again instead of crashing.

## Project structure

| File | Purpose |
|------|---------|
| `BattlegroundApp.java` | The user interface: Scanner input, input validation loops, the formatted `printf` report, and the run-again loop |
| `BattlegroundLogic.java` | The business logic: state data, state code cleanup and validation, the swing model, vote and spending totals, efficiency, and rating |
| `BattlegroundLogicTest.java` | JUnit 5 tests for the logic methods |

The logic is kept separate from the user interface so it can be tested without any console input or output.

## Running it

You need a JDK (Java 11 or newer).

From the command line:

```
javac BattlegroundLogic.java BattlegroundApp.java
java BattlegroundApp
```

Or open the folder in an IDE such as IntelliJ and run `BattlegroundApp`.

## Running the tests

The tests use JUnit 5 (Jupiter). In IntelliJ, right-click `BattlegroundLogicTest.java` and choose **Run**. If JUnit is not on the classpath, IntelliJ will offer to add it.

The tests cover `calculateSwing` (zero, negative, normal, diminishing returns, and the cap), `standardizeStateCode`, `isValidState`, `isValidSpend`, `isStateWon`, `calculateTotalVotes`, `calculateTotalSpent`, `calculateEfficiency`, and `getRating`.

## Concepts demonstrated

Scanner input and input validation, `if` / `else if` decisions, structured loops (no `while(true)`), methods with parameters and return values, `Math.sqrt` and `Math.min`, String methods (`trim`, `toUpperCase`, `substring`, `indexOf`, `length`), arrays, `printf` column formatting, and JUnit testing.
