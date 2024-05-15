package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {

        // Retrieve the graph.
        ShortestPathData data = getInputData();
        Graph graph = data.getGraph();

        final int nbNodes = graph.size();

        // Initialize array of distances.
        double[] distances = new double[nbNodes];
        Arrays.fill(distances, Double.POSITIVE_INFINITY);
        distances[data.getOrigin().getId()] = 0;

        // Initialize array of predecessors.
        Arc[] predecessorArcs = new Arc[nbNodes];

        // Actual Dijkstra's algorithm.
        for (int i = 0; i < nbNodes; ++i) {
            Node node = null;
            double minDistance = Double.POSITIVE_INFINITY;
            // Find the node with the smallest distance.
            for (Node n: graph.getNodes()) {
                if (!Double.isInfinite(distances[n.getId()]) && distances[n.getId()] < minDistance) {
                    minDistance = distances[n.getId()];
                    node = n;
                }
            }
            if (node == null) {
                break; // All remaining nodes are inaccessible.
            }
            // Process current node.
            distances[node.getId()] = Double.POSITIVE_INFINITY; // Mark node as processed.
            for (Arc arc: node.getSuccessors()) {
                // Small test to check allowed roads...
                if (!data.isAllowed(arc)) {
                    continue;
                }
                // Retrieve weight of the arc.
                double w = data.getCost(arc);
                double oldDistance = distances[arc.getDestination().getId()];
                double newDistance = distances[node.getId()] + w;
                // Check if new distances would be better, if so update...
                if (newDistance < oldDistance) {
                    distances[arc.getDestination().getId()] = newDistance;
                    predecessorArcs[arc.getDestination().getId()] = arc;
                }
            }
        }

        ShortestPathSolution solution = null;

        // Destination has no predecessor, the solution is infeasible...
        if (predecessorArcs[data.getDestination().getId()] == null) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {
            // The destination has been found.
            // Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = predecessorArcs[data.getDestination().getId()];
            while (arc != null) {
                arcs.add(arc);
                arc = predecessorArcs[arc.getOrigin().getId()];
            }
            // Reverse the path...
            Collections.reverse(arcs);
            // Create the final solution.
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        }

        return solution;
    }
}