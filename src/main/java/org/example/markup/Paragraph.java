package markup;

import java.util.List;

public class Paragraph extends MarkupBase implements ListItemBase {
    public Paragraph(List<ParagraphBase> elements) {
        super(elements, "", "para");
    }
}
