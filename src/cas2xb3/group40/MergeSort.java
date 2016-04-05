package cas2xb3.group40;

public class MergeSort {
    private static Intersection[] aux;

    public static void sort(Intersection[] a, Sortable s) {
        aux = new Intersection[a.length];
        sort(a, 0, a.length-1, s);
    }

    private static void sort(Intersection[] a, int lo, int hi, Sortable s) {
        if (hi <= lo) return;
        int mid = lo + (hi-lo)/2;
        sort(a, lo, mid, s);
        sort(a, mid+1, hi, s);
        merge(a, lo, mid, hi, s);
    }

    private static void merge(Intersection[] a, int lo, int mid, int hi, Sortable s) {
        int i = lo, j = mid + 1;

        for (int k=lo; k <= hi; k++)
            aux[k] = a[k];


        try {
            for (int k = lo; k <= hi; k++)
                if (i > mid)
                    a[k] = aux[j++];
                else if (j > hi)
                    a[k] = aux[i++];
                else if (less(aux[j], aux[i], s))
                    a[k] = aux[j++];
                else
                    a[k] = aux[i++];
        } catch (NullPointerException e) {
            return;
        }
    }

    private static boolean less(Intersection a, Intersection b, Sortable s) {
        return a.compareTo(b, s) == -1;
    }

    public static void main(String[] args) {
        Intersection[] arr = {new Intersection("hello", "world", 323, 42),
                              new Intersection("good", "bye", 34, 543),
                              new Intersection("again", "here", 432, 453),
                              new Intersection("one", "more", 31445, 53)};

        MergeSort.sort(arr, Sortable.Y);
        for (Intersection i: arr) System.out.println(i);
    }

}