package markup;

import java.util.List;

public class ListItem extends DocBookBuilder implements DocBook {
    public ListItem(List<ListItemBase> elements) {
        super(elements);
    }

    @Override
    public void toDocBook(StringBuilder stringBuilder) {
        buildDocBook(stringBuilder, "listitem");
    }
}
