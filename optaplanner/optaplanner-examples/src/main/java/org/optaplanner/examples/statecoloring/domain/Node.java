package org.optaplanner.examples.statecoloring.domain;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.examples.common.domain.AbstractPersistable;
import org.optaplanner.examples.common.swingui.components.Labeled;

import java.util.List;

@PlanningEntity
public class Node extends AbstractPersistable implements Labeled {
    private Color color;
    private List<Node> adjacentNodeList;

    @PlanningVariable(valueRangeProviderRefs = {"colorRange"})
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @PlanningEntityCollectionProperty
    public List<Node> getAdjacentNodeList() {
        return adjacentNodeList;
    }

    public void setAdjacentNodeList(List<Node> adjacentNodeList) {
        this.adjacentNodeList = adjacentNodeList;
    }

    public Node() {
    }

    public Node(long id) {
        super(id);
    }

    @Override
    public String getLabel() {
        return color.getName();
    }
}
