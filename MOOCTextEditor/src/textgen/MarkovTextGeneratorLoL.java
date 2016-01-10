package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		// TODO: Implement this method
		
		if (sourceText == null || sourceText.equals(""))
			return;


		
		// i guess splitting on space is good enough here?
		String[] words = sourceText.split("[\\s]+");
		
		if(words.length <= 1)
			return;
		
		this.starter = words[0];
		String prevWord = this.starter;
		
		for (int i = 1; i < words.length; i++){
			if(!this.isAdded(prevWord))
				addNewNode(prevWord);
			
			appendToNode(prevWord, words[i]);			
			prevWord = words[i];
		}
		
		// biz requirement: add starter to be a next word for the last word in the source text.
		if(!this.isAdded(prevWord))
			addNewNode(prevWord);		
		appendToNode(prevWord, this.starter);
		
	}
	
	private void appendToNode(String nodeToAppend, String val){
		for (ListNode node : this.wordList){
			if(node.getWord().equals(nodeToAppend))
				node.addNextWord(val);
		}
	}
	
	private void addNewNode(String nodeToAdd){
		
		this.wordList.add(new ListNode(nodeToAdd));
		
	}
	
	// check if word has been added 
	private boolean isAdded(String word){
		for (ListNode node : wordList){
			if(node.getWord().equals(word))
				return true;
		}
		return false;
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    // TODO: Implement this method
		
		if (numWords <= 0)
			return "";
		
		String currentWord = this.starter;
		String out = "";
		int cnt = 1;
		out = out + currentWord;

		
		while(cnt < numWords){
			
			for (ListNode node : this.wordList){
				if (node.getWord().equals(currentWord)){
					currentWord = node.getRandomNextWord(this.rnGenerator);
					out = out + " " + currentWord;
					break;
				}
			}
			
			cnt++;
		}
		
		return out;
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// TODO: Implement this method.
		wordList = new LinkedList<ListNode>();
		starter = "";
		train(sourceText);
	}
	
	// TODO: Add any private helper methods you need here.
	
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		/*
		gen.train("hi there hi Leo");
		System.out.println(gen.toString());
		*/
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		String res = gen.generateText(20);
		System.out.println(res);
		System.out.println("actual length " + (res.split(" +")).length);
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		// TODO: Implement this method
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
		
		// a pseudorandom, uniformly distributed int value between 0 (inclusive) and the specified value (exclusive)
		// random.nextInt(max - min + 1) + min
	    return this.nextWords.get(generator.nextInt(this.nextWords.size()-1 - 0 + 1) + 0);
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


