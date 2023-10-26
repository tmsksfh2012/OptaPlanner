package org.optaplanner.statecoloring;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class Edge {
    public int getVertexA() {
        return vertexA;
    }

    public void setVertexA(int vertexA) {
        this.vertexA = vertexA;
    }

    public int getVertexB() {
        return vertexB;
    }

    public void setVertexB(int vertexB) {
        this.vertexB = vertexB;
    }

    private int vertexA;
    private int vertexB;

}
