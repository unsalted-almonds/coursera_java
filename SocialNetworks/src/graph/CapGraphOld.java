/**
 * 
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * @author Your name here.
 * 
 * For the warm up assignment, you must implement your Graph in a class
 * named CapGraph.  Here is the stub file.
 *
 */
public class CapGraphOld implements Graph {

	/* (non-Javadoc)
	 * @see graph.Graph#addVertex(int)
	 */
	
	// use adjacency list/set 
	public HashMap<Integer, HashSet<Integer>> adjList = new HashMap<Integer, HashSet<Integer>>();
	
	//Map<Integer, Map<String,Set<Integer>>> circles = new HashMap<Integer, Map<String,Set<Integer>>>();
	
	Map<Integer, Map<String,Set<Integer>>> circles = new HashMap<Integer, Map<String,Set<Integer>>>();
	
	
	public void addCircle(Integer egoNode, Map<String,Set<Integer>> circle){
		circles.put(egoNode, circle);
	}
	
	@Override
	public void addVertex(int num) {
		if (!adjList.containsKey(num))
			adjList.put(num, new HashSet<Integer>());
	}

	/* (non-Javadoc)
	 * @see graph.Graph#addEdge(int, int)
	 */
	@Override
	public void addEdge(int from, int to) {

		if (adjList.containsKey(from))
			adjList.get(from).add(to);
		else
			adjList.put(from, new HashSet<Integer>());
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getEgonet(int)
	 */
	@Override
	public Graph getEgonet(int center) {
		
		CapGraphOld res = new CapGraphOld();
		
		if (this.adjList.containsKey(center)){						
			res.addVertex(center);
			// add all vertices and edges from center to them
			for(Integer i : this.listEdgeFromVertex(center)){
				res.addVertex(i);
				res.addEdge(center, i);
			}
			// add all edges inter-connected 
			for (Integer i : res.listVerices()){
				// skip center 
				if ( !i.equals(center)){					
					for (Integer p : this.listEdgeFromVertex(i)){						
						if (res.listVerices().contains(p))
							res.addEdge(i, p);						
					}					
				}
			}			
		}
		
		return res;
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getSCCs()
	 */
	@Override
	public List<Graph> getSCCs() {
		// TODO Auto-generated method stub
		
		Stack<Integer> verticesStack = this.stackifyVertices(this.listVerices());
		
		verticesStack = this.graphTraversal(this, verticesStack);
				
		return this.graphTraversalSecond(this.transposeGraph(this), verticesStack);
	}

	/* (non-Javadoc)
	 * @see graph.Graph#exportGraph()
	 */
	@Override
	public HashMap<Integer, HashSet<Integer>> exportGraph() {
		return this.adjList;
	}
	
	public Set<Integer> listVerices(){
		return this.adjList.keySet();
	}
	
	public Set<Integer> listEdgeFromVertex(int num){
		Set<Integer> res = null;
		
		if (this.adjList.containsKey(num))
			res = this.adjList.get(num);
		
		return res;
	}
	
	
	private Stack<Integer> graphTraversal(CapGraphOld g, Stack<Integer> verticesStack){

		Set<Integer> visited = new HashSet<Integer>();
		Stack<Integer> finished = new Stack<Integer>();
		
		while (!verticesStack.isEmpty()){			
			Integer vertex = verticesStack.pop();			
			if (!visited.contains(vertex))
				graphTraversal(g, vertex, visited ,finished);						
		}
		
		return finished;
	}

	private List<Graph> graphTraversalSecond(CapGraphOld g, Stack<Integer> verticesStack){

		Set<Integer> visited = new HashSet<Integer>();
		Stack<Integer> finished = new Stack<Integer>();
		
		List<Graph> res = new ArrayList<Graph>();
		
		while (!verticesStack.isEmpty()){			
			Integer vertex = verticesStack.pop();			
			if (!visited.contains(vertex)){
				graphTraversal(g, vertex, visited ,finished);
				res.add(subGraph(g, finished));
			}			
		}
		
		return res;
	}
	
	private void graphTraversal(CapGraphOld g, Integer vertex, Set<Integer> visited ,Stack<Integer> finished){
		
		visited.add(vertex);
		
		for (Integer neighbor : g.listEdgeFromVertex(vertex)){
			
			if (!visited.contains(neighbor))
				graphTraversal(g, neighbor, visited ,finished);
		}		
		finished.push(vertex);		
	}
	
	private CapGraphOld transposeGraph(CapGraphOld g) {

		CapGraphOld res = new CapGraphOld();

		for (Integer i : g.listVerices()) {			
			res.addVertex(i);			
			for (Integer p : g.listEdgeFromVertex(i)){
				res.addVertex(p);
				res.addEdge(p, i);
			}
		}
		return res;
	}
	
	private Stack<Integer> stackifyVertices(Set<Integer> vertices){
		Stack<Integer> res = new Stack<Integer>();
		for (Integer i : vertices)
			res.push(i);
		return res;
	}
	
	// create sub graph of the original graph using given vertices 
	private CapGraphOld subGraph(CapGraphOld g, Stack<Integer> vertices){
		CapGraphOld res = new CapGraphOld();
		
		while (!vertices.isEmpty()){		
			Integer vertex = vertices.pop();
			res.addVertex(vertex);
			for (Integer neighbor : g.listEdgeFromVertex(vertex)){
				if (vertices.contains(neighbor))
					res.addEdge(vertex, neighbor);
			}
		}
		/*
		for (Integer vertex : vertices){
			res.addVertex(vertex);
			for (Integer neighbor : g.listEdgeFromVertex(vertex)){
				if (vertices.contains(neighbor))
					res.addEdge(vertex, neighbor);
			}
		}*/
		
		return res;
	}
	
}
