package org.optaplanner.examples.statecoloring.domain;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningEntityProperty;
import org.optaplanner.core.api.domain.solution.ProblemFactProperty;
import org.optaplanner.examples.common.domain.AbstractPersistable;

import java.util.List;

@PlanningEntity
public class Edge extends AbstractPersistable {
    private Node node;

    @PlanningEntityCollectionProperty
    private List<Node> nodeList;

    public Edge() {
    }

    public Edge(long id) {super(id);}

    public Edge(Node node, List<Node> nodeList) {
        this(node.getId());
        this.node = node;
        this.nodeList = nodeList;
    }

    @PlanningEntityProperty
    public List<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<Node> nodeList) {
        this.nodeList = nodeList;
    }

    @PlanningEntityProperty
    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
