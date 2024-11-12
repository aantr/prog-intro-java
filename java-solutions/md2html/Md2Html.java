package md2html;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Scanner;

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

    private static void readSpecial(
            final Deque<String> open, final StringBuilder res,
            final String special, final String str, final int index
    ) {
        if (!open.isEmpty() && open.getLast().equals(OPENED.getOrDefault(special, special))) {
            open.removeLast();
            res.append("</%s>".formatted(KEYWORDS.get(special)));
        } else if (KEYWORDS.containsKey(special) && index + 1 < str.length() &&
                !Character.isWhitespace(str.charAt(index + 1))) {
            open.addLast(special);
            res.append("<%s>".formatted(KEYWORDS.get(special)));
        } else {
            for (final char ch : special.toCharArray()) {
                res.append(SCREENING.getOrDefault(ch, Character.toString(ch)));
            }
        }
    }

    private static String parse(final String str) {
        final Deque<String> open = new ArrayDeque<>();
        final StringBuilder res = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (SPECIAL.contains(Character.toString(c))) {
                int start = i;
                while (i + 1 < str.length() && str.charAt(i + 1) == c) {
                    i++;
                }
                readSpecial(open, res, str.substring(start, i + 1), str, i);
            } else {
                if (c == '\\' && i + 1 < str.length()) {
                    c = str.charAt(++i);
                }
                res.append(SCREENING.getOrDefault(c, Character.toString(c)));
            }
        }
        return res.toString();
    }

    private static String getTagged(final String tag, final String str) {
        return "<" + tag + ">" + str + "</" + tag + ">";
    }

    private static String readHeader(final String str) {
        final int level = levelHeader(str);
        return getTagged("h" + level, parse(str.substring(level + 1)));
    }

    private static String readParagraph(final String str) {
        return getTagged("p", parse(str));
    }

    private static int levelHeader(final String str) {
        int level = 0;
        while (level < str.length() && str.charAt(level) == '#') {
            level++;
        }
        if (level < str.length() && Character.isWhitespace(str.charAt(level))) {
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
                while ((line = scanner.nextLine()).isBlank()) {
                    if (!scanner.hasNextLine()) {
                        break;
                    }
                }
                if (line.isBlank()) { // fixed
                    break;
                }
                final StringBuilder paragraph = new StringBuilder();
                do {
                    if (!paragraph.isEmpty()) {
                        paragraph.append(System.lineSeparator());
                    }
                    paragraph.append(line);
                } while (scanner.hasNextLine() && !(line = scanner.nextLine()).isBlank());
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
