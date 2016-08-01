/**
 * 
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

import util.CapGraphLoader;


public class CapGraph  {
	
	private CapAdjList adjList = new CapAdjList();
	
	//private CapNodeVectorMap nodeVector = new CapNodeVectorMap();
	
	private CapCircleMap circles = new CapCircleMap();
	
	public static void main (String args[]) throws Exception{
		
		CapGraph g = new CapGraph();
		
		CapGraphLoader.loadCapGraph(g);
		
		Map<Integer, Integer> res = g.shortestPathDijkstra(0);
		
		System.out.println(res);
		
		System.out.println(g.buildPath(res,0,879));
		
		
	}

	public List<Integer> buildPath(Map<Integer, Integer> backTrackMap, Integer from, Integer to) {
		if (adjList.hasNode(from) && adjList.hasNode(to)) {
			Integer node = to;
			List<Integer> res = new ArrayList<Integer>();
			while (node != null && !node.equals(from)) {
				res.add(node);
				node = backTrackMap.get(node);
			}
			// return empty list when they are not connected
			if (node == null)
				return new ArrayList<Integer>();

			res.add(from);
			return res;

		} else
			throw new IllegalArgumentException("input node is not in graph");
	}
	

	
	// get shortest path from one start node to every other node
	public Map<Integer, Integer> shortestPathDijkstra(Integer startNode){	
		if(adjList.hasNode(startNode)){
			// store previous node for back tracking to obtain shortest path  
			Map<Integer, Integer> previousNode = new HashMap<Integer, Integer>();			
			Map<Integer, Integer> distanceMap = new HashMap<Integer,Integer>();			
			Set<Integer> visited = new HashSet<Integer>();			
			PriorityQueue<CapNode> toVisit = new PriorityQueue<CapNode>();
			
			toVisit.add(new CapNode(startNode,0));
			distanceMap.put(startNode,0);
			previousNode.put(startNode, null);
			
			while (!toVisit.isEmpty()){				
				CapNode minNode = toVisit.poll();
				Integer minNodeDist = minNode.getDist();
				
				for (CapEdge edge : adjList.getEdgefrom(minNode.getVal())) {
					Integer nodeVal = edge.getTo();
					Integer weight = edge.getWeight();
					if (!visited.contains(nodeVal)){
						Integer distance = minNodeDist + weight;
						if (!distanceMap.containsKey(nodeVal) ){
							distanceMap.put(nodeVal, distance);
							toVisit.add(new CapNode(nodeVal, distance));
							previousNode.put(nodeVal, minNode.getVal());
						}else {							
							if (distanceMap.get(nodeVal) > distance){								
								toVisit.remove(new CapNode(nodeVal, distanceMap.get(nodeVal)));
								toVisit.add(new CapNode(nodeVal, distance));
								distanceMap.put(nodeVal, distance);
								previousNode.put(nodeVal, minNode.getVal());
							}							
						}
					}
				}
				
				visited.add(minNode.getVal());
				System.out.println("visited node " + minNode.getVal());
			}
						
			return previousNode;
		}else
			throw new IllegalArgumentException("input node is not in graph");		
	}
	
	
	

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
