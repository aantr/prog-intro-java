import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

class E {

    public static boolean get(int vert, int parent, int[][] gr, int dst, Stack<Integer> path) {
        path.add(vert);
        if (vert == dst) {
            return true;
        }
        boolean was = false;
        for (var u : gr[vert]) {
            if (u == parent) {
                continue;
            }
            if (get(u, vert, gr, dst, path)) {
                was = true;
                break;
            }
        }
        if (!was) {
            path.pop();
        }
        return was;
    }

    private static int deepest = 0;
    private static int diam = 0;

    public static void diam(int v, int p, int d, int[][] gr, int[] vert) {
        if (vert[v] == 1 && d > diam) {
            diam = d;
            deepest = v;
        }
        for (var u : gr[v]) {
            if (u == p) {
                continue;
            }
            diam(u, v, d + 1, gr, vert);
        }
    }

    private static int dist = -1; // -1 - not found, -2 - different values

    public static void check(int v, int p, int current_depth, int[][] gr, int[] vert) {
        if (vert[v] == 1) {
            if (dist == -1 || dist == current_depth) {
                dist = current_depth;
            } else {
                dist = -2;
            }
        }
        for (var u : gr[v]) {
            if (u == p) {
                continue;
            }
            check(u, v, current_depth + 1, gr, vert);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] gr = new int[n][];
        int[] sz = new int[n];
        int[] vert = new int[n];
        for (int i = 0; i < n; i++) {
            gr[i] = new int[1];
        }
        for (int i = 0; i < n - 1; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            u--;
            v--;
            if (gr[v].length <= sz[v]) {
                gr[v] = Arrays.copyOf(gr[v], gr[v].length * 2);
            }
            if (gr[u].length <= sz[u]) {
                gr[u] = Arrays.copyOf(gr[u], gr[u].length * 2);
            }
            gr[v][sz[v]++] = u;
            gr[u][sz[u]++] = v;
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
        Stack<Integer> res = new Stack<>();
        int start = 0;
        for (int i = 0; i < n; i++) {
            if (vert[i] == 1) {
                start = i;
            }
        }
        deepest = start;
        diam(start, -1, 0, gr, vert);
        get(start, -1, gr, deepest, res);
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
