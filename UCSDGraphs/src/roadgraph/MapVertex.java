package roadgraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import geography.GeographicPoint;

/*
 * MapVertex class represent a single vertex in MapGraph
 * */
public class MapVertex extends GeographicPoint{

	// neighbor here is a vertex which can be accessed FROM this one (one direction)
	private Map<MapVertex,MapEdge> neighbors;
	
	private GeographicPoint vertex;
	
	public MapVertex(GeographicPoint vertex)
	{
		//this.vertex = new GeographicPoint(latitude, longitude);
		super(vertex.getX(), vertex.getY());
		this.neighbors = new HashMap<MapVertex,MapEdge>();
	}
	
	// add a neighbor vertex which can be accessed FROM this vertex 
	// replace existing edge attributes if the neighbor already exists 
	public boolean addNeibghor(MapVertex neighbor, MapEdge edge){	
		// don't add if edge is not correct 
		if (!edge.getStartVertex().equals(this) || !edge.getEndVertex().equals(neighbor))
			return false;
		
		this.getNeighbors().put(neighbor, edge);
		
		return true;
	}
	
	// check if a vertex is a neighbor of this  
	public boolean isNeighbor(MapVertex vertex){
		return this.getNeighbors().containsKey(vertex);
	}

	public Map<MapVertex, MapEdge> getNeighbors() {
		return neighbors;
	}
	
	public int getNumNeighbors(){
		return this.getNeighbors().size();
	}
	
	// return the edge from this vertex to the input neighbor vertex 
	// returns null if input is not a neighbor
	public MapEdge getEdge(MapVertex vertex){
		if(!this.isNeighbor(vertex))
			return null;
		
		return this.getNeighbors().get(vertex);	
	}

}
