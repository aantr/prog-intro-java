package Сhampionship;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class M {

    static int N = (int) (2e5 + 5);

    public static boolean get(int v, int p, ArrayList<ArrayList<Integer>> gr, int b, Stack<Integer> res) {
        res.add(v);
        if (v == b) {
            return true;
        }
        boolean was = false;
        for (var u : gr.get(v)) {
            if (u == p) continue;
            if (get(u, v, gr, b, res)) {
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

    public static void diam(int v, int p, int d, ArrayList<ArrayList<Integer>> gr) {
        if (d > diam) {
            diam = d;
            b = v;
        }
        for (var u : gr.get(v)) {
            if (u == p) continue;
            diam(u, v, d + 1, gr);
        }
    }

    static int dist = -1;

    public static void check(int v, int p, int d, ArrayList<ArrayList<Integer>> gr, int[] vert) {
        if (vert[v] == 1) {
            if (dist == -1 || dist == d) {
                dist = d;
            } else {
                dist = -2;
            }
        }
        for (var u : gr.get(v)) {
            if (u == p) continue;
            check(u, v, d + 1, gr, vert);
        }
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        ArrayList<ArrayList<Integer>> gr = new ArrayList<>();
        int[] deg = new int[N];
        int[] vert = new int[N];
        Stack<Integer> ones = new Stack<>();
        for (int i = 0; i < n; i++) {
            gr.add(new ArrayList<>());
        }
        for (int i = 0; i < n - 1; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            u--;
            v--;
            gr.get(v).add(u);
            gr.get(u).add(v);
            deg[u]++;
            deg[v]++;
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
                ones.add(i);
            }
        }
        while (!ones.isEmpty()) {
            int v = 0;
            for (int i : ones) {
                v = i;
                break;
            }
            int u = gr.get(v).iterator().next();
            gr.get(v).remove(u);
            gr.get(u).remove(v);
            deg[u]--;
            deg[v]--;
            ones.remove(v);
            if (deg[u] == 1 && vert[u] == 0) {
                ones.add(u);
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
        diam(a, -1, 0, gr);
        get(a, -1, gr, b, res);
        if (res.size() % 2 == 0) {
            System.out.println("NO");
            return;
        }
        int v = res.get(res.size() / 2);
        check(v, -1, 0, gr, vert);
        if (dist != -2) {
            System.out.println("YES");
            System.out.println(v + 1);
        } else {
            System.out.println("NO");
        }

    }
}
