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
	static final String FILE_NAME_COMBINED_EDGES = "facebook_combined_0.txt";

	public static void main(String args[]) throws Exception {
		CapstoneGraph<Integer> g = new UndirectedWeightedGraph<Integer>();

		loadCapGraph(g, "data/facebook", FILE_NAME_COMBINED_EDGES);

	}

	public static void loadCapGraph(CapstoneGraph<Integer> g, String folder, String fileName) throws Exception {

		loadEdges(g, folder, fileName);

	}

	private static void loadEdges(CapstoneGraph<Integer> g, String filePath, String fileName) throws FileNotFoundException {

		Scanner sc = new Scanner(new File(filePath + "/" + fileName));

		while (sc.hasNextInt()) {
			int v1 = sc.nextInt();
			int v2 = sc.nextInt();
			g.addEdge(v1, v2);
		}
		sc.close();
	}

}
