import java.io.IOException;
import java.io.InputStreamReader;

public class MyTest {
    public static void main(String[] args) {
//        System.setProperty("line.separator", args[0]);
        MyScanner scanner = new MyScanner(new InputStreamReader(System.in));
        while (true) {
            try {
                if (!scanner.hasNextLine()) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            while (!scanner.hasLineSeparator()) {
                try {
                    scanner.nextWord();
                } catch (IOException | ScannerException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.print("\n");
        }
    }
}
