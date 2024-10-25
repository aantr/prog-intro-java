package markup;

public class Text implements ParagraphBase {
    private final String text;

    public Text(String text) {
        this.text = text;
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        stringBuilder.append(text);
    }

    @Override
    public void toDocBook(StringBuilder stringBuilder) {
        stringBuilder.append(text);
    }
}
