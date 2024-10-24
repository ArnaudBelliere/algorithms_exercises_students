package sorting;


import java.util.ArrayList;

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
        Interval[] sorted = sort(intervals);
        // intervals are now sorted, now can check the number of overlap between intervals 1 and others
        int min = sorted[0].min;
        int max = sorted[0].max;
        ArrayList<Interval> res = new ArrayList<>();
        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i].min > max) {
                res.add(new Interval(min, max));
                min = sorted[i].min;
                max = sorted[i].max;
            } else {
                max = Math.max(max, sorted[i].max);
            }
        }
        res.add(new Interval(min, max));
        return res.toArray(new Interval[0]);

    }
    public static Interval[] sort(Interval[] intervals){
        // bubble sort
        for(int i =0;i< intervals.length -1;i++){
            for (int j =0; j < intervals.length -1; j++){
                if(intervals[j].compareTo(intervals[j+1]) ==1){
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
