package graphs;

import java.util.*;

/**
 * Santa’s sleigh relies on a magical communication network of relay stations and bidirectional magic pathways. This
 * network ensures seamless coordination between the North Pole and Santa's team during the Christmas gift deliveries.
 * However, the network is fragile: certain pathways are critical, and their removal could disrupt the entire system by
 * splitting the network into disconnected parts.
 *
 * Santa needs your help to identify all these critical pathways so that the elves can reinforce them before the big
 * night.
 *
 * Given the network of relay stations and pathways, determine all the critical pathways whose removal would increase
 * the number of connected components in the network. At the start, the network is composed of a single connected
 * component.
 *
 * Input:
 *     * A graph represented as an adjacency list. The adjacency list is stored as an array of HashSet where each set
 *       contains the ids of the nodes that are adjacent to the node.
 *
 * Output:
 *     * A list of pairs (u,v) representing the critical pathways in the network. Since the graph is undirected, each
 *       edge appears twice in the adjacency list. However, you only need to return each edge once.
 *
 * For example:
 *     * Input: adj = [
 *                        (1, 2),
 *                        (0, 2, 3),
 *                        (0, 1, 5),
 *                        (1, 4),
 *                        (3),
 *                        (2)
 *                    ]
 *     * Output: [(2, 5), (1, 3), (3, 4)]
 *     * Explanation:
 *
 *         adj represents the graph:
 *
 *                   2 ----- 5
 *                 /  \
 *               /     \
 *              0 ----- 1 ----- 3 ----- 4
 *
 *         In this graph if the edge (2, 5) is removed then some nodes (5) are not connected to the remaining of the
 *         graph. It is also the same for the edges (1, 3) and (3, 4).
 *
 * Expected Time-Complexity: O(N^3) (N being the number of nodes)
 *
 * Hint:
 *     * It would be inefficient to take into account all the edges in the graph. Since a spanning tree must cover all
 *       the nodes in a graph, every critical edge in the original graph will be part of any spanning tree. If we only
 *       consider these edges, deleting them one by one from the original graph could make it possible to determine
 *       which ones are critical.
 */
public class CriticalPathways {


    /**
     * Determines all the critical pathways whose removal would increase the number of components in the network.
     *
     * @param adj A graph represented as an adjacency list. The adjacency list is stored as an array of HashSet where
     *            each set contains the ids of the nodes that are adjacent to the node.
     *
     * @return A list of pairs (u,v) representing the critical pathways in the network. Since the graph is undirected,
     *         each edge appears twice in the adjacency list. However, you only need to return each edge once.
     */
    public static int[][] findCriticalPathways(HashSet<Integer>[] adj) {
        // TODO
        //System.out.println(Arrays.toString(adj));
        System.out.println(computeMST(adj));
        ArrayList<int[]> edges = computeMST(adj);
        for (int[] e : edges){
            System.out.println(Arrays.toString(e));
        }
        // the mst is computed so only edges in the MST can be critical
        // need to check for each edge if the graph becomes unconnected
        ArrayList<int[]> critical = new ArrayList<>();
        HashMap<int[], Integer> visited = new HashMap<>();
        for (int[] e : edges){
            if ( !visited.containsKey(e)){
                edges.remove(e);
                if (isConnexe(edges)){
                    critical.add(e);
                }
                edges.add(e);
                visited.put(e,1);
            }
        }
        return null;
    }

    public static boolean isConnexe(ArrayList<int[]> edges){

    }

    public static ArrayList<int[]> computeMST(HashSet<Integer>[] adj){
        //System.out.println(Arrays.toString(adj));
        UnionFind uf = new UnionFind(adj.length);
        ArrayList<int[]> edges = new ArrayList<>();
        for (int i = 0; i < adj.length; i ++){
            //System.out.println(i);
            for (int j : adj[i]){
                //System.out.println("entre");
                int u = uf.find(i);
                int v = uf.find(j);
                if ( u != v){
                    uf.union(u,v);
                    edges.add(new int[]{i,j});
                }
            }
        }
        return edges;
    }


    private static class UnionFind {
        int[] parent;
        int[] size;

        UnionFind(int n){
            parent = new int[n];
            size = new int[n];

            for (int i = 0 ; i < n ; i ++){
                parent[i] = i;
                size[i] = 1;
            }
        }

        int find(int a){
            return parent[a] == a ? a : find(parent[a]);
        }

        void union(int a, int b){
            a = find(a);
            b = find(b);

            if ( size[b] > size[a]){
                int temp = a;
                a = b;
                b = temp;
            }

            parent[b] = a;
            size[a] += size[b];
            return;
        }
    }
}
