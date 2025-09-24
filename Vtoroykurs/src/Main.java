import java.util.*;

public class Main {

    static int select(int[] a, int k) {
        if (k < 0 || k >= a.length) throw new IllegalArgumentException("k out of range");
        return select(a, 0, a.length - 1, k);
    }

    private static int select(int[] a, int lo, int hi, int k) {
        while (true) {
            if (lo == hi) return a[lo];
            int pivot = mom5(a, lo, hi);
            int[] b = part3(a, lo, hi, pivot);
            if (k <= b[0]) hi = b[0];
            else if (k <= b[1]) return a[k];
            else lo = b[1] + 1;
        }
    }

    private static int mom5(int[] a, int lo, int hi) {
        int n = hi - lo + 1;
        if (n <= 5) { ins(a, lo, hi); return a[lo + n/2]; }
        int groups = (n + 4) / 5;
        for (int g = 0; g < groups; g++) {
            int s = lo + g*5, e = Math.min(s + 4, hi);
            ins(a, s, e);
            int m = s + (e - s)/2;
            swap(a, lo + g, m);
        }
        int L = lo, H = lo + groups - 1, K = L + groups/2;
        return select(a, L, H, K);
    }

    private static int[] part3(int[] a, int lo, int hi, int p) {
        int lt = lo, i = lo, gt = hi;
        while (i <= gt) {
            if (a[i] < p) swap(a, i++, lt++);
            else if (a[i] > p) swap(a, i, gt--);
            else i++;
        }
        return new int[]{ lt - 1, gt };
    }

    private static void ins(int[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            int x = a[i], j = i - 1;
            while (j >= lo && a[j] > x) { a[j + 1] = a[j]; j--; }
            a[j + 1] = x;
        }
    }

    private static void swap(int[] a, int i, int j) { int t = a[i]; a[i] = a[j]; a[j] = t; }

    public static void main(String[] args) {
        int[] a = {7, 3, 9, 1, 5, 5, 2, 11, 0, 8, 13, 4};
        int k = 9;

        System.out.println("Исходный массив: " + Arrays.toString(a));
        System.out.println("k (0-индексация): " + k + "  → это " + (k+1) + "-й по порядку элемент");

        int val = select(Arrays.copyOf(a, a.length), k);
        System.out.println("Результат (Deterministic Select): " + val);

        int[] sorted = Arrays.copyOf(a, a.length);
        Arrays.sort(sorted);
        System.out.println("Отсортированная копия: " + Arrays.toString(sorted));
        System.out.println("Проверка: элемент с индексом k в отсортированном = " + sorted[k]);

        boolean ok = (val == sorted[k]);
        System.out.println("Совпадает ли результат? " + (ok ? "ДА " : "НЕТ "));

        Random r = new Random(7);
        int trials = 50;
        for (int t = 0; t < trials; t++) {
            int n = 20 + r.nextInt(40);
            int[] arr = r.ints(n, -1000, 1000).toArray();
            int[] b = Arrays.copyOf(arr, n); Arrays.sort(b);
            int kk = r.nextInt(n);
            if (select(arr, kk) != b[kk]) { System.out.println("Self-check: FAIL"); return; }
        }
        System.out.println("Self-check на " + trials + " массивах: OK ");
    }
}
