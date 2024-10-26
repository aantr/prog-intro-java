import java.util.Scanner;

public class ReverseSumAbs {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int maxLineLength = 0;
        StringBuilder allLines = new StringBuilder();
        int linesAmount = 0;
        while (scanner.hasNextLine()) {
            linesAmount++;
            String line = scanner.nextLine();
            allLines.append(line).append('*');
        }
        int[][] matrix = new int[linesAmount][];
        for (int k = 0; k < linesAmount; k++) {
            String[] parts = takeOut(allLines);
            String string = parts[0];
            String unusedString = parts[1];
            if (string == null) {
                matrix[k] = new int[0];
            } else {
                int[] digits = split(string);
                maxLineLength = Math.max(maxLineLength, digits.length);
                matrix[k] = digits;
            }
            allLines.setLength(0);
            allLines.append(unusedString);
        }
        int[][] sumMatrix = new int[linesAmount][maxLineLength];
        for (int i = 0; i < linesAmount; i++) {
            int[] array = matrix[i];
            int lineSum = 0;
            for (int j = 0; j < array.length; j++) {
                lineSum += Math.abs(array[j]);
                sumMatrix[i][j] = -array[j];
            }
            for (int j = 0; j < array.length; j++) {
                sumMatrix[i][j] += lineSum;
            }
        }
        for (int j = 0; j < maxLineLength; j++) {
            int columnSum = 0;
            for (int i = 0; i < linesAmount; i++) {
                if (matrix[i].length > j) {
                    columnSum += Math.abs(matrix[i][j]);
                }
            }
            for (int i = 0; i < linesAmount; i++) {
                if (matrix[i].length > j) {
                    sumMatrix[i][j] += columnSum;
                }
            }
        }
        for (int i = 0; i < linesAmount; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < matrix[i].length; j++) {
                builder.append(sumMatrix[i][j]).append(' ');
            }
            System.out.println(builder);
        }
        scanner.close();
        System.err.println((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);

    }

    static int[] split(String stringToSplit) {
        String[] digits = new String[stringToSplit.length() / 2 + 1];
        int digitsCount = 0;
        StringBuilder tempString = new StringBuilder();
        for (int i = 0; i < stringToSplit.length(); i++) {
            char symbol = stringToSplit.charAt(i);
            if (Character.isWhitespace(symbol) || symbol == '-') {
                if (!tempString.isEmpty()) {
                    digits[digitsCount++] = tempString.toString();
                    tempString.setLength(0);
                }
            } else {
                tempString.append(symbol);
            }
        }
        if (!tempString.isEmpty()) {
            digits[digitsCount++] = tempString.toString();
        }
        int[] result = new int[digitsCount];
        for (int j = 0; j < digitsCount; j++) {
            result[j] = Integer.parseInt(digits[j]);
        }
        return result;
    }

    static String[] takeOut(StringBuilder text) {
        String[] result = new String[2];
        StringBuilder line = new StringBuilder();
        for (int x = 0; x < text.length(); x++) {
            char symbol = text.charAt(x);
            if (symbol == '*') {
                result[0] = line.toString();
                text.delete(0, x + 1);
                result[1] = text.toString();
                break;
            } else {
                line.append(symbol);
            }
        }
        return result;
    }
}