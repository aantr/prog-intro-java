package markup;

import java.util.List;

public class ListBase implements ListItemBase {

    private final String tag;
    private final List<ListItem> elements;

    public ListBase(String tag, List<ListItem> elements) {
        this.tag = tag;
        this.elements = elements;
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        // todo
    }

    @Override
    public void toDocBook(StringBuilder stringBuilder) {
        stringBuilder.append('<').append(tag).append('>');
        for (ListItem element : elements) {
            element.toDocBook(stringBuilder);
        }
        stringBuilder.append("</").append(tag).append('>');
    }
}
