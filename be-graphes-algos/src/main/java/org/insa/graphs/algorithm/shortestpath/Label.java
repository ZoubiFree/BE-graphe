package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label implements Comparable<Label> {
    private Node sommet_courant;
    private boolean marque;
    private boolean reached;
    private float cout_realise;
    private Arc parent;
    private int ID;

    public Label(Node s_courant, boolean m, boolean r, float cost, Arc par)
    {
        this.sommet_courant = s_courant;
        this.marque = m;
        this.cout_realise = cost;
        this.parent = par;
        this.ID = s_courant.getId();
        this.reached = r;
    }

    public Label(Node Sommet_defaut)
    {
        this.sommet_courant = Sommet_defaut;
        this.marque = false;
        this.cout_realise = Float.MAX_VALUE;
        this.parent = null;
        this.ID = Sommet_defaut.getId();
        this.reached = false;
    }

    public int getID()
    {
        return this.ID;
    }

    public float getCost()
    {
        return this.cout_realise;
    }

    public boolean hasBeenReached()
    {
        return this.reached;
    }

    /**
     * 
     * @param newCost
     * @param parent
     * @return returns 0 if updated, 1 if cost not updated (new is >), 2 if already marque
     */
    public int updateCostAndParent(float newCost, Arc newParent)
    {
        this.reached = true;
        if (marque)
            return 2;
        if (newCost > this.cout_realise)
            return 1;
        this.cout_realise = newCost;
        this.parent = newParent;
        return 0;
    }

    public void setCost(float newCost)
    {
        this.reached = true;
        this.cout_realise = newCost;
    }

    public boolean is_marked()
    {
        return this.marque;
    }

    public void mark()
    {
        this.marque = true;
    }

    public void unmark()
    {
        this.marque = false;
    }

    public Arc getParent()
    {
        return this.parent;
    }

    public Node get_sommet_courant()
    {
        return this.sommet_courant;
    }

    @Override
    public int compareTo(Label o) {
        return Float.compare(this.cout_realise, o.cout_realise);
    }
}

