package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label implements Comparable<Label> {

    // Attributs de la classe Label
    private Node sommet_courant; // Noeud associé à ce label
    private boolean marque; // Indique si le noeud associé a été marqué lors de l'algorithme
    private boolean reached; // Indique si le noeud associé a été atteint (utile pour Dijkstra)
    public float cout_realise; // Coût actuel pour atteindre le noeud associé
    private Arc parent; // Arc précédent permettant d'atteindre le noeud associé
    private int ID; // Identifiant du noeud associé

    // Constructeur avec paramètres
    public Label(Node s_courant, boolean m, boolean r, float cost, Arc par) {
        this.sommet_courant = s_courant;
        this.marque = m;
        this.cout_realise = cost;
        this.parent = par;
        this.ID = s_courant.getId();
        this.reached = r;
    }

    // Constructeur par défaut
    public Label(Node Sommet_defaut) {
        this.sommet_courant = Sommet_defaut;
        this.marque = false;
        this.cout_realise = Float.MAX_VALUE;
        this.parent = null;
        this.ID = Sommet_defaut.getId();
        this.reached = false;
    }

    public int getID() {
        return this.ID;
    }

    public float getCost() {
        return this.cout_realise;
    }

    public Arc getParent() {
        return this.parent;
    }

    // Getter pour obtenir le noeud associé au label
    public Node get_sommet_courant() {
        return this.sommet_courant;
    }


    public boolean hasBeenReached() {
        return this.reached;
    }
    /**
     * 
     * @param newCost
     * @param parent
     * @return returns 0 if updated, 1 if cost not updated (new is >), 2 if already marked
     */
    // Méthode pour mettre à jour le coût et le parent du label
    public int updateCostAndParent(float newCost, Arc newParent) {
        this.reached = true;
        if (marque)
            return 2; // Le noeud est déjà marqué
        if (newCost > this.cout_realise)
            return 1; // Le nouveau coût est supérieur à l'ancien
        this.cout_realise = newCost;
        this.parent = newParent;
        return 0; // Mise à jour réussie
    }

    // Setter pour définir le coût
    public void setCost(float newCost) {
        this.reached = true;
        this.cout_realise = newCost;
    }

    // Méthodes pour marquer et démarquer le label
    public boolean is_marked() {
        return this.marque;
    }

    public void mark() {
        this.marque = true;
    }

    public void unmark() {
        this.marque = false;
    }

    //fonction de total cost (ici elle ne contient que le cout depuis origine)
    public float getTotalCost(){
        float res=this.cout_realise;
        return res;
    }


    // Méthode de comparaison pour la priorité dans une file de priorité
    @Override
    public int compareTo(Label o) {
        //return Float.compare(this.cout_realise, o.cout_realise);
        return Float.compare(this.getTotalCost(),o.getTotalCost());
    }
}