import myscanner.MyScanner;
import myscanner.ScannerException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WordStatWordsMiddle {

    public static class StringReverseComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) { // reverse comparator
            return -1 * o1.compareTo(o2);
        }
    }

    public static void main(String[] args) {
        String filenameIn = args[0], filenameOut = args[1];
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        try {
            MyScanner scanner = new MyScanner(new FileReader(filenameIn, StandardCharsets.UTF_8));
            try {
                while (scanner.hasNextLine()) {
                    while (!scanner.hasNextLineSeparatorWord()) {
                        String str;
                        try {
                            str = scanner.nextWord();
                        } catch (ScannerException e) {
                            System.err.println("Scanner error: " + e.getMessage());
                            return;
                        }
                        str = str.toLowerCase();
                        if (str.length() > 6) {
                            str = str.substring(3, str.length() - 3);
                        }
                        if (!treeMap.containsKey(str)) {
                            treeMap.put(str, 0);
                        }
                        treeMap.put(str, treeMap.get(str) + 1);
                    }
                }
            } finally {
                scanner.close();
            }
        } catch (IOException e) {
            System.err.println("Read error: " + e.getMessage());
            return;
        }
        try {
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filenameOut), StandardCharsets.UTF_8);
            try {
                for (var i : treeMap.reversed().entrySet()) {
                    writer.write(i.getKey() + " " + i.getValue() + System.lineSeparator());
                }
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            System.err.println("Write error: " + e.getMessage());
        }
    }
}
