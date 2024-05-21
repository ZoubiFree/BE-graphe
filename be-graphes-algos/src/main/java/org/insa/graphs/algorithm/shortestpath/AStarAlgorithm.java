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

import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.algorithm.shortestpath.Labelstar;

public class AStarAlgorithm extends DijkstraAlgorithm {
    ShortestPathData data;
    Labelstar nodeLabels[];
    /*Labelstar goingBack;
    Labelstar successorLabel;
    Labelstar sommet_min;*/

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
        this.data=data;
    }
    public void initnalisnaton(){
        this.nodeLabels=new Labelstar[this.NbNodes];
        for (Node node : graph.getNodes()) {
            nodeLabels[node.getId()] = new Labelstar(node,data); // Crée un label pour chaque nœud
        }
        heap.insert(nodeLabels[this.originID]); // Ajoute le label de l'origine au tas
        nodeLabels[this.originID].setCost(0); // Initialise le coût de l'origine à zéro
    }
    }
