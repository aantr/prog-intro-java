import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Stack;

class E {

    public static boolean get(int v, int p, int[][] gr, int b, Stack<Integer> res) {
        res.add(v);
        if (v == b) {
            return true;
        }
        boolean was = false;
        for (var u : gr[v]) {
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

    public static void diam(int v, int p, int d, int[][] gr, int[] vert) {
        if (vert[v] == 1 && d > diam) {
            diam = d;
            b = v;
        }
        for (var u : gr[v]) {
            if (u == p) continue;
            diam(u, v, d + 1, gr, vert);
        }
    }

    static int dist = -1;

    public static void check(int v, int p, int d, int[][] gr, int[] vert) {
        if (vert[v] == 1) {
            if (dist == -1 || dist == d) {
                dist = d;
            } else {
                dist = -2;
            }
        }
        for (var u : gr[v]) {
            if (u == p) continue;
            check(u, v, d + 1, gr, vert);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int N = n;
        int[][] gr = new int[N][];
        int[] sz = new int[N];
        int[] deg = new int[N];
        int[] vert = new int[N];
        HashSet<Integer> ones = new HashSet<>();
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
                ones.add(i);
            }
        }
        Stack<Integer> res = new Stack<>();
        int a = 0;
        for (int i = 0; i < n; i++) {
            if (vert[i] == 1) {
                a = i;
            }
        }
        b = a;
        diam(a, -1, 0, gr, vert);
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