package spelling;

import static org.junit.Assert.assertEquals;

import java.util.List;

public class MyTest {
	
	public static void main(String args[]){
		AutoCompleteDictionaryTrie smallDict = new AutoCompleteDictionaryTrie();

		smallDict.addWord("Hello");
		smallDict.addWord("HElLo");
		smallDict.addWord("help");
		smallDict.addWord("he");
		smallDict.addWord("hem");
		smallDict.addWord("hot");
		smallDict.addWord("hey");
		smallDict.addWord("a");
		smallDict.addWord("subsequent");
		
		smallDict.printTree();
		
		List<String> completions;
		completions = smallDict.predictCompletions("", 0);		
		
		System.out.println(completions.toString());
		
		completions = smallDict.predictCompletions("",  4);
		
		System.out.println(completions.toString());
	}

}
