package markup;

import java.util.List;

public class MarkdownBuilder {
    private final List<? extends Markdown> elements;

    public MarkdownBuilder(List<? extends Markdown> elements) {
        this.elements = elements;
    }

    public void buildMarkdown(StringBuilder stringBuilder, String symbol) {
        stringBuilder.append(symbol);
        for (Markdown el : elements) {
            el.toMarkdown(stringBuilder);
        }
        stringBuilder.append(symbol);
    }
}
