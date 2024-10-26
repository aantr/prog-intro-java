package markup;

import java.util.List;

public class MarkupBase {
    private final List<? extends Element> elements;

    public MarkupBase(List<? extends Element> elements) {
        this.elements = elements;
    }

    public void buildMarkdown(StringBuilder stringBuilder, String symbol) {
        stringBuilder.append(symbol);
        for (Element el : elements) {
            el.toMarkdown(stringBuilder);
        }
        stringBuilder.append(symbol);
    }

    public void buildDocBook(StringBuilder stringBuilder, String tag, String role) {
        stringBuilder.append('<').append(tag).append(role != null ? String.format(" role='%s'", role) : "").append('>');
        for (Element el : elements) {
            el.toDocBook(stringBuilder);
        }
        stringBuilder.append("</").append(tag).append('>');
    }

    public void buildDocBook(StringBuilder stringBuilder, String tag) {
        buildDocBook(stringBuilder, tag, null);
    }
}
