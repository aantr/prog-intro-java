/*
https://ru.wikipedia.org/wiki/Префикс-функция
*/

public class PrefixFunction {
    public String str;
    private int[] prefixFunction;

    public PrefixFunction(String str) {
        this.str = str;
    }

    private int decrease(char ch, int cur) {
        while (ch != str.charAt(cur) && cur > 0) {
            cur = prefixFunction[cur - 1];
        }
        if (ch == str.charAt(cur)) {
            return cur + 1;
        }
        return 0;
    }

    public PrefixFunction build() {
        prefixFunction = new int[str.length()];
        for (int i = 1; i < str.length(); i++) {
            prefixFunction[i] = decrease(str.charAt(i), prefixFunction[i - 1]);
        }
        return this;
    }

    public int get(int previousValue, char newChar) {
        if (previousValue == str.length()) {
            previousValue = prefixFunction[previousValue - 1];
        }
        return decrease(newChar, previousValue);
    }

}
