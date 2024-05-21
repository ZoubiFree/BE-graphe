package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Point;
import java.lang.Math;

public class Labelstar extends Label {
    ShortestPathData data;
    public Labelstar(Node s_courant, boolean marque, boolean reached, float cost, Arc parent,ShortestPathData data) {
        super(s_courant,marque,reached,cost,parent);
        this.data=data;
    }

    public float costkikiwi(Point point_courant,Point point_dest){
        double dist2=Math.pow(point_courant.getLatitude()-point_dest.getLatitude(),2)+Math.pow(point_courant.getLongitude()-point_dest.getLongitude(),2);
        double dist=Math.pow(dist2,1/2);
        return (float)dist;
    }

    public float getTotalCost(){
        float res=this.getCost()+costkikiwi(this.get_sommet_courant().getPoint(),this.data.getDestination().getPoint());
        return res;
    }
}

