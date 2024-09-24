import java.util.HexFormat;

public class SumLongPunctHex {

    public static void main(String[] args) {
        long sum = 0;
        for (String i : args) {

            StringBuilder current = new StringBuilder();
            for (char j : i.toCharArray()) {
                if (Character.isDigit(j) || Character.isAlphabetic(j) || j == '-') {
                    current.append(j);
                } else {
                    if (!current.isEmpty()) {
                        if (current.length() >= 2 && current.charAt(0) == '0' && Character.toLowerCase(current.charAt(1)) == 'x') {
                            sum += HexFormat.fromHexDigitsToLong(current.substring(2));
                        } else {
                            sum += Long.parseLong(current.toString());
                        }
                        current = new StringBuilder();
                    }
                }
            }
            if (!current.isEmpty()) {
                if (current.length() >= 2 && current.charAt(0) == '0' && current.charAt(1) == 'x') {
                    sum += HexFormat.fromHexDigitsToLong(current.substring(2));
                } else {
                    sum += Long.parseLong(current.toString());
                }
            }
        }

        System.out.println(sum);
    }
}