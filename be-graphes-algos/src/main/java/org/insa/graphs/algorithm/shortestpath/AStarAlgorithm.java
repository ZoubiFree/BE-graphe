package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.Node;
import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.GraphStatistics;

public class AStarAlgorithm extends DijkstraAlgorithm {

    /*Labelstar goingBack;
    Labelstar successorLabel;
    Labelstar sommet_min;*/

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
        //this.data=data;
        //this.nodeLabels=new Labelstar[data.getGraph().size()];
    }

    @Override
    public Label[] init(ShortestPathData data){
        Node origine = data.getOrigin();
        Node s_dest = data.getDestination();
        int speed = graph.getGraphInformation().getMaximumSpeed();
        int taille =graph.getNodes().size();
        Graph graph = data.getGraph();;
        Mode mode = data.getMode();;
    
        Label[] nodeLabels = new Label[taille];
        nodeLabels[origine.getId()] = new Labelstar(origine, s_dest,mode,speed);
        nodeLabels[origine.getId()].setCost(0);

        for (Node node : graph.getNodes()) {
            if (!node.equals(origine)){

            nodeLabels[node.getId()] = new Labelstar(node,data.getDestination(), mode, speed); 
            nodeLabels[node.getId()].setCost(Float.MAX_VALUE); 
        }
        }

    return nodeLabels;
    }
    }