public class SumLongPunctHex {

    private static long parse(String str) {
        if (str.toLowerCase().startsWith("0x")) {
            return Long.parseUnsignedLong(str, 2, str.length(), 16);
        } else {
            return Long.parseLong(str, 10);
        }
    }

    public static void main(String[] args) {
        long sum = 0;
        for (String arg : args) {
            int start = 0;
            for (int idx = 0; idx < arg.length(); idx++) {
                char ch = arg.charAt(idx);
                if (Character.isWhitespace(ch) ||
                        Character.getType(ch) == Character.START_PUNCTUATION ||
                        Character.getType(ch) == Character.END_PUNCTUATION) {
                    if (start != idx) {
                        sum += parse(arg.substring(start, idx));
                    }
                    start = idx + 1;
                }
            }
            if (arg.length() != start) {
                sum += parse(arg.substring(start));
            }
        }
        System.out.println(sum);
    }
}