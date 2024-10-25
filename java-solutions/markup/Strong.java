package markup;

import java.util.List;

public class Strong extends MarkupBase implements ParagraphBase {
    public Strong(List<ParagraphBase> elements) {
        super(elements);
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        buildMarkdown(stringBuilder, "__");
    }

    @Override
    public void toDocBook(StringBuilder stringBuilder) {
        buildDocBook(stringBuilder, "emphasis", "bold");
    }
}
