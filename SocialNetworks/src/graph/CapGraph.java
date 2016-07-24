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


public class CapGraph  {
	
	private CapAdjList adjList = new CapAdjList();
	
	//private CapNodeVectorMap nodeVector = new CapNodeVectorMap();
	
	private CapCircleMap circles = new CapCircleMap();

	public void addEdge(Integer from, Integer to, Integer weight){
		adjList.addEdge(from, to, weight);
	};

	public void addEdge(Integer from, Integer to){
		adjList.addEdge(from, to);
	};
	
	public boolean applyWeight(Integer from, Integer to, Integer weight){
		return adjList.setWeight(from, to, weight);
	};
	
	public void initWeight(){
		adjList.initWeight();
	}
	
	public void printEdgeFromNode(Integer nodeVal){
		adjList.printEdgeByNode(nodeVal);
	}
	
	public void addNodeVectorByNode(Integer nodeVal, List<Boolean> vector){
		adjList.addVector(nodeVal, vector);
	}
	
	public boolean hasVector(Integer nodeVal){
		return adjList.hasVector(nodeVal);
	}
	
	public List<Boolean> getVectorByNode(Integer nodeVal){
		if (adjList.hasVector(nodeVal))
			return adjList.getVectorByNodeVal(nodeVal);
		return null;
	}
	
	public void setVector(Integer nodeVal, List<Boolean> vector){
		adjList.setVector(nodeVal, vector);
	}
	
	public void printVectorByNode(Integer nodeVal){
		adjList.printVectorByNode(nodeVal);
	}
	
	public void setCircle(Integer egoNodeVal, String circleName, List<Integer> members){
		circles.setCircle(egoNodeVal, circleName, members);
	}
	
	public void printCirclesByEgoNode(Integer egoNodeVal){
		circles.printCirclesByEgoNode(egoNodeVal);
	}
	
}
