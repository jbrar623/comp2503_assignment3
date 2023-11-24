
import java.util.Iterator;
import java.util.Scanner;

/**
 * COMP 2503 Fall 2023 Assignment 3 Avenger Statistics
 * 
 * This program reads a input stream and keeps track of the statistics for avengers
 * mentioned by name, alias, or performer's last name.
 * The program uses a BST
 * for storing the data. Multiple BSTs with alternative orderings are
 * constructed to show the required output.
 * 
 * @author Maryam Elahi
 * @date Fall 2023
 */

public class A3 {

	public String[][] avengerRoster = { { "captainamerica", "rogers", "evans" }, { "ironman", "stark", "downey" },
			{ "blackwidow", "romanoff", "johansson" }, { "hulk", "banner", "ruffalo" },
			{ "blackpanther", "tchalla", "boseman" }, { "thor", "odinson", "hemsworth" },
			{ "hawkeye", "barton", "renner" }, { "warmachine", "rhodes", "cheadle" },
			{ "spiderman", "parker", "holland" }, { "wintersoldier", "barnes", "stan" } };

	private int topN = 4;
	private int totalwordcount = 0;
	private Scanner input = new Scanner(System.in);
	private BST<Avenger> alphabeticalBST = new BST<Avenger>();
	private BST<Avenger> mentionBST = new BST<Avenger>(new AvengerComparatorMentionOrder());
	private BST<Avenger> mostPopularAvengerBST = new BST<Avenger>(new AvengerComparatorFreqDesc());
	private BST<Avenger> mostPopularPerformerBST = new BST<Avenger>(new AvengerPerformerComparatorFreqDesc());
	
	public static void main(String[] args) {
		A3 a3 = new A3();
		a3.run();
	}

	public void run() {
		readInput();
		createdAlternativeOrderBSTs();
		printResults();
	}

	
	private void createdAlternativeOrderBSTs() {
		/* TODO:
//		 *   - delete the following two avengers (if they exist) from the alphabetical tree
//		 *   	- barton
//		 *   	- banner
//		 *   use the tree iterator to do an in-order traversal of the alphabetical tree,
//		 *   and add avengers to the other trees with alternative ordering
//		 */
		
	    // Delete the following two avengers (if they exist) from the alphabetical tree
//	    Avenger toDeleteBarton = new Avenger("hawkeye", "barton", "renner");
//	    Avenger toDeleteBanner = new Avenger("hulk", "banner", "ruffalo");
//	    alphabeticalBST.delete(toDeleteBarton);
//	    alphabeticalBST.delete(toDeleteBanner);
//
//	    // Use the tree iterator to do an in-order traversal of the alphabetical tree
//	    BSTIterator iterator = new BSTIterator(alphabeticalBST);
//
//	    // Add avengers to the other trees with alternative ordering
//	    while (iterator.hasNext()) {
//	        Avenger currentAvenger = iterator.next();
//
//	        // Add to mostPopularPerformerBST
//	        mostPopularPerformerBST.add(currentAvenger);
//
//	        // Add to mostPopularAvengerBST
//	        mostPopularAvengerBST.add(currentAvenger);
//
//	        // Add to mentionBST
//	        mentionBST.add(currentAvenger);
		 	Avenger toDeleteBarton = new Avenger("banner", "barton", 0);
			alphabeticalBST.delete(toDelete); 
			for (Avenger a : alphabeticalBST) {
				mostPopularAvengerBST.add(a); 
				mostPopularPerformerBST.add(a); 
				mentionBST.add(a); 
	    }
	}

	/**
	 * read the input stream and keep track how many times avengers are mentioned by
	 * alias or last name or performer name.
	 */
	private void readInput() {
		/* Create a mention index counter and initialize it to 1
		 * While the scanner object has not reached end of stream, 
		 * 	- read a word. 
		 * 	- clean up the word 
		 * 	- if the word is not empty, add the word count. 
		 * 
		 * 	- Check if the word is either an avenger alias or last name, or performer last name then
		 *		- Create a new avenger object with the corresponding alias and last name and performer last name.
		 *		- check if this avenger has already been added to the alphabetically ordered tree
		 *
		 *			- if yes, increase the corresponding frequency count for the object already in the tree.
		 *			- if no, add the newly created avenger to the alphabetically ordered BST, 
		 *				- remember to set the frequency and the mention index.
		 * You need to think carefully about how you are keeping track of the mention order by setting the mention order for each new avenger.
		 */
		int mentionIndexCount = 1;
		while (input.hasNext()) {

			String word = cleanWord(input.next());

			if (word.length() > 0) {
				totalwordcount++;
				
				Avenger newAvengerObject = createAvengerObject(word);
				if (newAvengerObject == null) {
					continue;
				} else {
					int inx = alphabeticalBST.find(newAvengerObject);  //use iterator here  
					if (inx == -1) {
						newAvengerObject.addFrequency(word);
						alphabeticalBST.add(newAvengerObject);
						newAvengerObject.setMentionIndex();
						mentionIndexCount ++;
						
					} else {
						Avenger existingAvenger = alphabeticalBST.(inx);
						existingAvenger.addFrequency(word);
					}
				}
			}
		}
	}
	

