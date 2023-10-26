package org.optaplanner.examples.statecoloring.domain;

import java.util.List;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.simple.SimpleScore;
import org.optaplanner.examples.common.domain.AbstractPersistable;

@PlanningSolution
public class Graph extends AbstractPersistable {
    private int n;
    private List<Node> nodeList;
    private List<Color> colorList;

    @PlanningEntityCollectionProperty
    public List<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<Node> nodeList) {
        this.nodeList = nodeList;
    }

    @ValueRangeProvider(id = "colorRange")
    @ProblemFactCollectionProperty
    public List<Color> getColorList() {
        return colorList;
    }

    public void setColorList(List<Color> colorList) {
        this.colorList = colorList;
    }

    @PlanningScore
    private SimpleScore score;

    public SimpleScore getScore() {
        return score;
    }

    public void setScore(SimpleScore score) {
        this.score = score;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public Graph() {
    }

    public Graph(Long id) {
        super(id);
    }
}
