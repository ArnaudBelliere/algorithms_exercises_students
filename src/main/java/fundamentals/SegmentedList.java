package fundamentals;

import java.util.*;

/**
 * Author Pierre Schaus
 *
 * The SegmentedList is a linear structure that consists of several segments (or groups) of elements,
 * where each segment can have its own size.
 * It is like a list of lists, but the user interacts with it as a single linear sequence.
 * The iterator will need to traverse each segment in order, element by element,
 * seamlessly presenting the entire structure as a single flat sequence.
 *
 * Be careful with the iterator, it should implement the fail-fast behavior.
 * A fail-fast iterator detects illegal concurrent modification during iteration (see the tests).
 *
 * Example:
 *
 *         SegmentedList<Integer> segmentedList = create();
 *
 *         // Add segments
 *         List<Integer> segment1 = new ArrayList<>();
 *         segment1.add(1); segment1.add(2); segment1.add(3);
 *         segmentedList.addSegment(segment1); // add [1,2,3]
 *
 *         List<Integer> segment2 = new ArrayList<>();
 *         segment2.add(4); segment2.add(5);
 *         segmentedList.addSegment(segment2); // add [4,5]
 *
 *         List<Integer> segment3 = new ArrayList<>();
 *         segment3.add(6); segment3.add(7); segment3.add(8); segment3.add(9);
 *         segmentedList.addSegment(segment3); // add [6,7,8,9]
 *
 *         segmentedList.removeSegment(1); // remove [4,5], the segment in second position
 *
 *         // Iterate through the SegmentedList that is elements 1,2,3,6,7,8,9
 *         for (Integer value : segmentedList) {
 *             System.out.println(value);
 *         }
 *
 *         // Example of using get()
 *         System.out.println("Element at global index 4: " + segmentedList.get(4)); // Should print 5
 *
 * @param <T>
 */
public interface SegmentedList<T> extends Iterable<T> {

    // Add a new segment (list) to the SegmentedList.
    void addSegment(List<T> segment);

    // Remove a segment by its index.
    void removeSegment(int index);

    // Get the total size of the segmented list (across all segments).
    int size();

    // Retrieve an element at a global index (spanning all segments).
    T get(int globalIndex);

    // Static method inside the interface
    static <T> SegmentedList<T> create() {
        return new SegmentedListImpl<>();
    }

}

class SegmentedListImpl<T> implements SegmentedList<T> {

    // TODO: Implement the SegmentedList interface here

    private List<List<T>> segmentList = new LinkedList<>();
    private int nop = 0;


    // Add a new segment (list) to the SegmentedList.
    public void addSegment(List<T> segment) {
        nop ++;
        segmentList.add(segment);
    }

    // Remove a segment by its index.
    public void removeSegment(int index) {
        nop ++;
        if ( index < 0 || index >= segmentList.size() ){
            throw new IndexOutOfBoundsException();
        }
        segmentList.remove(index);
    }

    // Get the total size of the segmented list (across all segments).
    public int size() {
        int i = 0;
        for ( List<T> seg : segmentList){
            i += seg.size();
        }
        return i;
    }

    // Retrieve an element at a global index (spanning all segments).
    public T get(int globalIndex) {
         int i = 0;
         for ( List<T> seg : segmentList ){
             if ( globalIndex < i + seg.size()){
                 return seg.get(globalIndex-i);
             }
             i += seg.size();
         }
         throw new IndexOutOfBoundsException();
    }



    // Return an iterator for the segmented list.
    @Override
    public Iterator<T> iterator() {
        return new SegmentedListIterator();
    }
    
    private class SegmentedListIterator implements Iterator<T> {

        private int segmentIndex = 0; // Current segment we are traversing
        private int elementIndex = 0; // Current element within the segment

        private int t = nop;

        @Override
        public boolean hasNext() {
            if (t != nop) {
                throw new ConcurrentModificationException("Concurrent modification detected.");
            }
            // If we have reached the end of a segment, move to the next segment.
            while (segmentIndex < segmentList.size()) {
                if (elementIndex < segmentList.get(segmentIndex).size()) {
                    return true; // There's more elements in this segment.
                }
                segmentIndex++;
                elementIndex = 0; // Reset element index for the next segment.
            }
            return false; // No more elements left.
        }

        @Override
        public T next() {
            if (t != nop) {
                throw new ConcurrentModificationException("Concurrent modification detected.");
            }
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements left.");
            }
            return segmentList.get(segmentIndex).get(elementIndex++);
        }
    }


}
