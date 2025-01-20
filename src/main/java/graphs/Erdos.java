package graphs;

import java.util.*;

/**
 * The erdos number is a "collaborative distance" metric to Paul Erdos (a prolific mathematician)
 * based on co-authorship of mathematical articles.
 * It is computed as follows:
 * - Erdos has, by definition an erdos-number of 0.
 * - For each other author, we look at all his/her co-authors in each article.
 *   If n is the minimum erdos-number from all his co-authors, then this author has an erdos-number of n+1.
 *
 * For example:
 *
 * Given this set of co-authors relations:
 *
 *          { "Paul Erdös", "Edsger W. Dijkstra" }
 *          { "Edsger W. Dijkstra", "Alan M. Turing" }
 *          { "Edsger W. Dijkstra", "Donald Knuth" }
 *          { "Donald Knuth", "Stephen Cook", "Judea Pearl" }
 *
 *  The erdos number of Paul Erdos is 0, of Edsger W. Dijkstra is 1, of Alan M. Turing is 2, of Donald Knuth is 2, of Stephen Cook is 3.
 *
 *  Debug your code on the small examples in the test suite.
 */
public class Erdos {

	public static final String erdos = "Paul Erdös";



	/**
	 * Constructs an Erdos object and computes the Erdős numbers for each author.
	 *
	 * The constructor should run in O(n*m^2) where n is the number of co-author relations,
	 * and m the maximum number of co-authors in one article.
	 *
	 * @param articlesAuthors An ArrayList of String arrays, where each array represents the list of authors of a single article.
	 */
	HashMap<String, Integer> distMap = new HashMap<>();

	public Erdos(ArrayList<String []> articlesAuthors) {
		// TODO
		HashMap<String, ArrayList<String>> adjMap = new HashMap<>(); // map id , adjacent nodes
		for (String[] str : articlesAuthors){
			for (int i = 0 ; i < str.length ; i ++){
				for (int j = i+1 ; j < str.length ; j++){
					if (!adjMap.containsKey(str[i])){
						adjMap.put(str[i], new ArrayList<>());
					}
					if (!adjMap.containsKey(str[j])){
						adjMap.put(str[j], new ArrayList<>());
					}
					adjMap.get(str[i]).add(str[j]);
					adjMap.get(str[j]).add(str[i]);
				}
			}
		}
		//System.out.println("Here ?");
		PriorityQueue<String> queue = new PriorityQueue<>();
		queue.add(erdos);
		distMap.put(erdos,0);
		//System.out.println("Working");
		while (!queue.isEmpty()){
			//System.out.println("In");
			String u = queue.poll();
			//System.out.println("In again");
			int cost = distMap.get(u) +1;
			//System.out.println("In still");
			for (String v : adjMap.get(u)){
				//System.out.println("Inside");
				if (distMap.containsKey(v)){
					//System.out.println("The crash");
					if (distMap.get(v) > cost){
						distMap.remove(v);
						distMap.put(v,cost);
					}
				}
				else{
					//System.out.println("Or");
					distMap.put(v,cost);
					queue.add(v);
				}
			}
		}
	}

	/**
	 * Returns the Erdős number of a given author.
	 * This method is expected to run in O(1).
	 * @param author The name of the author whose Erdős number is to be found.
	 * @return The Erdős number of the specified author. If the author is not in the network, returns -1.
	 */
	public int findErdosNumber(String author) {
		// TODO
		return distMap.get(author);
	}

}