	private Avenger createAvengerObject(String word) {
		for (int i = 0; i < avengerRoster.length; i++) {
			if (avengerRoster[i][0].equals(word) || avengerRoster[i][1].equals(word)
					|| avengerRoster[i][2].equals(word)) {
				return new Avenger(avengerRoster[i][0], avengerRoster[i][1], avengerRoster[i][2]);
			}
		}
		return null;
	}
	

	private String cleanWord(String next) {
		// First, if there is an apostrophe, the substring
		// before the apostrophe is used and the rest is ignored.
		// Words are converted to all lowercase.
		// All other punctuation and numbers are skipped.
		String ret;
		int inx = next.indexOf('\'');
		if (inx != -1)
			ret = next.substring(0, inx).toLowerCase().trim().replaceAll("[^a-z]", "");
		else
			ret = next.toLowerCase().trim().replaceAll("[^a-z]", "");
		return ret;
	}
	
	/**
	 * print the results
	 */
	private void printResults() {
//		// Todo: Print the total number of words (this total should not include words that are all digits or punctuation.)
//		System.out.println("Total number of words: " + totalwordcount);
//		// TODO: Print the number of mentioned avengers after deleting "barton" and "banner".
//		//System.out.println("Number of Avengers Mentioned: " + ??);
//		System.out.println();
//
//		System.out.println("All avengers in the order they appeared in the input stream:");
//		// TODO: Print the list of avengers in the order they appeared in the input
//		// Make sure you follow the formatting example in the sample output
//		System.out.println();
//		
//		System.out.println("Top " + topN + " most popular avengers:");
//		// TODO: Print the most popular avengers, see the instructions for tie breaking
//		// Make sure you follow the formatting example in the sample output
//		System.out.println();
//
//		System.out.println("Top " + topN + " most popular performers:");
//		// TODO: Print the most popular performers, see the instructions for tie breaking
//		// Make sure you follow the formatting example in the sample output
//		System.out.println();
//
//		System.out.println("All mentioned avengers in alphabetical order:");
//		// TODO: Print the list of avengers in alphabetical order
//		System.out.println();
//
//		//TODO: Print the actual height and the optimal height for each of the four trees.
//		System.out.println("Height of the mention order tree is : " + ??
//				+ " (Optimal height for this tree is : " + ?? + ")");
//		System.out.println("Height of the alphabetical tree is : " + ??
//				+ " (Optimal height for this tree is : " + ?? + ")");
//		System.out.println("Height of the most frequent tree is : " + ??
//				+ " (Optimal height for this tree is : " + ?? + ")");
//		System.out.println("Height of the most frequent performer tree is : " + ??
//		+ " (Optimal height for this tree is : " + ?? + ")");
		
		// TODO: Print the total number of words (this total should not include words that are all digits or punctuation.)
				System.out.println("Total number of words: " + totalwordcount);
				// TODO: Print the number of mentioned avengers after deleting "barton" and "banner".
				//System.out.println("Number of Avengers Mentioned: " + ??);
				System.out.println();

				//Avengers printed in mention order
				System.out.println("All avengers in the order they appeared in the input stream:");
				Iterator<Avenger> itMention = A3.mentionBST.iterator(); 
				while (itMention.hasNext()) {
					System.out.println(itMention.next()); 
				}
				System.out.println();
				
				//Most popular Avengers printed
				System.out.println("Top " + topN + " most popular avengers:");
				Iterator<Avenger> itPopAvg = A3.mostPopularAvengerBST.iterator(); 
				while (itPopAvg.hasNext()) {
					System.out.println(itPopAvg.next()); 
				}
				System.out.println();

				//Most popular performers printed
				System.out.println("Top " + topN + " most popular performers:");
				Iterator<Avenger> itPopPer = A3.mostPopularPerformerBST.iterator(); 
				while (itPopPer.hasNext()) {
					System.out.println(itPopPer.next()); 
				}
				System.out.println();
				
				//Avengers printed in alphabetical order
				System.out.println("All mentioned avengers in alphabetical order:");
				Iterator<Avenger> itAlph = A3.alphabeticalBST.iterator(); 
				while (itAlph.hasNext()) {
					System.out.println(itAlph.next()); 
				}
				System.out.println();
	}
}
