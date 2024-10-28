package markup;

import java.util.List;

public class DocBookBuilder {
    private final List<? extends DocBook> elements;

    public DocBookBuilder(List<? extends DocBook> elements) {
        this.elements = elements;
    }

    public void buildDocBook(StringBuilder stringBuilder, String tag, String role) {
        stringBuilder.append('<').append(tag).append(role != null ? String.format(" role='%s'", role) : "").append('>');
        for (DocBook el : elements) {
            el.toDocBook(stringBuilder);
        }
        stringBuilder.append("</").append(tag).append('>');
    }

    public void buildDocBook(StringBuilder stringBuilder, String tag) {
        buildDocBook(stringBuilder, tag, null);
    }
}
