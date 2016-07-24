package graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * map node value to its feat vector
 * @author Shilin
 *
 */
public class CapNodeVectorMap {
		
	private Map<Integer,List<Boolean>> nodeVectorMap = new HashMap<Integer,List<Boolean>>();
	
	public void addVector(Integer nodeVal, List<Boolean> vector){
		nodeVectorMap.put(nodeVal, vector);
	}
	
	public List<Boolean> getVectorByNodeVal(Integer nodeVal){
		return nodeVectorMap.get(nodeVal);
	}
	
	public boolean hasVector(Integer nodeVal){
		return nodeVectorMap.containsKey(nodeVal);
	}
	
	public void setVector(Integer nodeVal, List<Boolean> vector){
		nodeVectorMap.put(nodeVal, vector);
	}
	
	public void printVectorByNode(Integer nodeVal){
		if (hasVector(nodeVal))
			System.out.println("Node " + nodeVal + "'s vector: " + nodeVectorMap.get(nodeVal));
		else
			System.out.println("Node " + nodeVal + "'s vector is not found.");
	}

}
