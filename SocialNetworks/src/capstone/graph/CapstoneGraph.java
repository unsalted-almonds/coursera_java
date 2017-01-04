package capstone.graph;

public class CapstoneGraph<V> implements Graph<V>{
	
	private AdjacencyList<V> adjacencyList;
	
	public CapstoneGraph(AdjacencyList<V> adjacencyList){
		this.adjacencyList = adjacencyList;
	}

	@Override
	public Boolean addVertex(V vertex) {
		return adjacencyList.addVertex(vertex);
		
	}

	@Override
	public Boolean addEdge(V vertexA, V vertexB) {
		return adjacencyList.addEdge(vertexA, vertexB);
	}

	public AdjacencyList<V> getAdjacencyList() {
		return adjacencyList;
	}
	
	
	

}
