package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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

	private static Map<Integer, Map<Integer, Integer>> featLookup = new HashMap<Integer, Map<Integer, Integer>>();

	private static Set<Integer> nodeSet = new HashSet<Integer>();

	private static int featSize = 0;

	public static void main(String args[]) throws Exception {
		CapGraph g = new CapGraph();
		/*
		 * featSize = analyzeFeat(new File("data/facebook"));
		 * 
		 * System.out.println("feat size is " + featSize);
		 * 
		 * System.out.println(featLookup.get(1684));
		 * 
		 * 
		 * 
		 * loadEdges(g, "data/facebook/0.edges", "0.edges");
		 * 
		 * g.printEdgeFromNode(329);
		 * 
		 * loadFeat(g, "data/facebook/0.egofeat", "0.egofeat");
		 * 
		 * g.printVectorByNode(0);
		 * 
		 * loadFeat(g, "data/facebook/0.feat", "0.feat");
		 * 
		 * g.printVectorByNode(69);
		 */
		loadCapGraph(g);

		g.printVectorByNode(69);
		g.printVectorByNode(861);
		g.printVectorByNode(0);
		g.printCirclesByEgoNode(0);

	}

	public static void loadCapGraph(CapGraph g) throws Exception {
		String fileName = "";
		String fileType = "";
		String filePath = "";
		String fileNameToken[];
		final File folder = new File("data/facebook");

		// first analyze feat files
		// feat starts from 0, so plus one from the max feat id
		featSize = analyzeFeat(folder) + 1;

		for (final File fileEntry : folder.listFiles()) {
			fileName = fileEntry.getName();
			filePath = fileEntry.getPath();
			fileNameToken = fileName.split("\\.");
			if (fileNameToken.length != 2) {
				throw new Exception("File " + filePath + " has problem, skipping ...");
			}

			fileType = fileNameToken[1];

			switch (fileType) {
			case FILE_TYPE_EDGES:
				loadEdges(g, filePath, fileName);
				break;
			case FILE_TYPE_FEAT:
				loadFeat(g, filePath, fileName);
				break;
			case FILE_TYPE_EGOFEAT:
				loadFeat(g, filePath, fileName);
				break;
			case FILE_TYPE_CIRCLES:
				loadCircles(g, filePath, fileName);
			}
		}

		g.initWeight();
	}

	/**
	 * scan feat files to load feat mapping for each egonode
	 * 
	 * @param folder
	 * @return
	 */
	private static int analyzeFeat(final File folder) {
		String fileName = "";
		String fileType = "";
		String filePath = "";
		String fileNameToken[];
		Integer nodeVal;
		Integer maxFeat = -1;

		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				analyzeFeat(fileEntry);
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

					fileType = fileNameToken[1];
					// featnames file is where absolute feat ids are stored
					if (fileType.equals(FILE_TYPE_FEATNAMES)) {
						nodeVal = Integer.parseInt(fileNameToken[0]);
						String fileLine;
						String[] fileLineToken;
						Integer actualfeatId;
						Integer decoratedFeatId;
						featLookup.put(nodeVal, new HashMap<Integer, Integer>());
						try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
							while ((fileLine = br.readLine()) != null) {
								// System.out.println(fileLine);
								fileLineToken = fileLine.split(" ");
								decoratedFeatId = Integer.parseInt(fileLineToken[0]);
								actualfeatId = Integer.parseInt(fileLineToken[3]);
								featLookup.get(nodeVal).put(decoratedFeatId, actualfeatId);
								if (actualfeatId > maxFeat)
									maxFeat = actualfeatId;
							}

						}
					}

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return maxFeat;
	}

	private static void loadEdges(CapGraph g, String filePath, String fileName) throws FileNotFoundException {

		String[] fileNameToken = fileName.split("\\.");
		if (fileNameToken.length != 2 || !fileNameToken[1].equals(FILE_TYPE_EDGES)) {
			throw new IllegalArgumentException("File " + filePath + " has problem, skipping ...");
		}
		Integer egoNodeVal = Integer.parseInt(fileNameToken[0]);

		// first load all nodes and edges, weight will be loaded later
		Set<Integer> seen = new HashSet<Integer>();
		Scanner sc = new Scanner(new File(filePath));
		// Iterate over the lines in the file, adding new
		// vertices as they are found and connecting them with
		// edges.
		while (sc.hasNextInt()) {
			int v1 = sc.nextInt();
			int v2 = sc.nextInt();
			if (!seen.contains(v1))
				seen.add(v1);
			if (!seen.contains(v2))
				seen.add(v2);

			g.addEdge(v1, v2);
			g.addEdge(v2, v1);

		}
		sc.close();
		// egoNode is also connected to every node
		for (Integer node : seen)
			g.addEdge(egoNodeVal, node);
	}

	private static void loadFeat(CapGraph g, String filePath, String fileName)
			throws NumberFormatException, IOException {
		String[] fileNameToken = fileName.split("\\.");
		if (fileNameToken.length != 2
				|| !(fileNameToken[1].equals(FILE_TYPE_FEAT) || fileNameToken[1].equals(FILE_TYPE_EGOFEAT))) {
			throw new IllegalArgumentException("File " + filePath + " has problem, skipping ...");
		}
		Integer egoNodeVal = Integer.parseInt(fileNameToken[0]);

		System.out.println("process feat in egonode " + egoNodeVal);

		String fileLine;
		List<String> fileLineToken;
		Integer nodeVal;
		List<Boolean> vector;

		BufferedReader br = new BufferedReader(new FileReader(filePath));
		while ((fileLine = br.readLine()) != null) {
			// System.out.println(fileLine);
			fileLineToken = new ArrayList<String>(Arrays.asList(fileLine.split(" ")));

			if (fileNameToken[1].equals(FILE_TYPE_FEAT)) {
				nodeVal = Integer.parseInt(fileLineToken.get(0));
				if (g.hasVector(nodeVal))
					vector = g.getVectorByNode(nodeVal);
				else
					vector = new ArrayList<Boolean>(Collections.nCopies(featSize, false));

				for (int i = 1; i < fileLineToken.size(); i++) {
					if (fileLineToken.get(i).equals("1"))
						vector.set(featLookup.get(egoNodeVal).get(i - 1), true);
				}
				g.setVector(nodeVal, vector);
				/* debug statement 
				if (nodeVal.equals(0)) {
					System.out.println("set vector for node " + nodeVal);
					System.out.println("vector value: " + fileLine);
					g.printVectorByNode(0);
				}
				*/

			}

			else if (fileNameToken[1].equals(FILE_TYPE_EGOFEAT)) {
				if (g.hasVector(egoNodeVal))
					vector = g.getVectorByNode(egoNodeVal);
				else
					vector = new ArrayList<Boolean>(Collections.nCopies(featSize, false));

				for (int i = 0; i < fileLineToken.size(); i++) {
					if (fileLineToken.get(i).equals("1"))
						vector.set(featLookup.get(egoNodeVal).get(i), true);
				}
				g.setVector(egoNodeVal, vector);
				/* debug statement 
				if (egoNodeVal.equals(0)) {
					System.out.println("set vector for node " + egoNodeVal);
					g.printVectorByNode(0);
				}
				*/
			}

		}
	}

	private static void loadCircles(CapGraph g, String filePath, String fileName) {
		String fileLine;
		List<String> fileLineToken;
		String[] fileNameToken = fileName.split("\\.");
		Integer egoNodeVal = Integer.parseInt(fileNameToken[0]);
		String circleName;
		List<Integer> members = new ArrayList<Integer>();

		if (fileNameToken.length != 2 || !fileNameToken[1].equals(FILE_TYPE_CIRCLES)) {
			throw new IllegalArgumentException("File " + filePath + " has problem, skipping ...");
		}

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			while ((fileLine = br.readLine()) != null) {
				// System.out.println(fileLine);
				fileLineToken = new ArrayList<String>(Arrays.asList(fileLine.split(" ")));
				circleName = fileLineToken.get(0);
				for (int i = 1; i < fileLineToken.size(); i++) {
					members.add(Integer.parseInt(fileLineToken.get(i)));
				}

				g.setCircle(egoNodeVal, circleName, members);
				// System.out.println("loaded circles for node: " + egoNode);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println("members for 0's circle: " + .get(0));
	}

}
