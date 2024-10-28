package markup;

import java.util.List;

public class UnorderedList extends ListBase {
    public UnorderedList(List<ListItem> elements) {
        super(elements);
    }

    @Override
    public void toDocBook(StringBuilder stringBuilder) {
        buildDocBook(stringBuilder, "itemizedlist");
    }
}
