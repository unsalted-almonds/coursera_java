package capstone.graph;

import java.util.Collections;
import java.util.List;

/**
 * Interface for running the algorithm 
 * @author Shilin
 *
 */
public class Application {
	
	// feel free to change file destination
	static final String FOLDER = "data/facebook";
	static final String TEST_FILE = "facebook_combined_test.txt";
	
	/**
	 * run algorithm, by default three times unless specified using input 
	 * @param args number of times to run algorithm
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception{
		
		// default number of times to run algorithm
		int n = 3;
		
		if (args.length != 0)
			n = Integer.parseInt(args[0]);
				
		CapstoneGraph<Integer> g = new UndirectedWeightedGraph<Integer>();
		
		GraphLoader.loadCapGraph(g, FOLDER, TEST_FILE);
        System.out.println("Graph Loaded...");
        
		List<List<Integer>> result;
		for (int i = 0; i < n; i++) {
			result = g.detectCommunities();
			System.out.println("number of connected components = " + result.size());
			g.removeEdgeWithMaxBetweeness();
			for (List<Integer> list : result) {
				Collections.sort(list);
				System.out.println("connected component is " + list);
			}			
		}		
		
		
	}

}
