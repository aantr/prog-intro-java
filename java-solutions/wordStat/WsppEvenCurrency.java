//package wordStat;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class WsppEvenCurrency {

    public static void main(String[] args) {
        String filenameIn = args[0], filenameOut = args[1];
        Map<String, ArrayList<Integer>> map = new LinkedHashMap<>();
        Map<String, Integer> cnt = new LinkedHashMap<>();
        try (MyScanner scanner = new MyScanner(new FileReader(filenameIn, StandardCharsets.UTF_8))) {

            while (scanner.hasNextLine()) {
                int index = 0;
                Map<String, Integer> cntLine = new LinkedHashMap<>();
                String str;
                while (!(str = scanner.nextOrSeparator(MyScanner::isValidWordCurrency)).isEmpty()) {
                    str = str.toLowerCase();
                    // if map does not contain current word add empty list of occurrences
                    map.putIfAbsent(str, new ArrayList<>());
                    cnt.putIfAbsent(str, 0);

                    // update cntLine and map if cntLine[x] % 2 == 1 (even)
                    int cntL = cntLine.getOrDefault(str, 0);
                    cntLine.put(str, cntL + 1);
                    if (cntL % 2 == 1) {
                        map.get(str).add(index);
                    }

                    index++;
                }
                // Update cnt
                for (var i : cntLine.entrySet()) {
                    cnt.put(i.getKey(), cnt.get(i.getKey()) + i.getValue());
                }
            }
        } catch (IOException e) {
            System.err.println("Read error: " + e.getMessage());
            return;
        } catch (ScannerException e) {
            System.err.println("Scanner error: " + e.getMessage());
            return;
        }

        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filenameOut), StandardCharsets.UTF_8)) {
            for (var word : map.entrySet()) {
                var el = word.getValue();
                writer.write(word.getKey() + " " + cnt.get(word.getKey()));
                for (var i : el) {
                    writer.write(" " + (i + 1));
                }
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Write error: " + e.getMessage());
        }
    }
}