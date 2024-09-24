import java.util.Scanner;

public class Reverse {
    public static void main(String[] args) {
        int[] numbers = new int[30000];
        int current = 0;
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            numbers[current++] = Integer.MAX_VALUE;
            StringBuilder n = new StringBuilder();
//             /*

//            String line = sc.nextLine();
//            for (int i = 0; i < line.length(); i++) {
//                char ch = line.charAt(i);
//                if (Character.isDigit(ch) || ch == '-') {
//                    n.append(ch);
//                } else {
//                    if (!n.isEmpty()) {
//                        numbers[current++] = Integer.parseInt(String.valueOf(n));
//                        n = new StringBuilder();
//                    }
//                }
//            }
//            if (!n.isEmpty()) {
//                numbers[current++] = Integer.parseInt(String.valueOf(n));
//            }

//              */


            Scanner scanner = new Scanner(sc.nextLine());
            while (scanner.hasNext()) {
                numbers[current++] = scanner.nextInt();;
            }


        }

        for (int i = 0; i < current; i++) {
            int n = numbers[current - 1 - i];
            if (n == Integer.MAX_VALUE) {
                System.out.print("\n");
            } else {
                System.out.print(n + " ");
            }
        }
    }
}
