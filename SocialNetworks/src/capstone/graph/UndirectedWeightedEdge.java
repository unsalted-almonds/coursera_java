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
	
	private Map<String, String> attributes;
	
	public UndirectedWeightedEdge(){}
	
	/**
	 * initialize weight to 1 and betweenness to 0
	 * @param v1
	 * @param v2
	 */
	public UndirectedWeightedEdge(V v1, V v2) {
		this.v1 = v1;
		this.v2 = v2;
		attributes = new HashMap<String, String>();
		attributes.put(ATTRIBUTE_WEIGHT, "1");
		attributes.put(ATTRIBUTE_BETWEENNESS, "0");
	}
	
	public void setWeight(Integer weight){
		attributes.put(ATTRIBUTE_WEIGHT, String.valueOf(weight));
	}
	
	public void setBetweenness(Integer betweenness){
		attributes.put(ATTRIBUTE_BETWEENNESS, String.valueOf(betweenness));	
	}
	
	public void incrementBetweenness(Integer betweenness){
		attributes.put(ATTRIBUTE_BETWEENNESS, String.valueOf(getBetweenness() + betweenness));		
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((v1 == null) ? 0 : v1.hashCode());
		result = prime * result + ((v2 == null) ? 0 : v2.hashCode());
		return result;
	}

	/**
	 * it is written in the way that v1 and v2's order doesn't matter
	 * 
	 * this is probably not needed here!!!
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		UndirectedWeightedEdge<V> other = (UndirectedWeightedEdge<V>) obj;
		
		// order of v1 and v2 doesn't matter
		if ((v1.equals(other.v1) && v2.equals(other.v2)) || (v1.equals(other.v2) && v2.equals(other.v1)))
			return true;
		return false;
	}
	
}
