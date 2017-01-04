package capstone.graph;

public class UndirectedWeightedGraph<V> implements Graph<V> {
	
	private UndirectedWeightedAdjacencyList<V> adjacencyList;
	
	public UndirectedWeightedGraph(){
		adjacencyList = new UndirectedWeightedAdjacencyList<V>();
	}

	@Override
	public Boolean addVertex(V vertex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean addEdge(V from, V to) {
		// TODO Auto-generated method stub
		return null;
	}


	
	

}
