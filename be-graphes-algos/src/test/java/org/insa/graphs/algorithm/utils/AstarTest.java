package org.insa.graphs.algorithm.utils;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.ShortestPathAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.algorithm.utils.PriorityQueue;
import org.insa.graphs.algorithm.utils.PriorityQueueTest;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathAlgorithm;


@RunWith(Parameterized.class)
public class AstarTest{
    
    private static Graph read(String mapName) throws IOException {
        GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

        return reader.read();
    }

    @Parameterized.Parameters
    public static Collection<Object> data() throws IOException {
        Collection<Object> data = new ArrayList<>();

        final Graph map = read("Maps\\insa.mapfg");

        // Chemin normal à l'INSA (filters[0] = shortest all roads allowed)
        data.add(new ShortestPathData(
                map,
                map.get(479),
                map.get(702),
                ArcInspectorFactory.getAllFilters().get(0)
        ));

        // Chemin normal à l'INSA (filters[0] = shortest all roads allowed)
        data.add(new ShortestPathData(
                map,
                map.get(479),
                map.get(702),
                ArcInspectorFactory.getAllFilters().get(0)
        ));

        // Chemin normal à l'INSA (filters[0] = fastest all roads allowed)
        data.add(new ShortestPathData(
                map,
                map.get(479),
                map.get(702),
                ArcInspectorFactory.getAllFilters().get(2)
        ));

        // Chemin normal à l'INSA (filters[0] = fastest all roads allowed)
        data.add(new ShortestPathData(
                map,
                map.get(479),
                map.get(702),
                ArcInspectorFactory.getAllFilters().get(2)
        ));

        // Trajet de longueur nulle
        data.add(new ShortestPathData(
                map,
                map.get(479),
                map.get(479),
                ArcInspectorFactory.getAllFilters().get(0)
        ));

        // Trajet infaisable
        data.add(new ShortestPathData(
                map,
                map.get(186),
                map.get(864),
                ArcInspectorFactory.getAllFilters().get(0)
        ));
        System.err.println("data "+data);
        return data;
    }

    
    @Before
    public void init() {

        this.algorithm = new BellmanFordAlgorithm(this.inputData);
        this.solution = this.algorithm.run();
    }

    private ShortestPathSolution solution;

    public ShortestPathAlgorithm algorithm;

    @Parameterized.Parameter
    public ShortestPathData inputData;


    @Test
    public void originShouldBeOrigin() {
        //init();
        Assume.assumeTrue(this.solution.isFeasible());
        // vérifier que l'origine est bien l'origine
        assertEquals(this.inputData.getOrigin(), this.solution.getInputData().getOrigin());

    }

}


