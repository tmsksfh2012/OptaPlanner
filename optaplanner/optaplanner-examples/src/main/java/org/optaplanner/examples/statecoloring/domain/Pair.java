package org.optaplanner.examples.statecoloring.domain;

public class Pair<T1, T2> {
    private T1 nodeA;
    private T2 nodeB;

    public Pair(T1 nodeA, T2 nodeB) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
    }

    public T1 getNodeA() {
        return nodeA;
    }

    public void setNodeA(T1 nodeA) {
        this.nodeA = nodeA;
    }

    public T2 getNodeB() {
        return nodeB;
    }

    public void setNodeB(T2 nodeB) {
        this.nodeB = nodeB;
    }
}
