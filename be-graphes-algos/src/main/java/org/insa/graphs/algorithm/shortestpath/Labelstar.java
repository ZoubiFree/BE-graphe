package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Point;

public class Labelstar extends Label {
    public Labelstar(Node s_courant, boolean marque, boolean reached, float cost, Arc parent) {
        super(s_courant,marque,reached,cost,parent);
    }

    public float costkikiwi(Point point_courant,Point point_dest){
        float dist=((point_courant.getLatitude()-point_dest.getLatitude())**2+(point_courant.getLongitude()-point_dest.getLongitude())**2)**(1/2);
        return dist;
    }
}
