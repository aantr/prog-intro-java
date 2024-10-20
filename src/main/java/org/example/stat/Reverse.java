package org.example.stat;

import javax.imageio.stream.ImageOutputStreamImpl;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Reverse {
    private final static int MAX_N = 3_000_000;

    public static void main(String[] args) {
        try {
            int[] numbers = new int[MAX_N];
            int current = 0;
            MyScanner sc = new MyScanner(new InputStreamReader(System.in));
            while (sc.hasNextLine()) {
                numbers[current++] = Integer.MAX_VALUE;
//                while (scanner.hasNextInt()) {
//                int nextInt = scanner.nextInt();
                for (int nextInt : sc.nextLineInt()) {
                    numbers[current++] = nextInt;

                }
            }

            for (int i = 0; i < current; i++) {
                int n = numbers[current - 1 - i];
                if (n == Integer.MAX_VALUE) {
                    System.out.print("\n");
                } else {
                    System.out.print(n + " ");
                }
            }
        } catch (IOException e) {
            System.err.println("error: " + e.getMessage());
        }
    }
}
