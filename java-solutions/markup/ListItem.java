package markup;

import java.util.List;

public class ListItem extends MarkupBase implements Element {
    public ListItem(List<ListItemBase> elements) {
        super(elements);
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {

    }

    @Override
    public void toDocBook(StringBuilder stringBuilder) {
        buildDocBook(stringBuilder, "listitem");
    }
}
