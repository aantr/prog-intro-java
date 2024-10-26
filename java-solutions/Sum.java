
public class Sum {

    public static void main(String[] args) {
        int sum = 0;
        for (String i : args) {

            StringBuilder current = new StringBuilder();
            for (char j : i.toCharArray()) {
                if (Character.isDigit(j) || j == '-') {
                    current.append(j);
                } else {
                    if (!current.isEmpty()) {
                        sum += Integer.parseInt(current.toString());
                    }
                    current = new StringBuilder();
                }
            }
            if (!current.isEmpty()) {
                sum += Integer.parseInt(current.toString());
            }
        }

        System.out.println(sum);
    }
}

