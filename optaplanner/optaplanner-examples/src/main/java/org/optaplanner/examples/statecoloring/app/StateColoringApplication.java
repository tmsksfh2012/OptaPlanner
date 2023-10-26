package org.optaplanner.examples.statecoloring.app;

import java.util.List;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.examples.statecoloring.domain.Graph;
import org.optaplanner.examples.statecoloring.domain.Node;
import org.optaplanner.examples.statecoloring.domain.State;
import org.optaplanner.examples.statecoloring.persistence.StateColoringGenerator;

public class StateColoringApplication {
    public static final String SOLVER_CONFIG =
            "org/optaplanner/examples/statecoloring/stateColoringSolverConfig.xml";

    public static final String DATA_DIR_NAME = "statecoloring";

    public static void main(String[] args) {
        // Build the Solver
        SolverFactory<Graph> solverFactory = SolverFactory.createFromXmlResource(SOLVER_CONFIG);
        Solver<Graph> solver = solverFactory.buildSolver();

        // Load a problem
        Graph unsolvedGraph = new StateColoringGenerator().createStates();

        // Solve the problem
        Graph solvedGraph = solver.solve(unsolvedGraph);

        System.out.println("\nSolved Graph:\n" + toDisplayString(solvedGraph));
    }

    // Display the result
    public static String toDisplayString(Graph graph) {
        StringBuilder displayString = new StringBuilder();
        int n = graph.getN();
        List<Node> queenList = graph.getNodeList();
        for (int i = 0; i < n; i++) {
            Node node = queenList.get(i);
            displayString.append(State.values()[(int) node.getId()].name());
            displayString.append(" ");
            displayString.append(node.getColor().getName());
            displayString.append(" ");
        }
        return displayString.toString();
    }
}
