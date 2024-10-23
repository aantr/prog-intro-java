//package wordStat;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.TreeMap;

public class WordStatWordsMiddle {

    public static void main(String[] args) {
        String filenameIn = args[0], filenameOut = args[1];
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        try (MyScanner scanner = new MyScanner(new FileReader(filenameIn, StandardCharsets.UTF_8))) {
            while (scanner.hasNextLine()) {
                String str;
                while (!(str = scanner.nextNextOrLineSeparator(MyScanner::isValidWordCurrency)).isEmpty()) {
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
        } catch (IOException e) {
            System.err.println("Read error: " + e.getMessage());
            return;
        } catch (ScannerException e) {
            System.err.println("Scanner error: " + e.getMessage());
            return;
        }
        try {
            try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filenameOut), StandardCharsets.UTF_8)) {
                for (var i : treeMap.reversed().entrySet()) {
                    writer.write(i.getKey() + " " + i.getValue() + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.err.println("Write error: " + e.getMessage());
        }
    }
}
