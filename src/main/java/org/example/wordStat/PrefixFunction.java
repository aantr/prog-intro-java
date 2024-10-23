/*

from https://ru.algorithmica.org/cs/string-searching/prefix-function/

vector<int> prefix_function(string s) {
    int n = (int) s.size();
    vector<int> p(n, 0);
    for (int i = 1; i < n; i++) {
        // префикс функция точно не больше этого значения + 1
        int cur = p[i - 1];
        // уменьшаем cur значение, пока новый символ не сматчится
        while (s[i] != s[cur] && cur > 0)
            cur = p[cur - 1];
        // здесь либо s[i] == s[cur], либо cur == 0
        if (s[i] == s[cur])
            p[i] = cur + 1;
    }
    return p;
}

 */

public class PrefixFunction {
    static String str; // will be System.lineSeparator()
    static int[] prefixFunction;

    public PrefixFunction(String str) {
        PrefixFunction.str = str;
        prefixFunction = new int[str.length()];
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
