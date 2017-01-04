package capstone.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GraphLoader {

	static final String FILE_TYPE_CIRCLES = "circles";
	static final String FILE_TYPE_EDGES = "edges";
	static final String FILE_TYPE_EGOFEAT = "egofeat";
	static final String FILE_TYPE_FEAT = "feat";
	static final String FILE_TYPE_FEATNAMES = "featnames";
	static final String FILE_NAME_COMBINED_EDGES = "facebook_combined.txt";

	public static void main(String args[]) throws Exception {
		// inject corresponding adjacency list implementation
		CapstoneGraph<Integer> g = new CapstoneGraph<Integer>(new UndirectedWeightedAdjacencyList<Integer>());

		loadCapGraph(g);

		System.out.println("add edge between 0 and 1 = " + g.getAdjacencyList().addEdge(0, 1));

		System.out.println("remove edge between 0 and 400 = " + g.getAdjacencyList().removeEdge(0, 400));
		System.out.println("add edge between 0 and 400 = " + g.getAdjacencyList().addEdge(0, 400));
		System.out.println("remove edge between 0 and 400 = " + g.getAdjacencyList().removeEdge(0, 400));

	}

	public static void loadCapGraph(Graph<Integer> g) throws Exception {

		loadEdges(g, "data/facebook", FILE_NAME_COMBINED_EDGES);

	}

	private static void loadEdges(Graph<Integer> g, String filePath, String fileName) throws FileNotFoundException {

		Scanner sc = new Scanner(new File(filePath + "/" + fileName));

		while (sc.hasNextInt()) {
			int v1 = sc.nextInt();
			int v2 = sc.nextInt();
			g.addEdge(v1, v2);
		}
		sc.close();
	}

}
