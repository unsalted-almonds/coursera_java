package capstone.graph;

public class UndirectedWeightedGraph<V> implements Graph<V> {
	
	private UndirectedWeightedAdjacencyList<V> adjacencyList;
	
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

	
	
	
	

}
