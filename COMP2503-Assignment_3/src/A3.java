
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
	private int mentionIndex = 0;
	private Scanner input = new Scanner(System.in);
	private BST<Avenger> alphabeticalBST = new BST<Avenger>();
	private BST<Avenger> mentionBST = new BST<Avenger>(new AvengerComparatorMentionOrder());
	private BST<Avenger> mostPopularAvengerBST = new BST<Avenger>(new AvengerComparatorFreqDesc());
	private BST<Avenger> mostPopularPerformerBST = new BST<Avenger>(new AvengerPerformerComparatorFreqDesc());
	
	public static void main(String[] args) {
		A3 a3 = new A3();
		a3.run();
	}

	/**
     * Runs the Avenger Statistics program.
     */
	public void run() {
		readInput();
		createdAlternativeOrderBSTs();
		printResults();
	}

	/**
	 * creates altnerately ordered BST's from the alphabetic tree and 
	 * deletes specified Avengers
	 */
	private void createdAlternativeOrderBSTs() {
		/* TODO:
//		 *   - delete the following two avengers (if they exist) from the alphabetical tree
//		 *   	- barton
//		 *   	- banner
//		 *   use the tree iterator to do an in-order traversal of the alphabetical tree,
//		 *   and add avengers to the other trees with alternative ordering
//		 */
		
	    // Delete the following two avengers (if they exist) from the alphabetical tree
	    Avenger toDeleteBarton = new Avenger("hawkeye", "barton", "renner");
	    Avenger toDeleteBanner = new Avenger("hulk", "banner", "ruffalo");
	    alphabeticalBST.delete(toDeleteBarton);
	    alphabeticalBST.delete(toDeleteBanner);
	    
	    // Use the tree iterator to traverse the alphabetical tree in order
	    Iterator<Avenger> iterator = alphabeticalBST.iterator();

	    // Add avengers to the other trees with alternative ordering
	    while (iterator.hasNext()) {
	        Avenger currentAvenger = iterator.next();

	        // Add to mostPopularPerformerBST
	        mostPopularPerformerBST.add(currentAvenger);

	        // Add to mostPopularAvengerBST
	        mostPopularAvengerBST.add(currentAvenger);

	        // Add to mentionBST
	        mentionBST.add(currentAvenger);
	            
	        }
	}

	/**
	 * reads the input stream and keeps track of how many times Avengers are mentioned by fields: 
	 * alias, last name, or performer name.
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
	            
	          //If avenger is not part of roster, do not create
                if (newAvengerObject == null) {
                    continue;
                }
                else {
	                // Check if the avenger is already in the alphabetically ordered tree
	                Avenger existingAvenger = alphabeticalBST.find(newAvengerObject);
	                
	                if (existingAvenger != null ) {
	                    // If avenger already exists, increase frequency count
	                    existingAvenger.addFrequency(word);
	           
	                } else {
	                    // If avenger is not in the tree, add a new node
	                	newAvengerObject.addFrequency(word);
	                    newAvengerObject.setMentionIndex(mentionIndexCount++);
	                    alphabeticalBST.add(newAvengerObject);
	                }
	            }
	        }
		}
	
	}
		
	
	/**
	 * creates an avenger object based on a provided word 
	 * @param word - a string from input that includes Avenger object fields
	 * @return a new Avenger object 
	 */
	private Avenger createAvengerObject(String word) {
		for (int i = 0; i < avengerRoster.length; i++) {
			if (avengerRoster[i][0].equals(word) || avengerRoster[i][1].equals(word)
					|| avengerRoster[i][2].equals(word)) {
				return new Avenger(avengerRoster[i][0], avengerRoster[i][1], avengerRoster[i][2]);
			}
		}
		return null;
	}
	
	/**
	 * Cleans up a word by converting it to lowercase and removing non-alphabetic characters.
	 * @param next
	 * @return string - word with only alphabetic characters 
	 */
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
	 * uses iterator of a given BST to print the avenger objects it contains
	 * @param list
	 */
	private void printList(BST<Avenger> list) {
		Iterator<Avenger> iterator = list.iterator(); 
		while (iterator.hasNext()) {
			System.out.println(iterator.next()); 
		}
	}
	
	/**
	 * prints the top or first  Avenger object from a given BST 
	 * @param list - takes in an ordered BST containign avenger objects 
	 * @return Avenger objects - to be printed in a string
	 */
	private Avenger printTopN(int topN, BST<Avenger> list) {
		Iterator<Avenger> iterator = list.iterator(); 
		Avenger printString = null;
		
		while (iterator.hasNext() && topN > 0) {
			printString = iterator.next(); 	
//			System.out.println(printString.toString());	
		}	
			return printString;
	}
	
	
	/**
     * Prints the results of the Avenger Statistics program, such as total word count and 
     * number of mentioned avengers. 
     */
	private void printResults() {
		
		
		// Todo: Print the total number of words (this total should not include words that are all digits or punctuation.)
				System.out.println();
				System.out.println("Total number of words: " + totalwordcount);
				// TODO: Print the number of mentioned avengers after deleting "barton" and "banner".
				System.out.println("Number of Avengers Mentioned: " + mentionBST.size());
				System.out.println();

				System.out.println("All avengers in the order they appeared in the input stream:");
				// TODO: Print the list of avengers in the order they appeared in the input
				// Make sure you follow the formatting example in the sample output
				printList(mentionBST);
				System.out.println();
				
				System.out.println("Top " + printTopN(topN, mostPopularAvengerBST) + " most popular avengers:");
				// TODO: Print the most popular avengers, see the instructions for tie breaking
				// Make sure you follow the formatting example in the sample output
				System.out.println();

				System.out.println("Top " + printTopN(topN, mostPopularPerformerBST) + " most popular performers:");
				// TODO: Print the most popular performers, see the instructions for tie breaking
				// Make sure you follow the formatting example in the sample output
				System.out.println();

				System.out.println("All mentioned avengers in alphabetical order:");
				// TODO: Print the list of avengers in alphabetical order
				printList(alphabeticalBST);
				System.out.println();

				//TODO: Print the actual height and the optimal height for each of the four trees.
				System.out.println("Height of the mention order tree is : " + mentionBST.height()
				+ " (Optimal height for this tree is : " + mentionBST.optimalHeight() + ")");
				System.out.println("Height of the alphabetical tree is : " + alphabeticalBST.height()
				+ " (Optimal height for this tree is : " + alphabeticalBST.optimalHeight() + ")");
				System.out.println("Height of the most frequent tree is : " + mostPopularAvengerBST.height()
				+ " (Optimal height for this tree is : " + mostPopularAvengerBST.optimalHeight() + ")");
				System.out.println("Height of the most frequent performer tree is : " + mostPopularPerformerBST.height()
				+ " (Optimal height for this tree is : " + mostPopularPerformerBST.optimalHeight() + ")");
		
	}
}

