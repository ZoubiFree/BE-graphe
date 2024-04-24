package org.insa.graphs.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * Class representing a path between nodes in a graph.
 * </p>
 * 
 * <p>
 * A path is represented as a list of {@link Arc} with an origin and not a list
 * of {@link Node} due to the multi-graph nature (multiple arcs between two
 * nodes) of the considered graphs.
 * </p>
 *
 */
public class Path {

    /**
     * dijkstra temps
     * 
     * Create a new path that goes through the given list of nodes (in order),
     * choosing the fastest route if multiple are available.
     * 
     * @param graph Graph containing the nodes in the list.
     * @param nodes List of nodes to build the path.
     * 
     * @return A path that goes through the given list of nodes.
     * 
     * @throws IllegalArgumentException If the list of nodes is not valid, i.e. two
     *         consecutive nodes in the list are not connected in the graph.
     * 
     * @deprecated Need to be implemented.
     */
    public static Path createFastestPathFromNodes(Graph graph, List<Node> nodes)
    throws IllegalArgumentException {
        List<Arc> arcs = new ArrayList<Arc>();
        Node act;
        for (int i=0;i<nodes.size();i++){//boucle sur les nodes
            act=nodes.get(i);
            List<Arc> listearete=act.getSuccessors();//liste des arcs successeurs
            int plusrapide=0;
            if (listearete.size()>1){
                double temps0=listearete.get(0).getMinimumTravelTime();//temps de trajet minimum sur le premier arc
                for (int j=0;j<listearete.size()-1;j++){//boucle sur les arcs de la node actuelle
                    double temps1=listearete.get(j).getMinimumTravelTime();//temps de trajet minimum sur le j'eme arc
                    if (temps1<temps0){
                        temps0=temps1;
                        plusrapide=j;//on prend le plus rapide
                    }
                }
                }
            arcs.add(listearete.get(plusrapide));
        }
    return new Path(graph, arcs);
    }

    /**
     * dijkstra distance
     * 
     * Create a new path that goes through the given list of nodes (in order),
     * choosing the shortest route if multiple are available.
     * 
     * @param graph Graph containing the nodes in the list.
     * @param nodes List of nodes to build the path.
     * 
     * @return A path that goes through the given list of nodes.
     * 
     * @throws IllegalArgumentException If the list of nodes is not valid, i.e. two
     *         consecutive nodes in the list are not connected in the graph.
     * 
     * @deprecated Need to be implemented.
     */
    
    public static Path createShortestPathFromNodes(Graph graph, List<Node> nodes)
        throws IllegalArgumentException {
    List<Arc> arcs = new ArrayList<Arc>();
    int n = nodes.size(); // On récupere le nombre de noeuds de la liste


    /*GERER LE CAS D UN SEUL NOEUD DANS LA LISTE */
    if (nodes.size() == 1) {
        return new Path(graph, nodes.get(0));
    }

    for (int i = 0; i < n - 1; i++) { 
        Node noeud_actuel = nodes.get(i); 
        Node noeud_suiv = nodes.get(i + 1);
        
        Arc court_arc = null; //On va stocker le chemin
        double court_arc_longueur = Double.POSITIVE_INFINITY; //On part du principe que la longueur du chemin  est infiniment grande
        for (Arc arc : noeud_actuel.getSuccessors()) { //On va comparer avec tous les successeurs
            if (arc.getDestination() == noeud_suiv && arc.getLength() < court_arc_longueur) { //on verifie que le prochain noeud corresppond au chemin donné et si le chemin est plus court
                court_arc = arc;
                court_arc_longueur = arc.getLength();
            }
        }
        
        if (court_arc == null) {
            throw new IllegalArgumentException("Il n y a pas d'aretes sortant");
        }
        
        arcs.add(court_arc);
    }


    return new Path(graph, arcs);
}

    /**
     * Concatenate the given paths.
     * 
     * @param paths Array of paths to concatenate.
     * 
     * @return Concatenated path.
     * 
     * @throws IllegalArgumentException if the paths cannot be concatenated (IDs of
     *         map do not match, or the end of a path is not the beginning of the
     *         next).
     */
    public static Path concatenate(Path... paths) throws IllegalArgumentException {
        if (paths.length == 0) {
            throw new IllegalArgumentException("Cannot concatenate an empty list of paths.");
        }
        final String mapId = paths[0].getGraph().getMapId();
        for (int i = 1; i < paths.length; ++i) {
            if (!paths[i].getGraph().getMapId().equals(mapId)) {
                throw new IllegalArgumentException(
                        "Cannot concatenate paths from different graphs.");
            }
        }
        ArrayList<Arc> arcs = new ArrayList<>();
        for (Path path: paths) {
            arcs.addAll(path.getArcs());
        }
        Path path = new Path(paths[0].getGraph(), arcs);
        if (!path.isValid()) {
            throw new IllegalArgumentException(
                    "Cannot concatenate paths that do not form a single path.");
        }
        return path;
    }

