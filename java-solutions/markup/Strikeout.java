package markup;

import java.util.List;

public class Strikeout extends MarkupBase implements ParagraphBase {
    public Strikeout(List<ParagraphBase> elements) {
        super(elements);
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        buildMarkdown(stringBuilder, "~");
    }

    @Override
    public void toDocBook(StringBuilder stringBuilder) {
        buildDocBook(stringBuilder, "emphasis", "strikeout");
    }
}
