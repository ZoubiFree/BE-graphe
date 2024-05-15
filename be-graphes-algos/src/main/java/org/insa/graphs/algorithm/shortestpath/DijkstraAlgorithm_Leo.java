package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.algorithm.shortestpath.Label;
import java.util.Map;

public class DijkstraAlgorithm_Leo extends ShortestPathAlgorithm {

    public DijkstraAlgorithm_Leo(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        // TODO:
        /*List<Integer> tabval0=new ArrayList<>();
        List<Integer> tabval1=new ArrayList<>();
        boolean tous_marque=false;
        Node origine=data.getOrigin();
        Node nodeact=origine;
        while (!tous_marque){
            tabval0.add(nodeact.getId());
            tabval1=tabval0;
            List<Arc> suivants=nodeact.getSuccessors();
            int pluspetit=0;
            for (int i=0;i<suivants.size();i++){
                if (suivants.get(i).getLength()<suivants.get(pluspetit).getLength()){
                    pluspetit=i;
                }
            }
        */
        Map<Node,Label> labels =new HashMap<>();
        BinaryHeap<Label> tas=new BinaryHeap<>();
        boolean fini=false;
        Node actnode=data.getOrigin();
        while (!tas.isEmpty()){
            List<Arc> suivants=actnode.getSuccessors();
            for (int i=0;i<suivants.size();i++){
                labels.putIfAbsent(suivants.get(i).getDestination(),new Label(suivants.get(i).getDestination(),false,Integer.MAX_VALUE,suivants.get(i)));
                if (!labels.get(suivants.get(i)).ismarked()){
                    tas.insert(labels.get(suivants.get(i)));
                }
            }


        }

            
        }
        return solution;
    }



}
