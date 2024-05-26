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
import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathAlgorithm;

@RunWith(Parameterized.class)
public class DijkstaTest {

    private ShortestPathData inputData;
    private ShortestPathAlgorithm algorithm;
    private ShortestPathSolution solution;

    public static Graph read(String mapName) throws IOException {//fonction qui nous permet d'avoir le graph
        GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

        return reader.read();
    }

    public DijkstaTest(ShortestPathData inputData) {
        this.inputData = inputData;
    }

@Parameterized.Parameters
public static Collection<Object> data() throws IOException {
    Collection<Object> data = new ArrayList<>();

    final Graph map = read("D:\\Insa\\3 MIC\\BE-Graph\\BE-graphe\\BE-graphe\\Maps\\toulouse.mapgr");

    //trajet normal
    data.add(new ShortestPathData(
            map,
            map.get(7697),
            map.get(16001),
            ArcInspectorFactory.getAllFilters().get(0)
    ));

    //depart=arriver
    data.add(new ShortestPathData(
            map,
            map.get(16001),
            map.get(16001),
            ArcInspectorFactory.getAllFilters().get(0)
    ));

    //innacessible
    data.add(new ShortestPathData(
            map,
            map.get(10388),
            map.get(16001),
            ArcInspectorFactory.getAllFilters().get(0)
    ));

    return data;
}

    @Before
    public void init() {
        this.algorithm = new DijkstraAlgorithm(this.inputData);//Avec Dijkstra
        this.solution = this.algorithm.run();
    }   

    @Test
    public void testOrigin() {
        Assume.assumeTrue(solution.isFeasible());
        assertEquals(inputData.getOrigin(), solution.getInputData().getOrigin());
    }

    @Test
    public void testDestination() {
        Assume.assumeTrue(solution.isFeasible());
        assertEquals(inputData.getDestination(), solution.getInputData().getDestination());
    }

    @Test
    public void solutionEqualsBellmanFord(){
        Assume.assumeTrue(this.inputData.getGraph().getNodes().size() <= 5000);

        BellmanFordAlgorithm bFAlgo = new BellmanFordAlgorithm(this.inputData);
        ShortestPathSolution bFSolution = bFAlgo.run();

        assertSame(bFSolution.getStatus(), this.solution.getStatus());

        if (!this.solution.isFeasible()) return;

        assertEquals(bFSolution.getPath().getLength(), this.solution.getPath().getLength(), 0.01);
        assertSame(bFSolution.isFeasible(), this.solution.isFeasible());

        if (!this.solution.isFeasible()) {
            return;
        }

        Path solutionPath = this.solution.getPath();
        Path bFSolutionPath = bFSolution.getPath();

        assertSame(solutionPath.getArcs().size(), bFSolutionPath.getArcs().size());

        for (int i = 0; i < solutionPath.getArcs().size(); i++) {
            assertSame(solutionPath.getArcs().get(i).getDestination(), bFSolutionPath.getArcs().get(i).getDestination());
        }
    }


}
