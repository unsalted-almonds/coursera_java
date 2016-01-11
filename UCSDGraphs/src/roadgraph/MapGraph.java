/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	//TODO: Add your member variables here in WEEK 2
	
	// vertices, edges (this is done by using adjacency list)
	// this graph is sparse by nature, so using adjList is better 
	
	// here the list is the points which can be accessed from the Map Key (not necessarily neighbors as it's a directed graph)
	// since there are attributes associated with each edge, here I use a Map to store them
	// private Map<GeographicPoint,List<Map<String,GeographicPoint>>> adjList;
	private Map<GeographicPoint,Map<GeographicPoint,Map<String,String>>> adjList;

	private int numVertices;
	private int numEdges;
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		// TODO: Implement in this constructor in WEEK 2
		//this.adjList = new HashMap<GeographicPoint,List<Map<String,GeographicPoint>>>();
		this.adjList = new HashMap<GeographicPoint,Map<GeographicPoint,Map<String,String>>>();
		
		//this.rawLink = new HashMap<GeographicPoint,Set<GeographicPoint>>();
		
		// have numEdges increments each time an edge is added, 
		// this is more efficient than calculating from adjList on the fly  
		this.numEdges = 0;
		this.numVertices = 0;
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 2
		return this.numVertices;
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 2
		// it's all keys of the adjList
		return this.adjList.keySet();
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 2
		return this.numEdges;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// TODO: Implement this method in WEEK 2
		
		// check
		// parent class of GeographicPoint should have properly implemented hashcode and equals method
		// so using containsKey method compares value rather than reference 
			if(location == null || this.adjList.containsKey(location))
				return false;
		
		this.adjList.put(location, new HashMap<GeographicPoint,Map<String,String>>());
		
		this.numVertices++;
		
		return true;
	}
	
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		//TODO: Implement this method in WEEK 2
		
		if (!isPointInMap(from)||!isPointInMap(to)||roadName == null || roadType == null || length < 0)
				throw new IllegalArgumentException("One of the Arguments is not Valid");
		
		// check if there's already an edge
		boolean isLinked = this.adjList.get(from).containsKey(to);	
		
		Map<String,String> newEdge = new HashMap<String,String>();
		newEdge.put("roadName", roadName);
		newEdge.put("roadType", roadType);
		newEdge.put("length", ""+length);
		
		// add to to from's list
		this.adjList.get(from).put(to,newEdge);
		// increment edge number if this is a newly created link
		if (!isLinked)
			this.numEdges++;
		
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal,
			Consumer<GeographicPoint> nodeSearched) {
		// TODO: Implement this method in WEEK 2

		// Hook for visualization. See writeup.
		// nodeSearched.accept(next.getLocation());

		// first make sure points are in the graph
		if (!isPointInMap(start) || !isPointInMap(goal))
			return null;

		Queue<GeographicPoint> myQ = new LinkedList<GeographicPoint>();
		Set<GeographicPoint> visited = new HashSet<GeographicPoint>();

		// lastStep stores current point as key, its last point as parent
		Map<GeographicPoint, GeographicPoint> lastStep = new HashMap<GeographicPoint, GeographicPoint>();

		myQ.add(start);

		lastStep.put(start, null);

		boolean hasPath = false;

		while (!myQ.isEmpty()) {
			// get and remove first element from the queue
			GeographicPoint point = myQ.poll();
			visited.add(point);
			nodeSearched.accept(point);
			if (point.equals(goal)) {
				hasPath = true;
				break;
			}

			// insert its neighbor into the queue
			for (GeographicPoint p : this.adjList.get(point).keySet()) {
				if(!visited.contains(p)){
					myQ.add(p);
					// last step is current point
					lastStep.put(p, point);
				}
			}
		}

		//System.out.println("searching complete: " + hasPath);

		//System.out.println(lastStep);
		
		return (hasPath) ? buildPath(start, goal, lastStep) : null;
	}
	
	// this is called when there's a path, otherwise it is a infinite loop!
	private List<GeographicPoint> buildPath(GeographicPoint start, GeographicPoint goal,
			Map<GeographicPoint, GeographicPoint> lastStep) {
		if (!lastStep.containsKey(goal))
			return null;
		else {
			LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
			GeographicPoint parentPoint = lastStep.get(goal);
			path.addFirst(goal);
			while (parentPoint != null) {
				path.addFirst(parentPoint);
				parentPoint = lastStep.get(parentPoint);
			}

			return path;
		}

	}
	
	private boolean isPointInMap(GeographicPoint point){
		return this.adjList.containsKey(point);
	}
	

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3

		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		return null;
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		return null;
	}

	
	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", theMap);
		System.out.println("DONE.");
		
		// You can use this method for testing.  
		
		/* Use this code in Week 3 End of Week Quiz
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		*/
		
	}
	
}
