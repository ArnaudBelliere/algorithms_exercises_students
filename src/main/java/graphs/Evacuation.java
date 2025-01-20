package graphs;

import java.util.*;

/**
 * In case of an emergency at the Olympic games,
 * it’s crucial to ensure that all spectators can be evacuated efficiently.
 *
 * Each venue is represented as a node in an undirected graph.
 * The pathways between them are represented as weighted edges.
 * A set of exits are also represented as nodes in the graph.
 *
 * The goal is to determine the shortest path from each venue to the nearest exit.
 *
 * To enable people to find the shortest path from any venue to the nearest exit,
 * we are going to place one directional arrow at each venue indicating the
 * next venue to follow on the shorted to reach the nearest exit from that node.
 *
 * Hint: You can use Dijkstra's algorithm with minor adaptations (starting from the exits) to solve this problem.
 *
 * The expected time-complexity if O((V + E) log V) where V is the number nodes and E is the number of edges.
 *
 * Look at the test cases for more details on the input and output format as well as some examples.
 */
public class Evacuation {

    /**
     * @param graph the graph representing the venues (and exits), pathways
     *        The graph is represented as an adjacency matrix,
     *              where graph[i][j] is the weight of the edge between i and j.
     *              If there is no edge between i and j, graph[i][j] = 0.
     * @param exits the nodes of the graph representing the exits
     * @return an array of integers, where the i-th element is the index of the next venue to visit
     * to reach the nearest exit from the i-th venue. If the i-th venue is an exit, the value is -1.
     */
    public static int[] findShortestPaths(int[][] graph, int[] exits) {
        System.out.println();
        System.out.println("Graph : " + Arrays.deepToString(graph));
        System.out.println("exits : " + Arrays.toString(exits));
        HashMap<Integer, ArrayList<Integer>> adjMap = new HashMap<>();
        int n = graph.length;
        for (int i = 0 ; i < n ; i ++){
            ArrayList<Integer> edges = new ArrayList<>();
            for (int j = 0 ; j < n ; j ++){
                if(graph[i][j]!=0){
                    edges.add(j);
                }
            }
            adjMap.put(i,edges);
        }
        HashMap<Integer, Integer> distMap = new HashMap<>(); // map node , next node
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        int[] distance = new int[n];
        Arrays.fill(distance,Integer.MAX_VALUE);
        for (int i : exits){
            queue.add(i);
            distMap.put(i,-1);
            distance[i]=0;
        }
        while(!queue.isEmpty()){
            int u = queue.poll();
            //System.out.println("u : "+ u);
            //System.out.println("adj map de "+u + " : " + adjMap.get(u));
            for (int i : adjMap.get(u)){
                //System.out.println("curr u : " + u + ", Neigh i : " + i);
                int newdist = distance[u]+graph[i][u];
                if (distance[i] > newdist){
                    distance[i] = newdist;
                    //System.out.println("neighbor : "  + i);
                    //System.out.println("u,v : "+u + " , " + i);
                    distMap.put(i,u); // add i and the distance / the next node u
                    queue.add(i);
                }
            }
        }
        //System.out.println("Got out");
        //System.out.println(distMap.values());
        System.out.println(distMap.entrySet());
        int[] ret = new int[distMap.size()];
        int j = 0;
        for (int i : distMap.values()){
            ret[j] = i;
            j++;
        }
        return ret;
    }

    public static void main(String[] args) {

    }
}
