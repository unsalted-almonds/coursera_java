package capstone.graph;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.*;


/**
 * Test behaviors of running algorithm multiple times
 * @author Shilin
 *
 */
public class CapstoneGraphTest {
	
	CapstoneGraph<Integer> graphUndertest;
	final String FOLDER = "data/facebook";
	final String TEST_FILE = "facebook_combined_test.txt";
	// all vertices in the testing file
	final Set<Integer> allVertices = new HashSet<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15));
	// three communities that can be detected by observation
	final Set<Integer> communityOne = new HashSet<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6));
	final Set<Integer> communityTwo = new HashSet<Integer>(Arrays.asList(7, 8, 9, 10));
	final Set<Integer> communityThree = new HashSet<Integer>(Arrays.asList(11, 12, 13, 14, 15));
	
    @Before
    public void setup() throws Exception {
    	graphUndertest = new UndirectedWeightedGraph<Integer>();
    	GraphLoader.loadCapGraph(graphUndertest, FOLDER, TEST_FILE);
        System.out.println("Graph Loaded...");
    }
    
    
    @Test
    public void testRunOnce() {
    	List<List<Integer>> result = graphUndertest.detectCommunities();
    	// there's only one community after first run
    	assertEquals(1, result.size());
   	
    	allVertices.removeAll(new HashSet<Integer>(result.get(0)));
    	// this one community has all vertices of the graph under test
    	assertEquals(0, allVertices.size());    	
    }
    
    @Test
	public void testRunThreeTimes() {
		List<List<Integer>> result = null;
		for (int i = 0; i < 3; i++) {
			result = graphUndertest.detectCommunities();
			System.out.println("number of connected components = " + result.size());
			graphUndertest.removeEdgeWithMaxBetweeness();
			for (List<Integer> list : result) {
				Collections.sort(list);
				System.out.println("connected component is " + list);
			}			
		}
		
		// there're three communities after the third run
    	assertEquals(3, result.size());
    	    	
    	communityOne.removeAll(new HashSet<Integer>(result.get(0)));   	
    	// match community one
    	assertEquals(0, communityOne.size());   
    	
    	communityTwo.removeAll(new HashSet<Integer>(result.get(1)));   	
    	// match community two
    	assertEquals(0, communityTwo.size()); 
    	
    	communityThree.removeAll(new HashSet<Integer>(result.get(2)));   	
    	// match community three
    	assertEquals(0, communityThree.size()); 

    }
    
    @Test
    public void testRunTwentyOneTimes(){
		List<List<Integer>> result = null;
		for (int i = 0; i < 21; i++) {
			result = graphUndertest.detectCommunities();
			System.out.println("number of connected components = " + result.size());
			graphUndertest.removeEdgeWithMaxBetweeness();
			for (List<Integer> list : result) {
				Collections.sort(list);
				System.out.println("connected component is " + list);
			}			
		}		
		// there're 16 communities after the twenty one run
    	assertEquals(16, result.size());   	    	
    }
    
}
