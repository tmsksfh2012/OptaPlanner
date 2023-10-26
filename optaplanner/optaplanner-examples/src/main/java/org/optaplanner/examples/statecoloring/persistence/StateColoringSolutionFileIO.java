package org.optaplanner.examples.statecoloring.persistence;

import org.optaplanner.examples.common.persistence.AbstractJsonSolutionFileIO;
import org.optaplanner.examples.statecoloring.domain.Graph;

public class StateColoringSolutionFileIO extends AbstractJsonSolutionFileIO<Graph> {

    public StateColoringSolutionFileIO() {
        super(Graph.class);
    }
}
