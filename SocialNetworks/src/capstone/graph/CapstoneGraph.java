package capstone.graph;

import java.util.List;

/**
 * graph interface that defines graph behavior for this Capstone projects
 * 
 * @author Shilin
 *
 */
public interface CapstoneGraph<V> {
	/**
	 * add a vertex
	 * 
	 * @param vertex
	 *            vertex needs to be added
	 * @return True if the vertex is added successfully, false if this vertex
	 *         already existed
	 */
	public Boolean addVertex(V vertex);

	/**
	 * add undirected edge to the graph, it doesn't matter in which order the
	 * vertices are given as input
	 * 
	 * @param vertexA
	 *            vertex at one end
	 * @param vertexB
	 *            vertex at one end
	 * @return True if edge is added, False if an edge has already been
	 *         established in which case this edge needs to be removed before
	 *         being added
	 */
	public Boolean addEdge(V from, V to);

	/**
	 * remove undirected edge with highest betweenness from the graph
	 * 
	 * @return True if edge is removed, False if the edge doesn't exist
	 */
	public Boolean removeEdgeWithMaxBetweeness();

	/**
	 * run algorithm once to return identified communities
	 * 
	 * @param n
	 * @return list of communities which are represented as a list of vertices
	 *         in them
	 */
	public List<List<V>> detectCommunities();

}
