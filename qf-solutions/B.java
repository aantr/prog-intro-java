import java.util.Scanner;
import java.lang.Math;

public class B {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        final int howmuch = 710;
        int now = -710 * 25000;
        int n = sc.nextInt();
        for(int i = 0; i < n; i++) {
            System.out.println(now + i * (howmuch));
        }
    }
}
