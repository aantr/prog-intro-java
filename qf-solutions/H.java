import java.lang.Math;
import java.util.Map;
import java.util.HashMap;
import java.io.*;
import java.util.Scanner;

public class H {

    public static int pref(int l, int r, int[] p) {
        if (l != 0) {
            return p[r] - p[l - 1];
        } else {
            return p[r];
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<Integer, Integer> map = new HashMap<>();
        int n = sc.nextInt();
        int mx = 0;
        int[] a = new int[n];
        int[] p = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
            mx = Math.max(a[i], mx);
        }
        for (int i = 0; i < n; i++) {
            p[i] = a[i];
            if (i != 0) {
                p[i] = p[i] + p[i - 1];
            }
        }
        int q = sc.nextInt();
        while (q-- > 0) {

            int t = sc.nextInt();
            if (map.containsKey(t)) {
                System.out.println(map.get(t));
            } else if (t < mx) {
                System.out.println("Impossible");
            } else {
                int i = 0;
                int res = 0;
                while (i < n) {
                    int l = i, r = n;
                    while (l < r - 1) {
                        int m = (l + r) / 2;
                        int tmp = pref(i, m, p);
                        if (tmp <= t) {
                            l = m;
                        } else {
                            r = m;
                        }
                    }
                    i = r;
                    res++;
                }
                map.put(t, res);
                System.out.println(res);
            }
        }
    }
}
