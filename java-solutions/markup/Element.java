package markup;

public interface Element {
    void toMarkdown(StringBuilder stringBuilder);

    void toDocBook(StringBuilder stringBuilder);
}
