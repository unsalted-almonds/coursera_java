package capstone.graph;

import java.util.HashMap;
import java.util.Map;

/**
 * edge used in undirected weighted graph.
 * it has attributes weight and betweenness that can be set and retrieved  
 * @author Shilin
 *
 * @param <V>
 */
public class UndirectedWeightedEdge<V> {
	
	private final String ATTRIBUTE_WEIGHT = "WEIGHT";
	private final String ATTRIBUTE_BETWEENNESS = "BETWEENNESS";
	
	private V v1;
	private V v2;
	
	private Map<String, String> attributes = new HashMap<String, String>();
	
	public UndirectedWeightedEdge(){}
	
	public UndirectedWeightedEdge(V v1, V v2) {
		this.v1 = v1;
		this.v2 = v2;
	}
	
	public void setWeight(Integer weight){
		attributes.put(ATTRIBUTE_WEIGHT, String.valueOf(weight));
	}
	
	public void setBetweenness(Integer betweenness){
		attributes.put(ATTRIBUTE_BETWEENNESS, String.valueOf(betweenness));
	}
	
	public Integer getWeight(){
		return Integer.parseInt(attributes.get(ATTRIBUTE_WEIGHT));
	}
	
	public Integer getBetweenness(){
		return Integer.parseInt(attributes.get(ATTRIBUTE_BETWEENNESS));
	}

	public V getV1() {
		return v1;
	}

	public void setV1(V v1) {
		this.v1 = v1;
	}

	public V getV2() {
		return v2;
	}

	public void setV2(V v2) {
		this.v2 = v2;
	}

	
	
}
