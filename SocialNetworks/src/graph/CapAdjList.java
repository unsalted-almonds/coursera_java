package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class CapAdjList {

	Map<Integer, Map<Integer, Integer>> adjList = new HashMap<Integer, Map<Integer, Integer>>();

	CapNodeVectorMap vectorMap = new CapNodeVectorMap();

	public void initWeight() {
		Integer from;
		Integer to;
		for (Map.Entry<Integer, Map<Integer, Integer>> entry : adjList.entrySet()) {
			from = entry.getKey();
			for (Map.Entry<Integer, Integer> entry1 : entry.getValue().entrySet()) {
				to = entry1.getKey();
				setWeight(from, to, computeWeight(from, to));
			}
		}
	}

	private Integer computeWeight(Integer from, Integer to) {
		List<Boolean> fromVector = vectorMap.getVectorByNodeVal(from);
		List<Boolean> toVector = vectorMap.getVectorByNodeVal(to);
		int weight = 0;
		for (int i = 0; i < fromVector.size(); i++) {
			if (!(fromVector.get(i) == true && toVector.get(i) == true))
				weight++;
		}

		System.out.println("weight from " + from + " to " + to + " is " + weight);

		return weight;
	}

	public void addNode(Integer nodeVal) {
		adjList.put(nodeVal, new HashMap<Integer, Integer>());
	}

	public void addEdge(Integer from, Integer to, Integer weight) {
		if (adjList.containsKey(from))
			adjList.get(from).put(to, weight);
		else {
			Map<Integer, Integer> edge = new HashMap<Integer, Integer>();
			edge.put(to, weight);
			adjList.put(from, edge);
		}
	}

	public void addEdge(Integer from, Integer to) {
		if (adjList.containsKey(from))
			adjList.get(from).put(to, null);
		else {
			Map<Integer, Integer> edge = new HashMap<Integer, Integer>();
			edge.put(to, null);
			adjList.put(from, edge);
		}
	}

	public void printEdgeByNode(Integer from) {
		if (hasNode(from)) {
			List<Integer> res = new ArrayList<Integer>();
			for (Entry<Integer, Integer> entry : adjList.get(from).entrySet()) {
				res.add(entry.getKey());
			}

			System.out.println(res);
		} else
			System.out.println("Node " + from + " is not found.");
	}

	public List<CapEdge> getEdgefrom(Integer from) {
		if (adjList.containsKey(from)) {

			List<CapEdge> edgeList = new ArrayList<CapEdge>();
			for (Entry<Integer, Integer> entry : adjList.get(from).entrySet()) {
				edgeList.add(new CapEdge(from, entry.getKey(), entry.getValue()));
			}

			return edgeList;
		} else
			return null;
	}

	private boolean hasNode(Integer from) {
		if (adjList.containsKey(from))
			return true;
		return false;
	}

	private boolean hasEdge(Integer from, Integer to) {
		if (adjList.containsKey(from) && adjList.get(from).containsKey(to))
			return true;
		return false;
	}

	public boolean setWeight(Integer from, Integer to, Integer weight) {
		if (hasEdge(from, to)) {
			adjList.get(from).put(to, weight);
			return true;
		}
		return false;
	}

	public void addVector(Integer nodeVal, List<Boolean> vector) {
		vectorMap.addVector(nodeVal, vector);
	}

	public List<Boolean> getVectorByNodeVal(Integer nodeVal) {
		return vectorMap.getVectorByNodeVal(nodeVal);
	}

	public boolean hasVector(Integer nodeVal) {
		return vectorMap.hasVector(nodeVal);
	}

	public void setVector(Integer nodeVal, List<Boolean> vector) {
		vectorMap.setVector(nodeVal, vector);
	}

	public void printVectorByNode(Integer nodeVal) {
		if (hasVector(nodeVal))
			System.out.println("Node " + nodeVal + "'s vector: " + vectorMap.getVectorByNodeVal(nodeVal));
		else
			System.out.println("Node " + nodeVal + "'s vector is not found.");
	}

}
