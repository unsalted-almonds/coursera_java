package document;

/** 
 * A class that represents a text document
 * @author UC San Diego Intermediate Programming MOOC team
 */
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Document_shilin {

	private String text;
	
	/** Create a new document from the given text.
	 * Because this class is abstract, this is used only from subclasses.
	 * @param text The text of the document.
	 */
	protected Document_shilin(String text)
	{
		this.text = text;
	}
	
	/** Returns the tokens that match the regex pattern from the document 
	 * text string.
	 * @param pattern A regular expression string specifying the 
	 *   token pattern desired
	 * @return A List of tokens from the document text that match the regex 
	 *   pattern
	 */
	protected List<String> getTokens(String pattern)
	{
		ArrayList<String> tokens = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile(pattern);
		Matcher m = tokSplitter.matcher(text);
		
		while (m.find()) {
			tokens.add(m.group());
		}
		
		return tokens;
	}
	
	// This is a helper function that returns the number of syllables
	// in a word.  You should write this and use it in your 
	// BasicDocument class.
	// You will probably NOT need to add a countWords or a countSentences method
	// here.  The reason we put countSyllables here because we'll use it again
	// next week when we implement the EfficientDocument class.
	protected int countSyllables(String word) {
		// TODO: Implement this method so that you can call it from the
		// getNumSyllables method in BasicDocument (module 1) and
		// EfficientDocument (module 2).

		int count = 0;
		int index = 0;
		// char[] letters = word.toCharArray();
		boolean isLastVowel = false;
		boolean isThisVowel = false;

		char[] vowels = { 'a', 'e', 'i', 'o', 'u', 'y', 'A', 'E', 'I', 'O', 'U', 'Y' };

		for (char c : word.toCharArray()) {
			// last char
			if (index == (word.length() - 1)) {
				if (isLastVowel)
					count++;
				else {
					isThisVowel = false;
					for (char v : vowels) {
						if (v == c) {
							isThisVowel = true;
							break;
						}
					}				
					if(isThisVowel){
						if (count == 0) {
							count++;
						} else {
							if (c != 'e' && c != 'E')
								count++;
						}
					}
				}
			} else {
				isThisVowel = false;
				for (char v : vowels) {
					if (v == c) {
						isThisVowel = true;
						break;
					}
				}
				if (isLastVowel == true && isThisVowel == false)
					count++;
				isLastVowel = isThisVowel;
			}
			index++;
		}
		return count;
	}
	
	/** A method for testing
	 * 
	 * @param doc The Document object to test
	 * @param syllables The expected number of syllables
	 * @param words The expected number of words
	 * @param sentences The expected number of sentences
	 * @return true if the test case passed.  False otherwise.
	 */
	public static boolean testCase(Document doc, int syllables, int words, int sentences)
	{
		System.out.println("Testing text: ");
		System.out.print(doc.getText() + "\n....");
		boolean passed = true;
		int syllFound = doc.getNumSyllables();
		int wordsFound = doc.getNumWords();
		int sentFound = doc.getNumSentences();
		if (syllFound != syllables) {
			System.out.println("\nIncorrect number of syllables.  Found " + syllFound 
					+ ", expected " + syllables);
			passed = false;
		}
		if (wordsFound != words) {
			System.out.println("\nIncorrect number of words.  Found " + wordsFound 
					+ ", expected " + words);
			passed = false;
		}
		if (sentFound != sentences) {
			System.out.println("\nIncorrect number of sentences.  Found " + sentFound 
					+ ", expected " + sentences);
			passed = false;
		}
		
		if (passed) {
			System.out.println("passed.\n");
		}
		else {
			System.out.println("FAILED.\n");
		}
		return passed;
	}
	
	
	/** Return the number of words in this document */
	public abstract int getNumWords();
	
	/** Return the number of sentences in this document */
	public abstract int getNumSentences();
	
	/** Return the number of syllables in this document */
	public abstract int getNumSyllables();
	
	/** Return the entire text of this document */
	public String getText()
	{
		return this.text;
	}
	
	/** return the Flesch readability score of this document */
	public double getFleschScore()
	{
	    // TODO: Implement this method
		//Ensure there is no round-off error. We accept answers rounded to 1 decimal place.  Double check the formula and make sure you are casting your ints to doubles before you divide so you are getting floating point division.
	    return 206.835 - 1.015 * ((double)this.getNumWords()/(double)this.getNumSentences()) - 84.6 * ((double)this.getNumSyllables()/(double)this.getNumWords());
	
	// before cast
	//118.17500000000001 118.17500000000001 116.14500000000001 121.22000000000003 120.20500000000001 29.515000000000015 

	// after cast
	//    97.025 75.87500000000001 96.11442307692309 57.77000000000004 107.7809523809524 28.132113043478256  
	    
	    
	}
	
	
	
}
