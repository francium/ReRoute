package cas2xb3.group40;

/**
 *
 */
public class BinarySearch {

    public static Intersection search(Intersection key, Intersection[] array, Sortable s) {
        int lo = 0;
        int hi = array.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo)/2;
            if (key.compareTo(array[mid], s) == -1) hi = mid - 1;
            else if (key.compareTo(array[mid], s) == 1) lo = mid + 1;
            else return array[mid];
        }
        return null;
    }
}
