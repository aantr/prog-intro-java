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

    private static String getOpened(final String str) {
        if (OPENED.containsKey(str)) {
            return OPENED.get(str);
        }
        return str;
    }

    private static String getHtml(final char ch) {
        if (SCREENING.containsKey(ch)) {
            return SCREENING.get(ch);
        } else {
            return Character.toString(ch);
        }
    }

    private static void readSpecial(
            final Stack<String> open, final StringBuilder res,
            final String special, final String str, final int index
    ) {
        if (!open.isEmpty() && Objects.equals(open.getLast(), getOpened(special))) {
            open.pop();
            res.append("</%s>".formatted(KEYWORDS.get(special)));
        } else if (KEYWORDS.containsKey(special) && index + 1 < str.length() &&
                !Character.isWhitespace(str.charAt(index + 1))) {
            open.add(special);
            res.append("<%s>".formatted(KEYWORDS.get(special)));
        } else {
            for (final char ch : special.toCharArray()) {
                res.append(getHtml(ch));
            }
        }
    }

    private static String parse(final String str) {
        // :NOTE: Stack -> Deque
        final Stack<String> open = new Stack<>();
        final StringBuilder res = new StringBuilder();
        boolean screening = false;
        for (int i = 0; i < str.length(); i++) {
            final char c = str.charAt(i);
            if (SPECIAL.contains(Character.toString(c)) && !screening) {
                final StringBuilder special = new StringBuilder().append(c);
                while (i + 1 < str.length() && str.charAt(i + 1) == c) {
                    i++;
                    special.append(c);
                }
                // :NOTE: -> substring
                readSpecial(open, res, special.toString(), str, i);
            } else {
                // :NOTE: next symbol
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

    private static String readHeader(final String str) {
        final int level = levelHeader(str);
        final String tag = "h" + level;
        return "<h%d>".formatted(level) +
                parse(str.substring(level + 1)) +
                "</h%d>".formatted(level);
    }

    private static String readParagraph(final String str) {
        final String tag = "p";
        return "<p>" + parse(str) + "</p>";
    }

    private static int levelHeader(final String str) {
        int level = 0;
        while (level < str.length() && str.charAt(level) == '#') {
            level++;
        }
        // :NOTE: \t
        if (level < str.length() && str.charAt(level) == ' ') {
            return level;
        }
        return 0;
    }

    private static String md2Html(final String text) {
        return levelHeader(text) > 0 ? readHeader(text) : readParagraph(text);
    }


    public static void main(final String[] args) {
        assert args.length >= 2;
        final String filenameIn = args[0];
        final String filenameOut = args[1];

        final StringBuilder answer = new StringBuilder();
        try (final Scanner scanner = new Scanner(new FileReader(filenameIn, StandardCharsets.UTF_8))) {
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
                final StringBuilder paragraph = new StringBuilder();
                do {
                    if (!paragraph.isEmpty()) {
                        paragraph.append(System.lineSeparator());
                    }
                    paragraph.append(line);
                    // :NOTE: isEmpty
                } while (scanner.hasNextLine() && !(line = scanner.nextLine()).isEmpty());
                answer.append(md2Html(paragraph.toString())).append(System.lineSeparator());
            }
        } catch (final IOException e) {
            System.err.println("Read error: " + e.getMessage());
            return;
        }

        try (final OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filenameOut), StandardCharsets.UTF_8)) {
            writer.write(answer.toString());
        } catch (final IOException e) {
            System.err.println("Write error: " + e.getMessage());
        }
    }

}