    // Graph containing this path.
    private final Graph graph;

    // Origin of the path
    private final Node origin;

    // List of arcs in this path.
    private final List<Arc> arcs;

    /**
     * Create an empty path corresponding to the given graph.
     * 
     * @param graph Graph containing the path.
     */
    public Path(Graph graph) {
        this.graph = graph;
        this.origin = null;
        this.arcs = new ArrayList<>();
    }

    /**
     * Create a new path containing a single node.
     * 
     * @param graph Graph containing the path.
     * @param node Single node of the path.
     */
    public Path(Graph graph, Node node) {
        this.graph = graph;
        this.origin = node;
        this.arcs = new ArrayList<>();
    }

    /**
     * Create a new path with the given list of arcs.
     * 
     * @param graph Graph containing the path.
     * @param arcs Arcs to construct the path.
     */
    public Path(Graph graph, List<Arc> arcs) {
        this.graph = graph;
        this.arcs = arcs;
        this.origin = arcs.size() > 0 ? arcs.get(0).getOrigin() : null;
    }

    /**
     * @return Graph containing the path.
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * @return First node of the path.
     */
    public Node getOrigin() {
        return origin;
    }

    /**
     * @return Last node of the path.
     */
    public Node getDestination() {
        return arcs.get(arcs.size() - 1).getDestination();
    }

    /**
     * @return List of arcs in the path.
     */
    public List<Arc> getArcs() {
        return Collections.unmodifiableList(arcs);
    }

    /**
     * Check if this path is empty (it does not contain any node).
     * 
     * @return true if this path is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.origin == null;
    }

    /**
     * Get the number of <b>nodes</b> in this path.
     * 
     * @return Number of nodes in this path.
     */
    public int size() {
        return isEmpty() ? 0 : 1 + this.arcs.size();
    }

    /**
     * 
     * Estvalide
     * 
     * Check if this path is valid.
     * 
     * A path is valid if any of the following is true:
     * <ul>
     * <li>it is empty;</li>
     * <li>it contains a single node (without arcs);</li>
     * <li>the first arc has for origin the origin of the path and, for two
     * consecutive arcs, the destination of the first one is the origin of the
     * second one.</li>
     * </ul>
     * 
     * @return true if the path is valid, false otherwise.
     * 
     * @deprecated Need to be implemented.
     */
    public boolean isValid() {
        boolean result = true; 
        if (isEmpty()) {
            result = true;
        }
        else if (arcs.isEmpty()) {
            result = true;
        }
        else if (arcs.get(0).getOrigin() != origin) {
            result = false;
        }
        else{

        for (int i = 0; i < arcs.size() - 1; i++) {
            Arc arc_actuel = arcs.get(i);
            Arc arc_suiv = arcs.get(i + 1);
            if (arc_actuel.getDestination() != arc_suiv.getOrigin()) { //On verifie que le sommet suivant est bien lié au sommet d'origine
                result = false;
            }
        }
    }
        return result;
    }
    /**
     * Compute the length of this path (in meters).
     * 
     * @return Total length of the path (in meters).
     * 
     * @deprecated Need to be implemented.
     */
    public float getLength() {
        float longueur = 0;
        for (Arc arc : arcs) {
            longueur += arc.getLength();
        }
        return longueur;
    }

    /**
     * Compute the time required to travel this path if moving at the given speed.
     * 
     * @param speed Speed to compute the travel time.
     * 
     * @return Time (in seconds) required to travel this path at the given speed (in
     *         kilometers-per-hour).
     * 
     * @deprecated Need to be implemented.
     */
    public double getTravelTime(double speed) {
        double travel_temps = 0; // Initialise le temps de trajet à 0
        for (Arc arc : arcs) { // Parcourt chaque arc dans le chemin
            double arc_temps = arc.getTravelTime(speed);
            /*double distance = arc.getLength(); // longueur de l'arc                    INUTILE, car la fonction dans Arc fait déjà le travail
            double arc_temps = distance / (speed); // Calcul du temps du trajet*/
            travel_temps = travel_temps + arc_temps; // Ajoute le temps de trajet sur cet arc au temps total de trajet
        }
        return travel_temps;
    }

    /**
     * Compute the time to travel this path if moving at the maximum allowed speed
     * on every arc.
     * 
     * @return Minimum travel time to travel this path (in seconds).
     * 
     * @deprecated Need to be implemented.
     */
    public double getMinimumTravelTime() {
        // TODO:
        return 0;
    }

}

