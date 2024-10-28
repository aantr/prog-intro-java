package markup;

import java.util.List;

public class OrderedList extends ListBase {
    public OrderedList(List<ListItem> elements) {
        super(elements);
    }

    @Override
    public void toDocBook(StringBuilder stringBuilder) {
        buildDocBook(stringBuilder, "orderedlist");
    }
}
