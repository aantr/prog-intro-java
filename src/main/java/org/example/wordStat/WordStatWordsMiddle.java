import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.TreeMap;

public class WordStatWordsMiddle {

    public static void main(String[] args) {
        String filenameIn = args[0], filenameOut = args[1];
        try {
            FileReader f = new FileReader(filenameIn, StandardCharsets.UTF_8);
            FileWriter f2 = new FileWriter("myoutput.txt", StandardCharsets.UTF_8);
            int ch;
            while ((ch = f.read()) != -1) {
                f2.write(ch);
            }
            f.close();
            f2.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        try (MyScanner scanner = new MyScanner(new FileReader(filenameIn, StandardCharsets.UTF_8))) {
            while (scanner.hasNextLine()) {
                String str;
                while (!(str = scanner.nextOrLineSeparator(MyScanner::isValidWord)).isEmpty()) {
                    str = str.toLowerCase();
                    if (str.length() >= 7) {
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
