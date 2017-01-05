package capstone.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class UndirectedWeightedGraph<V> implements Graph<V> {
	
	private UndirectedWeightedAdjacencyList<V> adjacencyList;
	
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
	
	public List<V> buildPath(Map<V, V> backTrackMap, V from, V to) {
		
		if (from != currentStartingNode)
			throw new IllegalArgumentException("path not available, run algorithm on input from node first");
		
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

	UndirectedWeightedAdjacencyList<V> getAdjacencyList() {
		return adjacencyList;
	}
	
	
	
}
