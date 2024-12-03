package md2html;

public class Element {
    public String str, key;

    public Element(String str) {
        this.str = str;
    }

    public Element(String str, String key) {
        this(str);
        this.key = key;
    }
}
