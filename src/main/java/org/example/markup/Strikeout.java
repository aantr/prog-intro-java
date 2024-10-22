package markup;

import java.util.List;

public class Strikeout extends MarkupBase implements ParagraphBase {
    public Strikeout(List<ParagraphBase> elements) {
        super(elements, "~", "emphasis", "strikeout");
    }
}
