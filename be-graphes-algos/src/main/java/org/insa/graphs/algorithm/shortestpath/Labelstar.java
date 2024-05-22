package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Point;
import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.model.GraphStatistics;

public class Labelstar extends Label {
    private float shortestDistance;
    
    public Labelstar(Node s_courant, boolean marque, boolean reached, float cost, Arc parent, Node s_dest, Mode mode, int maxSpeed)
    {
        super(s_courant, marque, reached, cost, parent);
        shortestDistance = costkikiwi(s_courant, s_dest, maxSpeed, mode);
    }

    public Labelstar(Node s_depart, Node s_dest,Mode mode, int maxSpeed)
    {
        super(s_depart);
        shortestDistance = costkikiwi(s_depart, s_dest, maxSpeed,mode);
    }

    public float costkikiwi(Node start, Node end, int maxSpeed, Mode mode) {
        if (mode == Mode.LENGTH){
            return (float) Point.distance(start.getPoint(), end.getPoint());
        } 
        if (maxSpeed == -1)
            return (float) Point.distance(start.getPoint(), end.getPoint())*3600 / (1000*130);
        return (float) Point.distance(start.getPoint(), end.getPoint())*3600 / (1000*maxSpeed);
    }

    
    @Override
    public float getTotalCost()
    {
        return this.cout_realise + shortestDistance;
    }


}