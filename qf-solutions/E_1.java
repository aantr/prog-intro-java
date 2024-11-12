package Сhampionship;

import java.util.*;

public class M {
    public static boolean get(int v, int p, ArrayList<ArrayList<Integer>> gr, ArrayList<Integer> vert, Stack<Integer> res) {
        res.add(v);
        if (v == vert.get(1)) {
            return true;
        }
        boolean was = false;
        for (var u : gr.get(v)) {
            if (u == p) continue;
            if (get(u, v, gr, vert, res)) {
                was = true;
                break;
            }
        }
        if (!was) {
            res.pop();
        }
        return was;
    }
    static int dist = -1;
    public static void check(int v, int p, int d, ArrayList<ArrayList<Integer>> gr, Set<Integer> vert) {
        if (vert.contains(v)) {
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
        ArrayList<Integer> vert = new ArrayList<>();
        for (int i = 0; i < n; i++) gr.add(new ArrayList<>());
        for (int i = 0; i < m; i++) vert.add(0);
        for (int i = 0; i < n - 1; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            u--;
            v--;
            gr.get(v).add(u);
            gr.get(u).add(v);
        }
        if (m == 1) {
            System.out.println("YES\n1");
            return;
        }
        for (int i = 0; i < m; i++) {
            vert.set(i, sc.nextInt() - 1);
        }
        Stack<Integer> res = new Stack<>();
        get(vert.get(0), -1, gr, vert, res);
        if (res.size() % 2 == 0) {
            System.out.println("NO");
            return;
        }
        int v = res.get(res.size() / 2);
        Set<Integer> h = new HashSet<>(vert);
        check(v, -1, 0, gr, h);
        if (dist != -2) {
            System.out.println("YES");
            System.out.println(v + 1);
        } else {
            System.out.println("NO");
        }

    }
}
