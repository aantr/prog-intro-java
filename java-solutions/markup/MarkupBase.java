package markup;

import java.util.List;

public class MarkupBase {
    private final List<? extends Element> elements;

    public MarkupBase(final List<? extends Element> elements) {
        this.elements = elements;
    }

    public void buildMarkdown(final StringBuilder stringBuilder, final String symbol) {
        stringBuilder.append(symbol);
        for (final Element el : elements) {
            el.toMarkdown(stringBuilder);
        }
        stringBuilder.append(symbol);
    }

    public void buildDocBook(final StringBuilder stringBuilder, final String tag, final String role) {
        stringBuilder.append('<').append(tag).append(role != null ? String.format(" role='%s'", role) : "").append('>');
        for (final Element el : elements) {
            el.toDocBook(stringBuilder);
        }
        stringBuilder.append("</").append(tag).append('>');
    }

    public void buildDocBook(final StringBuilder stringBuilder, final String tag) {
        buildDocBook(stringBuilder, tag, null);
    }
}
