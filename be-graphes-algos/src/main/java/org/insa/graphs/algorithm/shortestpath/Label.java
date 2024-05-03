package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.Arc;

public class Label {
    private int sommet_courant;
    private boolean marque;
    private int cout_realise;
    private Arc arc_pere;


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
