import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

public class MyTest {
    public static void main(String[] args) throws ScannerException, IOException {
        MyScanner input = new MyScanner(new InputStreamReader(System.in), "\n");
        String sep = input.nextOrLineSeparator((char c) -> true);
        input.nextOrLineSeparator((char c) -> true);
        String test = input.nextOrLineSeparator((char c) -> true);
        System.err.println("\n\n");

        MyScanner scanner = new MyScanner(new StringReader(test), sep);
        try {
            StringBuilder ans = new StringBuilder();
            while (scanner.hasNextOrLineSeparator(MyScanner::isValidWord)) {
                String str;
                if ((str = scanner.nextOrLineSeparator(MyScanner::isValidWord)).isEmpty()) {
                    ans.append('\n');
                } else {
                    ans.append(str);
                }
            }
            System.out.print(ans);
        } catch (ScannerException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
