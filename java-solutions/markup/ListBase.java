package markup;

import java.util.List;

public abstract class ListBase extends DocBookBuilder implements ListItemBase {
    public ListBase(List<ListItem> elements) {
        super(elements);
    }
}
