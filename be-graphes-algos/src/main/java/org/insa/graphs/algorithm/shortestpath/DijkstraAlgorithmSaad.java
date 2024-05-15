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

    private Map <Node, Label> maplabel;

    public DijkstraAlgorithmSaad(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        //Récupération des données du graphe
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        BinaryHeap<Label> pile = new BinaryHeap<>();

        boolean found = false;

        // Recupération du graphe (à partir des données)
        Graph graph = data.getGraph();
        final int nbNodes = graph.size();

        // Notifie que le noeud d'origine a été traité
        notifyOriginProcessed(data.getOrigin());

        // Initialise le tableau d'arcs prédécesseurs
        Arc[] predecessorArcs = new Arc[nbNodes];

        // Association des noeuds à Label + initialisation label + ajout map
        maplabel= new HashMap<>();
        Label originlabel = new Label(data.getOrigin(), false, 0, null);
        maplabel.put(data.getOrigin(), originlabel);

        //Insertion label dans tas
        pile.insert(maplabel.get(data.getOrigin()));

        while (!found && !pile.isEmpty()){
            //SUpprime label cout faible
            Label currentLabel = pile.deleteMin();
            currentLabel.setmarque(true);
           
            //Utilisation DOUBLE pour avoir une valeur "infinie" (pas dispo avec int)
            double cout_actuel = Double.POSITIVE_INFINITY;
            
            //Parcours de tous les successeurs du neoude actuel
            for(Arc succ : currentLabel.getsommet_courant().getSuccessors()){
                Label prochain = new Label(succ.getDestination(), false, data.getCost(succ) + currentLabel.getCost(), currentLabel.getparent());
                //Verification de si le successeur est la destination (pour mettre à jour le cout)
                if (prochain.getsommet_courant() == data.getDestination()){
                    found = true;
                    cout_actuel = data.getCost(succ);
                }
                else{
                    if (!prochain.ismarked()){
                        pile.insert(maplabel.get(prochain.getsommet_courant()));
                        if (data.getCost(succ)<cout_actuel){
                            cout_actuel = data.getCost(succ);
                        }
                    }
                }
                //MAJ des arcs prédécesseurs
                predecessorArcs[succ.getDestination().getId()] = succ;
                
            }
        }
        //Verifie que la destination n'a pas encore été trouvée
        if (!found){
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        //Destination trouvée
        else{
            notifyDestinationReached(data.getDestination());

            // Crée le chemin à partir du tableau des arcs prédécesseurs..
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = predecessorArcs[data.getDestination().getId()];
            while (arc != null) {
                arcs.add(arc);
                arc = predecessorArcs[arc.getOrigin().getId()];
            }

            // Inverse le chemin...
            Collections.reverse(arcs);

            // SOLUTION OPTIMALE
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        }

        return solution;
    }

}