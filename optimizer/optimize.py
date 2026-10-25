import gurobipy as gp
from gurobipy import GRB

states = {"WI": (10, 1.0), "MI": (15, 1.5), "PA": (19, 2.0), "GA": (16, 2.5)}  # (EVs, deficit)
budget = 20_000_000

m = gp.Model("battleground")
win = m.addVars(states, vtype=GRB.BINARY)   # 1 if we win the state
spend = m.addVars(states, lb=0)             # dollars spent in the state

for s, (ev, deficit) in states.items():
    min_spend = (deficit / 0.001) ** 2      # from 0.001*sqrt(spend) >= deficit
    m.addConstr(spend[s] >= min_spend * win[s])

m.addConstr(spend.sum() <= budget)

# maximize EVs; the tiny penalty on spend breaks ties toward the cheaper plan
m.setObjective(gp.quicksum(states[s][0] * win[s] for s in states) - 1e-9 * spend.sum(), GRB.MAXIMIZE)
m.optimize()

for s in states:
    print(s, "WON" if win[s].X > 0.5 else "LOST", round(spend[s].X))
print("EVs:", sum(states[s][0] for s in states if win[s].X > 0.5))
