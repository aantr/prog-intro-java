package Сhampionship;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class M {

    static int N = (int) (2e5 + 5);

    public static boolean get(int v, int p, int[][] gr, int b, Stack<Integer> res, int[] removed) {
        res.add(v);
        if (v == b) {
            return true;
        }
        boolean was = false;
        for (var u : gr[v]) {
            if (u == p || removed[u] == 1) continue;
            if (get(u, v, gr, b, res, removed)) {
                was = true;
                break;
            }
        }
        if (!was) {
            res.pop();
        }
        return was;
    }

    static int b = 0;
    static int diam = 0;

    public static void diam(int v, int p, int d, int[][] gr, int[] removed) {
        if (d > diam) {
            diam = d;
            b = v;
        }
        for (var u : gr[v]) {
            if (u == p || removed[u] == 1) continue;
            diam(u, v, d + 1, gr, removed);
        }
    }

    static int dist = -1;

    public static void check(int v, int p, int d, int[][] gr, int[] vert, int[] removed) {
        if (vert[v] == 1) {
            if (dist == -1 || dist == d) {
                dist = d;
            } else {
                dist = -2;
            }
        }
        for (var u : gr[v]) {
            if (u == p || removed[u] == 1) continue;
            check(u, v, d + 1, gr, vert, removed);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] gr = new int[N][];
        int[] sz = new int[N];
        int[] deg = new int[N];
        int[] vert = new int[N];
        int[] removed = new int[N];
        Stack<Integer> ones = new Stack<>();
        for (int i = 0; i < n; i++) {
            gr[i] = new int[1];
        }
        for (int i = 0; i < n - 1; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            u--;
            v--;
            if (gr[v].length <= sz[v]) gr[v] = Arrays.copyOf(gr[v], gr[v].length * 2);
            if (gr[u].length <= sz[u]) gr[u] = Arrays.copyOf(gr[u], gr[u].length * 2);
            gr[v][sz[v]++] = u;
            gr[u][sz[u]++] = v;
            deg[u]++;
            deg[v]++;
        }
        for (int i = 0; i < n; i++) {
            gr[i] = Arrays.copyOf(gr[i], sz[i]);
        }
        if (m == 1) {
            System.out.println("YES\n1");
            return;
        }
        for (int i = 0; i < m; i++) {
            vert[sc.nextInt() - 1] = 1;
        }
        for (int i = 0; i < n; i++) {
            if (deg[i] == 1 && vert[i] == 0) {
                ones.push(i);
            }
        }
        while (!ones.isEmpty()) {
            int v = ones.peek();
            int u = 0;
            for (int i = 0; i < gr[v].length; i++) {
                if (removed[gr[v][i]] == 0) {
                    u = gr[v][i];
                    break;
                }
            }
//            gr.get(v).remove(u);
//            gr.get(u).remove(v);
            removed[v] = 1;
            deg[u]--;
            deg[v]--;
            if (deg[u] == 0) {
                removed[u] = 1;
            }
            if (deg[u] == 1 && vert[u] == 0) {
                ones.push(u);
            }
        }
        Stack<Integer> res = new Stack<>();
        int a = 0;
        for (int i = 0; i < n; i++) {
            if (deg[i] == 1) {
                a = i;
            }
        }
        b = a;
        diam(a, -1, 0, gr, removed);
        get(a, -1, gr, b, res, removed);
        if (res.size() % 2 == 0) {
            System.out.println("NO");
            return;
        }
        int v = res.get(res.size() / 2);
        check(v, -1, 0, gr, vert, removed);
        if (dist != -2) {
            System.out.println("YES");
            System.out.println(v + 1);
        } else {
            System.out.println("NO");
        }
    }
}
