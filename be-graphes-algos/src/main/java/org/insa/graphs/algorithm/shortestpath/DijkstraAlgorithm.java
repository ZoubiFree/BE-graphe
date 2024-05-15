package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
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
        final ShortestPathData data = getInputData();
        final int originID = data.getOrigin().getId();
        final int destinationID = data.getDestination().getId();
        boolean isDestinationMarked = false;
        ShortestPathSolution solution = null;

        // initialising
        Graph graph = data.getGraph();
        final int nbNodes = graph.size();
        Label nodeLabels[] = new Label[nbNodes];
        BinaryHeap<Label> heap = new BinaryHeap<>();
        for (Node node : graph.getNodes()) {
            nodeLabels[node.getId()] = new Label(node);
        }

        heap.insert(nodeLabels[originID]);
        nodeLabels[originID].setCost(0);

        while (!heap.isEmpty() && !isDestinationMarked) {
            Label sommet_min = heap.deleteMin();

            nodeLabels[sommet_min.getID()].mark();
            if (sommet_min.getID() == destinationID) {
                isDestinationMarked = true;
                break;
            }
            Node minNode = sommet_min.get_sommet_courant();
            notifyNodeMarked(minNode);

            for (Arc arc : minNode.getSuccessors()) {
                Node successor = arc.getDestination();
                
                if (!data.isAllowed(arc)) {
                    continue;
                }
                
                Label successorLabel = nodeLabels[successor.getId()];

                if (!successorLabel.is_marked()) {
                    if (successorLabel.getCost() != Float.MAX_VALUE)
                        heap.remove(successorLabel);
                    else
                        notifyNodeReached(successor);

                    successorLabel.updateCostAndParent(sommet_min.getCost() + (float) data.getCost(arc),arc);

                    heap.insert(successorLabel);

                }
            }
        }

        if (!isDestinationMarked) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);;
            return solution;
        }

        ArrayList<Arc> shortestArcs = new ArrayList<>();
        Label goingBack = nodeLabels[destinationID];
        while (goingBack.getParent() != null) {
            shortestArcs.add(goingBack.getParent());
            goingBack = nodeLabels[goingBack.getParent().getOrigin().getId()];
        }
        Collections.reverse(shortestArcs);
        solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, shortestArcs));
        return solution;
    }

}
