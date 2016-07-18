
package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import graph.CapGraph;

public class CapGraphLoader {
	
	static final String FILE_TYPE_CIRCLES = "circles";
	static final String FILE_TYPE_EDGES = "edges";
	static final String FILE_TYPE_EGOFEAT = "egofeat";
	static final String FILE_TYPE_FEAT = "feat";
	static final String FILE_TYPE_FEATNAMES = "featnames";
	
	
	/**
	 * Loads graph with data from a file. The file should consist of lines with
	 * 2 integers each, corresponding to a "from" vertex and a "to" vertex.
	 */
	public static void loadGraph(graph.Graph g, String filename) {
		Set<Integer> seen = new HashSet<Integer>();
		Scanner sc;
		try {
			sc = new Scanner(new File(filename));
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		// Iterate over the lines in the file, adding new
		// vertices as they are found and connecting them with edges.
		while (sc.hasNextInt()) {
			int v1 = sc.nextInt();
			int v2 = sc.nextInt();
			if (!seen.contains(v1)) {
				g.addVertex(v1);
				seen.add(v1);
			}
			if (!seen.contains(v2)) {
				g.addVertex(v2);
				seen.add(v2);
			}
			g.addEdge(v1, v2);
		}

		sc.close();
	}

	public static void loadGraph() {

	}

	public static void listFilesForFolder(CapGraph g,final File folder) {

		String fileName;
		String filePath;
		String[] fileNameToken;
		Integer node;
		String fileType;

		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(g, fileEntry);
			} else {
				// System.out.println(fileEntry.getName() + ": " +
				// fileEntry.getPath());
				try {
					fileName = fileEntry.getName();
					filePath = fileEntry.getPath();
					fileNameToken = fileName.split("\\.");
					if (fileNameToken.length != 2) {
						throw new Exception("File " + filePath + " has problem, skipping ...");
					}
					node = Integer.parseInt(fileNameToken[0]);
					fileType = fileNameToken[1];
					
					switch(fileType){
					
					case FILE_TYPE_CIRCLES:
						loadCircles(g,node,filePath);
						break;
					case FILE_TYPE_EDGES:
						loadEdges(g,node,filePath);
						break;						
					default:
						//throw new Exception("File Type " + fileType + " is not supported, skipping ...");
					}
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		// load feat/edge weight after everything else is loaded 
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(g, fileEntry);
			} else {
				// System.out.println(fileEntry.getName() + ": " +
				// fileEntry.getPath());
				try {
					fileName = fileEntry.getName();
					filePath = fileEntry.getPath();
					fileNameToken = fileName.split("\\.");
					if (fileNameToken.length != 2) {
						throw new Exception("File " + filePath + " has problem, skipping ...");
					}
					node = Integer.parseInt(fileNameToken[0]);
					fileType = fileNameToken[1];
					
					if (fileType.equals(FILE_TYPE_EGOFEAT) || fileType.equals(FILE_TYPE_FEAT)){
						
					}

					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
	private static void loadEgoFeat(CapGraph g,Integer egoNode, String filePath){
		
		Set<Integer> seen = new HashSet<Integer>();
		try (Scanner sc = new Scanner(new File(filePath))){
			// Iterate over the lines in the file, adding new
			// vertices as they are found and connecting them with edges.
			while (sc.hasNextInt()) {
				int v1 = sc.nextInt();
				int v2 = sc.nextInt();
				if (!seen.contains(v1)) {
					g.addVertex(v1);
					seen.add(v1);
				}
				if (!seen.contains(v2)) {
					g.addVertex(v2);
					seen.add(v2);
				}
				g.addEdge(v1, v2);
			}
			
			// egoNode is also connected to every node
			for (Integer node : seen)
				g.addEdge(egoNode, node);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}		
		
	}
	
	private static void loadEdges(CapGraph g,Integer egoNode, String filePath){
		Set<Integer> seen = new HashSet<Integer>();
		try (Scanner sc = new Scanner(new File(filePath))){
			// Iterate over the lines in the file, adding new
			// vertices as they are found and connecting them with edges.
			while (sc.hasNextInt()) {
				int v1 = sc.nextInt();
				int v2 = sc.nextInt();
				if (!seen.contains(v1)) {
					g.addVertex(v1);
					seen.add(v1);
				}
				if (!seen.contains(v2)) {
					g.addVertex(v2);
					seen.add(v2);
				}
				g.addEdge(v1, v2);
			}
			
			// egoNode is also connected to every node
			for (Integer node : seen)
				g.addEdge(egoNode, node);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	private static void loadCircles(CapGraph g,Integer egoNode, String filePath){
		String fileLine;
		String[] fileLineToken;
		String circleName;
		Set<Integer> circleMembers = new HashSet<Integer>();
		Map<String, Set<Integer>> circleMap = new HashMap<String, Set<Integer>>();
		try (BufferedReader br = new BufferedReader( new FileReader(filePath))) {
            while( (fileLine = br.readLine()) != null){
                //System.out.println(fileLine);
                fileLineToken = fileLine.split(" ");
                circleName = fileLineToken[0];
                for (int i = 1; i < fileLineToken.length; i++){
                	circleMembers.add(Integer.parseInt(fileLineToken[i]));
                }
                circleMap.put(circleName, circleMembers);
                g.addCircle(egoNode, circleMap);
                //System.out.println("loaded circles for node: " + egoNode);
            }
            
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public static void main(String args[]) {
		
		CapGraph g = new CapGraph();
		
		listFilesForFolder(g,new File("data/facebook"));
		
		System.out.println(g.adjList.get(0).size());

	}
}
