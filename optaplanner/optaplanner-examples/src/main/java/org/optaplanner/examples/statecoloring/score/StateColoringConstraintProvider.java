package org.optaplanner.examples.statecoloring.score;

import org.optaplanner.core.api.score.buildin.simple.SimpleScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.examples.statecoloring.domain.Node;
import java.util.List;
import java.util.Objects;

public class StateColoringConstraintProvider implements ConstraintProvider {
    @Override
    public Constraint[] defineConstraints(ConstraintFactory factory) {
        return new Constraint[] {
//                nodeIndexConflict(factory),
                nodeColorConflict(factory),
        };
    }

    protected Constraint nodeIndexConflict(ConstraintFactory factory) {
        return factory.forEach(Node.class)
                .filter(node -> {
                    List<Node> adjacentNodeList = node.getAdjacentNodeList();
                    if(Objects.isNull(adjacentNodeList)) return false;
                    return adjacentNodeList.stream()
                            .anyMatch(adjacentNode -> adjacentNode.getId() == node.getId());
                })
                // 두 노드의 index가 같으면 penalty를 부여한다.
                .penalize(SimpleScore.ONE)
                .asConstraint("Node index conflict");
    }

    protected Constraint nodeColorConflict(ConstraintFactory factory) {
        return factory.forEach(Node.class)
                .filter(node -> {
                    List<Node> adjacentNodeList = node.getAdjacentNodeList();
                    if(Objects.isNull(adjacentNodeList)) return false;
                    return adjacentNodeList.stream()
                            .anyMatch(adjacentNode -> adjacentNode.getColor() == node.getColor());
                })
                // 두 노드의 color가 같으면 penalty를 부여한다.
                .penalize(SimpleScore.ONE)
                .asConstraint("Node color conflict");
    }
}
