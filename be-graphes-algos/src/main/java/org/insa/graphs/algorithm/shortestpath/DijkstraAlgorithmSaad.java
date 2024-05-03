package org.insa.graphs.algorithm.shortestpath;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

import java.util.Map;
import java.util.HashMap;

public class DijkstraAlgorithmSaad extends ShortestPathAlgorithm {

    public DijkstraAlgorithmSaad(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        
        BinaryHeap<Label> pile = new BinaryHeap<>();
        boolean found = false;
        Graph graph = data.getGraph();
        final int nbNodes = graph.size();
        notifyOriginProcessed(data.getOrigin());

        Arc[] predecessorArcs = new Arc[nbNodes];

        maplabel= new HashMap<>();
        Label originlabel = new Label(data.getOrigin(), false, 0, null);
        maplabel.put(data.getOrigin(), originlabel);

        return solution;
    }

}
