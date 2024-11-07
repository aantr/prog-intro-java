package Сhampionship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class M {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int test = 0; test < t; test++) {
            int n = sc.nextInt();
            List<Integer> list = new ArrayList<Integer>();
            for (int i = 0; i < n; i++) {
                int x = sc.nextInt();
                list.add(x);
            }
            int ans = getAnInt(n, list);
            System.out.println(ans);
        }
    }

    private static int getAnInt(int n, List<Integer> list) {
        int ans = 0;
        HashMap<Integer, Integer> hm = new HashMap<>();
        for (int j = n - 1; j >= 0; j--) {
            for (int i = 0; i < j; i++) {
                int tmp = 2 * list.get(j) - list.get(i);
                if (hm.containsKey(tmp)) {
                    ans = ans + hm.get(tmp);
                }
            }
            int key = list.get(j);
            if (hm.containsKey(key)) {
                hm.put(key, hm.get(key) + 1);
            } else {
                hm.put(key, 1);
            }
        }
        return ans;
    }
}