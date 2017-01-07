package capstone.graph;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Application {
	
	public static void main(String args[]) throws Exception{
		UndirectedWeightedGraph<Integer> g = new UndirectedWeightedGraph<Integer>();

		GraphLoader.loadCapGraph(g);

//		System.out.println("add edge between 0 and 1 = " + g.getAdjacencyList().addEdge(0, 1));
//
//		System.out.println("remove edge between 0 and 400 = " + g.getAdjacencyList().removeEdge(0, 400));
//		System.out.println("add edge between 0 and 400 = " + g.getAdjacencyList().addEdge(0, 400));
//		System.out.println("remove edge between 0 and 400 = " + g.getAdjacencyList().removeEdge(0, 400));
//		
//		Map<Integer, Integer> backTrackMap = g.shortestPathDijkstra(0);
//		
//		List<Integer> path = g.buildPath(backTrackMap, 0, 348);
//		
//		System.out.println("path from 0 to 348 ===== " + path);
//		
//		path = g.buildPath(backTrackMap, 0, 0);
//		
//		System.out.println("path from 0 to 0 ===== " + path);
//		
//		path = g.buildPath(backTrackMap, 0, 348);
//		
//		g.printEdgeBetweenness();
//		
//		g.calculateBetweenness(path);
//
//		g.printEdgeBetweenness();
//		
//		g.resetBetweenness();
//		
//		List<List<Integer>> superPath = g.buildPath(backTrackMap, 0);
//		
//		g.calculateAllBetweenness(superPath);
//		
//		g.printEdgeBetweenness();
//		
//		g.resetBetweenness();
//		
//		g.printEdgeBetweenness();
//		
//		g.algorithm();
		
		//g.printEdgeBetweenness();
		
		//edge to remove: 1684 <-> 107 with betweenness 2642418
		
//		List<List<Integer>> result = g.getConnectedComponents();
//		
//		System.out.println("number of connected components = " + result.size());
//		
//		Collections.sort(result.get(0));
//		
//		for (Integer node : result.get(0)){
//			System.out.println(node);
//		}
		
		//g.algorithm();

		List<List<Integer>> result;
		
		for (int i = 0; i < 500 * 4; i++){
			g.algorithm();

			result = g.getConnectedComponents();
			
			System.out.println("number of connected components = " + result.size());
			
			for (List<Integer> list : result){
				Collections.sort(list);
				System.out.println("connected component is " + list);
			}
			
			//g.printEdgeToRemove();
			
			g.removeEdgeWithMaxBetweeness();
			
			System.out.println("==========================");
		}
		
	}

}
