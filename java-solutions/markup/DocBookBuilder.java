package markup;

import java.util.List;

public class DocBookBuilder {
    private final List<? extends DocBook> elements;

    public DocBookBuilder(final List<? extends DocBook> elements) {
        this.elements = elements;
    }

    public void buildDocBook(final StringBuilder stringBuilder, final String tag, final String role) {
        stringBuilder.append('<').append(tag).append(role != null ? String.format(" role='%s'", role) : "").append('>');
        for (final DocBook el : elements) {
            el.toDocBook(stringBuilder);
        }
        stringBuilder.append("</").append(tag).append('>');
    }

    public void buildDocBook(final StringBuilder stringBuilder, final String tag) {
        buildDocBook(stringBuilder, tag, null);
    }
}
