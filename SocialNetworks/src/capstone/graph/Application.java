package capstone.graph;

import java.util.List;
import java.util.Map;

public class Application {
	
	public static void main(String args[]) throws Exception{
		UndirectedWeightedGraph<Integer> g = new UndirectedWeightedGraph<Integer>();

		GraphLoader.loadCapGraph(g);

		System.out.println("add edge between 0 and 1 = " + g.getAdjacencyList().addEdge(0, 1));

		System.out.println("remove edge between 0 and 400 = " + g.getAdjacencyList().removeEdge(0, 400));
		System.out.println("add edge between 0 and 400 = " + g.getAdjacencyList().addEdge(0, 400));
		System.out.println("remove edge between 0 and 400 = " + g.getAdjacencyList().removeEdge(0, 400));
		
		Map<Integer, Integer> backTrackMap = g.shortestPathDijkstra(0);
		
		List<Integer> path = g.buildPath(backTrackMap, 1, 348);
		
		System.out.println("path from 0 to 99 ===== " + path);

	}

}
