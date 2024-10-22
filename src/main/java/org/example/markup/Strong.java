package markup;

import java.util.List;

public class Strong extends MarkupBase implements ParagraphBase {
    public Strong(List<ParagraphBase> elements) {
        super(elements, "__", "emphasis", "bold");
    }
}
