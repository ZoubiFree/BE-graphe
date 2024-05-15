package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;
public class Label_ancien implements Comparable<Label> {
    private Node sommet_courant;
    private boolean marque;
    private double cout_realise;
    private Arc arc_pere;

    public Label_ancien(Node sommet_courant, boolean marque, double cout_realise, Arc arc_pere){
        this.sommet_courant = sommet_courant;
        this.marque = marque;
        this.cout_realise = cout_realise;
        this.arc_pere = arc_pere;
    }

    
    public Node getsommet_courant(){
        return sommet_courant;
    }

    public void setmarque(boolean value){
        marque = value;
    }
    
    public boolean ismarked(){
        return marque;
    }

    public Arc getparent(){
        return arc_pere;
    }

    public double getCost(){
        return cout_realise;
    }

    public int compareTo(Label o) {
        if (o == this){
            return 0;
        }
        return 1;
     }
}