package markup;

import java.util.List;

public class Strong extends MarkupBase implements ParagraphBase {
    public Strong(final List<ParagraphBase> elements) {
        super(elements);
    }

    @Override
    public void toMarkdown(final StringBuilder stringBuilder) {
        buildMarkdown(stringBuilder, "__");
    }

    @Override
    public void toDocBook(final StringBuilder stringBuilder) {
        buildDocBook(stringBuilder, "emphasis", "bold");
    }
}
