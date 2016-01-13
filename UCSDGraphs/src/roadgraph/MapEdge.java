package roadgraph;

import java.util.HashMap;
import java.util.Map;

import geography.GeographicPoint;

/*
 * class of MapEdge
 * it represents a single directed edge between two vertices in MapGraph
 */
public class MapEdge {
	
	private GeographicPoint startVertex;
	private GeographicPoint endVertex;
	// stores key-value pairs which map this edge's attributes 
	private Map<String,String> edgeAttributes;
	
	public static final String ROAD_NAME = "roadName";
	public static final String ROAD_TYPE = "roadType";
	public static final String LENGTH = "length";	
	
	public MapEdge(GeographicPoint startVertex,GeographicPoint endVertex,String roadName, String roadType, Double length){
		this.startVertex = startVertex;
		this.endVertex = endVertex;
		this.edgeAttributes = new HashMap<String,String>();
		this.edgeAttributes.put(ROAD_NAME, roadName);
		this.edgeAttributes.put(ROAD_TYPE, roadType);
		this.edgeAttributes.put(LENGTH, ""+length);
	}
	
	public String getRoadName(){
		return this.getEdgeAttributes().get(ROAD_NAME);
	}
	
	public String getRoadType(){
		return this.getEdgeAttributes().get(ROAD_TYPE);
	}	

	public Double getLength(){
		return Double.parseDouble(this.getEdgeAttributes().get(LENGTH));
	}

	public GeographicPoint getStartVertex() {
		return startVertex;
	}

	public GeographicPoint getEndVertex() {
		return endVertex;
	}

	public Map<String, String> getEdgeAttributes() {
		return edgeAttributes;
	}	
	
	
}
