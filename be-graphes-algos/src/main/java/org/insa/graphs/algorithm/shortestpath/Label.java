package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;
public class Label {
    private int sommet_courant;
    private boolean marque;
    private int cout_realise;
    private Arc arc_pere;

    public Label(int sommet_courant, boolean marque, int cout_realise, Arc arc_pere){
        this.sommet_courant = sommet_courant;
        this.marque = marque;
        this.cout_realise = cout_realise;
        this.arc_pere = arc_pere;
    }
    public int getSommet_courant(){
        return sommet_courant;
    }
    
    public boolean ismarked(){
        return marque;
    }

    public Arc getparent(){
        return arc_pere;
    }

    public int getCost(){
        return cout_realise;
    }
}