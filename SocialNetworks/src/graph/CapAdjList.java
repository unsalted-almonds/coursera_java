package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CapAdjList {

	Map<CapNode, Set<CapEdge>> capAdjList = new HashMap<CapNode, Set<CapEdge>>();

	public void addVertex(CapNode vertex) {
		if (!capAdjList.containsKey(vertex))
			capAdjList.put(vertex, new HashSet<CapEdge>());
	}

	public void addEdge(CapNode from, CapNode to) {

		CapEdge edge = new CapEdge();
		edge.setFrom(from);
		edge.setTo(to);

		if (capAdjList.containsKey(from))
			capAdjList.get(from).add(edge);
		else {
			Set<CapEdge> edgeSet = new HashSet<CapEdge>();
			edgeSet.add(edge);
			capAdjList.put(from, edgeSet);
		}
	}
	
	public Set<CapNode> getNeighbors(CapNode vertex){
		
		Set<CapNode> neighborSet = new HashSet<CapNode>();
		
		for (CapEdge e : this.capAdjList.get(vertex)){
			neighborSet.add(e.getTo());
		}
		
		return neighborSet;
	}
}
