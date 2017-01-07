package capstone.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class UndirectedWeightedGraph<V> implements Graph<V> {
	
	private UndirectedWeightedAdjacencyList<V> adjacencyList;
	
	private V maxVertexA = null;
	private V maxVertexB = null;
	private Integer maxBetweenness = 0;
	
	private V currentStartingNode;
	
	public UndirectedWeightedGraph(){
		adjacencyList = new UndirectedWeightedAdjacencyList<V>();
	}

	@Override
	public Boolean addVertex(V vertex) {
		return adjacencyList.addVertex(vertex);
	}

	@Override
	public Boolean addEdge(V vertexA, V vertexB) {
		return adjacencyList.addEdge(vertexA, vertexB);
	}
	
	
	public void printEdgeToRemove(){
		System.out.println("edge to remove: " + maxVertexA + " <-> " + maxVertexB + " with betweenness " + maxBetweenness);
	}
	
	public boolean removeEdgeWithMaxBetweeness(){
		return adjacencyList.removeEdge(maxVertexA, maxVertexB);
	}
	
	public void algorithm(){
		Set<V> vertices = getAllVertices();
		
		adjacencyList.resetBetweenness();
		
		maxVertexA = null;
		maxVertexB = null;
		maxBetweenness = 0;
		
		for (V vertex : vertices){			
			calculateAllBetweenness(buildPath(shortestPathDijkstra(vertex), vertex));			
		}	
		
		printEdgeToRemove();
	}
	
	public Set<V> getAllVertices(){
		return adjacencyList.getAllVertices();
	}
	
	public void resetBetweenness(){
		adjacencyList.resetBetweenness();
	}
	
	public Set<V> getNeighbors(V vertex){
		return adjacencyList.getNeighbors(vertex);
	}
	
	public List<List<V>> getConnectedComponents(){
		
		List<List<V>> result = new ArrayList<List<V>>();
		List<V> singleResult = new ArrayList<V>();
		
		Set<V> visited = new HashSet<V>();
		
		Set<V> allVertices = getAllVertices();
		
		V startingVertex = null;
				
		while(visited.size() != allVertices.size()){
			
			for (V vertex : allVertices){
				if (!visited.contains(vertex)){
					startingVertex = vertex;
					break;
				}
			}			
			singleResult = bfs(startingVertex, visited);
			result.add(singleResult);						
		}		
		return result;
	}
	
	public List<V> bfs(V startingVertex, Set<V> visited){
		
		List<V> resultingVertices = new ArrayList<V>();
		
		Queue<V> queue = new LinkedList<V>();
		queue.add(startingVertex);
		visited.add(startingVertex);
		
		while (!queue.isEmpty()){
			V vertex = queue.remove();
			for (V neighbor : getNeighbors(vertex)){
				if (!visited.contains(neighbor)){
					queue.add(neighbor);
					visited.add(neighbor);
				}
			}
			//visited.add(vertex);
			resultingVertices.add(vertex);
		}		
		return resultingVertices;		
	}

	private Stack<V> graphTraversal(UndirectedWeightedGraph<V> g, Stack<V> verticesStack){

		Set<V> visited = new HashSet<V>();
		Stack<V> finished = new Stack<V>();
		
		while (!verticesStack.isEmpty()){			
			V vertex = verticesStack.pop();			
			if (!visited.contains(vertex))
				graphTraversal(g, vertex, visited ,finished);						
		}
		
		return finished;
	}

//	private List<Graph> graphTraversalSecond(CapGraph g, Stack<Integer> verticesStack){
//
//		Set<Integer> visited = new HashSet<Integer>();
//		Stack<Integer> finished = new Stack<Integer>();
//		
//		List<Graph> res = new ArrayList<Graph>();
//		
//		while (!verticesStack.isEmpty()){			
//			Integer vertex = verticesStack.pop();			
//			if (!visited.contains(vertex)){
//				graphTraversal(g, vertex, visited ,finished);
//				res.add(subGraph(g, finished));
//			}			
//		}
//		
//		return res;
//	}
	
	private void graphTraversal(UndirectedWeightedGraph<V> g, V vertex, Set<V> visited ,Stack<V> finished){
		
		visited.add(vertex);
		
		for (V neighbor : g.getNeighbors(vertex)){
			
			if (!visited.contains(neighbor))
				graphTraversal(g, neighbor, visited ,finished);
		}		
		finished.push(vertex);		
	}
	
	private Stack<V> stackifyVertices(Set<V> vertices){
		Stack<V> res = new Stack<V>();
		for (V i : vertices)
			res.push(i);
		return res;
	}	
	
	public List<List<V>> buildPath(Map<V, V> backTrackMap, V from){
		
		List<List<V>> result = new ArrayList<List<V>>();
		
		for (V vertex : backTrackMap.keySet()){
			result.add(buildPath(backTrackMap, from, vertex));
			//System.out.println("built path from " + from + " to " + vertex);
		}
		
		return result;		
	}
	
	public List<V> buildPath(Map<V, V> backTrackMap, V from, V to) {
		
		if (from != currentStartingNode)
			throw new IllegalArgumentException("path not available, run algorithm on input from node first");
		
		// return empty path if from and to are the same
		// so there's no path from a node to itself
		if (from.equals(to)){
			//System.out.println("from node and to node are the same");
			return new ArrayList<V>();
		}
		
		if (adjacencyList.hasVertex(from) && adjacencyList.hasVertex(to)) {
			V node = to;
			List<V> res = new ArrayList<V>();
			while (node != null && !node.equals(from)) {
				res.add(node);
				node = backTrackMap.get(node);
			}
			// return empty list when they are not connected
			if (node == null)
				return new ArrayList<V>();

			res.add(from);
			return res;

		} else
			throw new IllegalArgumentException("input node is not in graph");
	}
	
	public void calculateAllBetweenness(List<List<V>> pathList){
		for (List<V> path : pathList){
			calculateBetweenness(path);
		}
	}
	
	public void calculateBetweenness(List<V> path){
		if (path == null || path.size() == 0){
			//System.out.println("given path is empty");
			return;
		}
		
		if (path.size() == 1)
			throw new IllegalArgumentException("path needs to have at least two nodes");
		
		int idx1 = 0;
		int idx2 = 1;
		int currentBetweenness;
		
		while (idx2 < path.size()){
			V vertexA = path.get(idx1);
			V vertexB = path.get(idx2);

			adjacencyList.incrementEdgeBetweenness(vertexA, vertexB, 1);
			
			currentBetweenness = adjacencyList.getEdge(vertexA, vertexB).getBetweenness();
			
			// keep track of current max
			if (currentBetweenness > maxBetweenness){
				maxVertexA = vertexA;
				maxVertexB = vertexB;
				maxBetweenness = currentBetweenness;
			}
			
			idx1++;
			idx2++;
		}
		
	}
		
	// get shortest path from one start node to every other node
	public Map<V, V> shortestPathDijkstra(V startNode){	
		// for now, fix weight as 1
		final Integer weight = 1;
		
		if(adjacencyList.hasVertex(startNode)){
			// store previous node for back tracking to obtain shortest path  
			Map<V, V> previousNode = new HashMap<V, V>();			
			Map<V, Integer> distanceMap = new HashMap<V,Integer>();			
			Set<V> visited = new HashSet<V>();			
			PriorityQueue<DistanceNode<V>> toVisit = new PriorityQueue<DistanceNode<V>>();
			
			toVisit.add(new DistanceNode<V>(startNode,0));
			distanceMap.put(startNode,0);
			previousNode.put(startNode, null);
			
			while (!toVisit.isEmpty()){				
				DistanceNode<V> nearestNode = toVisit.poll();
				Integer nearestNodeDist = nearestNode.getDist();
				
				Integer distance;

				for (V neighbor : adjacencyList.getNeighbors(nearestNode.getVal())){
					// skip visited node
					if (visited.contains(neighbor))
						continue;
					
					distance = nearestNodeDist + weight;
					// if present in distanceMap then this node has been
					// calculated before
					if (distanceMap.containsKey(neighbor)) {
						if (distanceMap.get(neighbor) > distance) {
							toVisit.remove(new DistanceNode<V>(neighbor, distanceMap.get(neighbor)));
							toVisit.add(new DistanceNode<V>(neighbor, distance));
							distanceMap.put(neighbor, distance);
							previousNode.put(neighbor, nearestNode.getVal());
						}
					} else {
						distanceMap.put(neighbor, distance);
						toVisit.add(new DistanceNode<V>(neighbor, distance));
						previousNode.put(neighbor, nearestNode.getVal());
					}
				}
				
				visited.add(nearestNode.getVal());
				//System.out.println("visited node " + minNode.getVal());
			}
			
			// make input node as the current processed node for the graph
			currentStartingNode = startNode;
			
			return previousNode;
		}else
			throw new IllegalArgumentException("input node is not in graph");		
	}
	
	public void printEdgeBetweenness(){
		adjacencyList.printBetweenness();
	}

	UndirectedWeightedAdjacencyList<V> getAdjacencyList() {
		return adjacencyList;
	}
	
	
	
}
