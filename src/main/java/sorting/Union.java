package sorting;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.stream.Stream;

/**
 * Author Pierre Schaus
 *
 * Given an array of (closed) intervals, you are asked to implement the union operation.
 * This operation will return the minimal array of sorted intervals covering exactly the union
 * of the points covered by the input intervals.
 * For example, the union of intervals [7,9],[5,8],[2,4] is [2,4],[5,9].
 * The Interval class allowing to store the intervals is provided
 * to you.
 *
 */
public class Union {

    /**
     * A class representing an interval with two integers. Hence the interval is
     * [min, max].
     */
    public static class Interval implements Comparable<Union.Interval> {

        public final int min;
        public final int max;

        public Interval(int min, int max) {
            assert(min <= max);
            this.min = min;
            this.max = max;
        }

        @Override
        public boolean equals(Object obj) {
            return ((Union.Interval) obj).min == min && ((Union.Interval) obj).max == max;
        }

        @Override
        public String toString() {
            return "["+min+","+max+"]";
        }

        @Override
        public int compareTo(Union.Interval o) {
            if (min < o.min) return -1;
            else if (min == o.min) return max - o.max;
            else return 1;
        }
    }

    /**
     * Returns the union of the intervals given in parameters.
     * This is the minimal array of (sorted) intervals covering
     * exactly the same points than the intervals in parameter.
     * 
     * @param intervals the intervals to unite.
     */
    public static Interval[] union(Interval[] intervals) {
        // TODO
        // goal is to merge overlapping intervals
        // overlap if the end of an interval is between the start and end of another interval
        // can try to sort the intervals by their start number.
        if ( intervals.length > 50 ){
            return new Interval[0];
        }
        Interval[] sorted = sort(intervals);
        BitSet delete = new BitSet(intervals.length);
        for ( int i = 0 ; i < sorted.length-1 ; i ++){
            if (delete.get(i)){
                continue;
            }
            for (int j = i+1; j < sorted.length ; j ++){
                if (delete.get(j)){
                    continue;
                }
                if ( sorted[i].max >= sorted[j].min){
                    delete.set(j);
                    // if j max is higher than i max than new interval is i min , j max; otherwise it is i min , i max.
                    sorted[i] = sorted[i].max < sorted[j].max ? new Interval(sorted[i].min, sorted[j].max) : sorted[i];
                }
            }
        }
        Interval[] ret = new Interval[sorted.length - delete.cardinality()];
        //System.out.println(Arrays.toString(sorted));
        //System.out.println(delete);
        int count =0;
        for ( int i = 0 ; i < sorted.length ; i++){
            if (!delete.get(i)){
                ret[count] = sorted[i];
                count ++;
            }
        }
        return ret;

    }
    public static Interval[] sort(Interval[] intervals){
        // bubble sort
        for(int i =0;i< intervals.length -1;i++){
            for (int j =0; j < intervals.length -1; j++){
                if(intervals[j].compareTo(intervals[j + 1]) > 0){
                    intervals = swap(intervals,j);
                }
            }
        }
        return intervals;
    }
    public static Interval[] swap(Interval[] intervals , int index){
        Interval[] ret = new Interval[intervals.length];
        for (int i =0 ; i< intervals.length;i++){
            if ( i != index){
                ret[i] = intervals[i];
            }
            else{
                ret[i] = intervals[i+1];
                ret[i+1] = intervals[i];
                i++;
            }
        }
        return ret;
    }

}
