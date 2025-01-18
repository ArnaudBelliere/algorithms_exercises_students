package searching;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * In this exercise, you must compute the skyline defined by a set of buildings.
 * When viewed from far away, the buildings only appear as if they were on a two-dimensionnal line.
 * Hence, they can be defined by three integers: The start of the building (left side), the height
 * of the building and the end of the building (right side).
 * For example, a building defined by (2, 5, 4) would look like
 *
 *   xxx
 *   xxx
 *   xxx
 *   xxx
 *   xxx
 * ________
 *
 * Obviously in practice buildings are not on a line, so they can overlap. If we add a new, smaller,
 * building in front of the previous one, defined by (3, 3, 6), then the view looks like:
 *
 *   xxx
 *   xxx
 *   xyyyy
 *   xyyyy
 *   xyyyy
 * ________
 *
 * The skyline is then define as the line that follows the highest building at any given points.
 * Visually, for the above example, it gives:
 *
 *   sss
 *      
 *      ss
 *        
 *        
 * ________
 *
 * Input:
 * int[][] buildings = {{2, 5, 4}, {3, 3, 6}};
 * Output:
 * {{2,5},{5,3},{7,0}};
 *
 *
 * We ask you to compute, given a set of building, their skyline.
 */
public class Skyline {


    /**
     *   The buildings are defined with triplets (left, height, right).
     *         int[][] buildings = {{1, 11, 5}, {2, 6, 7}, {3, 13, 9}, {12, 7, 16}, {14, 3, 25}, {19, 18, 22}, {23, 13, 29}, {24, 4, 28}};
     *
     *         {{1,11},{3,13},{10,0},{12,7},{17,3},{19,18},{23,13},{30,0}};
     *
     * @param buildings
     * @return  the skyline in the form of a list of "key points [x, height]".
     *          A key point is the left endpoint of a horizontal line segment.
     *          The key points are sorted by their x-coordinate in the list.
     */
    public static List<int[]> getSkyline(int[][] buildings) {
        List<int[]> ret = new ArrayList<>();
        int maxsize = 0;
        int len = 0;
        for(int i =0; i< buildings.length;i++){
            if(buildings[i][1]>maxsize){
                maxsize = buildings[i][1];
            }
            if(buildings[i][2]> len){
                len = buildings[i][2];
            }
        }
        //System.out.println("Crash 1");
        int [][] arch = new int[len][maxsize];
        for(int i =0;i< buildings.length;i++){
            arch = fill(arch,buildings[i]);
        }
        //System.out.println("Crash 2");
        int maxHeight[] = new int[len];
        for(int i =0;i<len;i++){
            maxHeight[i] =0;
            for(int j = 0; j< maxsize;j++){
                if(arch[i][j]> maxHeight[i]){
                    maxHeight[i] = arch[i][j];
                }
            }
        }
        //System.out.println("Crash 3");
        for(int i =0; i< len;i++){
            int cur[] = new int[2];
            cur[0] = i;
            cur[1] = maxHeight[i];
            ret.add(cur);
        }
        int[] cur = new int[2];
        cur[0] = len;
        cur[1] = 0;
        ret.add(cur);
        //System.out.println("Crash 4");
        int firstBuild = 0;
        for(int i =0;i<len;i++){
            if(ret.get(i)[1]!=0){
                firstBuild = i;
                break;
            }
        }
        //System.out.println("Apres");
        for(int i =0; i< firstBuild;i++){
            ret.remove(i);
            i--;
            firstBuild --;
            len --;
        }
        for(int i =0;i<len-1;i++){
            if(ret.get(i)[1]== ret.get(i + 1)[1]){
                ret.remove(i+1);
                i--;
                len --;
            }
        }
        /*for(int i =0 ; i< ret.size();i++){
            System.out.println(ret.get(i)[0] + " " + ret.get(i)[1]);
        }*/
        return ret;
    }

    public static int[][] fill(int[][] arch, int[] build){
        int start = build[0];
        int height = build[1];
        int end = build[2];
        //System.out.println("Avant crash");
        for(int i = start;i< end;i++){
            for(int j = 0 ; j < height; j++){
                arch[i][j] = height;
            }
        }
        //System.out.println("Apres crash");
        return arch;
    }
}
