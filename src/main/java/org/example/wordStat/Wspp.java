//package wordStat;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class Wspp {

    public static void main(String[] args) {
        String filenameIn = args[0], filenameOut = args[1];
        HashMap<String, ArrayList<Integer>> m = new HashMap<>();
        ArrayList<String> first = new ArrayList<>();
        try (MyScanner scanner = new MyScanner(new FileReader(filenameIn, StandardCharsets.UTF_8))) {
            String str;
            int yandex = 0;
            while (scanner.hasNextLine()) {
                while (!(str = scanner.nextOrSeparator(MyScanner::isValidWord)).isEmpty()) {
                    str = str.toLowerCase();
                    if (!m.containsKey(str)) {
                        first.add(str);
                        m.put(str, new ArrayList<>());
                    }
                    m.get(str).add(yandex++);
                }
            }
        } catch (IOException e) {
            System.err.println("Read error: " + e.getMessage());
        } catch (ScannerException e) {
            System.err.println("Scanner error: " + e.getMessage());
            return;
        }
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filenameOut), StandardCharsets.UTF_8)) {
            for (String s : first) {
                ArrayList<Integer> el = m.get(s);
                writer.write(s + " " + el.size());
                for (int i : el) {
                    writer.write(" " + (i + 1));
                }
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Write error: " + e.getMessage());
        }
    }
}
