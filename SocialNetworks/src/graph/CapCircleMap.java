package graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CapCircleMap {

	private Map<Integer, Map<String, List<Integer>>> circleMap = new HashMap<Integer, Map<String, List<Integer>>>();

	public void setCircle(Integer egoNode, String circleName, List<Integer> members) {

		if (circleMap.containsKey(egoNode)) {
			circleMap.get(egoNode).put(circleName, members);
		} else {
			Map<String, List<Integer>> memberMap = new HashMap<String, List<Integer>>();

			memberMap.put(circleName, members);

			circleMap.put(egoNode, memberMap);
		}

	}

	public Map<String, List<Integer>> getMembers(Integer egoNode) {
		return circleMap.get(egoNode);
	}

	public void printCirclesByEgoNode(Integer egoNodeVal) {
		if (circleMap.containsKey(egoNodeVal))
			System.out.println("Circles for Egonode " + egoNodeVal + ": " + circleMap.get(egoNodeVal));
		else
			System.out.println("Circles for Egonode " + egoNodeVal + " is not found.");
	}

}
