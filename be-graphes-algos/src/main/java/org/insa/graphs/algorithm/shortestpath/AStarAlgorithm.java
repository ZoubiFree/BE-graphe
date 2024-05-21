package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {
    /*Labelstar goingBack;
    Labelstar successorLabel;
    Labelstar sommet_min;*/

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
        //this.data=data;
        this.nodeLabels=new Labelstar[data.getGraph().size()];
    }
    public void initnalisnaton(){
        for (Node node : graph.getNodes()) {
            nodeLabels[node.getId()] = new Labelstar(node,data.getDestination().getPoint()); // Crée un label pour chaque nœud
        }
        heap.insert(this.nodeLabels[this.originID]); // Ajoute le label de l'origine au tas
        this.nodeLabels[this.originID].setCost(0); // Initialise le coût de l'origine à zéro
    }
    }
