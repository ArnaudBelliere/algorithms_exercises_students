package sorting;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Santa’s elves are preparing for Christmas, but there’s a problem in the North Pole kitchen! They’ve
 * accidentally stacked the Christmas pancakes (each with a unique festive design) in the wrong order. Santa, being
 * the perfectionist he is, wants the pancakes sorted perfectly from smallest to largest by size, starting from the
 * top of the stack.
 *
 * If you want Christmas to run like clockwork, you can help the elves by flipping a stack of pancakes from the top
 * to a certain position i (inclusive). However, Santa's patience is limited - the elves and you have decided that
 * the maximum number of times you can flip the stack is 3 times the number of pancakes.
 *
 * To help the elves, you must provide the list of flips that need to be performed to sort the pancakes correctly.
 *
 * Input:
 *     * An array of n strictly positive integers, each representing the size of the pancakes.
 *
 * Output:
 *     * A list of integers representing the position i for each flip that needs to be performed to order the array
 *       correctly. If no flips are required, an empty list is returned.
 *
 * For example:
 *     * Input: [3, 1, 2, 5, 4]
 *     * Output: [3, 4, 3, 1]
 *
 *     * Explanation:
 *         * After the first flip (3), reverse the subarray from index 0 to index 3 (inclusive):
 *             Original: [3, 1, 2, 5, 4] -> After the flip: [5, 2, 1, 3, 4]
 *         * After the second flip (4), reverse the subarray from index 0 to index 4 (inclusive):
 *             Original: [5, 2, 1, 3, 4] -> After the flip: [4, 3, 1, 2, 5]
 *         * After the third flip (3), reverse the subarray from index 0 to index 3 (inclusive):
 *             Original: [4, 3, 1, 2, 5] -> After the flip: [2, 1, 3, 4, 5]
 *         * After the fourth and final flip (1), reverse the subarray from index 0 to index 1 (inclusive):
 *             Original: [2, 1, 3, 4, 5] -> After the flip: [1, 2, 3, 4, 5]
 */
public class PancakeSorting {

    /**
     * Reverses the order of elements in the array from the start up to a specified position to.
     *
     * @param array The array on which the operation is performed.
     * @param to The position up to which the array should be flipped. This position is inclusive, meaning the subarray
     *           from index 0 to index to will be reversed. If to is not a valid position in the array, the function
     *           throws an IndexOutOfBoundsException().
     */
    private static void flip(int[] array, int to) {
        if (to < 0 || to > array.length - 1) {
            throw new IndexOutOfBoundsException("To position is out of bounds");
        }
        for (int i = 0; i < (to + 1) / 2; i++) {
            int t = array[i];
            array[i] = array[to - i];
            array[to - i] = t;
        }
    }

    /**
     * Sorts the array of pancakes using a series of flips. It returns an ordered list of positions where each flip was
     * performed.
     *
     * @param array The series of pancakes that must be sorted.
     *
     * @return An array of integers representing the positions where flips were performed to sort the array. If no
     *         flips are required, an empty list is returned.
     */
    public static int[] sort(int[] array) {
        // TODO
        ArrayList<Integer> ret = new ArrayList<>();
        ArrayList<Integer> sorted = new ArrayList<>();
        for (int elem : array){
            sorted.add(elem);
        }
        Collections.sort(sorted);
        boolean direct = true;
        for (int i = 0 ; i < array.length; i ++){
            if ( array[i] != sorted.get(i)){
                direct = false;
                break;}}
        if ( direct ){return new int[]{};}
        int n = array.length-1;
        for (int i = 0 ; i < n ; i ++){
            int j = n-i;
            int firstFlip = getIndex(sorted.get(j), array);
            flip(array, firstFlip); // flip the array
            flip(array, j);
            ret.add(firstFlip);
            ret.add(j);
        }
        int[] rett = new int[ret.size()];
        for(int i = 0 ; i < ret.size(); i ++){
            rett[i] = ret.get(i);
        }
        return rett;
    }

    public static int getIndex(int number, int[] array){
        for (int i = 0 ; i < array.length ; i ++){
            if ( array[i] == number ){
                return i;
            }
        }
        return -1;
    }
}
