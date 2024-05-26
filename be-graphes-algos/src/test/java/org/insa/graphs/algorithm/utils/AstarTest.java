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
public class AstarTest {

    private ShortestPathData inputData;
    private ShortestPathAlgorithm algorithm;
    private ShortestPathSolution solution;

    public static Graph read(String mapName) throws IOException {//fonction qui nous permet d'avoir le graph
        GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

        return reader.read();
    }

    public AstarTest(ShortestPathData inputData) {
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
        this.algorithm = new AStarAlgorithm(this.inputData);//Avec Astar
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
    public void PareilqueBellmanford(){
        Assume.assumeTrue(this.inputData.getGraph().getNodes().size() <= 10000);
        BellmanFordAlgorithm AlgoB = new BellmanFordAlgorithm(this.inputData);
        ShortestPathSolution SolutionB =AlgoB.run();

        assertSame(SolutionB.getStatus(), this.solution.getStatus());//verifie que l'etat de la solution est pareil

        if (!this.solution.isFeasible()) return;

        assertEquals(SolutionB.getPath().getLength(), this.solution.getPath().getLength(), 0.01);//verifie meme longeure
        assertSame(SolutionB.isFeasible(), this.solution.isFeasible());//Les deux doivent avoir la meme valeur (si l'un est bon et pas l'autre alors marche pas)

        if (!this.solution.isFeasible()) {
            return;
        }

        Path chemin = this.solution.getPath();//Astar
        Path CheminB = SolutionB.getPath();//Belmanford

        assertSame(chemin.getArcs().size(), CheminB.getArcs().size());//verifie que les deux taille sont bien les memes

        for (int i = 0; i < chemin.getArcs().size(); i++) {
            assertSame(CheminB.getArcs().get(i).getDestination(), chemin.getArcs().get(i).getDestination());//verifie point par point que les deux chemin sont identiques
        }
    }


}
