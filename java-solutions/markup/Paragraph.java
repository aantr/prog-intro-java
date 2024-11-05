package markup;

import java.util.List;

public class Paragraph extends MarkupBase implements ListItemBase, Markdown {
    public Paragraph(final List<ParagraphBase> elements) {
        super(elements);
    }

    @Override
    public void toMarkdown(final StringBuilder stringBuilder) {
        buildMarkdown(stringBuilder, "");
    }

    @Override
    public void toDocBook(final StringBuilder stringBuilder) {
        buildDocBook(stringBuilder, "para");
    }
}
