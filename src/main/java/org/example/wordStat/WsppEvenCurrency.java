//package wordStat;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WsppEvenCurrency {

    public static void main(String[] args) {
        String filenameIn = args[0], filenameOut = args[1];
        LinkedHashMap<String, ArrayList<Integer>> map = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> cnt = new LinkedHashMap<>();
        try {
            MyScanner scanner = new MyScanner(new InputStreamReader(new FileInputStream(filenameIn), StandardCharsets.UTF_8));
            try {
                while (scanner.hasNextLine()) {
                    int index = 0;
                    LinkedHashMap<String, Integer> cntLine = new LinkedHashMap<>();
                    while (!scanner.hasNextLineSeparatorWordCurrency()) {
                        String str;
                        try {
                            str = scanner.nextWordCurrency();
                        } catch (ScannerException e) {
                            System.err.println("Scanner error: " + e.getMessage());
                            return;
                        }
                        str = str.toLowerCase();
                        if (!map.containsKey(str)) {
                            map.put(str, new ArrayList<>());
                            cnt.put(str, 0);
                        }
                        if (!cntLine.containsKey(str)) {
                            cntLine.put(str, 1);
                        } else {
                            int cntL = cntLine.get(str);
                            cntLine.put(str, cntL + 1);
                            if (cntL % 2 == 1) {
                                map.get(str).add(index);
                            }
                        }
                        index++;
                    }
                    for (var i : cntLine.entrySet()) {
                        cnt.put(i.getKey(), cnt.get(i.getKey()) + i.getValue());
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
                for (var word : map.entrySet()) {
                    var el = word.getValue();
                    writer.write(word.getKey() + " " + cnt.get(word.getKey()));
                    for (var i : el) {
                        writer.write(" " + (i + 1));
                    }
                    writer.write(System.lineSeparator());
                }
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            System.err.println("Write error: " + e.getMessage());
        }
    }
}