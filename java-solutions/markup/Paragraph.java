package markup;

import java.util.List;

public class Paragraph extends MarkupBase implements ListItemBase {
    public Paragraph(List<ParagraphBase> elements) {
        super(elements);
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        buildMarkdown(stringBuilder, "");
    }

    @Override
    public void toDocBook(StringBuilder stringBuilder) {
        buildDocBook(stringBuilder, "para");
    }
}
