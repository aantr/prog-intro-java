package markup;

import java.util.List;

public class UnorderedList extends ListBase {
    public UnorderedList(final List<ListItem> elements) {
        super(elements);
    }

    @Override
    public void toDocBook(final StringBuilder stringBuilder) {
        buildDocBook(stringBuilder, "itemizedlist");
    }
}
