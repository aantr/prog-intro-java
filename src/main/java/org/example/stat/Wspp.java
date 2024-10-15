import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Wspp {

    public static void main(String[] args) {
        String filenameIn = args[0], filenameOut = args[1];
        HashMap<String, ArrayList<Integer>> m = new HashMap<>();
        ArrayList<String> first = new ArrayList<>();
        try {
            MyScanner scanner = new MyScanner(new FileReader(filenameIn, StandardCharsets.UTF_8));
            try {
                for (int i = 0; scanner.hasNextWord(); i++) {
                    String str;
                    try {
                        str = scanner.nextWord().toLowerCase();
                    } catch (MyScanner.ScannerException e) {
                        System.err.println("Scanner error: " + e.getMessage());
                        return;
                    }
                    if (!m.containsKey(str)) {
                        first.add(str);
                        m.put(str, new ArrayList<>());
                    }
                    m.get(str).add(i);
                }
            } finally {
                scanner.close();
            }
        } catch (IOException e) {
            System.err.println("Read error: " + e.getMessage());
        }
        try {
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filenameOut), StandardCharsets.UTF_8);
            try {

                for (String s : first) {
                    ArrayList<Integer> el = m.get(s);
                    writer.write(s + " " + el.size());
                    for (int i : el) {
                        writer.write(" " + (i + 1));
                    }
                    writer.write(System.lineSeparator());
                }
            } finally {
                writer.close();
            }
        } catch (Exception e) {
            System.err.println("Write error: " + e.getMessage());
        }
    }
}
