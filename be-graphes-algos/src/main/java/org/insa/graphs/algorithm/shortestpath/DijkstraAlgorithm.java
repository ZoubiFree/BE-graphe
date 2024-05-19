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
        final ShortestPathData data = getInputData(); // Récupère les données d'entrée pour l'algorithme
        final int originID = data.getOrigin().getId(); // Identifiant du nœud d'origine
        final int destinationID = data.getDestination().getId(); // Identifiant du nœud de destination
        boolean isDestinationMarked = false; // Drapeau pour vérifier si la destination est atteinte
        ShortestPathSolution solution = null; // Variable pour stocker la solution finale

        // Initialisation du graphe et des structures de données
        Graph graph = data.getGraph(); 
        final int nbNodes = graph.size(); 
        Label nodeLabels[] = new Label[nbNodes]; 
        BinaryHeap<Label> heap = new BinaryHeap<>(); 
        for (Node node : graph.getNodes()) {
            nodeLabels[node.getId()] = new Label(node); // Crée un label pour chaque nœud
        }

        heap.insert(nodeLabels[originID]); // Ajoute le label de l'origine au tas
        nodeLabels[originID].setCost(0); // Initialise le coût de l'origine à zéro

        // Boucle principale de l'algorithme de Dikjstra
        while (!heap.isEmpty() && !isDestinationMarked) {
            Label sommet_min = heap.deleteMin(); // Extrait le nœud avec le coût minimum

            nodeLabels[sommet_min.getID()].mark(); // Marque ce nœud comme visité
            if (sommet_min.getID() == destinationID) {
                isDestinationMarked = true; // Si c'est le nœud de destination, mettre à jour le drapeau
                break;
            }
            Node minNode = sommet_min.get_sommet_courant();
            notifyNodeMarked(minNode); // Notifie que le nœud a été marqué

            // Traitement des successeurs
            for (Arc arc : minNode.getSuccessors()) {
                Node successor = arc.getDestination();
                
                if (!data.isAllowed(arc)) {
                    continue; // Ignore l'arc si non autorisé
                }
                
                Label successorLabel = nodeLabels[successor.getId()];

                if (!successorLabel.is_marked()) {
                    if (successorLabel.getCost() != Float.MAX_VALUE)
                        heap.remove(successorLabel); // Supprime le label du tas s'il est déjà présent
                    else
                        notifyNodeReached(successor); // Notifie que le successeur a été atteint

                    // Met à jour le coût et le parent du successeur
                    successorLabel.updateCostAndParent(sommet_min.getCost() + (float) data.getCost(arc), arc);

                    heap.insert(successorLabel); // Réinsère le label mis à jour dans le tas

                }
            }
        }

        // Si la destination n'a pas été atteinte
        if (!isDestinationMarked) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE); // Solution non réalisable
            return solution;
        }

        // Création du chemin le plus court en remontant les parents des labels
        ArrayList<Arc> shortestArcs = new ArrayList<>();
        Label goingBack = nodeLabels[destinationID];
        while (goingBack.getParent() != null) {
            shortestArcs.add(goingBack.getParent());
            goingBack = nodeLabels[goingBack.getParent().getOrigin().getId()];
        }
        Collections.reverse(shortestArcs); // Inverse la liste pour obtenir le chemin correct
        solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, shortestArcs)); // Solution optimale
        return solution;
    }
}
