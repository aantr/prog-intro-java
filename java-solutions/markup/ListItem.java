package markup;

import java.util.List;

public class ListItem extends DocBookBuilder implements DocBook {
    public ListItem(final List<ListItemBase> elements) {
        super(elements);
    }

    @Override
    public void toDocBook(final StringBuilder stringBuilder) {
        buildDocBook(stringBuilder, "listitem");
    }
}
