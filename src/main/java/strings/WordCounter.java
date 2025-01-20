package strings;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Implement the class WordCounter that counts the number of occurrences
 * of each word in a given piece of text.
 * Feel free to use existing java classes.
 */
public class WordCounter implements Iterable<String> {

    private Map<String, Integer> wordCounter;

    public WordCounter() {
        wordCounter = new TreeMap<>();
    }

    /**
     * Add the word so that the counter of the word is increased by 1
     */
    public void addWord(String word) {
        if ( wordCounter.containsKey(word)){
            wordCounter.put(word, wordCounter.get(word)+1);
        }
        else{
            wordCounter.put(word, 1);
        }
    }

    /**
     * Return the number of times the word has been added so far
     */
    public int getCount(String word) {
        if (wordCounter.containsKey(word)){
            return wordCounter.get(word);
        }
        return 0;
        //return -2;
    }

    // iterate over the words in ascending lexicographical order
    @Override
    public Iterator<String> iterator() {
         return wordCounter.keySet().iterator();
    }
}
