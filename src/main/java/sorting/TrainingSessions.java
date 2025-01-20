package sorting;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

/**
 * The Olympic Games organizers need to allocate facilities for the athletes' training sessions.
 * Each team has a schedule of training sessions with a start and end time
 * 
 * To organize the athletes' training smoothly, the organizing committee must know
 * the minimum number of facilities they need so that the teams can train without overlap.
 * Each team has given the organizers their training slots,
 * represented by two integers timestamps: the start (included) and end time (not included!) of their session,
 * Given the training sessions' time, write the `minFacilitiesRequired` function,
 * which returns the minimum number of required facilities.
 *
 * Example Input with its visual representation:
 *
 * int[][] sessions = {
 *     {12, 20},//   --------
 *     {14, 25},//     -----------
 *     {19, 22},//          ---
 *     {25, 30},//                -----
 *     {26, 30},//                 ----
 * };
 *
 * In this example, the minimum number of facilities required is 3
 * as at time 19, there are 3 sessions (intervals) overlapping,
 * namely [12, 20), [14, 25), and [19, 22).
 *
 *
 * More formally, the goal is to minimize k such that for all time t,
 * the number of sessions that overlap at time t is at most k
 *
 * The computation must be performed in O(n.log(n)) time complexity
 * where n is the number of training sessions.
 *
 *
 */
public class TrainingSessions {

    /**
     * Determines the minimum number of facilities required to accommodate
     * all the training sessions without overlap.
     *
     * @param sessions a non-null array of int arrays where each int array represents
     *                 a session with start time and end time.
     * @return the minimum number of facilities required.
     */
    public int minFacilitiesRequired(int[][] sessions) {
        // TODO
        if ( sessions.length == 0 ){
            return 0;
        }
        Arrays.sort(sessions, (u,v) -> u[0] == v[0] ? u[1] - v[1] : u[0]-v[0]);
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        queue.add(sessions[0][1]);
        for (int i = 1 ; i < sessions.length ; i ++){
            if ( queue.peek() <= sessions[i][0]){ // if the last sessions does not overlap with this one then izfine
                queue.poll(); // so we remove it and go to the next one.
            } // else we de not remove the overlapping sessions
            queue.add(sessions[i][1]);
        }
        return queue.size();
    }





}
