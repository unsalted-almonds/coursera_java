package capstone.graph;

public interface AdjacencyList<V>{
	/**
	 * add a vertex 
	 * @param vertex vertex needs to be added
	 * @return True if the vertex is added successfully, false if this vertex already existed
	 */
	public Boolean addVertex(V vertex);
	
	/**
	 * add undirected edge to the graph, it doesn't matter in which order the vertices are given as input
	 * @param vertexA vertex at one end
	 * @param vertexB vertex at one end
	 * @return True if edge is added, False if an edge has already been established in which case this edge needs to be removed before being added
	 */
	public Boolean addEdge(V vertexA, V vertexB);
	
	/**
	 * remove undirected edge from the graph, it doesn't matter in which order the vertices are given as input
	 * @param vertexA vertex at one end
	 * @param vertexB vertex at one end
	 * @return True if edge is removed, False if the edge doesn't exist
	 */
	public Boolean removeEdge(V vertexA, V vertexB);
}
