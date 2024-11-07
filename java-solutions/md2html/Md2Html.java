package md2html;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Stack;

public class Md2Html {

    private static final String SPECIAL = "-_`*<>{}";
    private static final Map<String, String> KEYWORDS = Map.of(
            "_", "em",
            "*", "em",
            "**", "strong",
            "__", "strong",
            "`", "code",
            "--", "s",
            "<<", "ins",
            ">>", "ins",
            "{{", "del",
            "}}", "del"
    );

    private static final Map<String, String> OPENED = Map.of(
            ">>", "<<",
            "{{", "}}"
    );

    private static final Map<Character, String> SCREENING = Map.of(
            '&', "&amp;",
            '<', "&lt;",
            '>', "&gt;"
    );

    private static String getOpened(String str) {
        if (OPENED.containsKey(str)) {
            return OPENED.get(str);
        }
        return str;
    }

    private static String getHtml(char ch) {
        if (SCREENING.containsKey(ch)) {
            return SCREENING.get(ch);
        } else {
            return Character.toString(ch);
        }
    }

    private static void readSpecial(Stack<String> open, StringBuilder res,
                                    StringBuilder special, String str, int index) {
        if (!open.isEmpty() && Objects.equals(open.getLast(), getOpened(special.toString()))) {
            open.pop();
            res.append("</%s>".formatted(KEYWORDS.get(special.toString())));
        } else if (KEYWORDS.containsKey(special.toString()) && index + 1 < str.length() &&
                !Character.isWhitespace(str.charAt(index + 1))) {
            open.add(special.toString());
            res.append("<%s>".formatted(KEYWORDS.get(special.toString())));
        } else {
            for (char ch : special.toString().toCharArray()) {
                res.append(getHtml(ch));
            }
        }
    }

    private static String parse(String str) {
        Stack<String> open = new Stack<>();
        StringBuilder res = new StringBuilder();
        boolean screening = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (SPECIAL.contains(Character.toString(c)) && (!screening)) {
                StringBuilder special = new StringBuilder(Character.toString(c));
                while (i + 1 < str.length() && str.charAt(i + 1) == c) {
                    i++;
                    special.append(c);
                }
                readSpecial(open, res, special, str, i);
            } else {
                if (c == '\\') {
                    screening = !screening;
                    if (screening) {
                        continue;
                    }
                }
                res.append(getHtml(c));
            }
        }
        return res.toString();
    }

    private static String readHeader(String str) {
        int level = levelHeader(str);
        return "<h%d>".formatted(level) +
                parse(str.substring(level + 1)) +
                "</h%d>".formatted(level);
    }

    private static String readParagraph(String str) {
        return "<p>" + parse(str) + "</p>";
    }

    private static int levelHeader(String str) {
        int level = 0;
        while (str.length() > level && str.charAt(level) == '#') {
            level++;
        }
        if (str.length() > level && str.charAt(level) == ' ') {
            return level;
        }
        return 0;
    }

    private static String read(String text) {
        if (levelHeader(text) > 0) {
            return readHeader(text);
        }
        return readParagraph(text);
    }


    public static void main(String[] args) {
        assert args.length >= 2;
        String filenameIn = args[0], filenameOut = args[1];

        StringBuilder answer = new StringBuilder();
        try (Scanner scanner = new Scanner(new FileReader(filenameIn, StandardCharsets.UTF_8))) {
            while (scanner.hasNextLine()) {
                // find first line
                String line;
                while ((line = scanner.nextLine()).isEmpty()) {
                    if (!scanner.hasNextLine()) {
                        break;
                    }
                }
                if (line.isEmpty()) {
                    break;
                }
                StringBuilder paragraph = new StringBuilder();
                do {
                    if (!paragraph.isEmpty()) {
                        paragraph.append(System.lineSeparator());
                    }
                    paragraph.append(line);
                } while (scanner.hasNextLine() && !(line = scanner.nextLine()).isEmpty());
                answer.append(read(paragraph.toString())).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Read error: " + e.getMessage());
            return;
        }
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filenameOut), StandardCharsets.UTF_8)) {
            writer.write(answer.toString());
        } catch (IOException e) {
            System.err.println("Write error: " + e.getMessage());
        }
    }

}
