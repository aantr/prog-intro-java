package markup;

public interface Element {
    public void toMarkdown(StringBuilder stringBuilder);

    public void toDocBook(StringBuilder stringBuilder);
}
