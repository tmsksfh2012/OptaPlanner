package org.optaplanner.examples.statecoloring.persistence;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.optaplanner.examples.common.app.CommonApp;
import org.optaplanner.examples.common.app.LoggingMain;
import org.optaplanner.persistence.common.api.domain.solution.SolutionFileIO;
import org.optaplanner.examples.statecoloring.app.StateColoringApplication;
import org.optaplanner.examples.statecoloring.domain.*;

public class StateColoringGenerator extends LoggingMain {
//    protected final SolutionFileIO<Graph> solutionFileIO;
//    protected final File outputDir;

//    public static void main(String[] args) {
//        StateColoringGenerator generator = new StateColoringGenerator();
//        generator.generatesStates();
//    }

//    public StateColoringGenerator() {
//        solutionFileIO = new StateColoringSolutionFileIO();
//        outputDir = new File(CommonApp.determineDataDir(StateColoringApplication.DATA_DIR_NAME), "unsolved");
//    }

//    public StateColoringGenerator(boolean withoutDao) {
//        if (!withoutDao) {
//            throw new IllegalArgumentException("The parameter withoutDao (" + withoutDao + ") must be true.");
//        }
//        solutionFileIO = null;
//        outputDir = null;
//    }

    public StateColoringGenerator() {
    }

//    private void generatesStates() {
//        String outputFileName = "Australia stateColoring.json";
//        File outputFile = new File(outputDir, outputFileName);
//        Graph graphs = createStates();
//        solutionFileIO.write(graphs, outputFile);
//        logger.info("Saved: {}", outputFile);
//    }

    public Graph createStates() {
        Graph graph = new Graph(0L);
        int n = 7;
        int colorNum = 3;
        graph.setN(n);
        graph.setColorList(createColorList(colorNum));
        graph.setNodeList(createNodeList(graph));
        createEdgeList(graph.getNodeList());
        return graph;
    }

    public List<Color> createColorList(Integer colorNum) {
        int n = colorNum;
        List<Color> colorList = new ArrayList<>(colorNum);
        for (int i = 0; i < n; i++) {
            colorList.add(new Color(i, ColorType.values()[i].name()));
        }
        return colorList;
    }

    private List<Node> createNodeList(Graph graph) {
        int n = graph.getN();
        List<Node> nodeList = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            Node node = new Node(i);
            nodeList.add(node);
        }
        return nodeList;
    }

    private void createEdgeList(List<Node> nodeList) {
        int edgeListSize = 9;

        // 인접한 노드들을 연결한다.
        // C = {3 != 0, 3 != 1, 3 != 2, 3 != 4, 3 != 5, 0 != 1, 1 != 2, 2 != 4, 4 != 5}
        Node node0 = nodeList.get(0);
        Node node1 = nodeList.get(1);
        Node node2 = nodeList.get(2);
        Node node3 = nodeList.get(3);
        Node node4 = nodeList.get(4);
        Node node5 = nodeList.get(5);

        for(int i = 0; i < edgeListSize; i++) {
            node3.setAdjacentNodeList(List.of(node0, node1, node2, node4, node5));
            node0.setAdjacentNodeList(List.of(node1));
            node1.setAdjacentNodeList(List.of(node2));
            node2.setAdjacentNodeList(List.of(node4));
            node4.setAdjacentNodeList(List.of(node5));
        }
    }
}
