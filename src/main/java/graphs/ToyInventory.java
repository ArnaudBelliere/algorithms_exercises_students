package graphs;


import java.util.*;

/**
 * Santa’s workshop is buzzing with activity, but there’s trouble in the toy inventory department! The elves in charge
 * of managing the stock have lost their counts for the total inventory and the crucial list linking each toy to its
 * category.
 *
 * Santa is determined to have an accurate count of his toys, so he’s asked you to help recreate the inventory based on
 * what the elves can provide you.
 *
 * Here’s what the elves remember:
 *     1. The older elves provide you a list of pairs of toys that are in the same category.
 *     2. Each warehouse managers provides a list of the toys they have in stock, along with the number of each toy.
 *
 * Now, Santa has a list of specific toys he wants to check, and for each, you must determine the total number of toys
 * in its category. Can you help the elves restore the inventory and answer Santa’s requests?
 *
 * Input:
 *     * A list of pairs of strings, where each pair (x, y) indicates that toys x and y belong to the same category.
 *     * A list of toys in the warehouses
 *     * A list of the count for each toy in the warehouses
 *     * A list of toys Santa wants to query.
 *
 * Output:
 *     * A list of integers where the i-th value represents the total count of toys in the category of the i-th queried
 *       toy. If a toy belongs to no category or is not in stock, the count should be 0.
 *
 * For example:
 *     * Input: relations = [[piano, flute], [flute, xylophone], [car, helicopter]],
 *              occurrences_name = [piano, flute, piano, xylophone, car, helicopter],
 *              occurrences_count = [3, 2, 1, 5, 2, 3],
 *              requests = [piano, flute, helicopter, teddybear]
 *     * Output: [11, 11, 5, 0] -> total_count({piano, flute, xylophone}) = 3 + 2 + 1 + 5 = 11;
 *                                 total_count({car, helicopter}) = 2 + 3 = 5;
 *                                 total_count({teddy bear}) = 0;
 *
 * Expected Time-Complexity: O(N * log(N)) (N being the number of relations).
 *
 * Hint:
 *     * The problem involves grouping objects that are in the same family and summing their counts efficiently.
 *       A Union-Find data structure allows to identify and merge such groups of object efficiently.
 */
public class ToyInventory {

    /**
     * Computes for each requested toy the total number of toys stored in the warehouses that belong to the same
     * category.
     *
     * @param relations A list of pairs of strings, where each pair (x, y) indicates that toys x and y belong to the
     *                  same category.
     * @param occurrencesName A list of toys in the warehouses
     * @param occurrencesCount A list of the count for each toy in the warehouses
     * @param requests A list of toys Santa wants to query.
     *
     * @return A list of integers where the i-th value represents the total count of toys in the category of the i-th
     *         requested toy. If a toy belongs to no category or is not in stock, the count should be 0.
     */
    public static int[] answerRequests(String[][] relations, String[] occurrencesName, int[] occurrencesCount, String[] requests) {
        // TODO
        //System.out.println(Arrays.toString(occurrencesName));
        //System.out.println(Arrays.toString(occurrencesCount));
        HashMap<String, Integer> counter = new HashMap<>();
        int m = occurrencesName.length;
        for (int i = 0 ; i < m; i++){
            String str = occurrencesName[i];
            if(counter.containsKey(str)){
                int count = occurrencesCount[i] +counter.get(str);
                counter.remove(str);
                counter.put(str,count);
            }
            else{
                counter.put(str,occurrencesCount[i]);
            }
        }
        //String[] entry = counter.keySet().toArray(new String[0]);
        //System.out.println("counter map : " + counter);
        int n = counter.size();
        HashMap<String, Integer> indexing = new HashMap<>();
        HashMap<Integer, String> indexToName = new HashMap<>();
        int a = 0;
        for (int i = 0 ; i < m ; i++){
            String str = occurrencesName[i];
            if (indexing.containsKey(str)){
                a += 1;
            }
            else{
                indexing.put(occurrencesName[i],i-a);
                indexToName.put(i-a,occurrencesName[i]);
            }
        }
        //System.out.println("index map : " + indexing);
        int[] array = new int[n];
        for (int i = 0 ; i < n ; i ++){
            array[i] = counter.get(indexToName.get(i)); // counter array to initialize uf
        }
        //System.out.println("counter array before relations : " + Arrays.toString(array));
        UnionFind uf = new UnionFind(n,array);
        for(String[] relation: relations){
            int u = uf.find(indexing.get(relation[0]));
            int v = uf.find(indexing.get(relation[1]));
            if ( u!= v ){
                uf.union(u,v);
            }
        }
        int[] count = uf.getCount(); // new counter array after the relations
        //System.out.println("counter array after relations : " + Arrays.toString(count));
        // for i in requests, if i is in index blabla then find parent and return parent, else 0
        int k = requests.length;
        int[] ret = new int[k];
        for(int i = 0 ; i < k ; i ++){
            String str = requests[i];
            if(!indexing.containsKey(str)){
                ret[i]= 0;
            }
            else{
                int u = uf.find(indexing.get(str)); // mon index
                ret[i] = count[u];
            }
        }
        //System.out.println("requests : " + Arrays.toString(requests));
        //System.out.println("return : " + Arrays.toString(ret));
        return ret;
    }

    private static class UnionFind {
        int[] parent;
        int[] count;

        UnionFind(int n, int[] array){
            parent = new int[n];
            count = new int[n];

            for (int i = 0 ; i < n ; i ++){
                parent[i] = i;
                count[i] = array[i];
            }
        }

        int find(int a){
            return parent[a] == a ? a : find(parent[a]);
        }

        void union(int a, int b){
            a = find(a);
            b = find(b);

            if( count[b] > count[a]){
                int temp = a;
                a = b;
                b = temp;
            }

            parent[b] = a;
            count[a] += count[b];
        }
        int[] getCount(){
            return count;
        }
    }
}
