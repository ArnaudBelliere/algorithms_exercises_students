package graphs;

import java.util.*;

/**
 * Author: Alexis Englebert
 * Context: You are operating a power plant in the new city of Louvain-La-Neuve,
 * but lack plans for the city's electrical network.
 * Your goal is to minimize the cost of electrical wires ensuring the city is connected with just one wire.
 *
 * The method 'minimumSpanningTreeCost' is designed to find the minimum cost to connect all cities in a given electrical network.
 * The network is represented as a graph where the nodes are the buildings, the edges are the possible connections
 * and their associated cost.
 *
 * Example:
 * Given a network with three buildings (nodes) and the cost of wires (edges) between them:
 * 0 - 1 (5), 1 - 2 (10), 0 - 2 (20)
 * The minimum cost to connect all the buildings is 15 (5 + 10).
 *
 * Note: The method assumes that the input graph is connected and the input is valid.
 */
public class Electricity {

    /**
     * @param n       The number of buildings (nodes) in the network.
     * @param edges   A 2D array where each row represents an edge in the form [building1, building2, cost].
     *                The edges are undirected so (building2, building1, cost) is equivalent to (building1, building2, cost).
     * @return       The minimum cost to connect all cities.
     */
    public static int minimumSpanningCost(int n, int [][] edges) {
        //TODO

        // the method to find the min spanning tree is too first sort the edges by their cost ( ascending ), then take all edges in order.
        // when the edge taken forms a cycle ( i.e. the two nodes already are in the previously selected edges ), we skip it.
        // comp should be O(n^2) ( while e : edges { if e in selectedEdges { .. } }
        int cost = 0;
        ArrayList<int[]> sortedList = new ArrayList<>(Arrays.asList(edges));
        sortedList.sort(Comparator.comparingInt(e -> e[2]));
        ArrayList<Integer> visited = new ArrayList<>();

        for (int i = 0 ; i < sortedList.size() ; i ++){
            int curr = 2;
            int e1 = sortedList.get(i)[0];
            int e2 = sortedList.get(i)[1];
            if ( !visited.contains(e1)){
                visited.add(e1);
                curr --;
            }
            if ( !visited.contains(e2)){
                visited.add(e2);
                curr --;
            }
            if ( curr < 2){
                cost += sortedList.get(i)[2];
                //System.out.println(" e1 : " + e1 + ", e2 : " + e2 + ", cost : " + sortedList.get(i)[2]);
            }
            if ( visited.size() >= n ){
                break;
            }
        }

        return cost;
    }

            /*
        System.out.println("Before Sort : ");
        for (int[] edge : sortedList) {
            System.out.println(Arrays.toString(edge));
        }
        // already sorted ?
        /*sortedList.sort(new Comparator<int[]>() {
            @Override
            public int compare(int[] e1, int[] e2) {
                return Integer.compare(e1[2], e2[2]);
            }
        });
        System.out.println("After Sort : ");
        for (int[] edge : sortedList) {
            System.out.println(Arrays.toString(edge));
        }*/

    /*
            for (int i = 0 ; i < edges.length ; i ++){
            int curr = 2;
            int e1 = edges[i][0];
            int e2 = edges[i][1];
            if ( !visited.contains(e1)){
                visited.add(e1);
                curr --;
            }
            if ( !visited.contains(e2)){
                visited.add(e2);
                curr --;
            }
            if ( curr < 2){
                cost += edges[i][2];
            }
            if ( visited.size() >= n ){
                break;
            }
        }
        */
}
