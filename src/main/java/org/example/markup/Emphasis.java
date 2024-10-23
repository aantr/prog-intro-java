package markup;

import java.util.List;

public class Emphasis extends MarkupBase implements ParagraphBase {
    public Emphasis(List<ParagraphBase> elements) {
        super(elements);
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        buildMarkdown(stringBuilder, "*");
    }

    @Override
    public void toDocBook(StringBuilder stringBuilder) {
        buildDocBook(stringBuilder, "emphasis");
    }
}
