//package org.example.stat;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class ReverseMaxAbsOctDec {
    public static void main(String[] args) throws IOException {
        MineScanner scanner = new MineScanner(new InputStreamReader(System.in));
        int[][] matrix = new int[1][];
        int[] maxInLine = new int[1];
        int[] maxInColumn = new int[1];
        int countLines = 0;
        int columnAmount = 0;
        while (scanner.hasNextLine()) {
            MineScanner sc = new MineScanner(scanner.nextLine());
            if (matrix.length >= maxInLine.length) {
                maxInLine = Arrays.copyOf(maxInLine, 2 * maxInLine.length);
            }
            int[] arr = new int[1];
            int countColumn = 0;
            countLines ++;
            int num;
            while (sc.hasNext()) {
                String number = sc.next();
                if (number.length() > 1) {
                    if ((number.endsWith("o")) || (number.endsWith("O"))) {
                        num = Integer.parseUnsignedInt(number.substring(0, number.length() - 1), 8);
                    } else {
                        num = Integer.parseInt(number);
                    }
                } else {
                    num = Integer.parseInt(number);
                }

                if (arr.length < countColumn + 1) {
                    arr = Arrays.copyOf(arr, 2 * arr.length);
                }

                if (Math.abs(num) > Math.abs(maxInLine[countLines - 1])) {
                    maxInLine[countLines - 1] = num;
                }

                if (countColumn >= maxInColumn.length) {
                    maxInColumn = Arrays.copyOf(maxInColumn, 2 * maxInColumn.length);
                }

                if (Math.abs(num) > Math.abs(maxInColumn[countColumn])) {
                    maxInColumn[countColumn] = num;
                }
                arr[countColumn++] = num;
            }
            arr = Arrays.copyOf(arr, countColumn);
            columnAmount = Math.max(columnAmount, countColumn);
            if (matrix.length < countLines) {
                matrix = Arrays.copyOf(matrix, 2 * matrix.length);
            }
            matrix[countLines - 1] = arr;
            sc.close();
        }
        scanner.close();
        matrix = Arrays.copyOf(matrix, countLines);
        maxInLine = Arrays.copyOf(maxInLine, countLines);
        maxInColumn = Arrays.copyOf(maxInColumn, columnAmount);
        for (int i = 0; i < countLines; i++) {
            if (matrix[i].length > 0) {
                for (int j = 0; j < matrix[i].length - 1; j++) {
                    if (Math.abs(maxInLine[i]) > Math.abs(maxInColumn[j])) {
                        System.out.print(Integer.toOctalString(maxInLine[i]) + "O ");
                    } else {
                        System.out.print(Integer.toOctalString(maxInColumn[j]) + "O ");
                    }
                }
                if (Math.abs(maxInLine[i]) > Math.abs(maxInColumn[matrix[i].length - 1])) {
                    System.out.println(Integer.toOctalString(maxInLine[i]) + "O");
                } else {
                    System.out.println(Integer.toOctalString(maxInColumn[matrix[i].length - 1]) + "O");
                }
            } else {
                System.out.println();
            }
        }
    }
}
