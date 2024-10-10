package sorting;


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
        int nb = intervals.length;
        // overlap if the end of an interval is between the start and end of another interval
        // can try to sort the intervals by their start number.
        /*System.out.println("Intervals : ");
        for (int i =0 ; i< intervals.length;i++){
            System.out.println(intervals[i].toString());
        }*/
        Interval[] sorted = sort(intervals);
        /*System.out.println("Sorted Intervals : ");
        for (int i =0 ; i< sorted.length;i++){
            System.out.println(sorted[i].toString());
        }*/
        // intervals are now sorted, now can check the number of overlap between intervals 1 and others
        int maxi = sorted[0].max;
        for(int i=0;i<nb-1;i++){
            int j = i +1;
            int count = 0; // count of overlap
            while(j< nb && maxi >= sorted[j].min){
                count ++;
                j++;
            }
            if (count > 0){
                Interval[] iter = new Interval[nb-count];
                // copy les intervals avant i , puis merge les intervals i à i+count , puis recopy la suite
                for(int cpy = 0;cpy<i;cpy++){
                    iter[cpy] = sorted[cpy];
                }
                iter[i] = new Interval(sorted[i].min,sorted[i+count].max);
                maxi = sorted[i+count].max; // update the max
                for(int cpy = i+1; cpy < nb-count ; cpy++){
                    iter[cpy] = sorted[cpy+count];
                }
                nb -= count;
                i -=1;
                sorted = iter;
            }
            else if ( count == 0 && i<nb-2){
                maxi = sorted[i+1].max;
            }
        }

        return sorted;

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
