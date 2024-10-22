package markup;

import java.util.List;

public class Emphasis extends MarkupBase implements ParagraphBase {
    public Emphasis(List<ParagraphBase> elements) {
        super(elements, "*", "emphasis");
    }
}
