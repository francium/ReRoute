package cas2xb3.group40;

/**
 *
 */
public class BinarySearch {

    public static Intersection search(Intersection key, Intersection[] array, Sortable s) {
        return array[index(key, array, s)];
    }

    public static int index(Intersection key, Intersection[] array, Sortable s) {
        int lo = 0;
        int hi = array.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo)/2;
            if (key.compareTo(array[mid], s) == -1) hi = mid - 1;
            else if (key.compareTo(array[mid], s) == 1) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

    public static void main(String[] args) {
        Intersection[] i = {new Intersection("1", "2", 1, 2),
                            new Intersection("2", "2", 2, 2),
                            new Intersection("3", "2", 3, 2),
                            new Intersection("4", "2", 4, 2),
                            new Intersection("5", "2", 5, 2)};

        System.out.println(search(new Intersection("", "", 4, 2), i, Sortable.X));
        System.out.println(index(new Intersection("", "", 4, 2), i, Sortable.X));
    }
}
