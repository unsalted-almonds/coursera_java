package roadgraph;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import geography.GeographicPoint;

public class AdjacencyList {

	// use Map to build adjacency list to ensure good performance
	// edge information is also stored here for easy retrieval
	private Map<GeographicPoint, Map<GeographicPoint, MapEdge>> adjList;

	public AdjacencyList() {
		adjList = new HashMap<GeographicPoint, Map<GeographicPoint, MapEdge>>();
	}

	// package access 
	Map<GeographicPoint, Map<GeographicPoint, MapEdge>> getAdjList() {
		return adjList;
	}

	// add a vertex
	public boolean addVertex(GeographicPoint vertex) {
		if (this.isInMap(vertex) || vertex == null)
			return false;

		this.getAdjList().put(vertex, new HashMap<GeographicPoint, MapEdge>());

		return true;
	}

	// add a neighbor vertex which can be accessed FROM this vertex
	// replace existing edge attributes if the neighbor already exists
	public boolean addNeibghor(GeographicPoint from, GeographicPoint to, MapEdge edge) {
		// don't add if edge is not correct
		if (!edge.getStartVertex().equals(from) || !edge.getEndVertex().equals(to)
				|| !this.getAdjList().containsKey(from) || !this.getAdjList().containsKey(to))
			return false;

		this.getAdjList().get(from).put(to, edge);

		return true;
	}

	// check if to is a neighbor of from
	public boolean isNeighbor(GeographicPoint from, GeographicPoint to) {
		if (this.isInMap(from))
			return this.getNeighbors(from).contains(to);

		return false;
	}

	public Set<GeographicPoint> getNeighbors(GeographicPoint from) {
		if (!this.isInMap(from))
			return null;
		return this.getAdjList().get(from).keySet();
	}

	// return number of neighbors from has, if it's not in the map, return 0
	public int getNumNeighbors(GeographicPoint from) {
		if (this.isInMap(from))
			return this.getNeighbors(from).size();
		else
			return 0;
	}

	// return number of total vertices
	public int getNumVertices() {
		return this.getAdjList().size();
	}

	public Set<GeographicPoint> getVertices() {
		return this.getAdjList().keySet();
	}

	// return the edge from from to to
	// returns null if there is no edge
	public MapEdge getEdge(GeographicPoint from, GeographicPoint to) {
		if (!this.isInMap(from))
			return null;

		return this.getAdjList().get(from).get(to);
	}

	// helper method which check whether a vertex is in the Map
	public boolean isInMap(GeographicPoint vertex) {
		return this.getAdjList().containsKey(vertex);
	}
}
