package capstone.graph;

/**
 * graph interface that defines common graph behavior
 * @author Shilin
 *
 */
public interface Graph<V> {
    /* Creates a vertex with the given number. */
    public Boolean addVertex(V vertex);
    
    /* Creates an edge from the first vertex to the second. */
    public Boolean addEdge(V from, V to);
  
} 
