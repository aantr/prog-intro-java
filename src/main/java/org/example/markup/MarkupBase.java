package markup;

import java.util.List;

public class MarkupBase implements Element {
    private final List<? extends Element> elements;
    private final String markdownSymbol;
    private final String tag;
    private final String role;

    public MarkupBase(List<? extends Element> elements, String symbol) {
        this.elements = elements;
        this.markdownSymbol = symbol;
        this.tag = null;
        this.role = null;
    }

    public MarkupBase(List<? extends Element> elements, String symbol, String tag) {
        this.elements = elements;
        this.markdownSymbol = symbol;
        this.tag = tag;
        this.role = null;
    }

    public MarkupBase(List<? extends Element> elements, String symbol, String tag, String role) {
        this.elements = elements;
        this.markdownSymbol = symbol;
        this.tag = tag;
        this.role = role;
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        stringBuilder.append(markdownSymbol);
        for (Element el : elements) {
            el.toMarkdown(stringBuilder);
        }
        stringBuilder.append(markdownSymbol);
    }

    @Override
    public void toDocBook(StringBuilder stringBuilder) {
        String roleString = "";
        if (role != null) {
            roleString += " role='" + role + "'";
        }
        stringBuilder.append('<').append(tag).append(roleString).append('>');
        for (Element el : elements) {
            el.toDocBook(stringBuilder);
        }
        stringBuilder.append("</").append(tag).append('>');
    }
}
