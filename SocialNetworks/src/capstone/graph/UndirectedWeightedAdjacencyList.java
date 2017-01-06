package capstone.graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * adjacency list that is used to serve undirected weighted graph
 * @author Shilin
 *
 * @param <V>
 */
public class UndirectedWeightedAdjacencyList<V> implements AdjacencyList<V>{
	
	private Map<V, Map<V, UndirectedWeightedEdge<V>>> adjacencyList = new HashMap<V, Map<V, UndirectedWeightedEdge<V>>>();
	
	/**
	 * add a vertex 
	 * @param vertex vertex needs to be added
	 * @return True if the vertex is added successfully, false if this vertex already existed
	 */
	@Override
	public Boolean addVertex(V vertex){
		
		if (hasVertex(vertex))
			return false;
		
		adjacencyList.put(vertex, new HashMap<V, UndirectedWeightedEdge<V>>());
		return true;
	}
	
	/**
	 * add undirected edge to the graph, it doesn't matter in which order the vertices are given as input
	 * @param vertexA vertex at one end
	 * @param vertexB vertex at one end
	 * @return True if edge is added, False if an edge has already been established in which case this edge needs to be removed before being added
	 */
	@Override
	public Boolean addEdge(V vertexA, V vertexB){
		// if an edge is already there return false
		// need to remove the existing edge before adding the same one
		if (hasEdge(vertexA, vertexB))
			return false;		
		
		if (!hasVertex(vertexA))
			addVertex(vertexA);
		
		if (!hasVertex(vertexB))
			addVertex(vertexB);
		
		// edge is added to both vertices' neighboring list
		adjacencyList.get(vertexA).put(vertexB, new UndirectedWeightedEdge<V>(vertexA,vertexB));
		adjacencyList.get(vertexB).put(vertexA, new UndirectedWeightedEdge<V>(vertexA,vertexB));
		
		return true;
	}
	
	/**
	 * remove undirected edge from the graph, it doesn't matter in which order the vertices are given as input
	 * @param vertexA vertex at one end
	 * @param vertexB vertex at one end
	 * @return True if edge is removed, False if the edge doesn't exist
	 */
	@Override
	public Boolean removeEdge(V vertexA, V vertexB){
		// return False if edge doesn't exist
		if (!hasEdge(vertexA, vertexB))
			return false;
		
		// edge is removed from both vertices' neighboring list 
		adjacencyList.get(vertexA).remove(vertexB);
		adjacencyList.get(vertexB).remove(vertexA);
		
		return true;
	}
	
	/**
	 * get all vertices reachable from given vertex
	 * @param vertex
	 * @return all vertices reachable from given vertex
	 */
	public Set<V> getNeighbors(V vertex){
		return adjacencyList.get(vertex).keySet();
	}
	
	/**
	 * increment betweenness to edge attribute 
	 * @param vertexA vertex at one end
	 * @param vertexB vertex at one end
	 * @param betweenness betweenness to set
	 * @return True if edge exists and set (it'll add to any existing betweenness for the edge), False if edge doesn't exist
	 */
	public Boolean incrementEdgeBetweenness(V vertexA, V vertexB, Integer betweenness){
		// return False if edge doesn't exist
		if (!hasEdge(vertexA, vertexB))
			return false;
		
		adjacencyList.get(vertexA).get(vertexB).incrementBetweenness(betweenness);
		adjacencyList.get(vertexB).get(vertexA).incrementBetweenness(betweenness);
		
		return true;
	}
	
	/**
	 * check if a vertex is present in the graph
	 * @param vertex
	 * @return true if present otherwise false
	 */
	public Boolean hasVertex(V vertex){
		return adjacencyList.containsKey(vertex);
	}
	
	private Boolean hasEdge(V vertexA, V vertexB){
		if (!hasVertex(vertexA) || !hasVertex(vertexB))
			return false;
		return adjacencyList.get(vertexA).get(vertexB) != null;
	}

}
